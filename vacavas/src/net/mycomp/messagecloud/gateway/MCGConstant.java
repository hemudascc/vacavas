package net.mycomp.messagecloud.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import net.util.MConstants;

public interface MCGConstant {

	 static final Logger logger = Logger
			.getLogger(MCGConstant.class);

	 public final static Object lock=new Object();
	 
	 public final static String MSG_CLOUD_SUBINFO_CONTENT_CACHE_PREFIX="MSG_CLOUD_SUBINFO_CONTENT_CACHE_PREFIX";
	 
	public static final String INVALID_KEY="INVALID";
	public static final String SUB_KEY="SUB";
	public static final String UNSUB_KEY="UNSUB";
	
	public static final String START_KEY="start";
	public static final String STOP_KEY="stop";
	
	public final static String MSG_CLOUD_CACHE_PREFIX="MSG_CLOUD_CACHE_PREFIX";
	
	public final static String SUBINFO_CONTENT_MSG_TYPE="SUBINFO_CONTENT";
	public final static String ALREADY_SUBSCRIBED_CONTENT_MSG_TYPE="ALREADY_SUBSCRIBED_CONTENT";
	public final static String WELCOME_CONTENT_MSG_TYPE="CONTENT";
	
	public final static String BILLLED_MSG_TYPE1="BILLED1";
	public final static String BILLLED_MSG_TYPE2="BILLED2";
	public final static String BILLLED_MSG_TYPE3="BILLED3";
	
	public static List<MCGServiceConfig> listMCGServiceConfig=new ArrayList<MCGServiceConfig>();
	
	public static Map<Integer,MCGServiceConfig> mapIdToMCGServiceConfig=new HashMap<Integer,MCGServiceConfig>();
	public static Map<Integer,MCGServiceConfig> mapServiceIdToMCGServiceConfig=new HashMap<Integer,MCGServiceConfig>();
	
	//public static Map<String,MCGServiceConfig> mapKeywordNetworkToMCGServiceConfig=new HashMap<String,MCGServiceConfig>();
	
	public static Map<String,MccMncNetwork> mapMccMncToMccMncNetwork=new HashMap<String,MccMncNetwork>();
	
	
	public static AtomicInteger inMCGMTMessageIdAtomicInteger=new AtomicInteger(0);
	

	
	
	public static MCGServiceConfig findMCGServiceConfig(String text,String network,String shortCode){
		
		MCGServiceConfig mcgServiceConfig=null;
		try{
		   String msg[]=text.trim().toLowerCase().split(" ");
//		    mcgServiceConfig=
//				   mapKeywordNetworkToMCGServiceConfig.get(msg[1]+network.toLowerCase());
		    mcgServiceConfig=mapIdToMCGServiceConfig.get(2);
		  
		}catch(Exception ex){
			logger.error("exception ",ex);
		}finally{
			try{
			if(mcgServiceConfig==null){
				for(MCGServiceConfig config:listMCGServiceConfig){
					if(config.getShortCode().equalsIgnoreCase(shortCode)
							&&config.getNetwork().equalsIgnoreCase(network)){
						mcgServiceConfig=config;
						break;
					}
				}
			}
			}catch(Exception ex){
				logger.error("finally ",ex);
			}	
		}
		return mcgServiceConfig;
	}
	
	
	public static MCGFallbackPricePointConfig 
	findMCGFallbackPricePointConfig(MCGServiceConfig mcgServiceConfig,
			Double billingFailedAmount){
		
		try{
//		if(billingFailedAmount==null){
//			return mcgServiceConfig.getListMCGFallbackPricePointConfig().get(0);
//		}
		if(billingFailedAmount==null){
		 for(MCGFallbackPricePointConfig tmp:mcgServiceConfig.getListMCGFallbackPricePointConfig()){
		   if(tmp.getMcgServiceConfigId().intValue()==mcgServiceConfig.getId()){
			return tmp;
			}
		 }		 
	    }else{
	    	MCGFallbackPricePointConfig tmp=null;
	    	int i=0;
	    	for(;i<mcgServiceConfig.getListMCGFallbackPricePointConfig().size();i++){
	    		tmp=mcgServiceConfig.getListMCGFallbackPricePointConfig().get(i);
	 		   if(tmp.getPricePoint().doubleValue()==billingFailedAmount){
	 			break;
	 			}
	 		 }
	    	return mcgServiceConfig.getListMCGFallbackPricePointConfig().get(++i);
	    }
	
		}catch(Exception ex){
			logger.error("exception ",ex);
		}
		return null;
	}
	
	public static String getMsg(String msgTemplate,MCGServiceConfig mcgServiceConfig,
			Double amount,Integer validity,String  msisdn){
		
		try{
		
	   if(msgTemplate==null){
			return null;
		}
		return msgTemplate.replaceAll("<currency>", Objects.toString(mcgServiceConfig.getCurrency()))
		 .replaceAll("<servicename>", Objects.toString(mcgServiceConfig.getServiceName()))
		 .replaceAll("<amount>", Objects.toString(amount))
		 .replaceAll("<validity>", Objects.toString(validity))
		 .replaceAll("<shortcode>", Objects.toString(mcgServiceConfig.getShortCode()))
		 .replaceAll("<portalurl>",Objects.toString(mcgServiceConfig.getPortalurl()+"?msisdn="+msisdn))
		 .replaceAll("<keyword>",Objects.toString(mcgServiceConfig.getKeyword()))
         .replaceAll("<msisdn>",Objects.toString(msisdn));
		
		}catch(Exception ex){
			logger.error("getMsg::: ",ex);
		}
		return msgTemplate;
	}
	
	public static String formatMsisdn(String msisdn){
		try{
			//07717607998
			if(msisdn.startsWith("+")){
				msisdn=msisdn.substring(1);
			}
			if(msisdn.startsWith("0")){
				msisdn=msisdn.substring(1);
			}
	     msisdn= msisdn.startsWith("41")?msisdn:"41"+msisdn;
			
		}catch(Exception ex){
			
		}
		return msisdn;
	}
	
	public static boolean valid(String msisdn){
		try{
			//07717607998
			if(msisdn.startsWith("+")){
				msisdn=msisdn.substring(1);
			}
			if(msisdn.startsWith("0")){
				msisdn=msisdn.substring(1);
			}
	     msisdn= msisdn.startsWith("41")?msisdn:"41"+msisdn;
			if(msisdn.length()==11){//41765005727
				return true;
			}
		}catch(Exception ex){
			
		}
		return false;
	}
	
	public static void main(String arg[]){
		/*
		 * System.out.println(formatMsisdn("+0787472088")); MCGServiceConfig
		 * mcgServiceConfig=mapIdToMCGServiceConfig.get(2); String
		 * action=MCGConstant.findAction("start abo",mcgServiceConfig);
		 * System.out.println(formatMsisdn("action:: "+action));
		 */
		System.out.println(valid("41787472088"));
	}
	public static String findAction(String text,MCGServiceConfig mcgServiceConfig){
		try{
		  // String msg[]=text.split(" ");
		   if(text.toLowerCase().contains(STOP_KEY)
				 //  &&text.contains(mcgServiceConfig.getKeyword().toLowerCase())
				   ){
			   return MConstants.DCT;
		   }
		   
		   if(text.toLowerCase().contains(START_KEY)
				   &&text.contains(mcgServiceConfig.getKeyword().toLowerCase())
				   ){
			   return MConstants.ACT;
		   }
		}catch(Exception ex){
			logger.info("findAction:: ",ex);
		}
		
		return INVALID_KEY;
	}
	

}
