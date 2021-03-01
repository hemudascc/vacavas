package net.mycomp.messagecloud.gateway;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.common.service.IDaoService;
import net.process.bean.SuscriberIdMsg;
import net.util.MConstants;

public class MCGRenewalScheduler {

	private static final Logger logger = Logger.getLogger(MCGRenewalScheduler.class);
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private JMSMCGService jmsMCGService;
	
//	@Scheduled(cron="${mcg.renewal.cron.scheduler}")
	public void sendRenewalBilled(){	
		
		try{
			
			List<Integer> listOperator=new ArrayList<Integer>();
			listOperator.add(MConstants.MESSAGE_CLOUD_GATEWAY_CH_SWISSCOM_OPERATOR_ID);
			listOperator.add(MConstants.MESSAGE_CLOUD_GATEWAY_CH_SALT_OPERATOR_ID);
			listOperator.add(MConstants.MESSAGE_CLOUD_GATEWAY_CH_SUNRISE_OPERATOR_ID);
			
			List<Integer> subscriberIds=
				daoService.findValidationExpiredSubscriberIdForRenewal(listOperator,
							MConstants.SUBSCRIBED,3);	
			
			logger.info("sendRenewalBilled:::::::start:subscriber list::  "+subscriberIds.size()
					+" , subscriberIds "+subscriberIds);
			long time=System.currentTimeMillis();
			logger.info("sendAlertOnCompletionFreePeriod:::::start:::subscriber list size:: "+subscriberIds);
			for(Integer subscriberId:subscriberIds){
				SuscriberIdMsg suscriberIdMsg=new SuscriberIdMsg();
				suscriberIdMsg.setAction(MConstants.RENEW);
				suscriberIdMsg.setSubscriberId(subscriberId);
				jmsMCGService.sendRenewal(suscriberIdMsg);
			}	
			logger.info("sendRenewalBilled:::::::::end:total time::  "+(System.currentTimeMillis()-time));
			
		}catch(Exception ex){
			logger.error("sendRenewalBilled:: ",ex);
		}
	}

	
	
	  
	
}
