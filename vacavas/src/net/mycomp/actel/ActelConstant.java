package net.mycomp.actel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public interface ActelConstant {

	 static final Logger logger = Logger
			.getLogger(ActelApiService.class.getName());
	
	public static final String MSISDN_PREFIX="971";
	public static final String SUB="SUB";
	public static final String UNSUB="UNSUB";
	public static final String RENEW="REN";
	public static final String ACTEL_DU_MSISDN_CACHE_PREFIX = "ACTEL_DU_MSISDN_CACHE_PREFIX";
	public static final String ACTEL_OTP_VALIDATION_CACHE="ACTEL_OTP_VALIDATION_CACHE";
	public static final String MODE_WEB="web";
	public static final String MOBILE_WEB="mobile";
	//Du
	public static final String ACTIVE="ACTIVE";
	
	public  static final List<ActelServiceConfig> listActelConfig=new ArrayList<ActelServiceConfig>();
	public  static final Map<Integer,ActelServiceConfig> mapServiceIdToActelServiceConfig
	=new  HashMap<Integer,ActelServiceConfig>();
	
	public static AtomicInteger actlelApiTransId=new AtomicInteger(0);
	
	
	
	
	public static Map<String,String> parseXml(String str) {
        String rootPath = "/xml/Service/";
       Map<String,String> map=new HashMap<String,String>();
        try {
            //Create DocumentBuilderFactory for reading xml file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(str.getBytes());
            Document doc = builder.parse(stream);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            XPathExpression xPathExpr = xpath.compile(rootPath + "Status/text()");
            Object result = xPathExpr.evaluate(doc, XPathConstants.STRING);
            map.put("status",result.toString());

           	            
        } catch (Exception e) {
           // logger.error("AirtelApiResponseWraper:: parseChargingActivationCallBack() :: Exception " , e);
        }
        return map;
    }
	
	public static String getPortalUrl(String portalUrl,String msisdn){
		try{
			return portalUrl
				
					.replaceAll("<msisdn>", msisdn);
					
		}catch(Exception ex){
			logger.error("Exception ",ex);
		}
		return portalUrl;
	}
	
	public static String formatMsisdn(String msisdn){
		try{
			
			msisdn=msisdn.replace("+", "");
			for(int i=0;i<=5;i++){
			if(msisdn.startsWith("0")){
				msisdn=msisdn.substring(1);
			}			
			}
			msisdn= msisdn.startsWith(MSISDN_PREFIX)?msisdn:MSISDN_PREFIX+msisdn;		
		
		}catch(Exception ex){
			
		}
		return msisdn;		
	}
	
	
	public static boolean isValidMsisdn(String msisdn){
		boolean valid=true;
		try{
			
			if(msisdn==null){
				valid= false;
			}
			if(msisdn.equalsIgnoreCase(MSISDN_PREFIX)){
				valid= false;
			}
			
			if(!msisdn.startsWith(MSISDN_PREFIX)){
				valid= false;
			}
			//971544576350
			if(msisdn.length()!=12){
				valid= false;
			}
			if(!valid){
				return valid;
			}
			
		}catch(Exception ex){
			return false;
		}
		logger.info("isValidMsisdn::: msisdn: "+msisdn+" , valid: "+valid);
		return true;
	}
	
	public static void main(String arg[]) throws Exception{
//		 String content = new String(Files.readAllBytes(
//				 Paths.get("C:\\Users\\mobitize\\Desktop\\temp\\17092019\\a.xml")));
//					 Map map= parseXml(content);
//		 System.out.println("sendPin:::::::: "+map);
		 System.out.println("forat msisdn:::::::: "+isValidMsisdn(formatMsisdn("+0971")));
				
		
		
	} 
	
}