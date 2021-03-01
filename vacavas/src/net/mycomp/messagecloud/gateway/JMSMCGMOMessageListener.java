package net.mycomp.messagecloud.gateway;

import java.sql.Timestamp;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPAIntargetUssdTrans;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.LiveReport;
import net.persist.bean.Service;
import net.persist.bean.SubscriberReg;
import net.util.MConstants;
import net.util.MData;

public class JMSMCGMOMessageListener implements MessageListener {

	private static final Logger logger = Logger
			.getLogger(JMSMCGMOMessageListener.class);

	

	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private MCGApiService mcgApiService;
	
	
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;

	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private SubscriberRegService subscriberRegService; 

	@Autowired
	private JPASubscriberReg jpaSubscriberReg; 
	
	@Autowired
	private JPAIntargetUssdTrans jpaIntargetUssdTrans;
	
  
	@Override
	public void onMessage(Message m) {

		MCGMoMessage mcgMoMessage = null;
		LiveReport liveReport = null;
		boolean update = false;
		long time = System.currentTimeMillis();
		MCGServiceConfig mcgServiceConfig=null;
		try {

			ObjectMessage objectMessage = (ObjectMessage) m;
			mcgMoMessage = (MCGMoMessage) objectMessage
					.getObject();
			logger.debug("mcgMoMessage::::::: "+mcgMoMessage);
			String msg=mcgMoMessage.getMessage();
			//mcgServiceConfig=MCGConstant.mapServiceIdToMCGServiceConfig.get(mcgMoMessage.getServiceId());
			if(mcgMoMessage.getNetwork().contains("SALT")) {
				mcgServiceConfig=MCGConstant.mapServiceIdToMCGServiceConfig.get(10);
			}else if(mcgMoMessage.getNetwork().contains("SUNRISE")) {
				mcgServiceConfig=MCGConstant.mapServiceIdToMCGServiceConfig.get(11);
			}else {
				mcgServiceConfig=MCGConstant.mapServiceIdToMCGServiceConfig.get(7);
			}
			
			if(mcgServiceConfig==null){
			 mcgServiceConfig=MCGConstant.findMCGServiceConfig(msg,
					mcgMoMessage.getNetwork()
					, mcgMoMessage.getShortcode());
			}
			logger.debug("mcgServiceConfig::::::: "+mcgServiceConfig);
			
			String action=MCGConstant.findAction(msg,mcgServiceConfig);
			
			 
//			MCGFallbackPricePointConfig mcgFallbackPricePointConfig=mcgServiceConfig
//					 .getListMCGFallbackPricePointConfig().get(0);
			
			MCGFallbackPricePointConfig mcgFallbackPricePointConfig=MCGConstant
					.findMCGFallbackPricePointConfig(mcgServiceConfig,null);
//			 for(MCGFallbackPricePointConfig tmp:mcgServiceConfig.getListMCGFallbackPricePointConfig()){
//					if(tmp.getId().intValue()==mcgServiceConfig.getId()){
//						mcgFallbackPricePointConfig=tmp;
//						break;
//					}
//				}
			
			Service service=MData.mapServiceIdToService.get(mcgServiceConfig.getServiceId());			
			liveReport=new LiveReport(service.getOpId(),new Timestamp(System.currentTimeMillis())
			 ,null,mcgServiceConfig.getServiceId(),0);
			liveReport.setProductId(service.getProductId());
			liveReport.setMsisdn(mcgMoMessage.getNumber());
			liveReport.setType(""+mcgServiceConfig.getServiceId());
			
			if(action.equalsIgnoreCase(MConstants.ACT)){
				
				SubscriberReg subscriberReg= jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(mcgMoMessage.getNumber(), 
						service.getProductId());
				if(subscriberReg!=null&&subscriberReg.getStatus()==MConstants.SUBSCRIBED){
					String mtMsg=mcgServiceConfig.getAlreadySubscribedSms();
					mcgApiService.
					sendContentMessage(mcgMoMessage.getNumber(),
							mcgServiceConfig,mcgFallbackPricePointConfig, mtMsg,null,
							MCGConstant.ALREADY_SUBSCRIBED_CONTENT_MSG_TYPE, mcgMoMessage.getMessageId(), null);
				}else{
				//	String networkName=mcgApiService.findNetworkName(mcgMoMessage.getNumber());
					String mtMsg=mcgServiceConfig.getWelcomeSms();						
					mcgApiService.sendContentMessage(mcgMoMessage.getNumber()
							, mcgServiceConfig, mcgFallbackPricePointConfig, mtMsg,
							null,MCGConstant.WELCOME_CONTENT_MSG_TYPE, mcgMoMessage.getMessageId(), null);
//					BilledMessage(mcgMoMessage.getMessageId(),
//							mcgMoMessage.getNumber()
//							, mcgServiceConfig,mcgFallbackPricePointConfig, mtMsg,networkName);
					
					 mtMsg=mcgServiceConfig.getFirstMt();						
					
					 mcgApiService.sendBilledMessage(mcgMoMessage.getMessageId(),
							mcgMoMessage.getNumber()
							, mcgServiceConfig,mcgFallbackPricePointConfig, mtMsg,mcgMoMessage.getNetwork()
							,MConstants.ACT,MCGConstant.BILLLED_MSG_TYPE1);
					 mtMsg=mcgServiceConfig.getSecondMt();		
					 if(!mcgMoMessage.getNetwork().contains("SUNRISE")) {
					mcgApiService.sendBilledMessage(mcgMoMessage.getMessageId(),
							mcgMoMessage.getNumber()
							, mcgServiceConfig,mcgFallbackPricePointConfig, 
							mtMsg,mcgMoMessage.getNetwork(),MConstants.ACT,MCGConstant.BILLLED_MSG_TYPE2);
					 mtMsg=mcgServiceConfig.getThirdMt();						
					 }
					 if(!mcgMoMessage.getNetwork().contains("SUNRISE")){
					 mcgApiService.sendBilledMessage(mcgMoMessage.getMessageId(),
							mcgMoMessage.getNumber()
							, mcgServiceConfig,mcgFallbackPricePointConfig,
							mtMsg,mcgMoMessage.getNetwork(),MConstants.ACT,MCGConstant.BILLLED_MSG_TYPE3);
					 }
				}
			}else if(action.equalsIgnoreCase(MConstants.DCT)){
				
				String mtMsg=mcgServiceConfig.getUnsubSms();
				liveReport.setAction(MConstants.DCT);
				liveReport.setDctCount(1);
				mcgApiService.sendContentMessage(mcgMoMessage.getNumber()
						, mcgServiceConfig,mcgFallbackPricePointConfig, mtMsg,null,MConstants.DCT, mcgMoMessage.getMessageId(), null);
				
			}else if(action.equalsIgnoreCase(MCGConstant.INVALID_KEY)){
				
				String mtMsg=mcgServiceConfig.getInvalidmessage();
				mcgApiService.sendContentMessage(mcgMoMessage.getNumber(),
						mcgServiceConfig,mcgFallbackPricePointConfig,
						mtMsg,null,MCGConstant.INVALID_KEY, mcgMoMessage.getMessageId(), null);
			}
			
		} catch (Exception ex) {
			
			logger.error("onMessage::::: ", ex);
			
			
		} finally {
			try {				
				logger.error("onMessage:::::liveReport:: "+liveReport);				
			if (liveReport.getAction() != null) {
					liveReport = liveReportFactoryService.process(liveReport);
				}				
			} catch (Exception ex) {
				logger.error(" fianlly liveReport:: " + liveReport
						+ ", : mcgMoMessage:: "
						+ mcgMoMessage);
				logger.error("onMessage::::::::::finally " ,ex);
			} finally {
				update = daoService.saveObject(mcgMoMessage);
			}
			logger.info("onMessage::::::::::::::::: :: update::live report "
					+ update + ", total time:: "
					+ (System.currentTimeMillis() - time));
		}
	}
}
