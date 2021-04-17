package net.mycomp.macrokiosk.thailand;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import net.common.service.CommonService;
import net.common.service.IDaoService;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.util.MConstants;
import net.util.MData;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("macroKioskFactoryService")
public class MacroKioskFactoryService extends  AbstractMacroKioskMTMessage{

	private static final Logger logger = Logger.getLogger(MacroKioskFactoryService.class);

	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private AISMacroKioskService aisMacroKioskService;
	
	@Autowired
	private TruemoveMacroKioskService truemoveMacroKioskService;
	
	
	@PostConstruct
	public void init(){
		
	
	}
	
	public IMacroKioskService findMacroKioskService(int opId){
		
		IMacroKioskService macroKioskService=null;
		switch(opId){
		case MConstants.MICROKIOSK_AIS_OPERATOR_ID:
			macroKioskService=aisMacroKioskService;	
		break;
//		case MConstants.MICROKIOSK_TRUEMOVE_OPERATOR_ID:
//			macroKioskService=truemoveMacroKioskService;	
//		break;
		default:{
			
			}
		}
//		case MConstants.DTAC_OPERATOR_ID:
//		  macroKioskService=dtacMacroKioskService;	
//		break;
//		case MConstants.TRUEMOVE_HUTCHISON_OPERATOR_ID:
//		//	macroKioskService=truemoveHutchisonMacroKioskService;	
//		break;
//		}
		return macroKioskService;
	}
	
	
	
	@Override
	public boolean handleSubscriptionMOMessage(THMOMessage moMessage) {
		
		return findMacroKioskService(moMessage.getOpId()).handleSubscriptionMOMessage(moMessage);
	}


	@Override
	public boolean sendSubscriptionRenewalRequest(SubscriberReg subscriberReg) {

		return findMacroKioskService(subscriberReg.getOperatorId()).
				sendSubscriptionRenewalRequest(subscriberReg);
	}

	@Override
	public void processDeliveryNotification(
			DeliveryNotification deliveryNotification) {
		  findMacroKioskService(deliveryNotification.getOpId()).
				processDeliveryNotification(deliveryNotification);
	}
	
	public static THConfig findThConfigByKeyWordAndTelcoId(String keyword ,int telcoId){	
		
		 THConfig thConfig=ThiaConstant.mapKeywordTelcoIdToTHConfig.get(keyword+""+telcoId);
		 return thConfig;
	}
	
}
