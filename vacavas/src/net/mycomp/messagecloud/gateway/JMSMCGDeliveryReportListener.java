package net.mycomp.messagecloud.gateway;

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
import net.jpa.repository.JPAMCGMTMessage;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.LiveReport;
import net.persist.bean.Service;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

public class JMSMCGDeliveryReportListener implements MessageListener {

	private static final Logger logger = Logger
			.getLogger(JMSMCGDeliveryReportListener.class);

	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;

	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;

	@Autowired
	private JPAMCGMTMessage jpaMCGMTMessage;
	
	@Override
	public void onMessage(Message m) {

		MCGDeliveryReport mcgDeliveryReport = null;
		LiveReport liveReport = null;
		boolean update = false;
		long time = System.currentTimeMillis();
		 MCGServiceConfig mcgServiceConfig=null;
		String token=null;
		try {

			ObjectMessage objectMessage = (ObjectMessage) m;
			mcgDeliveryReport = (MCGDeliveryReport) objectMessage
					.getObject();
			//logger.debug("messageId="+mcgDeliveryReport.getMessageId());
			MCGCGToken mcgCGToken=new MCGCGToken(mcgDeliveryReport.getMessageId(),
					mcgDeliveryReport.getMessageId());
			
		//	logger.debug("mtid"+mcgCGToken.getMtId());
			
			MCGMTMessage mcgMTMessage=jpaMCGMTMessage.findMCGMTMessageById(mcgCGToken.getMtId());
			 if(mcgMTMessage!=null){
				 token=mcgMTMessage.getToken();
			  }
			 if(token==null){
			   token=Objects.toString(redisCacheService.getObjectCacheValue(MCGConstant.MSG_CLOUD_CACHE_PREFIX+
		   				mcgDeliveryReport.getNumber()));				   
			 }
		CGToken cgToken=new CGToken(token);
		//mcgServiceConfig=MCGConstant.mapServiceIdToMCGServiceConfig.get(mcgDeliveryReport.getServiceId());
		
		if(mcgServiceConfig==null){
				VWServiceCampaignDetail vwServiceCampaignDetail = 
						MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			  if(vwServiceCampaignDetail!=null){
				mcgServiceConfig = MCGConstant.mapServiceIdToMCGServiceConfig.get(vwServiceCampaignDetail.getServiceId());
		    }else{
				    mcgServiceConfig=MCGConstant.mapIdToMCGServiceConfig.get(7);
			}
		}   
		   liveReport=new LiveReport(mcgServiceConfig.getOperatorId(),
				      new Timestamp(System.currentTimeMillis()), null,mcgServiceConfig.getServiceId(),0); 
			liveReport.setTokenId(cgToken.getTokenId());
		    liveReport.setToken(cgToken.getCGToken());			
		    liveReport.setMsisdn(mcgDeliveryReport.getNumber());
			liveReport.setCircleId(0);
			
			mcgDeliveryReport.setToken(token);
			
			MCGDeliveryReportEnum mcgDeliveryReportEnum=
					MCGDeliveryReportEnum.getMCGDeliveryStatusEnum(mcgDeliveryReport.getReport());
			
			logger.debug("mcgDeliveryReport::::::: "+mcgDeliveryReport);
			
			if(mcgDeliveryReportEnum.report.equalsIgnoreCase(MCGDeliveryReportEnum.DELIVERED.report)){
				logger.debug("mcg--DELIVERED");
				logger.debug("action"+mcgMTMessage.getAction());
				if(mcgMTMessage.getAction().equalsIgnoreCase(MConstants.ACT)){
					logger.debug("mcg--ACT");
					 liveReport.setAction(MConstants.ACT);
					 liveReport.setAmount(Double.valueOf(mcgMTMessage.getValue()));
					 liveReport.setConversionCount(1);
					 liveReport.setNoOfDays(7);					
					 liveReport.setParam1(mcgMTMessage.getNetwork());
					 liveReport.setParam2(""+mcgMTMessage.getValue());
				}else if(mcgMTMessage.getAction().equalsIgnoreCase(MConstants.RENEW)){
					logger.debug("mcg--RENEW");
					liveReport.setAction(MConstants.RENEW);
					liveReport.setRenewalCount(1);
					liveReport.setRenewalAmount(Double.valueOf(mcgMTMessage.getValue()));
					liveReport.setNoOfDays(7);		
					liveReport.setParam1(mcgMTMessage.getNetwork());
					Service service=MData.mapServiceIdToService.get(mcgServiceConfig.getServiceId());
					SubscriberReg subscriberReg=jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(mcgDeliveryReport.getNumber()
							, service.getProductId());
					liveReport.setParam2(String.valueOf(mcgMTMessage.getValue()
							+MUtility.toDouble(subscriberReg.getParam2(),0)));
				}
			}else if(mcgDeliveryReportEnum.report
					.equalsIgnoreCase(MCGDeliveryReportEnum.NO_CREDIT.report)){
				logger.info("No CREDIT need to retry billing for low price point");
			}	
		} catch (Exception ex) {
			logger.error("onMessage::::: ", ex);
		} finally {
			try {
				if(liveReport!=null&&liveReport.getAction()!=null){
					liveReportFactoryService.process(liveReport);
				}
			} catch (Exception ex) {
				logger.error(" fianlly liveReport:: " + liveReport
						+ ", : mcgDeliveryReport:: "
						+ mcgDeliveryReport);
				logger.error("onMessage::::::::::finally " ,ex);
			} finally {
				update = daoService.saveObject(mcgDeliveryReport);
			}
			logger.info("onMessage::::::::::::::::: :: update::live report "
					+ update + ", total time:: "
					+ (System.currentTimeMillis() - time));
		}
	}
}
