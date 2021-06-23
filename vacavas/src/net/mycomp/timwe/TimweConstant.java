package net.mycomp.timwe;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface TimweConstant {


	String TIMWE_TOKEN_MSISDN_CACHE_PREFIX="TIMWE_TOKEN_MSISDN_CACHE_PREFIX";
	String PIN_SEND="PIN_SEND";
	String PIN_VERIFY="PIN_VERIFY";
	String UNSUBSCRIBE="UNSUBSCRIBE";
	String SEND_MT="SEND_MT";
	String CONTENT_MESSAGE="Thanks for subscribing to <service> to use the content click on <url> url";

	static String getSubscriptionMessage(TimweServiceConfig timweServiceConfig,String msisdn) throws UnsupportedEncodingException{
		return CONTENT_MESSAGE
				.replaceAll("<service>", timweServiceConfig.getServiceName())
				.replaceAll("<url>", timweServiceConfig.getPortalURL().replaceAll("<msisdn>", msisdn));
	}

	static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	String ROLE_ID="3941";
	String SERVICE_ID="3894";
	String SUBSCRIPTION_API_KEY="8b9d5e392f5045269e724c119940d15b";
	String SEND_MT_API_KEY="7205aa5be40a496f8dc02c465d95374c";
	String SUBSCRIPTION_PRE_SHARED_KEY="tS5EngbxxqBSSDID";
	String SEND_MT_PRE_SHARED_KEY="bt3MhjcimhBGt94E";

	String IV_PARAM_SPEC_KEY="yzXzUhr3OAt1A47g7zmYxw==";
	String PSK="JCqIBCn5ebaGM1WOXJDzrA==";

	String WEEKLY_PRICEPONT_ID="54584";
	String DAILY_PRICEPONT_ID="MB-54569";


	String MO_NOTIFICATION="mo";
	String OPTIN_NOTIFICATION="user-optin";
	String OPTOUT_NOTIFICATION="user-optout";
	String RENEWAL_NOTIFICATION="user-renewed";
	String FIRST_CHARGE_NOTIFICATION="first-charge";

	Map<Integer,TimweServiceConfig> mapServiceIdToTimweServiceConfig = new HashMap<>();
}
