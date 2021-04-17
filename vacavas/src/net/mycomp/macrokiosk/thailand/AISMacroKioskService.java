package net.mycomp.macrokiosk.thailand;

import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.persist.bean.SubscriberReg;
import net.util.HTTPResponse;
import net.util.MConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("aisMacroKioskService")
public class AISMacroKioskService extends  AbstractMacroKioskMTMessage{

	private static final Logger logger = Logger.getLogger(AISMacroKioskService.class);

	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private SmsService smsService;
	
	@Value("${thialand.ais.mo.send.url}")
	private String moUrl;
	
	@Value("${thialand.ais.mt.send.url}")
	private String mtUrl;
	
	@Autowired
	private RedisCacheService redisCacheService; 

	@Override
	public boolean handleSubscriptionMOMessage(THMOMessage moMessage) {
		
		logger.info("handleSubscriptionMOMessage::::::::: "+moMessage);
		THConfig selectedTHConfig=null;
		for(THConfig thConfig:ThiaConstant.listTHConfig){
			if(thConfig.getTelcoId().intValue()==moMessage.getTelcoid()&&
					moMessage.getText().equalsIgnoreCase(thConfig.getKeyword())){
				selectedTHConfig=thConfig;
				break;
			}
		}		
		   
		
		//MT Welcome Message
		logger.info("handleSubscriptionMOMessage:: ::::::selectedTHConfig::  "+selectedTHConfig);
		String msg=ThiaConstant.convertToHexString(
				ThiaConstant.convertToDateTimeFormat())+
				selectedTHConfig.getMtWelcomeMessageTemplate();		
		THMTMessage mtMessage =createMTWelcomeMessage(selectedTHConfig,moMessage,msg);
		logger.info("handleSubscriptionMOMessage:: create MT message::::::mtMessage "+mtMessage);
		HTTPResponse  response=smsService.sendMTSMS(mtUrl, mtMessage);
		logger.info("handleSubscriptionMOMessage:: sendMTSMS::::::response "+response);
		daoService.updateObject(mtMessage);
		
		//MT Billing Messagge
		logger.info("handleSubscriptionMOMessage:: ::::::selectedTHConfig::  "+selectedTHConfig);
	     msg=ThiaConstant.convertToHexString(
				ThiaConstant.convertToDateTimeFormat())+
				selectedTHConfig.getMtBillingMessageTemplate().replaceAll("<subid>",moMessage.getMsisdn());		
		 mtMessage =createMTBillableMessage(selectedTHConfig,moMessage,msg);
		// mtMessage.setAction(MConstants.ACT);
		 mtMessage.setServiceId(selectedTHConfig.getServiceId());
		 
		 logger.info("handleSubscriptionMOMessage:: create MT message::::::mtMessage "+mtMessage);
		 response=smsService.sendMTSMS(mtUrl, mtMessage);
		 redisCacheService.putObjectCacheValueByEvictionMinute(ThiaConstant.MT_MESSAGE_CAHCHE_PREFIX
				 +mtMessage.getMsgId(),
				 mtMessage.getId(), 10*60);
		 
		logger.info("handleSubscriptionMOMessage:: sendMTSMS::::::response "+response);
		daoService.updateObject(mtMessage);
		return true;
	}
	
	

	@Override
	public boolean sendSubscriptionRenewalRequest(SubscriberReg subscriberReg) {
		
		
	//	int telcoId=ThialandOperatorTelcoidMap.getTelcoId(subscriberReg.getOperatorId());
		
		THConfig thConfig =ThiaConstant.mapServiceIdToTHConfig.get(subscriberReg.getServiceId());//MacroKioskFactoryService.findThConfigByServiceIdAndTelcoId(subscriberReg.getServiceId(), telcoId);
		
		THMTMessage mtMessage=new THMTMessage(true);
		mtMessage.setServiceId(thConfig.getServiceId());
		mtMessage.setMessageType(ThiaConstant.MT_BIILABLE_MESSAGE);
		mtMessage.setMtActionType(MConstants.RENEW);
		mtMessage.setUser(thConfig.getUser());
		mtMessage.setPass(thConfig.getPassword());	
		//mtMessage.setCat(ThialanMTCat.RENEWAL_SUBSCRIPTION.getCatId());
		mtMessage.setCat(ThialanMTCat.CONTENT_BRODCAST.getCatId());
		mtMessage.setFromStr(thConfig.getShortcode());
		mtMessage.setMsisdn(subscriberReg.getMsisdn());
		mtMessage.setKeyword(thConfig.getKeyword());		
		//mtMessage.setMsgId(moMessage.getMsgid());		
		mtMessage.setOpId(subscriberReg.getOperatorId());
		mtMessage.setPrice(thConfig.getPrice());		
		mtMessage.setTelcoId(thConfig.getTelcoId());
		String msg=ThiaConstant.convertToHexString(
				ThiaConstant.convertToDateTimeFormat())+
				thConfig.getMtRenewalMessageTemplate();//BillingMessageTemplate();		
		mtMessage.setTextMsg(msg);
		mtMessage.setType(ThiaConstant.MT_UCS);
		mtMessage.setSenderid(thConfig.getShortcode());	
		//mtMessage.setAction(MConstants.RENEW);
		HTTPResponse response=smsService.sendMTSMS(mtUrl, mtMessage);
		redisCacheService.putObjectCacheValueByEvictionMinute(ThiaConstant.MT_MESSAGE_CAHCHE_PREFIX+mtMessage.getMsgId(),
				 mtMessage.getId(), 10*60);
		logger.info("sendSubscriptionRenewalRequest:: sendMTSMS::::::mtMessage:: "+mtMessage+" ,response "+response);
		daoService.updateObject(mtMessage);
		return true;
	}
	
	

	@Override
	public void processDeliveryNotification(
			DeliveryNotification deliveryNotification) {
	    
		try{
			
		if(deliveryNotification.getNotificationType().equalsIgnoreCase(ThiaConstant.MT_BIILABLE_MESSAGE)){	
			if(deliveryNotification!=null&&deliveryNotification.getStatus()!=null&&
					(deliveryNotification.getStatus().contains("external:DELIVRD")
							||deliveryNotification.getStatus().contains("external:DELIVRD:000BROADCAST")
							||deliveryNotification.getStatus().contains("external:DELIVRD:000|BROADCAST")	
							//||deliveryNotification.getStatus().contains("external:successBROADCAST")
							||deliveryNotification.getStatus().contains("external:success|RECURRING")
							||deliveryNotification.getStatus().contains("external:successRECURRING")							
							)){
				deliveryNotification.setCharged(true);			
			}
		
			if(deliveryNotification!=null&&deliveryNotification.getStatus()!=null&&
					(
							deliveryNotification.getStatus().contains("external:Rejected:03319BROADCAST")
							||deliveryNotification.getStatus().contains("external:Rejected:03314BROADCAST")
							)){
				deliveryNotification.setRetry(true);			
			}
		}
		}catch(Exception ex){
			logger.error("processDeliveryNotification::: ",ex);
		}
	}
	
	
protected  THMTMessage createMTWelcomeMessage(THConfig thConfig,DeliveryNotification deliveryNotification,String msg){
		
		THMTMessage mtMessage=new THMTMessage(true);
		mtMessage.setMessageType(ThiaConstant.MT_WELCOME_MESSAGE);
		mtMessage.setMtActionType(ThiaConstant.MT_WELCOME_MESSAGE);
		mtMessage.setUser(thConfig.getUser());
		mtMessage.setPass(thConfig.getPassword());	
		mtMessage.setCat(ThialanMTCat.SUBCRIBE_TYPE.getCatId());
		mtMessage.setFromStr(deliveryNotification.getShortcode());
		mtMessage.setMsisdn(deliveryNotification.getMsisdn());
		mtMessage.setKeyword(thConfig.getKeyword());		
		//mtMessage.setMsgId(deliveryNotification.getMtid());	
		mtMessage.setServiceId(thConfig.getServiceId());
		mtMessage.setOpId(deliveryNotification.getOpId());
		mtMessage.setPrice(0d);		
		mtMessage.setTelcoId(deliveryNotification.getTelcoId());
		mtMessage.setTextMsg(msg);
		mtMessage.setType(ThiaConstant.MT_TEXT);
		mtMessage.setSenderid(deliveryNotification.getShortcode());
		return mtMessage;
	}


protected  THMTMessage createMTWelcomeMessage(THConfig thConfig,THMOMessage moMessage,String msg){
	
	THMTMessage mtMessage=new THMTMessage(true);
	mtMessage.setMessageType(ThiaConstant.MT_WELCOME_MESSAGE);
	mtMessage.setMtActionType(ThiaConstant.MT_WELCOME_MESSAGE);
	mtMessage.setUser(thConfig.getUser());
	mtMessage.setPass(thConfig.getPassword());	
	mtMessage.setCat(ThialanMTCat.SUBCRIBE_TYPE.getCatId());
	mtMessage.setFromStr(moMessage.getShortcode());
	mtMessage.setMsisdn(moMessage.getMsisdn());
	mtMessage.setKeyword(thConfig.getKeyword());		
	mtMessage.setMoMessageId(moMessage.getId());		
	mtMessage.setOpId(moMessage.getOpId());
	mtMessage.setPrice(0d);		
	mtMessage.setTelcoId(moMessage.getTelcoid());
	mtMessage.setTextMsg(msg);
	mtMessage.setType(ThiaConstant.MT_UCS);
	mtMessage.setSenderid(moMessage.getShortcode());
	mtMessage.setLinkId(moMessage.getMoid());
	return mtMessage;
 }


protected  THMTMessage createMTBillableMessage(THConfig thConfig,THMOMessage moMessage,String msg){
	//http://mis.etracker.cc/THPush/THpush.aspx?
	//user=creativeantenna&pass=creativeantenna123&type=5&to=1&
	//from=4541538&text=0E140E320E270E190E4C0E420E2B0E250E1400200076006900640065006F00200E440E140E490E440E210E480E080E330E010E310E140E170E380E010E270E310E190E170E350E480020&
	//price=10&telcoid=1&cat=6&keyword=hot&senderid=4541538
	THMTMessage mtMessage=new THMTMessage(true);
	mtMessage.setMessageType(ThiaConstant.MT_BIILABLE_MESSAGE);
	mtMessage.setMtActionType(MConstants.ACT);
	mtMessage.setUser(thConfig.getUser());
	mtMessage.setPass(thConfig.getPassword());	
	mtMessage.setCat(ThialanMTCat.CONTENT_BRODCAST.getCatId());
	mtMessage.setFromStr(moMessage.getShortcode());
	mtMessage.setMsisdn(moMessage.getMsisdn());
	mtMessage.setKeyword(thConfig.getKeyword());		
	mtMessage.setMoMessageId(moMessage.getId());		
	mtMessage.setOpId(moMessage.getOpId());
	mtMessage.setPrice(thConfig.getPrice());		
	mtMessage.setTelcoId(moMessage.getTelcoid());
	mtMessage.setTextMsg(msg);
	mtMessage.setType(ThiaConstant.MT_UCS);
	mtMessage.setSenderid(moMessage.getShortcode());
	mtMessage.setTokenId(moMessage.getTokenId());
	mtMessage.setToken(moMessage.getToken());
	mtMessage.setCampaignId(moMessage.getCampaignId());
	return mtMessage;
}	
}


