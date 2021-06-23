package net.mycomp.timwe;

import java.sql.Timestamp;
import java.util.Objects;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.persist.bean.LiveReport;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MConstants;
import net.util.MData;

public class JMSTimweNotificationListener implements MessageListener{
	
	private static final Logger logger = Logger.getLogger(JMSTimweNotificationListener.class);
	@Autowired
	private IDaoService daoService;
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;
	@Autowired
	private RedisCacheService redisCacheService;
	@Autowired
	private TimweApiService timweApiService;

	@Override
	public void onMessage(Message m) {
		TimweNotification timweNotification=null;
		LiveReport liveReport = null;
		boolean update = false;
		long time = System.currentTimeMillis();
		CGToken cgToken=new CGToken("");
		String action=null;
		TimweServiceConfig timweServiceConfig = null;
		try {
			ObjectMessage objectMessage = (ObjectMessage) m;
			timweNotification = (TimweNotification) objectMessage.getObject();
			timweNotification.setDefaultVariables();
			logger.debug("processing TimweNotification::::: "+timweNotification);
			cgToken=new CGToken(Objects.toString(redisCacheService.getObjectCacheValue(TimweConstant.TIMWE_TOKEN_MSISDN_CACHE_PREFIX+timweNotification.getMsisdn())));
			if(cgToken.getCampaignId()<0) {
				cgToken = new CGToken("-1c-1c23");
			}
			VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			timweServiceConfig = TimweConstant.mapServiceIdToTimweServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			liveReport = new LiveReport(timweServiceConfig.getOperatorId(), new Timestamp(System.currentTimeMillis()),
					cgToken.getCampaignId(), timweServiceConfig.getServiceId(), timweServiceConfig.getProductId());
			liveReport.setTokenId(cgToken.getTokenId());
			liveReport.setToken(cgToken.getCGToken());
			action = TimweUtill.findAction(timweNotification);
			logger.debug("ACTION::::::::"+action);
			if(MConstants.ACT.equals(action)) {
				liveReport.setMsisdn(timweNotification.getMsisdn());
				liveReport.setAction(MConstants.ACT);
				liveReport.setAmount(TimweUtill.findAmmountByPriceId(timweNotification.getPricepointId()));
				liveReport.setConversionCount(1);
			}else if(MConstants.GRACE.equals(action)) {
				liveReport.setMsisdn(timweNotification.getMsisdn());
				liveReport.setAction(MConstants.GRACE);
				liveReport.setGraceConversionCount(1);
			} else if(MConstants.RENEW.equals(action)) {
				liveReport.setMsisdn(timweNotification.getMsisdn());
				liveReport.setAction(MConstants.RENEW);
				liveReport.setRenewalAmount(TimweUtill.findAmmountByPriceId(timweNotification.getPricepointId()));
				liveReport.setRenewalCount(1);
			}else if(MConstants.DCT.equals(action)){
				liveReport.setMsisdn(timweNotification.getMsisdn());
				liveReport.setAction(MConstants.DCT);
				liveReport.setDctCount(1);
			}		
		} catch (Exception e) {
			logger.error("error while processing TimweNotification"+timweNotification,e);
		}finally {
				try {
					if(Objects.nonNull(liveReport.getAction())) {
						liveReportFactoryService.process(liveReport);
						if(MConstants.ACT.equals(liveReport.getAction())) {
							timweApiService.mtPush(timweNotification.getMsisdn());
						}
					}
					timweNotification.setActon(liveReport.getAction());
					timweNotification.setSendToAdnetwork(liveReport.getSendConversionCount()>0?true:false);
				} catch (Exception e) {
					logger.error(" fianlly liveReport:: " + liveReport+ ", : timweNotification:: "+ timweNotification);
					logger.error("onMessage::::::::::finally " ,e);
				}finally {
					update = daoService.saveObject(timweNotification);
				}
			logger.info("onMessage::::::::::::::::: :: update::live report "
					+ update + ", total time:: "
					+ (System.currentTimeMillis() - time));
		}
	}
}
