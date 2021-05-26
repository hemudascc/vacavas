package net.mycomp.tpay;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TpayConstant {
	
	public static List<TpayServiceConfig> listTpayServiceConfig = new ArrayList<>();
	
	public static Map<Integer,TpayServiceConfig> mapServiceIdToTpayServiceConfig = new HashMap<>();
	
	public static final String RECYCLED_SUBCRIBER ="RECYCLED_SUBCRIBER";
	
	Integer TPAY_PRODUCT_ID=1;
	public static final String TPAY_CACHE_PREFIX="TPAY_CACHE_PREFIX";
	public static final String TPAY_TEMP_SUBSCRIBE="TPAY_TEMP_SUBSCRIBE";
	public static final String TPAY_GRACE_RECEIVED_CACHE="TPAY_GRACE_RECEIVED_CACHE";
	public static final String TPAY_SUB_CONT_ID_PREFIX="TPAY_SUB_CONT_ID_PREFIX";
	public static final String TPAY_SUBCONTID_TOKEN_AND_MSISDN_CACHE_PREFIX="TPAY_SUBCONTID_TOKEN_AND_MSISDN_CACHE_PREFIX";
	
	public static final String LANG = "en";
	public static final String LANG_EN = "en";
	public static final String LANG_AR = "ar";
	public static final String THEME = "vaca";
	public static final String HASH_KEY_TYPE = "HMACSHA256";
	//public static final String TPAY_JS_API = "http://enrichment-staging.tpay.me/idxml.ashx/js-staging?date=<date>&digest=<digest>&simulate=true&operatorcode=<oc>&msisdn=<msisdn>";
	public static final String TPAY_JS_API = "http://lookup.tpay.me/idxml.ashx/js?date=<date>&lang=<lang>&digest=<digest>";
	
	
	/*
	 * public static final String TPAY_HEADER_API =
	 * "http://enrichment-staging.tpay.me/idxml.ashx/js-staging?date=[0]&digest=[4]";
	 */
	
//	public static final String PUBLIC_KEY = "3pxFle1Fe3jvdtLuwjMt";
//	public static final String PRIVATE_KEY = "d4U9NCOXBAmMSloiiH2u";	
//	public static final String SECRET_KEY = "d4U9NCOXBAmMSloiiH2u";
	
//	public static final String TPAY_PORTAL_URL="http://192.241.167.189:8080/gameshub/?msisdn=<msisdn>&lang=<lang>";
	public static final String PIN_URL = "https://live.tpay.me/api/TPAYSubscription.svc/json/AddSubscriptionContractRequest";
	public static final String PIN_VALIDATE_URL = "https://live.tpay.me/api/TPaySubscription.svc/json/VerifySubscriptionContract";
	public static final String PIN_RESEND_URL = "https://live.tpay.me/api/TPaySubscription.svc/json/SendSubscriptionContractVerificationSMS";
	public static final String SMS_URL = "https://live.tpay.me/api/TPay.svc/json/SendFreeMTMessage";
	public static final String CANCEL_SUBSCRIPTION_CONTARCT_URL = "https://live.tpay.me/api/TPaySubscription.svc/json/CancelSubscriptionContractRequest";
//	public static final String WELCOME_MESSAGE_SMS_ENG = "Thanks for subscribing to <servicename>  to enjoy visit <portalurl>. You will be charged for <price> <currency>/<billing_sequence> to unsubscribe send <unsub_keyword> to <shortcode> for free. For queries, contact on vas.support@vacastudios.com";
//	public static final String WELCOME_MESSAGE_SMS_ARB = new String("شكرًا لاشتراكك في <servicename> للاستمتاع بزيارة <portalurl>. سيتم محاسبتك على <price> <currency> / <billing_sequence> لإلغاء الاشتراك ، أرسل <unsub_keyword> إلى <shortcode> مجانًا. للاستفسارات ، اتصل على vas.support@vacastudios.com");
	public static final String WELCOME_MESSAGE_SMS_ENG = "You have subscribed to <servicename> for <price> <currency>/<billing_sequence>, auto-renewed & consumes from internet. Visit <portalurl>. To cancel, send CANCEL <unsub_keyword> to <shortcode> for free.";
	public static final String WELCOME_MESSAGE_SMS_ARB = new String("لقد اشتركت في <servicename> من أجل <price> <currency> / <billing_sequence> ، وتجدد تلقائيًا وتستهلك من الإنترنت. قم بزيارة <portalurl>. للإلغاء ، أرسل CANCEL <unsub_keyword> إلى <shortcode> مجانًا.");
	public static final String CONTENT_MESSAGE_SMS_ENG = "Please access the content using <portalurl> URL. For queries, contact on vas.support@vacastudios.com";
	public static final String CONTENT_MESSAGE_SMS_ARB = "يرجى الوصول إلى المحتوى باستخدام <portalurl> URL. للاستفسارات ، اتصل على vas.support@vacastudios.com";

	
	
	
	static String getMsisdnByOperatorCode(String operatorCode) {
		
		switch(operatorCode) {
			case "60202":
				return "201063721848";
			case "60201":
				return "201286438693";
			case "60203":
				return "201147213428";
			case "60204":
				return "201558802080";
			default :
				return "";
		}
	}
}
