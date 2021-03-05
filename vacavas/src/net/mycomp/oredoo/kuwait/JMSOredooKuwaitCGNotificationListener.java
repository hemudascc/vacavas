package net.mycomp.oredoo.kuwait;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.jpa.repository.JPAOredooCGCallback;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class JMSOredooKuwaitCGNotificationListener implements MessageListener{

	private static final Logger logger = Logger.getLogger(JMSOredooKuwaitCGNotificationListener.class);

	@Value("${oredoo.kuwait.sub.msg.template}")
	private String subMsgTemplate;

	@Value("${oredoo.kuwait.unsub.msg.template}")
	private String unsubMsgTemplate;

	@Autowired
	private LiveReportFactoryService liveReportFactoryService;

	@Autowired
	private IDaoService daoService;

	@Autowired
	private JPAOredooCGCallback jpaOredooCGCallback;

	@Autowired
	private JPASubscriberReg jpaSubscriberReg; 

	@Autowired
	private RedisCacheService redisCacheService;

	@Override
	public void onMessage(Message m) {

		OredooKuwaitCGNotification oredooKuwaitCGNotification=null;
		LiveReport liveReport=null;
		String action=null;
		String msisdn=null;
		try{
			ObjectMessage objectMessage = (ObjectMessage) m;
			oredooKuwaitCGNotification=(OredooKuwaitCGNotification)objectMessage.getObject();

			msisdn=oredooKuwaitCGNotification.getMsisdn();
			OredooKuwaitServiceConfig oredooKuwaitServiceConfig=
					OredoKuwaitConstant. 
					findOredooKuwaitServiceConfigByPlanId(oredooKuwaitCGNotification.getComvivaServiceId()
							+oredooKuwaitCGNotification.getRequestPlan());

			Integer token=(Integer)redisCacheService.getObjectCacheValue(OredoKuwaitConstant.
					CACHE_PREFIX_TRANSID+
					oredooKuwaitCGNotification.getMsisdn());


			if(token==null){

				List<OredooKuwaitCGCallback> list= jpaOredooCGCallback.
						findOredooKuwaitCGCallbackByMsisdn(msisdn,false);
				if(list!=null&&list.size()>0){
					OredooKuwaitCGCallback oredooKuwaitCGCallback= list.get(0);
					oredooKuwaitCGCallback.setUsed(true);
					jpaOredooCGCallback.save(oredooKuwaitCGCallback);
					token=MUtility.toInt(oredooKuwaitCGCallback.getTransId(),0);
				}
			}


			OredooKuwaitCGToken cgToken=new OredooKuwaitCGToken(
					Objects.toString(token));

			logger.info(msisdn+" cgToken:: "+cgToken);

			AdnetworkToken adnetworkToken=daoService.findAdnetworkTokenById(cgToken.getTokenId());
			logger.info(msisdn+", adnetworkToken:: "+adnetworkToken);
			int campaignId=-1;
			if(adnetworkToken!=null){
				campaignId=adnetworkToken.getCampaignId();
			}

			VWServiceCampaignDetail vwServiceCampaignDetail=
					MData.mapCamapignIdToVWServiceCampaignDetail.get(campaignId);
			logger.info(msisdn+", vwServiceCampaignDetail:: "+vwServiceCampaignDetail);
			liveReport=new LiveReport(MConstants.OREDOO_KUWAIT_OPERATOR_ID,
					new Timestamp(System.currentTimeMillis()),campaignId,0,0);

			if(vwServiceCampaignDetail!=null){
				oredooKuwaitCGNotification.setCampaignId(campaignId);
				liveReport.setTokenId(cgToken.getTokenId());	
				campaignId=vwServiceCampaignDetail.getCampaignId();		
				oredooKuwaitCGNotification.setTokenId(cgToken.getTokenId());

			}else{
				campaignId=-1;
			}

			liveReport.setAdnetworkCampaignId(campaignId);		 
			liveReport.setServiceId(oredooKuwaitServiceConfig.getServiceId());
			liveReport.setProductId(oredooKuwaitServiceConfig.getCcProductId());//product id==service id		 

			action=OredoKuwaitConstant.findAction(oredooKuwaitCGNotification.getOperationId());
			liveReport.setMsisdn(oredooKuwaitCGNotification.getMsisdn());
			logger.info(msisdn+", action:: "+action);
			liveReport.setMode(OredooKuwaitSubcriptionMode.getMode(oredooKuwaitCGNotification.getBearerId()));
			if(action.equalsIgnoreCase(MConstants.ACT)){
				liveReport.setAmount(oredooKuwaitCGNotification.getChargeAmount());
				liveReport.setConversionCount(1);
				liveReport.setNoOfDays(oredooKuwaitCGNotification.getValidityDays());

			}else if(action.equalsIgnoreCase(MConstants.DCT)){
				liveReport.setDctCount(1);			
				SubscriberReg subscriberReg=
						jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, liveReport.getProductId()); 
			}else if(action.equalsIgnoreCase(MConstants.RENEW)){			
				liveReport.setRenewalCount(1);
				liveReport.setNoOfDays(oredooKuwaitCGNotification.getValidityDays());
				liveReport.setRenewalAmount(oredooKuwaitCGNotification.getChargeAmount());
			}else if(action.equalsIgnoreCase(MConstants.GRACE)){
				liveReport.setGraceConversionCount(1);
				liveReport.setAction(MConstants.ACT);
			}
		}catch(Exception ex){
			logger.error(msisdn+" onMessage::::: "+oredooKuwaitCGNotification,ex);
		}finally{
			try {
				liveReport.setAction(action);
				oredooKuwaitCGNotification.setActionKey(liveReport.getAction());
				liveReportFactoryService.process(liveReport);
				oredooKuwaitCGNotification.setSendToAdnetwork(
						liveReport.getSendConversionCount() > 0 ? true : false);

			} catch (Exception e) {	
				logger.error(msisdn+" finally::: ",e);
			}
			daoService.saveObject(oredooKuwaitCGNotification);
		}
	}
}

