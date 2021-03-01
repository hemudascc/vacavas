
package net.common.service;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.adnetwork.callback.service.AdnetworkCallbackService;
import net.factory.AdnetworkChurnDataFactory;
import net.jpa.repository.JPAAdnetworkToken;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.CallbackDump;
import net.persist.bean.IPPool;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.util.MConstants;
import net.util.MData;
import net.util.ReportGenerateException;

@Service("liveReportFactoryService")
public class LiveReportFactoryService {
	
	private static final Logger logger = Logger.getLogger(LiveReportFactoryService.class);
	@Autowired
	private IpCheckService ipCheckService;

	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private RedisCacheService redisCacheService;

	@Autowired
	private AdnetworkCallbackService adnetworkCallbackService;

	@Autowired
	private SubscriberRegService subscriberRegService;
	
	@Autowired
	private JPAAdnetworkToken jpaAdnetworkToken;
	
	@Autowired
	private CommonService commonService;

	public LiveReport process(LiveReport liveReport) throws Exception {
	
		AdnetworkToken adnetworkToken = null;
		SubscriberReg subscriberReg = null;
		
		if(liveReport.getAction()==null){
			logger.warn("Live Report action not set,not process report generate");
			return liveReport;
		}
		
		net.persist.bean.Service subService=MData.mapServiceIdToService.get(liveReport.getServiceId());
		if(subService!=null){
			liveReport.setProductId(subService.getProductId());//Add product id feature 
		}
		
		
		logger.debug("process:::::::::::::LiveReportFactoryService::::::::::::::::::::::::: "+liveReport);
		try{
		if (liveReport.getAction().equalsIgnoreCase(MConstants.ACT)) {
			logger.debug("process:::::::::::::LiveReportFactoryServiceact:::::::::::::::::::::::::");
			if (!liveReport.isDuplicateRequest()) {
				logger.debug("process:::::::::::::LiveReportFactoryService not dumplicate:::::::::::::::::::::::::");
//				adnetworkToken = adnetworkCallbackService.sendAdnetworkCallBack(liveReport.getAction(),
//						liveReport.getMsisdn(), liveReport.getTokenId(), liveReport.getOperatorId(),
//						liveReport.getAmount());
				adnetworkToken = adnetworkCallbackService.sendAdnetworkCallBack(liveReport);
				
				logger.debug("process:::::::::::::LiveReportFactoryService :::::::::::::::::::::::::"+adnetworkToken);
			
				if(liveReport.getLcId()==null){
					liveReport.setLcId("0");
				}				
				subscriberReg = subscriberRegService.findOrCreateSubscriberByAct(liveReport.getMsisdn(),
						adnetworkToken,
						liveReport);
				liveReport.setSubId(subscriberReg.getSubscriberId());
				liveReport.setLastActivationTime(new Timestamp(System.currentTimeMillis()));
//				daoService.createChurnData(AdnetworkChurnDataFactory.createAdnetworkChurnDataForActivation(
//						liveReport,adnetworkToken));
				
				Integer adnetworkId=MConstants.DEFAULT_ADNETWORK_ID;
				if(adnetworkToken!=null){
					adnetworkId=adnetworkToken.getAdnetworkId();
				}
				
				redisCacheService.putConversionCapping(adnetworkId,
						liveReport.getOperatorId(),liveReport.getProductId(),liveReport.getReportDate());
				logger.debug("process:::::::::::::LiveReportFactoryService :::::::::::::::::::::::::"+subscriberReg);
			}	
			//liveReport.setConversionCount(1);
			setLiveReportVariable(adnetworkToken, liveReport);
			
		} else if (liveReport.getAction().equalsIgnoreCase(MConstants.GRACE)) {
			
			subscriberReg = subscriberRegService.findOrCreateSubscriberByAct(liveReport.getMsisdn(),
					adnetworkToken,
					liveReport);
			
			if (!liveReport.isDuplicateRequest()) {
//				adnetworkToken = adnetworkCallbackService.sendAdnetworkCallBack(liveReport.getAction(),
//						liveReport.getMsisdn(), liveReport.getTokenId(), liveReport.getOperatorId(),liveReport.getAmount());
				adnetworkToken = adnetworkCallbackService.sendAdnetworkCallBack(liveReport);
			}

			//liveReport.setGraceConversionCount(1);
			setLiveReportVariable(adnetworkToken, liveReport);
		} else if (liveReport.getAction().equalsIgnoreCase(MConstants.RENEW)) {
			logger.debug("process:::::::::::::LiveReportFactoryService :::::::::::::::::::::::::renew");
			if (!liveReport.isDuplicateRequest()) {
				//SubscriberReg subscriberRegRenewal = daoService.searchSubscriber(liveReport.getMsisdn());			
				subscriberReg = subscriberRegService.updateRenewal(liveReport.getMsisdn(), liveReport);
				if (subscriberReg != null && subscriberReg.getStatus() == 1) {
					liveReport.setSubId(subscriberReg.getSubscriberId());
					adnetworkToken = daoService.findAdnetworkTokenById(subscriberReg.getTokenId());
				}			
			}
			//liveReport.setRenewalCount(1);
		} else if (liveReport.getAction().equalsIgnoreCase(MConstants.DCT)) {
			boolean success = false;
			logger.debug(":::::::::::::LiveReportFactoryServiceDCT:::::::::::::::::::::::::");
			if (!liveReport.isDuplicateRequest()) {
				logger.debug(":::::::::::::LiveReportFactoryServiceDCT duplicate:::::::::::::::::::::::::"+liveReport.getAction());
				subscriberReg = subscriberRegService.updateDeactivation(liveReport.getMsisdn(),
						liveReport.getProductId());
				
				if (subscriberReg != null) {
					adnetworkToken = daoService.findAdnetworkTokenById(subscriberReg.getTokenId());
					liveReport.setChurnDctCount(subscriberReg.isChurn() ? 1 : 0);
					if (subscriberReg.isChurn()) {
						success = adnetworkCallbackService.sendChurnDCTAdnetworkCallBack(liveReport.getOperatorId(),
								adnetworkToken);	
						
						daoService.createChurnData(AdnetworkChurnDataFactory.createAdnetworkChurnDataForChurnDeactivation(
								liveReport,
								 adnetworkToken));
					}
					if (success) {
						liveReport.setDctSendCount(1);
					}
				}
			}
			//liveReport.setDctCount(1);
			setLiveReportVariable(adnetworkToken, liveReport);
		}

		int campaignId = MConstants.DEFAULT_ADNETWORK_CAMPAIGN_ID;
		if (adnetworkToken != null) {
			campaignId = adnetworkToken.getCampaignId();
			liveReport.setToken(adnetworkToken.getToken());	
			liveReport.setCircleId(adnetworkToken.getCircleId()!=null?adnetworkToken.getCircleId():0);
			if(liveReport.getAction().equalsIgnoreCase(MConstants.ACT)){
//			commonService.putChargeAndCappingValueInCache(liveReport.getMsisdn(),
//					 liveReport.getOperatorId(),campaignId,liveReport.getCircleId());
			}
		}		
		liveReport.setAdnetworkCampaignId(campaignId);		
		liveReport.setDuplicateValues();
		daoService.generateLiveReport(liveReport);
	}catch(Exception ex){
		throw new ReportGenerateException(liveReport,ex.getMessage());
	}
		createCallbackDump(liveReport);
		return liveReport;
	}

	private void setLiveReportVariable(AdnetworkToken adnetworkToken, LiveReport liveReport) {

		IPPool ipPool = null;
		if (adnetworkToken != null) {
			ipPool = ipCheckService.findOperatorByIp(adnetworkToken.getSource());
		}

		if (ipPool != null && liveReport.getOperatorId() == ipPool.getOpId()) {
			liveReport.setMismatchOperatorConversionCount(1);
		}
		if (adnetworkToken != null) {
			// liveReport.setPubId(adnetworkToken.getPubId());
			liveReport.setSendConversionCount(
					liveReport.getConversionCount() > 0 && adnetworkToken.getConversionSendToAdntework() ? 1 : 0);
//			liveReport.setGraceSendConversionCount(
//					liveReport.getGraceConversionCount() > 0 && adnetworkToken.getConversionSendToAdntework() ? 1 : 0);
		}
	}

	public LiveReport createLiveReportFromAdnetworkToken(VWServiceCampaignDetail vwServiceCampaignDetail,
			AdnetworkToken adnetworkToken) {

		int campaignId = MConstants.DEFAULT_ADNETWORK_CAMPAIGN_ID;
		int opId = MConstants.DEFAULT_OP_ID;
		int serviceId=0;
		int productId=0;
		if (vwServiceCampaignDetail != null) {
			campaignId = vwServiceCampaignDetail.getCampaignId();
			// campaignName = campaignDetails.getCampaignName();
			opId = vwServiceCampaignDetail.getOpId();
			serviceId=vwServiceCampaignDetail.getServiceId();
			productId=vwServiceCampaignDetail.getProductId();
		}

		LiveReport liveReport = new LiveReport(opId, 
				new Timestamp(System.currentTimeMillis()), campaignId,serviceId,productId);
		liveReport.setType(adnetworkToken.getType());
		liveReport.setClickCount(1);
		liveReport.setLastClickTime(new Timestamp(System.currentTimeMillis()));
		liveReport.setCircleId(CommonService.findCircleIdByMsisdnSeries(adnetworkToken.getMsisdn()));
		//Date date=new Date();
		//SimpleDateFormat smf=new SimpleDateFormat("HH");
		//liveReport.setActionHours(Integer.parseInt(smf.format(date).toString()));
		// liveReport.setPubId(adnetworkToken.getPubId());
		setClickCountType(liveReport, adnetworkToken.getAction());
		
		daoService.generateLiveReport(liveReport);
		return liveReport;

	}
	
	public LiveReport createLiveReportForManualConversion(Integer campaignId,Integer opId,
			AdnetworkToken adnetworkToken,String cronType) {
		
		LiveReport liveReport = new LiveReport(opId, 
				new Timestamp(System.currentTimeMillis()), campaignId,0,0);
		liveReport.setType(adnetworkToken.getType());
		
			
		if(cronType==MConstants.MANUAL_CRON){
		liveReport.setSendConversionCount(1);
		liveReport.setSendManualConversionCount(1);	
		}
		else if(cronType==MConstants.AUTOSENT_CRON) {
			liveReport.setSendAutoConversionCount(1);
		}
		
		liveReport.setCircleId(adnetworkToken.getCircleId());
		liveReport.setType(adnetworkToken.getType());
		daoService.generateLiveReport(liveReport);
		return liveReport;
	}

	
	public LiveReport createLiveReportForAutoConversion(Integer campaignId,Integer opId,
			AdnetworkToken adnetworkToken) {
		LiveReport liveReport = new LiveReport(opId, new Timestamp(System.currentTimeMillis()), campaignId,0,0);
		liveReport.setType(adnetworkToken.getType());
		liveReport.setSendConversionCount(1);	
		liveReport.setSendAutoConversionCount(1);	
		liveReport.setCircleId(adnetworkToken.getCircleId());
		liveReport.setType(adnetworkToken.getType());
		daoService.generateLiveReport(liveReport);
		return liveReport;
	}

	private void setClickCountType(LiveReport liveReport, String action) {

		if(action==null){
			return ;
		}
		
		switch (action) {
		case MConstants.REDIRECT_TO_WASTE_URL:					
		case MConstants.REDIRECT_TO_WASTE_URL_COMAPIGN_BLOCK:
		case MConstants.REDIRECT_TO_WASTE_URL_BLOCK_CIRCLE:
		case MConstants.REDIRECT_TO_WASTE_URL_DUE_TO_CHARGING_TYPE_BLOCK:
		case MConstants.REDIRECT_TO_WASTE_URL_CONVERSION_CAPPING:	
		case MConstants.REDIRECT_TO_WASTE_URL_IP_NOT_MATCHING:		
		case MConstants.REDIRECT_TO_WASTE_URL_PUB_ID_BLOCK:	
			liveReport.setReverseClickCount(1);
			break;
//			liveReport.setBlockClickCount(1);
//			break;
		case MConstants.PIN_SEND:	
			liveReport.setPinSendCount(1);//ReverseClickCount(1);
			break;
		case MConstants.PIN_VALIDATE:	
			liveReport.setPinValidationCount(1);
			break;
		case MConstants.REDIRECT_TO_ERROR:{
			liveReport.setReverseClickCount(1);
			break;
		}		
		case MConstants.REDIRECT_TO_WASTE_URL_DUPLICATE_MISISDN:
			liveReport.setDuplicateClickCount(1);
			liveReport.setReverseClickCount(1);
			break;
		case MConstants.REDIRECT_TO_WASTE_URL_BLOCKSERIES:
		case MConstants.REDIRECT_TO_WASTE_URL_DCT_BLOCK:	
		case MConstants.REDIRECT_TO_WASTE_URL_WRONG_MSISDN_FORMAT:	
			liveReport.setBlockClickCount(1);
			break;
		case MConstants.REDIRECT_TO_WASTE_URL_ALREADY_SUBSCRIBED:
			liveReport.setAlreadySubscribedCount(1);
			break;
		case MConstants.REDIRECT_TO_HOME_PAGE_COMAPIGN_NOT_FOUND:
			break;
		case MConstants.REDIRECT_TO_CG:
			liveReport.setValidClickCount(1);
			break;
		}
	}

	private void createCallbackDump(LiveReport liveReport){
		CallbackDump callbackDump=null;
		try{
			
	    callbackDump=new CallbackDump(true,liveReport.getReportDate());
		callbackDump.setMsisdn(liveReport.getMsisdn());
		callbackDump.setAction(liveReport.getAction());
		callbackDump.setAmount(liveReport.getAmount());
		callbackDump.setOperatorId(liveReport.getOperatorId());
		callbackDump.setServiceId(liveReport.getServiceId());
		callbackDump.setProductId(liveReport.getProductId());
		callbackDump.setCampaignId(liveReport.getAdnetworkCampaignId());
		callbackDump.setToken(liveReport.getToken());
		callbackDump.setTokenId(liveReport.getTokenId());
		callbackDump.setSendToAdnetwork(liveReport.getSendConversionCount()>0?true:false);
		AdnetworkToken adnetworkToken=jpaAdnetworkToken.findEnableAdnetworkToken(liveReport.getTokenId());
		callbackDump.setClickId(adnetworkToken.getToken());
		
		}catch(Exception ex){
			logger.error("createCallbackDump ",ex);
		}finally{
			daoService.saveObject(callbackDump);
		}
		
		
	}
}
