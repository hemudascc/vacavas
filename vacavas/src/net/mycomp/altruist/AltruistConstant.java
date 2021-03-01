package net.mycomp.altruist;

import java.util.HashMap;
import java.util.Map;

public interface AltruistConstant {	
	
	Map<Integer,AltruistServiceConfig> mapServiceIdToAltruistServiceConfig = new HashMap<>();
	String encriptionKey="DHDUFYlinsGDDSSs";
	String ALTRUIST_UNIQUE_TOKEN_PREFIX="ALTRUIST_UNIQUE_TOKEN_PREFIX";
	String PIN_SEND="PIN_SEND";
	String PIN_VERIFY="PIN_VERIFY";
	String UNSUBSCRIBE="UNSUBSCRIBE";
	String SEND_SMS="SEND_SMS";
	
	
	static String getSms(String action,AltruistServiceConfig altruistServiceConfig){
		String sms="";
		switch (action) {
		case PIN_VERIFY:
			sms = altruistServiceConfig.getWelcomeMessage();
			break;
		case UNSUBSCRIBE:
			sms = altruistServiceConfig.getUnsubscribeMessage();
			break;
		default:
			sms = altruistServiceConfig.getAlreadySubscribeMessage();
			break;
		}
		return sms;
	}
	
}
