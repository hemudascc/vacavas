package net.mycomp.macrokiosk.thailand;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("smsService")
public class SmsService {

	private static final Logger logger = Logger.getLogger(SmsService.class);

	
	//@Value("${thialand.mo.send.url}")
	//private String thialandMoSendUrl;
	
	private HttpURLConnectionUtil httpURLConnectionUtil; 
	
	@PostConstruct
	public void init(){
		httpURLConnectionUtil=new HttpURLConnectionUtil();
	}
	
	
	public HTTPResponse sendMOSMS(String url,THMOMessage moMessage){
		//http://mis.etracker.cc/THwap/WAPMORequest.aspx?
		//Telcoid=<telcoid>&Shortcode=<shortcode>&Keyword=<keyword>&refid=<refid>
		try{
		 url=url.replaceAll("<telcoid>",URLEncoder.encode(String.valueOf(moMessage.getTelcoid()),"utf-8"))
				.replaceAll("<shortcode>", URLEncoder.encode(moMessage.getShortcode(),"utf-8"))
				.replaceAll("<keyword>", URLEncoder.encode(moMessage.getKeyword(),"utf-8"))
				.replaceAll("<refid>", URLEncoder.encode(moMessage.getRefId(),"utf-8"));
		return  httpURLConnectionUtil.sendGet(url);
		}catch(Exception ex){
			logger.error("sendMOSMS:: ",ex);
		}
		return null;
	}
	
	
//	public HTTPResponse sendMTSMS(String url,THMTMessage mtMessage){
//		//User=<user>&Pass=<pass>&Type=<type>&To=<to>&From=<from>&Text=<text>
//		//&Price=<price>&Telcoid=<telcoid>&Cat=<cat>&Keyword=<keyword>&Senderid=<senderid>&Linkid=<linkid>
//		try{
//		       Map<String ,String> map=new HashMap<String,String>();		
//     			map.put("user",mtMessage.getUser());
//		        map.put("pass", mtMessage.getPass());
//				map.put("type", mtMessage.getType());
//				map.put("to",mtMessage.getMsisdn());
//				map.put("from", mtMessage.getFromStr());
//				map.put("text",mtMessage.getTextMsg());
//				map.put("price",String.valueOf(mtMessage.getPrice()));
//				map.put("telcoid",String.valueOf(mtMessage.getTelcoId()));
//				map.put("cat",String.valueOf(mtMessage.getCat()));
//				map.put("keyword", mtMessage.getKeyword());
//			    //Uncoment
//				map.put("senderid", mtMessage.getSenderid());
//				map.put("linkid",mtMessage.getLinkId());
//		 //http://mis.etracker.cc/thpush/thpush.aspx?user=macrokiosk
//				//&pass=macro 123&type=0&to=66874111222&from=4541404&
//				//text=welcome MT&price=0&telcoid=3&cat=1&keyword=wall&senderid=4541123&linkid=122920190480972745
//		
//		
//				
//		logger.info("sendMTSMS::::calling url:: "+url+", map:::::: "+map);
//		HTTPResponse response=httpURLConnectionUtil.sendPostRequest(url, map); 
//	    logger.info("sendMTSMS::::calling url::response: "+url+", map:::::: "+map+", response: "+response);
//		 mtMessage.setResponse(response.getResponseCode()+":" +response.getResponseStr());
//		 if(response.getResponseStr()!=null){
//			 //{mobileno}, {msgid}, {status}
//			// mtMessage.setResponse(response);
//			 String arr[]=response.getResponseStr().split(",");
//			 if(arr.length==3&&arr[2].equalsIgnoreCase("200")){
//			   mtMessage.setMsgId(arr[1]);
//			 }
//		 }		  
//		}catch(Exception ex){
//			logger.error("sendMTSMS:: ",ex);
//		}
//		return null;
//	}
	
	
	public HTTPResponse sendMTSMS(String url,THMTMessage mtMessage){
		//User=<user>&Pass=<pass>&Type=<type>&To=<to>&From=<from>&Text=<text>
		//&Price=<price>&Telcoid=<telcoid>&Cat=<cat>&Keyword=<keyword>&Senderid=<senderid>&Linkid=<linkid>
		try{
		       Map<String ,String> map=new HashMap<String,String>();		
     			map.put("user",mtMessage.getUser());
		        map.put("pass", mtMessage.getPass());
				map.put("type", mtMessage.getType());
				map.put("to",mtMessage.getMsisdn());
				map.put("from", mtMessage.getFromStr());
				map.put("text",mtMessage.getTextMsg());
				map.put("price",String.valueOf(mtMessage.getPrice().intValue()));
				map.put("telcoid",String.valueOf(mtMessage.getTelcoId()));
				map.put("cat",String.valueOf(mtMessage.getCat()));
				map.put("keyword", mtMessage.getKeyword());
			    //Uncoment
//				map.put("senderid", mtMessage.getSenderid());
				if(mtMessage.getMessageType().equalsIgnoreCase(ThiaConstant.MT_WELCOME_MESSAGE)) {
				map.put("linkid",mtMessage.getLinkId());
				}
		 //http://mis.etracker.cc/thpush/thpush.aspx?user=macrokiosk
				//&pass=macro 123&type=0&to=66874111222&from=4541404&
				//text=welcome MT&price=0&telcoid=3&cat=1&keyword=wall&senderid=4541123&linkid=122920190480972745
		
//		url=url+"?senderid="+MUtility.urlEncoding(mtMessage.getSenderid())
		url=url+"&pass="+MUtility.urlEncoding(mtMessage.getPass())
				+"&price="+MUtility.urlEncoding(""+mtMessage.getPrice().intValue())
				+"&cat="+MUtility.urlEncoding(""+mtMessage.getCat())
				+"&from="+MUtility.urlEncoding(mtMessage.getFromStr())
				+"&to="+MUtility.urlEncoding(mtMessage.getMsisdn())
				+"&text="+mtMessage.getTextMsg()
				+"&type="+MUtility.urlEncoding(mtMessage.getType())
				+"&keyword="+MUtility.urlEncoding(mtMessage.getKeyword())
				+"&user="+MUtility.urlEncoding(mtMessage.getUser())
				+"&telcoid="+MUtility.urlEncoding(""+mtMessage.getTelcoId())
				;
		if(mtMessage.getMessageType().equalsIgnoreCase(ThiaConstant.MT_WELCOME_MESSAGE)) {
			url +="&linkid="+MUtility.urlEncoding(mtMessage.getLinkId());
		}
				
		logger.info("sendMTSMS::::calling url:: "+url+", map:::::: "+map);
		//HTTPResponse response=httpURLConnectionUtil.sendPostRequest(url, map); 
		HTTPResponse response=httpURLConnectionUtil.sendGet(url); 
		mtMessage.setRequestUrl(url);
	    logger.info("sendMTSMS::::calling url::response: "+url+", map:::::: "+map+", response: "+response);
		 mtMessage.setResponse(response.getResponseCode()+":" +response.getResponseStr());
		 if(response.getResponseStr()!=null){
			 //{mobileno}, {msgid}, {status}
			// mtMessage.setResponse(response);
			 String arr[]=response.getResponseStr().split(",");
			 if(arr.length==3&&arr[2].equalsIgnoreCase("200")){
			   mtMessage.setMsgId(arr[1]);
			 }
		 }		  
		}catch(Exception ex){
			logger.error("sendMTSMS:: ",ex);
		}
		return null;
	}

}
