package net.mycomp.macrokiosk.thailand;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import net.common.service.IDaoService;
import net.persist.bean.SubscriberReg;
import net.process.bean.SuscriberIdMsg;
import net.util.MConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ThiaRenewalScheduler {

	private static final Logger logger = Logger.getLogger(ThiaRenewalScheduler.class);
	
	@Autowired
	private MacroKioskFactoryService macroKioskFactoryService;
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private JMSThialandService jmsThialandService;
	
	@Scheduled(cron="0 0 * * * *")
	public void resetValues(){
		ThiaConstant.thialandRetryDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));		
		ThiaConstant.retryBillingDate.replace(0,ThiaConstant.retryBillingDate.length(),
				new String(ThiaConstant.thialandRetryDateFormat.format(
				new Timestamp(System.currentTimeMillis()))));
		}
	
	
	@Scheduled(cron="${thia.ais.renewal.cron.scheduler}")
	public void sendAisRenewalBilled(){	
		
		try{
			
			List<Integer> listOperator=new ArrayList<Integer>();
			listOperator.add(MConstants.MICROKIOSK_AIS_OPERATOR_ID);
		
			List<Integer> subscriberIds=
				daoService.findValidationExpiredSubscriberIdForRenewal(listOperator,
							MConstants.SUBSCRIBED);	
			
			logger.info("sendRenewalBilled:::::::start:subscriber list::  "+subscriberIds.size());
			
			long time=System.currentTimeMillis();
			logger.info("sendAlertOnCompletionFreePeriod:::::start:::subscriber list size:: "+subscriberIds);
			for(Integer subscriberId:subscriberIds){
				SuscriberIdMsg suscriberIdMsg=new SuscriberIdMsg();
				suscriberIdMsg.setAction(MConstants.RENEW);
				suscriberIdMsg.setSubscriberId(subscriberId);
				jmsThialandService.sendRenewal(suscriberIdMsg);
			}	
			logger.info("sendRenewalBilled:::::::::end:total time::  "+(System.currentTimeMillis()-time));
			
		}catch(Exception ex){
			logger.error("sendRenewalBilled:: ",ex);
		}
	}

	
	@Scheduled(cron="${thia.ais.second.renewal.cron.scheduler}")
	public void sendThialandAisSecondRenewalBilled(){	
		
		try{
			
			List<Integer> listOperator=new ArrayList<Integer>();			
			listOperator.add(MConstants.MICROKIOSK_AIS_OPERATOR_ID);
			List<Integer> subscriberIds=
				daoService.findValidationExpiredSubscriberIdForTrueMoveSecondRenewal(listOperator,
							MConstants.SUBSCRIBED);				
			logger.info("sendThialandAisSecondRenewalBilled:::::::start:subscriber list::  "+subscriberIds.size());			
			long time=System.currentTimeMillis();
			logger.info("sendThialandAisSecondRenewalBilled:::::start:::subscriber list size:: "+subscriberIds);
			for(Integer subscriberId:subscriberIds){
				SuscriberIdMsg suscriberIdMsg=new SuscriberIdMsg();
				suscriberIdMsg.setAction(MConstants.RENEW);
				suscriberIdMsg.setSubscriberId(subscriberId);
				jmsThialandService.sendRenewal(suscriberIdMsg);
			}	
			logger.info("sendThialandAisSecondRenewalBilled:::::::::end:total time::  "+(System.currentTimeMillis()-time));
			
		}catch(Exception ex){
			logger.error("sendThialandAisSecondRenewalBilled:: ",ex);
		}
	}
	
	
	@Scheduled(cron="${thia.truemove.renewal.cron.scheduler}")
	public void sendTruemoveRenewalBilled(){	
//	Yash	
//		try{
//			
//			List<Integer> listOperator=new ArrayList<Integer>();
//			
//			listOperator.add(MConstants.MICROKIOSK_TRUEMOVE_OPERATOR_ID);
//			List<Integer> subscriberIds=
//				daoService.findValidationExpiredSubscriberIdForRenewalForFirstTrueMove(listOperator,
//							MConstants.SUBSCRIBED);	
//			
//			logger.info("sendTruemoveRenewalBilled:::::::start:subscriber list::  "+subscriberIds.size());
//			
//			long time=System.currentTimeMillis();
//			logger.info("sendTruemoveRenewalBilled:::::start:::subscriber list size:: "+subscriberIds);
//			for(Integer subscriberId:subscriberIds){
//				SuscriberIdMsg suscriberIdMsg=new SuscriberIdMsg();
//				suscriberIdMsg.setAction(MConstants.RENEW);
//				suscriberIdMsg.setSubscriberId(subscriberId);
//				jmsThialandService.sendRenewal(suscriberIdMsg);
//			}	
//			logger.info("sendTruemoveRenewalBilled:::::::::end:total time::  "+(System.currentTimeMillis()-time));
//			
//		}catch(Exception ex){
//			logger.error("sendTruemoveRenewalBilled:: ",ex);
//		}
	}
	
	
	@Scheduled(cron="${thia.truemove.second.renewal.cron.scheduler}")
	public void sendTruemoveSecondRenewalBilled(){	
//		Yash
//		try{
//			
//			List<Integer> listOperator=new ArrayList<Integer>();			
//			listOperator.add(MConstants.MICROKIOSK_TRUEMOVE_OPERATOR_ID);
//			List<Integer> subscriberIds=
//				daoService.findValidationExpiredSubscriberIdForTrueMoveSecondRenewal(listOperator,
//							MConstants.SUBSCRIBED);				
//			logger.info("sendTruemoveSecondRenewalBilled:::::::start:subscriber list::  "+subscriberIds.size());			
//			long time=System.currentTimeMillis();
//			logger.info("sendTruemoveSecondRenewalBilled:::::start:::subscriber list size:: "+subscriberIds);
//			for(Integer subscriberId:subscriberIds){
//				SuscriberIdMsg suscriberIdMsg=new SuscriberIdMsg();
//				suscriberIdMsg.setAction(MConstants.RENEW);
//				suscriberIdMsg.setSubscriberId(subscriberId);
//				jmsThialandService.sendRenewal(suscriberIdMsg);
//			}	
//			logger.info("sendTruemoveSecondRenewalBilled:::::::::end:total time::  "+(System.currentTimeMillis()-time));
//			
//		}catch(Exception ex){
//			logger.error("sendTruemoveSecondRenewalBilled:: ",ex);
//		}
	}
	
	  
	
}
