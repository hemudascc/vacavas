package net.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public interface MConstants {


	//public static SimpleDateFormat sdfDDMMyyyy= new SimpleDateFormat("ddMMyyyy");
	//public static SimpleDateFormat sdfDDMMyyyyHH = new SimpleDateFormat("ddMMyyyyHH");
	public static DateTimeFormatter sdfDDMMyyyy = DateTimeFormatter.ofPattern("ddMMyyyy");
	public static DateTimeFormatter sdfDDMMyyyyHH = DateTimeFormatter.ofPattern("ddMMyyyyHH");

	public static final int DEFAULT_ADNETWORK_ID = 2;	    
	//public static final int TPAY_EGYPT_WE_OPERATOR_ID = 112;

	public static final int TPAY_EGYPT_VODAFONE_OPERATOR_ID = 1;
	public static final int TPAY_EGYPT_ETISALAT_OPERATOR_ID = 3;
	public static final int TPAY_EGYPT_ORANGE_OPERATOR_ID = 2;
	public static final int TPAY_EGYPT_WE_OPERATOR_ID = 4;
	public static final int ACTEL_DU_OPERATOR_ID = 5;
	public static final int MOBIMIND_OOREDOO_OPERATOR_ID = 6;
	public static final int MESSAGE_CLOUD_GATEWAY_CH_SWISSCOM_OPERATOR_ID = 7;
	public static final int MESSAGE_CLOUD_GATEWAY_CH_SALT_OPERATOR_ID = 10;
	public static final int MESSAGE_CLOUD_GATEWAY_CH_SUNRISE_OPERATOR_ID = 11;
	public static final int INTARGET_SAFARICOM_OPERATOR_ID = 8;
	public static final int MOBIMIND_KUWAIT_VIVA_OPERATOR_ID = 9;
	public static final int MONDIA_PAY_MTN_OPERATOR_ID = 12;
	public static final int OREDOO_KUWAIT_OPERATOR_ID=13;
	public static final int ALTRUIST_ETISALAT_UAE_OPERATOR_ID=14;
	public static final int MICROKIOSK_AIS_OPERATOR_ID=15;
	  
	public static final String CMPID="evid";
	public static final String ACTIVE="ACTIVE";
	public static final String INACTIVE="INACTIVE";

	public static final Random random=new Random();
	public final String INFO = "INFO";
	public static final String DCT_BLOCK_PREFIX="DCT_BLOCK_PREFIX";
	public final int AMQ_SCHDULE_DELAY= 30*60*1000;
	public  static final int WASTE_SERVICE_ID=100;
	public static final DateFormat dfYYYYMMDDhhmmTZD=new SimpleDateFormat("YYYY-MM-DD'T'hh:mmZ");

	public static final DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");

	public static final String KEY_SEPERATOR="-";
	public static final int SUBSCRIBED = 1;
	public static final String SUBSCRIBED_DESC = "SUBSCRIBED";

	public static final int UNSUBSCRIBED = 0;
	public static final String UNSUBSCRIBED_DESC = "UNSUBSCRIBED";

	public static final int SUBSCRIPTION_FAILED = -1;
	public static final String SUBSCRIPTION_FAILED_DESC = "SUBSCRIPTION_FAILED";

	public static final int GRACE_STATUS = 10;







	public static final String REDIRECT_TO_ERROR = "REDIRECT_TO_ERROR";
	public static final String REDIRECT_TO_WASTE_URL= "REDIRECT_TO_WASTE_URL_MSISDN_MISSING";
	public static final String REDIRECT_TO_WASTE_URL_COMAPIGN_BLOCK = "REDIRECT_TO_WASTE_URL_COMAPIGN_BLOCK";
	public static final String REDIRECT_TO_WASTE_URL_BLOCKSERIES = "REDIRECT_TO_WASTE_URL_BLOCKSERIES";
	public static final String REDIRECT_TO_WASTE_URL_BLOCK_CIRCLE= "REDIRECT_TO_WASTE_URL_BLOCKCIRCLE";
	public static final String REDIRECT_TO_WASTE_URL_DUPLICATE_MISISDN="REDIRECT_TO_WASTE_URL_DUPLICATE_MISISDN";
	public static final String  REDIRECT_TO_WASTE_URL_PUB_ID_BLOCK="REDIRECT_TO_WASTE_URL_PUB_ID_BLOCK";
	public static final String  REDIRECT_TO_WASTE_URL_DCT_BLOCK="REDIRECT_TO_WASTE_URL_DCT_BLOCK";
	public static final String  REDIRECT_TO_WASTE_URL_WRONG_MSISDN_FORMAT="REDIRECT_TO_WASTE_URL_WRONG_MSISDN_FORMAT";
	public static final String REDIRECT_TO_WASTE_URL_DUE_TO_CHARGING_TYPE_BLOCK = "REDIRECT_TO_WASTE_URL_DUE_TO_CHARGING_TYPE_BLOCK";
	public static final String REDIRECT_TO_WASTE_URL_ALREADY_SUBSCRIBED = "REDIRECT_TO_WASTE_URL_ALREADY_SUBSCRIBED";
	public static final String REDIRECT_TO_HOME_PAGE_COMAPIGN_NOT_FOUND = "REDIRECT_TO_HOME_PAGE_COMAPIGN_NOT_FOUND";
	public static final String REDIRECT_TO_CG = "REDIRECT_TO_CG";
	public static final String PIN_SEND = "PIN_SEND";
	public static final String PIN_VALIDATE = "PIN_VALIDATE";
	public static final String TOKEN_SEPERATOR = "c";


	//public static final int DEFAULT_CIRCLE_ID = 500;

	public static final String REDIRECT_TO_WASTE_URL_CONVERSION_CAPPING= "REDIRECT_TO_WASTE_URL_CONVERSION_CAPPING";
	public static final String REDIRECT_TO_WASTE_URL_IP_NOT_MATCHING="REDIRECT_TO_WASTE_URL_IP_NOT_MATCHING";
	public static final String REDIRECT_TO_WASTE_URL_EARLIER_CHARGED= "REDIRECT_TO_WASTE_URL_EARLIER_CHARGED";


	public static final String DEFAULT_CIRCLE_CODE = "DEFAULT";


	public final int ALL_OPERATOR_ID=10000;
	public final String CHARGED="CHARGED";
	public final int ALL_ADNETWORK_ID=10000;
	public final int ALL_CIRCLE_ID=10000;
	//public final int DU_OPERATOR_ID=1;


	public final int PROCESS_IN_PROGRESS=1;
	public final String PROCESS_IN_PROGRESS_DESC="IN_PROCESS";
	public final int PROCESS_IN_COMPLETE=2;
	public final String PROCESS_IN_COMPLETE_DESC="COMPLETE";
	public final int PROCESS_IN_ERROR=3;
	public final String PROCESS_IN_ERROR_DESC="ERROR";
	public static final String MANUAL_CRON="MANUAL_CRON";
	public static final String AUTOSENT_CRON="AUTOSENT_CRON";
	public static final int CHURN_HOUR=24;

	public static String getCappingKey(Integer operatorId,Integer adnetworkId,Integer circleId){		
		return MConstants.dateFormat.format(new Timestamp(System.currentTimeMillis()))
				+KEY_SEPERATOR+operatorId+KEY_SEPERATOR+adnetworkId+KEY_SEPERATOR+circleId;
	}

	public static String getAdnetworkTypeConfigKey(String type,Integer operatorId,Integer adnetworkId){		
		return type+KEY_SEPERATOR+operatorId+KEY_SEPERATOR+adnetworkId;	
	}
	public static final String DEFAULT_CIRCLE = "17";
	public static final int DEFAULT_CIRCLE_ID = 17;
	public static final int DEFAULT_ADNETWORK_CAMPAIGN_ID = -1;
	public static final String DEFAULT_PUB_ID = "-1";
	public static final int DEFAULT_OP_ID = -1;
	public static final String DEFAULT_CLICK_TYPE = "-1";

	public static final String DEFAULT_ADNETWORK_CAMPAIGN_NAME = "DEFAULT";
	public final String ACT = "ACT";
	public final String ALREADY_SUBSCRIBED = "ALREADY_SUBSCRIBED";
	public final String TEMPORARY_ACT = "TEMPORARY_ACT";
	public final String PARK_TO_ACT = "PARK_TO_ACT";
	public final String GRACE = "GRACE";
	public final String DCT = "DCT";
	public final String RENEW = "RENEW";
	public final String CHURN = "CHURN";
	public final String BLOCKED = "BLOCKED";
	public final String UNBLOCKED = "UNBLOCKED";
	public final String POSTPAID_RESPONSE = "Postpaid:Active";
	public final int BLOCK_CHARGING_TYPE_ALL_ADNETWORK=10000;
	public final int BLOCK_CHARGING_TYPE_ALL_CIRCLE=10000;

	public static final String DAILY_REPORT_TYPE="DAILY";
	public static final String MONTHLY_REPORT_TYPE="MONTHLY";

}




