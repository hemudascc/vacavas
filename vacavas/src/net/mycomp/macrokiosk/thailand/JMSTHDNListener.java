package net.mycomp.macrokiosk.thailand;


import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPASubscriberReg;
import net.jpa.repository.JPATHMTMessage;
import net.mycomp.macrokiosk.thailand.DeliveryNotification;
import net.mycomp.macrokiosk.thailand.MacroKioskFactoryService;
import net.mycomp.macrokiosk.thailand.THConfig;
import net.mycomp.macrokiosk.thailand.THMOMessage;
import net.mycomp.macrokiosk.thailand.THMTMessage;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.process.bean.SuscriberIdMsg;
import net.util.MConstants;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class JMSTHDNListener implements MessageListener {
	private static final Logger logger = Logger.getLogger(JMSTHDNListener.class);


	@Autowired
	private MacroKioskFactoryService macroKioskFactoryService;
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private JPATHMTMessage jpaTHMTMessage;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	
	@Autowired
	private JMSThialandService jmsThialandService;
	
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;
	
	@Override
	public void onMessage(Message m) {
		
		long time = System.currentTimeMillis();
		logger.info("onMessage::::::::::::::::: " + m);
		ObjectMessage objectMessage = (ObjectMessage) m;
		boolean update = false;		
		DeliveryNotification deliveryNotification=null;
		LiveReport liveReport=null;
		try {		
		    deliveryNotification=(DeliveryNotification)objectMessage.getObject();
		    logger.info("onMessage::deliveryNotification:  "+deliveryNotification);
		    
		    
//		   Integer mtId= MUtility.toInt(Objects.toString(redisCacheService.
//				   getObjectCacheValue(ThiaConstant.MT_MESSAGE_CAHCHE_PREFIX+
//		    		deliveryNotification.getMtid())),0);
		   
		    THMTMessage mtMessage=null;
		    List<THMTMessage> list=jpaTHMTMessage.findMTMessageByMessageId(deliveryNotification.getMtid());
		    if(list!=null&&list.size()>0){
		    	mtMessage=list.get(0);
		    }
		    deliveryNotification.setNotificationType(mtMessage.getMessageType());
		    macroKioskFactoryService.processDeliveryNotification(deliveryNotification);
		    
		    Integer cmapignId=MConstants.DEFAULT_ADNETWORK_CAMPAIGN_ID;
		    Integer moId=0;
		    
		    if(mtMessage!=null){
		    	deliveryNotification.setKeyword(mtMessage.getKeyword());		    	
		    	deliveryNotification.setTokenId(mtMessage.getTokenId());	
		    	deliveryNotification.setToken(mtMessage.getToken());
		    	cmapignId=mtMessage.getCampaignId();
		    	moId=mtMessage.getMoMessageId();
		    }
		    
		    THConfig thConfig=ThiaConstant.mapServiceIdToTHConfig.get(mtMessage.getServiceId());
			//LiveReport(int operatorId, Timestamp timestamp, Integer adnetworkCampaignId,int serviceId)
		    liveReport=new LiveReport(deliveryNotification.getOpId(),deliveryNotification.getCreateTime(),
	    			cmapignId,thConfig.getServiceId(),thConfig.getProductId()
						 );
		    liveReport.setMsisdn(deliveryNotification.getMsisdn());
	    
	    	
	    if(mtMessage!=null&&mtMessage.getMessageType()!=null
	    		&&mtMessage.getMessageType().equalsIgnoreCase(ThiaConstant.MT_BIILABLE_MESSAGE)){
	    	
	    	liveReport.setTokenId(deliveryNotification.getTokenId());
	    	liveReport.setToken(deliveryNotification.getToken());
	    	
	    	SubscriberReg subscriberReg=jpaSubscriberReg
	    			.findSubscriberRegByMsisdnAndProductId(deliveryNotification.getMsisdn()
					, thConfig.getProductId());
	    	
	    	if(subscriberReg!=null){
	    		liveReport.setMode(subscriberReg.getMode());
	    	}
	    	
		     if(deliveryNotification.isCharged()){	
		    	
		    if(mtMessage.getMtActionType().equalsIgnoreCase(MConstants.ACT)){
		    	
			    	liveReport.setAction(MConstants.ACT);	    	
			    	logger.info("onMessage::thConfig:  "+thConfig);
			    	liveReport.setAmount(thConfig.getPrice());
			    	//liveReport.setConversionCount(1);
			    	liveReport.setNoOfDays(thConfig.getValidity());
			    	deliveryNotification.setAmount(thConfig.getPrice());
			    	
			    }else if(mtMessage.getMtActionType().equalsIgnoreCase(MConstants.RENEW)){
			    	
			    	liveReport.setAction(MConstants.RENEW);	    	
			    	logger.info("onMessage::thConfig:  "+thConfig);
			    	liveReport.setRenewalAmount(thConfig.getPrice());
			    	liveReport.setRenewalCount(1);
			    	liveReport.setNoOfDays(thConfig.getValidity());
			    	deliveryNotification.setAmount(thConfig.getPrice());
			    }
		    		    	
		     }else if(deliveryNotification.getRetry()!=null&&deliveryNotification.getRetry()==true){
		    	 
		    		String key=ThiaConstant.THIALAND_RETRY_BILLING_PREFIX+ThiaConstant.retryBillingDate
							+deliveryNotification.getMsisdn();					
					Integer counter=(Integer)redisCacheService.getObjectCacheValue(key);
					if(counter==null){
						counter=0;						
					}
					deliveryNotification.setCurrentRetryCounter(counter);
					logger.info("retry counter::: "+counter);
					
					if(counter==0){
						liveReport.setAction(MConstants.GRACE);
						liveReport.setGraceConversionCount(1);
					}
					
		    		SuscriberIdMsg suscriberIdMsg=new SuscriberIdMsg();
					suscriberIdMsg.setAction(mtMessage.getMtActionType());
					
					suscriberIdMsg.setSubscriberId(subscriberReg.getSubscriberId());
					jmsThialandService.sendRenewalRetry(suscriberIdMsg);
		         }	     
		     }
			logger.info("update:: "+deliveryNotification+", update:: "+update);
			deliveryNotification.setProcessStatus(true);
		} catch (Exception e) {
			logger.error("onMessage:::::::::::::::::"  + " , Exception  " , e);
			deliveryNotification.setProcessStatus(false);
		}finally{
			try{
				if(liveReport.getAction()!=null){
					liveReportFactoryService.process(liveReport);
				}
				deliveryNotification.setAction(liveReport.getAction());
				deliveryNotification.setCcMode(liveReport.getMode());
				
			}catch(Exception ex){
				logger.error("onMessage:::::::::::::::::"  + " , Exception  " , ex);
			}finally{
			update=daoService.saveObject(deliveryNotification);	
			}
		}
		logger.info("onMessage::::::::::::::::: :: update:: " + update+ ", total time:: " + (System.currentTimeMillis() - time));
	}	
}



