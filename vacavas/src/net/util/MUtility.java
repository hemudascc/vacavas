package net.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;







import net.common.service.IpCheckService;
import net.persist.bean.IPPool;
import net.persist.bean.TrafficRoutingOperatorDetails;

public class MUtility {
	
	
	@Autowired
	private IpCheckService ipCheckService;
	
	private static Random random = new Random();
	private static final Logger logger = Logger.getLogger(MUtility.class.getName());

	public static int noOfDaysDiffrence(Timestamp ts1,Timestamp ts2) {

		try {		
	       return (int)ChronoUnit.DAYS.between( ts1.toLocalDateTime().toLocalDate(),
	    		   ts2.toLocalDateTime().toLocalDate());
	       
		} catch (Exception ex) {
			logger.error("noOfDaysDiffrence:::exception"+ex);
		}
		return 0;
	}
	
	
	public static long timeDiffrence(Timestamp ts1,Timestamp ts2) {

		try {		
	       return ts2.getTime()-ts1.getTime();
	       
		} catch (Exception ex) {
			logger.error("noOfDaysDiffrence:::exception"+ex);
		}
		return 0;
	}
	
	
	public static void setSession(HttpServletRequest request,String msisdn) {
		request.getSession().setAttribute("msisdn", msisdn);
		request.getSession().setMaxInactiveInterval(20000);
	}
	
	public static boolean isChurn(int operatorId,Timestamp subDate) {

		try {
			
			long hour =-1;
			LocalDateTime today = LocalDateTime.now();
          	LocalDate today2 = LocalDate.now();
				hour = ChronoUnit.HOURS.between(subDate.toLocalDateTime().toLocalDate(),today2);	
				if(hour<=24){
					logger.debug("other operator churn ::operatorId:"+operatorId+"::"+hour);
					return true;	
				}	
		} catch (Exception ex) {
			logger.error("isChurn:::exception"+ex);
		}
		return false;
	}

//	public static boolean isChurn(int operatorId,Timestamp subDate) {
//
//		try {
//			
//			long days =-1;
//			LocalDateTime today = LocalDateTime.now();
//          	LocalDate today2 = LocalDate.now();
//				days = ChronoUnit.DAYS.between(subDate.toLocalDateTime().toLocalDate(),today2);	
//				if(days==0){
//					logger.debug("other operator churn ::operatorId:"+operatorId+"::"+days);
//					return true;	
//				}
//		
//			
//		} catch (Exception ex) {
//			logger.error("isChurn:::exception"+ex);
//		}
//		return false;
//	}
	

	public static long randomNumber(long lowerlimit, long upperlimit) {
		return random.longs(1, lowerlimit, upperlimit).findFirst().getAsLong();
	}

	

	

	
	public static List<String> findBlockSeriesCode(String msisdn) {

		List<String> list = new ArrayList<String>();
		if (msisdn.length() > 10) {
			msisdn = msisdn.substring(2);
			
		}

		if (msisdn != null && msisdn.length() == 10) {
			list.add(msisdn);
		}

		if (msisdn != null && msisdn.length() > 4) {
			list.add(msisdn.substring(0, 5));
		}
		if (msisdn != null && msisdn.length() > 6) {
			list.add(msisdn.substring(0, 6));
		}

		return list;
	}

	public static String findCircleSeriesCode6Digit(String msisdn) {
		return msisdn.substring(0, 6);

	}
	public static String findCircleSeriesCode7Digit(String msisdn) {
		return msisdn.substring(0, 7);

	}

	public static String formatMsisdn(String msisdn) {

		if (msisdn != null && msisdn.length() == 10) {
			msisdn = "91" + msisdn;
		}
		return msisdn;
	}

	public static int hourDiffrence(Timestamp t1) {
		try {
			return (int) ((System.currentTimeMillis() - t1.getTime()) / (1000 * 60 * 60));
		} catch (Exception ex) {
		}
		return 0;
	}

	public static Integer toInt(String str, Integer defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception ex) {
		}
		return defaultValue;
	}
	
	public static long toLong(String str, long defaultValue) {
		try {
			return Long.parseLong(str);
		} catch (Exception ex) {
		}
		return defaultValue;
	}
	
	public static Double toDouble(String str, double defaultValue) {
		try {
			return Double.parseDouble(str);
		} catch (Exception ex) {
		}
		return defaultValue;
	}

	public static boolean toBoolean(String str, boolean defaultValue) {
		try {
			if(str!=null){
				
			return Boolean.valueOf(str);
			}
		} catch (Exception ex) {
		}
		return defaultValue;
	}

	public static boolean trafficRouting(AtomicInteger atomicTrafficRouting, Integer trafficRoutingCounter) {
		try {
			return atomicTrafficRouting.getAndUpdate(n -> ((n == trafficRoutingCounter) ? 1 : n + 1))
					% trafficRoutingCounter > 0;
		} catch (Exception ex) {
		}
		return false;
	}

	public static String urlEncoding(String url, String token) {
		try {
			url = url.replaceAll("<token>", URLEncoder.encode(token, "utf-8"));
		} catch (Exception ex) {
		}
		return url;
	}
	
	
	public static String urlEncoding(String value) {
		try {
			if(value==null){
				return "";
			}
			return  URLEncoder.encode(value, "utf-8");
		} catch (Exception ex) {
		}
		return value;
	}

	public static String getDomainName(String url) throws MalformedURLException {
		String host = null;
		if (url != null) {
			URL netUrl = new URL(url);
			host = netUrl.getHost();

			if (host != null) {
				host = host.replaceAll("https://", "").replaceAll("http://", "").replaceAll("www", "");
			}
		}
		return host;
	}
	 public static String getBase64DecodedString(String str){
	        byte[] byteArray = Base64.decodeBase64(str.getBytes());
	        String decodedString = new String(byteArray);
	        return decodedString;
	        
	    }
	    public static String getBase64EncodedString(String str){
	        byte[] byteArray = Base64.encodeBase64(str.getBytes());
	        String encodedString = new String(byteArray);
	        return encodedString;	        
	    }
	    
	    public static Long strToLong(String str,long defaultVal){
	    	 Long retVal=defaultVal;
	        try{
	            retVal=Long.parseLong(str);
	        }catch(Exception e){
	            retVal=defaultVal;
	        }
	        return retVal;
	    }
	    
	     public static String formatString(String strVal,String defaultVal){
	         strVal=(strVal==null)?defaultVal:strVal.trim();
	         return strVal;
	     }
	    
	     public static String find12DigitMobileNo(String msisdn) {

	 		if (msisdn != null && msisdn.length() ==10) {
	 			return "91"+msisdn;
	 		}
	 		return msisdn;
	 	}
	     
	     
	     public static Timestamp addNumberOfDay(Timestamp ts,int noOfDay){
	    		Timestamp ts2 =null;
	    		  try{
	    			LocalDateTime time = LocalDateTime.ofInstant(ts.toInstant(), ZoneOffset.ofHours(0));
	    			time = time.plus(noOfDay, ChronoUnit.DAYS);
	    			//Convert back to instant, again, no time zone offset.
	    			Instant output = time.atZone(ZoneOffset.ofHours(0)).toInstant();
	    			 ts2 = Timestamp.from(output);
	    			}catch(Exception ex){
	    				
	    			}
	    			return ts2;
	    		}
	     

	     public static long getHourDiffrence(Timestamp ts1,Timestamp ts2){
	    	 if(ts1==null){
	    		 return -1;
	    	 }
	    	 if(ts2==null){
	    		 return -2;
	    	 }
	    	 
			return new Period(ts1.getTime(),ts2.getTime()).getHours();
					
				 
	     }
	     
	     public static String removeNull(String s1,String defaultStr){
	    	if(s1!=null){
	    		return s1.trim();
	    	}
			return defaultStr;	 
	     }
	     
	     public String[] parserTokenToCG(String tokenToCG){
	    	// adnetworkToken.setTokenToCg(adnetworkToken.getReqTimeLong()
	 		//		+ MConstants.TOKEN_SEPERATOR + adnetworkToken.getTokenId()
	 		//		+ MConstants.TOKEN_SEPERATOR
	 		//		+ adNetworkRequestBean.vwserviceCampaignDetail.getCampaignId());
	    	 
	    	 return tokenToCG.split(MConstants.TOKEN_SEPERATOR);
	     }
	   
	     
	     public static String find11DigitMobileNo(String msisdn) {
	 		if (msisdn != null) {
	 			return msisdn;
	 		}
	 		return "";
	 	}
	     
	     public static String find7DigitMobileNo(String msisdn) {

		 		if (msisdn != null && msisdn.length() >=7) {
		 			return msisdn.substring(0,7);
		 		}		 		
		 		return msisdn;
		 	}
	     
	     
	     public static void main(String arg[]) {
	    	 List<String> list=Arrays.asList(new String[]{
					  MUtility.find7DigitMobileNo("26834600152"),
					  MUtility.find11DigitMobileNo("26834600152")					
					  });
	    	 
	    	 System.out.println(list);
	    	 System.out.println("is param1:: "+toBoolean("123", true));
	    	 
//	 		for (int i = 0; i < 100; i++) {
//	 		System.out.println(randomNumber(0,1));
//	 		}
//	 		int skipReverseNumber = 2;
//	 		AtomicInteger atomicActReverseSkipNumber = new AtomicInteger(1);
	 //
//	 		for (int i = 0; i < 10; i++) {
//	 			boolean isSendToAdnetwork = (atomicActReverseSkipNumber.getAndUpdate(n -> n < skipReverseNumber ? n + 1 : 1)
//	 					% skipReverseNumber) == 0;
//	 			System.out.println("isSendToAdnetwork:: " + isSendToAdnetwork);
//	 		}

	 	}
	     
	     
	     public static String getStringFromRequest(ServletInputStream sis){
	 		StringBuilder sb = new StringBuilder();
	 		try {
	 			
	 	        java.io.BufferedReader bis = new java.io.BufferedReader(new java.io.InputStreamReader(sis));
	 	        String line = "";
	 	        while ((line = bis.readLine()) != null) {
	 	            sb.append(line);
	 	        }
	 	        bis.close();
	 	    } catch (Exception e) {
	 	    	logger.error("getStringFromRequest:: exception::::: ",e);
	 	       
	 	    }finally{
	 	    	if(sis!=null){
	 	    		try {
	 					sis.close();
	 				} catch (IOException e) {
	 					logger.error("getStringFromRequest::finnally:: exception:::::",e);
	 				}
	 	    	}
	 	    }
	 		return sb.toString();
	 	}
	     
	     public static int gcd(int numberOne, int numberTwo) {
		 	    return (numberTwo == 0) ? numberOne : gcd(numberTwo, numberOne % numberTwo);
		 	}
	     
	     
	     public static int getDayDiffrence(Timestamp ts1,Timestamp ts2){
	    	 if(ts1==null){
	    		 return -1;
	    	 }
	    	 if(ts2==null){
	    		 return -2;
	    	 }	    	 
			return new Period(ts1.getTime(),ts2.getTime()).getDays();
			
	     }
	     
	     
	     public static String removeNull(String param){
	    	 if(param==null)return "";
	    	 return param;
	     }
	     
	     }
	  

