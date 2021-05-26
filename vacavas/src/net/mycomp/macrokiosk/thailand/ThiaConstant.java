package net.mycomp.macrokiosk.thailand;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public interface ThiaConstant {

	 static final Logger logger = Logger.getLogger(ThiaConstant.class);
	 
	public static AtomicInteger moMessageIdAtomicInteger=new AtomicInteger(0);
	public static AtomicInteger mtMessageIdAtomicInteger=new AtomicInteger(0);
	
	public final String MT_MESSAGE_CAHCHE_PREFIX="THIALAND_MT_MESSAGE_CAHCHE_PREFIX";
	
	public final String MO_MESSAGE_CAHCHE_PREFIX="THIALAND_MO_MESSAGE_CAHCHE_PREFIX";
	
	public final String THIALAND_RETRY_BILLING_PREFIX="THIALAND_RETRY_BILLING_PREFIX";
	public static final DateFormat thialandRetryDateFormat=new SimpleDateFormat("yyyyMMdd");
	
	public static StringBuilder retryBillingDate=new StringBuilder(thialandRetryDateFormat.format(
			new Timestamp(System.currentTimeMillis())));
	//public static StringBuilder retryBillingDate=new StringBuilder();
	 
	public static final List<THConfig> listTHConfig=new ArrayList<THConfig>();
	public static Map<Integer,THConfig> mapIdToTHConfig=new HashMap<Integer,THConfig>();
	 
    public static Map<Integer,THConfig> mapServiceIdToTHConfig=new HashMap<Integer,THConfig>();
   // public static Map<String,THConfig> mapproductIdTelcoIdToTHConfig=new HashMap<String,THConfig>();
    public static Map<String,THConfig> mapKeywordTelcoIdToTHConfig=new HashMap<String,THConfig>();
	
	
    public static final DateFormat yyyyMMddHHmmssAccessToken=new SimpleDateFormat("yyyyMMddHHmmss");
    
	public static final DateFormat ddMMYYYYMA=new SimpleDateFormat("[dd/MM/YYYY: HH:mma]");
	public static final DateFormat dfYYYYMMDDhhmm=new SimpleDateFormat("yyyy-MM-ddhh:mm:ss");
	//public static final DateTimeFormatter ddMMYYYYMA = DateTimeFormatter.ofPattern("dd/MM/YYYY: H:ma");
	public static final String MT_BIILABLE_MESSAGE="MT_BILLABLE";
	public static final String MT_WELCOME_MESSAGE="MT_WELCOME";
	public static final String MT_RENEWAL="HOT";
	
	
	public static final String  MT_TEXT="0";
	public static final String MT_BINARY="1";
	public static final String MT_UCS="5";
	
	public static String convertToHexString(String str){
		String hex=""; 
		try{
		char[]arr= str.toCharArray();
			for(char ch:arr){
				hex+=Integer.toHexString(ch | 0x10000).substring(1);
			}  
		}catch(Exception ex){
			hex=null;
		}
		return hex.toUpperCase();
	}
	
	
	public static String convertToDateTimeFormat(){
		
		try{
		//LocalDateTime local=LocalDateTime.now();
		//TimeZone timeZone=TimeZone.getTimeZone("GMT+7");  
		//ZoneId thiaZoneId = ZoneId.of(timeZone);
		  
		// ZonedDateTime zonedDateTime = local.atZone(timeZone);
		//return "["+ddMMYYYYMA.format(zonedDateTime)+"]";
		//below cooment in 19-04-2019
		//ddMMYYYYMA.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		//return ddMMYYYYMA.format(new Timestamp(System.currentTimeMillis()));
		}catch(Exception ex){
		System.out.println("error:: "+ex);	
		}
		return "";
	}
	
	public static Timestamp convertStringToTimestamp(String time){
		try {
			return new Timestamp(dfYYYYMMDDhhmm.parse(time).getTime());
		} catch (Exception e) {			
			
		}
		return null;
	}
	
	public static String md5(String str){
		try {
			 MessageDigest md = MessageDigest.getInstance("MD5");
			 md.update(str.getBytes());
			 byte digest[]=md.digest();
			 StringBuffer s = new StringBuffer();
		    	for (int i=0;i<digest.length;i++) {
		    		String hex=Integer.toHexString(0xff & digest[i]);
		   	     	if(hex.length()==1) s.append('0');
		   	          s.append(hex);
		    	}   
		    	return s.toString();
		} catch (Exception e) {			
			logger.error("Exception ",e);
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		String str="MKRING4541889201809141555126F0F4D469EAEAD0AC18DA3A460F263B6";
	System.out.println("Md5:: "+md5(str));	

	//System.out.println("mode :: "+ThialandSubModeEnum.getMode("0"));	
	}
	
}
