package net.mycomp.oredoo.kuwait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import net.util.MConstants;

public interface OredoKuwaitConstant {

	 static final Logger logger = Logger
			.getLogger(OredoKuwaitConstant.class.getName());
	
	 public static final String OREDOO_KUWAIT_CG_SERVICE_ID_PREFIX="OREDOO_KUWAIT_CG_SERVICE_ID_PREFIX";
	public static final String SUBCRIPTION_API="SUBCRIPTION_API";
	public static final String UNSUBCRIPTION_API="UNSUBCRIPTION_API";
	public static final String SMS_API="SMS_API";
	public static final String CHECK_SUB_API="CHECK_SUB_API";
	
	public static final String UNSUBCRIPTION_API_RESULT="DBILL:Ok, Accepted";
	
	public static Map<Integer,OredooKuwaitServiceConfig> mapServiceIdToOredooKuwaitServiceConfig=new HashMap<Integer,OredooKuwaitServiceConfig>();
	
	
	public static Map<String,OredooKuwaitServiceConfig> mapPlanIdToOredooKuwaitServiceConfig
	  =new HashMap<String,OredooKuwaitServiceConfig>();
	
	
	public static Map<Integer,List<OredoKuwaitFallbackPricePoint>> mapServiceIdToListOredoKuwaitFallbackPricePoint=new HashMap<Integer,List<OredoKuwaitFallbackPricePoint>>();
	
	public AtomicInteger oredooKuwaitOCSLogDetailId=new AtomicInteger(0);
	
	public static final String RESULT_SUCCESS="SUCCESS";
	public static final String RESULT_FAIL="FAIL";
	public static final String RESULT_REJECT="REJECT";	
	public static final String CACHE_PREFIX_TRANSID="OREDOO";
	public static final String MOBILE_NUMBER_PREFIX="965";
	
	public static final String PREPAID="2";
	public static final String POSTPAID="1";
	
	public static final int POSTPAID_MONTHLY_SERVICE_ID=25;
	public static final int PREPAID_WEEKLY_SERVICE_ID=23;
	public static final int PREPAID_DAILY_SERVICE_ID=24;
	
	
	public static final int KIDOKINGDOM_POSTPAID_MONTHLY_SERVICE_ID=94;
	public static final int KIDOKINGDOM_PREPAID_WEEKLY_SERVICE_ID=93;
	public static final int KIDOKINGDOM_PREPAID_DAILY_SERVICE_ID=92;
	
	public static String findAction(String operationId){
		
		if(operationId==null){
			return "";
		}
		String action="";		
		switch(operationId){		
		
		case "SN":
		case "SR":
		case "RN":
		//case "SP":
		{
			action=MConstants.ACT;
			break;
		}
		case "YR":
		case "GR":
		case "RR":
		
		{
			action=MConstants.RENEW;
			break;
		}
		case "SS":
		{
			action=MConstants.DCT;
			break;
		}
		
		case "YG":
		case "SP":{			
			action=MConstants.GRACE;
			break;
		}
		
		case "YS":
		case "GS":
		case "RS":
		case "PS":	
		{
			action=MConstants.CHURN;
			break;
		}
		case "SH":
		{
			action=MConstants.BLOCKED;
			break;
		}
	}
	return action;	
	}

	public static int findServiceId(String subType,int requestServiceId,int productId){
		int serviceId=0;
		try{
			if(productId==10){
			if(subType.equals(OredoKuwaitConstant.PREPAID)){
				serviceId=requestServiceId;
				
				switch(requestServiceId){
				case OredoKuwaitConstant.PREPAID_DAILY_SERVICE_ID:						
				case OredoKuwaitConstant.PREPAID_WEEKLY_SERVICE_ID:
					break;
				default:
					serviceId=OredoKuwaitConstant.PREPAID_WEEKLY_SERVICE_ID;
				}
				
			}else if(subType.equals(OredoKuwaitConstant.POSTPAID)){
				
				 serviceId=OredoKuwaitConstant.POSTPAID_MONTHLY_SERVICE_ID;
			}
			}else{
				if(subType.equals(OredoKuwaitConstant.PREPAID)){
					serviceId=requestServiceId;		
					
					switch(requestServiceId){
					case OredoKuwaitConstant.KIDOKINGDOM_PREPAID_DAILY_SERVICE_ID:						
					case OredoKuwaitConstant.KIDOKINGDOM_PREPAID_WEEKLY_SERVICE_ID:
						break;
					default:
						serviceId=OredoKuwaitConstant.KIDOKINGDOM_PREPAID_WEEKLY_SERVICE_ID;
					}
					
				}else if(subType.equals(OredoKuwaitConstant.POSTPAID)){
					
					 serviceId=OredoKuwaitConstant.KIDOKINGDOM_POSTPAID_MONTHLY_SERVICE_ID;
				}
			}
			
			
	}catch(Exception ex){
		logger.error("findServiceId: ",ex);
	}
		return serviceId;
	}
	
	
	public static OredooKuwaitServiceConfig findOredooKuwaitServiceConfigByPlanId(String planId){
		
		OredooKuwaitServiceConfig oredooKuwaitServiceConfig=null;
		try{
			
			oredooKuwaitServiceConfig=mapPlanIdToOredooKuwaitServiceConfig.get(planId);
			if(oredooKuwaitServiceConfig==null){
				oredooKuwaitServiceConfig=mapServiceIdToOredooKuwaitServiceConfig.get(OredoKuwaitConstant.PREPAID_WEEKLY_SERVICE_ID);
				
			}
		
	}catch(Exception ex){
		logger.error("findOredooKuwaitServiceConfigByPlanId: ",ex);
	}
		return oredooKuwaitServiceConfig;
	}
	
	
	
	public static String formatMsisdnRemove965(String msisdn){
		
		if(msisdn!=null&&msisdn.startsWith("965")){
		msisdn=msisdn.substring(3);
		}
		return msisdn;
	}
	
public static String formatMsisdnAdd965(String msisdn){
		
		if(msisdn!=null&&!msisdn.startsWith("965")){
		msisdn="965"+msisdn;
		}
		return msisdn;
	}

public static String getMsg(OredooKuwaitServiceConfig oredooKuwaitServiceConfig,
		String msgTemplate,double amount,int validity){
	try{
		return msgTemplate
				.replaceAll("<servicename>", oredooKuwaitServiceConfig.getServiceName())
				.replaceAll("<amount>",""+ amount)
				.replaceAll("<days>", ""+validity)
				.replaceAll("<subkeyword>", oredooKuwaitServiceConfig.getSubKeyword())
				.replaceAll("<unsubkeyword>", oredooKuwaitServiceConfig.getUnsubKeyword())
				.replaceAll("<shortcode>", oredooKuwaitServiceConfig.getShortCode())
				.replaceAll("<servicename>", oredooKuwaitServiceConfig.getServiceName());
	}catch(Exception ex){
		logger.error("getMsg ",ex);
	}
	return msgTemplate;
}

public static String getPortalUrl(String portalUrl,
		String msisdn,int subId){
	
	try{
		return portalUrl
				.replaceAll("<msisdn>",msisdn)
				.replaceAll("<subid>",""+ subId);	
		
	}catch(Exception ex){
		logger.error("getMsg ",ex);
	}
	return portalUrl;
}

}


