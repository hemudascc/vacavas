package net.mycomp.macrokiosk.thailand;

import net.common.service.IDaoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


public abstract class AbstractMacroKioskMTMessage implements IMacroKioskService{

	private static final Logger logger = Logger.getLogger(AbstractMacroKioskMTMessage.class);

	@Autowired
	protected SmsService smsService;
	
	@Autowired
	protected IDaoService daoService;
	
	@Value("${thialand.mt.send.url}")
	protected String mtUrl;

//	@Override
//	public boolean handleSubscriptionMOMessage(MOMessage moMessage) {
//		
//		logger.info("handleMOMessage::::::::: "+moMessage);
//		THConfig selectedTHConfig=null;
//		for(THConfig thConfig:MacroKioskFactoryService.listTHConfig){
//			if(thConfig.getTelcoId().intValue()==moMessage.getTelcoid()&&
//					moMessage.getText().equalsIgnoreCase(thConfig.getKeyword())){
//				selectedTHConfig=thConfig;
//				break;
//			}
//		}		
//		
//		logger.info("handleMOMessage:: ::::::selectedTHConfig::  "+selectedTHConfig);
//		String msg=ThiaConstant.convertToHexString(
//				ThiaConstant.convertToDateTimeFormat())+
//				selectedTHConfig.getMtBillingMessageTemplate();		
//		MTMessage mtMessage =createMTBillableMessage(selectedTHConfig,moMessage,msg);
//		logger.info("handleMOMessage:: create MT Biiling message::::::mtMessage "+mtMessage);
//		HTTPResponse response=smsService.sendMTSMS(mtUrl, mtMessage);
//		logger.info("handleMOMessage:: sendMTSMS::::::response "+response);
//		daoService.saveObject(mtMessage);
//		return true;
//	}
	
	public void handleWelcomeSubscriptionMTMessage(THMTMessage MTMessage){
		logger.info("handleWelcomeMTMessage::::::::::::: ");
	}
	
	
	
}
