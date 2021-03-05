package net.mycomp.altruist;

import java.util.HashMap;
import java.util.Map;

import net.util.MConstants;

public interface AltruistConstant {	
	
	Map<Integer,AltruistServiceConfig> mapServiceIdToAltruistServiceConfig = new HashMap<>();
	String encriptionKey="DHDUFYlinsGDDSSs";
	String ALTRUIST_UNIQUE_TOKEN_PREFIX="ALTRUIST_UNIQUE_TOKEN_PREFIX";
	String PIN_SEND="PIN_SEND";
	String PIN_VERIFY="PIN_VERIFY";
	String UNSUBSCRIBE="UNSUBSCRIBE";
	String SEND_SMS="SEND_SMS";
	String CONTENT_MESSAGE="Thanks for subscribing to <servicename> to use the content click on <url> url.";
	
	
	String SUB="SUB";
	String UNSUB="UNSUB";
	String REN="REN";
	String SUB_FAIL="SUB_FAIL";
	
	static String getSubscriptionMessage(AltruistServiceConfig altruistServiceConfig,String msisdn){
		return CONTENT_MESSAGE.replaceAll("<servicename>", altruistServiceConfig.getServiceName())
						.replaceAll("<url>", altruistServiceConfig.getPortalURL().replaceAll("<msisdn>", msisdn));
	}

	static String findAction(AltruistCallback altruistCallback) {
		String action="";
		switch (altruistCallback.getTransactionType()) {
		case SUB:
			action=MConstants.ACT; 
			break;
		case REN:
			action=MConstants.RENEW; 
			break;
		case SUB_FAIL:
			action=MConstants.GRACE; 
			break;
		case UNSUB:
			action=MConstants.DCT; 
			break;
		default:
			break;
		}
		return action;
	}
	
}