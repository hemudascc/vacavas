package net.mycomp.intarget;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;
import net.util.MConstants;

import org.apache.log4j.Logger;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("intargetApiService")
public class IntargetApiService {

	private static final Logger logger = Logger
			.getLogger(IntargetApiService.class.getName());
	
	
	private JAXBContext moxyContext;
	
	private HttpURLConnectionUtil httpURLConnectionUtil; 
	
	@Value("${intarget.service.url}")
	private String intargetServiceUrl; 
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private RedisCacheService redisCacheService; 
	
	@PostConstruct
	public void init(){
		try{
		 moxyContext = JAXBContextFactory.createContext(new Class<?>[]{
				InTargetMessageRequest.class,IntargetMessageResponse.class,IntargetNotificationMessage.class}, null);
		
	     
	     httpURLConnectionUtil=new HttpURLConnectionUtil();
		}catch(Exception ex){
			logger.error("init::: ",ex);
		}
	    
	}
	
	public InTargetUssdTrans sendSMS(InTargetConfig inTargetConfig,
			String msisdn,String action,IntargetMessageType intargetMessageType,
			String pricePointCode,String msg,String tag){
		InTargetUssdTrans inTargetUssdTrans=null;
		try{
		
		inTargetUssdTrans=new InTargetUssdTrans(true);
		inTargetUssdTrans.setAction(action);
		inTargetUssdTrans.setMsgType(intargetMessageType.getType());
		inTargetUssdTrans.setMsisdn(msisdn);
		inTargetUssdTrans.setServiceId(inTargetConfig.getServiceId());
		inTargetUssdTrans.setTag(tag);
			
		InTargetMessageRequest inTargetMessageRequest=new InTargetMessageRequest(
        		"SendSMS",
        		String.valueOf(inTargetUssdTrans.getId()), 
        		inTargetConfig.getUserName(),
        		inTargetConfig.getPassword(),
        		msisdn,
        		inTargetConfig.getValidityCode(),
        		intargetMessageType.getFlag(),
        		inTargetConfig.getServiceCode(), 
        		inTargetConfig.getPricePointCode(), 
        		msg,tag				 
        		);
		
		if(intargetMessageType.type.equalsIgnoreCase(IntargetConstant.USSD_MSG)||
				action.equalsIgnoreCase(MConstants.RENEW)){			
		   redisCacheService.putObjectCacheValueByEvictionMinute(
				   IntargetConstant.INTRAGET_TRX_PREFIX+msisdn,
				                               tag,60);
		}
		
		StringWriter sw = new StringWriter();
		Marshaller marshaller = moxyContext.createMarshaller();
	     marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		logger.info("marshaller::: "+marshaller+" ,sw:: "+sw+", inTargetMessageRequest: "+inTargetMessageRequest);
		marshaller.marshal(inTargetMessageRequest,sw);
		inTargetUssdTrans.setRequestStr(sw.toString());
		HTTPResponse httpResponse=httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(intargetServiceUrl,inTargetUssdTrans.getRequestStr());
		inTargetUssdTrans.setResponseStr(httpResponse.getResponseStr());
		Unmarshaller unmarshaller = moxyContext.createUnmarshaller();
		IntargetMessageResponse intargetMessageResponse=(IntargetMessageResponse)unmarshaller.
				unmarshal(new StringReader(httpResponse.getResponseStr()));
		inTargetUssdTrans.setSeqNo(intargetMessageResponse.getSeqNo());
		inTargetUssdTrans.setMsgNo(intargetMessageResponse.getMsgNo());
		
		logger.info("sendSMS:: inTargetUssdTrans:: "+inTargetUssdTrans);
		}catch(Exception ex){
			logger.error("sendSMS:: inTargetUssdTrans:: "+inTargetUssdTrans,ex);
		}finally{
			
			daoService.updateObject(inTargetUssdTrans);	
		}
		return inTargetUssdTrans;
	}
	
	
	public InTargetUssdTrans sendContentSMS(InTargetConfig inTargetConfig,
			String msisdn,String action,IntargetMessageType intargetMessageType,
			String msg,
			String subService,String description, String adultRating){
		
		InTargetUssdTrans inTargetUssdTrans=null;
		try{
			String tag="2";
		inTargetUssdTrans=new InTargetUssdTrans(true);
		inTargetUssdTrans.setAction(action);
		inTargetUssdTrans.setMsgType(intargetMessageType.getType());
		inTargetUssdTrans.setMsisdn(msisdn);
		inTargetUssdTrans.setServiceId(inTargetConfig.getServiceId());
		inTargetUssdTrans.setTag(tag);
			
		InTargetMessageRequest inTargetMessageRequest=new InTargetMessageRequest(
        		"SendSMS",
        		String.valueOf(inTargetUssdTrans.getId()), 
        		inTargetConfig.getUserName(),
        		inTargetConfig.getPassword(),
        		msisdn,
        		inTargetConfig.getValidityCode(),
        		intargetMessageType.getFlag(),
        		"COLCBLK", 
        		"0", 
        		msg,tag,"", msisdn,
        		"",  "0"				 
        		);
		
		if(intargetMessageType.type.equalsIgnoreCase(IntargetConstant.USSD_MSG)||
				action.equalsIgnoreCase(MConstants.RENEW)){			
		   redisCacheService.putObjectCacheValueByEvictionMinute(
				   IntargetConstant.INTRAGET_TRX_PREFIX+msisdn,
				                               tag,60);
		}
		
		StringWriter sw = new StringWriter();
		Marshaller marshaller = moxyContext.createMarshaller();
	     marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		logger.info("marshaller::: "+marshaller+" ,sw:: "+sw+", inTargetMessageRequest: "+inTargetMessageRequest);
		marshaller.marshal(inTargetMessageRequest,sw);
		inTargetUssdTrans.setRequestStr(sw.toString());
		HTTPResponse httpResponse=httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(intargetServiceUrl,inTargetUssdTrans.getRequestStr());
		inTargetUssdTrans.setResponseStr(httpResponse.getResponseStr());
		Unmarshaller unmarshaller = moxyContext.createUnmarshaller();
		IntargetMessageResponse intargetMessageResponse=(IntargetMessageResponse)unmarshaller.
				unmarshal(new StringReader(httpResponse.getResponseStr()));
		inTargetUssdTrans.setSeqNo(intargetMessageResponse.getSeqNo());
		inTargetUssdTrans.setMsgNo(intargetMessageResponse.getMsgNo());
		
		logger.info("sendSMS:: inTargetUssdTrans:: "+inTargetUssdTrans);
		}catch(Exception ex){
			logger.error("sendSMS:: inTargetUssdTrans:: "+inTargetUssdTrans,ex);
		}finally{
			
			daoService.updateObject(inTargetUssdTrans);	
		}
		return inTargetUssdTrans;
	}
	
//	public InTargetUssdTrans sendSMS(InTargetConfig inTargetConfig,
//			String msisdn,String action,IntargetMessageType intargetMessageType,String pricePointCode,String msg,String tag,
//			String subService,String chargeAddr,String description){
//		InTargetUssdTrans inTargetUssdTrans=null;
//		try{
//		
//		inTargetUssdTrans=new InTargetUssdTrans(true);
//		inTargetUssdTrans.setAction(action);
//		inTargetUssdTrans.setMsgType(intargetMessageType.getType());
//		inTargetUssdTrans.setMsisdn(msisdn);
//		inTargetUssdTrans.setServiceId(inTargetConfig.getServiceId());
//		inTargetUssdTrans.setTag(tag);
//			
//		InTargetMessageRequest inTargetMessageRequest=new InTargetMessageRequest(
//        		"SendSMS",
//        		String.valueOf(inTargetUssdTrans.getId()), 
//        		inTargetConfig.getUserName(),
//        		inTargetConfig.getPassword(),
//        		msisdn,
//        		inTargetConfig.getValidityCode(),
//        		intargetMessageType.getFlag(),
//        		inTargetConfig.getServiceCode(), 
//        		inTargetConfig.getPricePointCode(), 
//        		msg,tag, null, null, null				 
//        		);
//		
//		if(intargetMessageType.type.equalsIgnoreCase(IntargetConstant.USSD_MSG)||
//				action.equalsIgnoreCase(MConstants.RENEW)){			
//		   redisCacheService.putObjectCacheValueByEvictionMinute(
//				   IntargetConstant.INTRAGET_TRX_PREFIX+msisdn,
//				                               tag,60);
//		}
//		
//		StringWriter sw = new StringWriter();
//		Marshaller marshaller = moxyContext.createMarshaller();
//	     marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		logger.info("marshaller::: "+marshaller+" ,sw:: "+sw+", inTargetMessageRequest: "+inTargetMessageRequest);
//		marshaller.marshal(inTargetMessageRequest,sw);
//		inTargetUssdTrans.setRequestStr(sw.toString());
//		HTTPResponse httpResponse=httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(intargetServiceUrl,inTargetUssdTrans.getRequestStr());
//		inTargetUssdTrans.setResponseStr(httpResponse.getResponseStr());
//		Unmarshaller unmarshaller = moxyContext.createUnmarshaller();
//		IntargetMessageResponse intargetMessageResponse=(IntargetMessageResponse)unmarshaller.
//				unmarshal(new StringReader(httpResponse.getResponseStr()));
//		inTargetUssdTrans.setSeqNo(intargetMessageResponse.getSeqNo());
//		inTargetUssdTrans.setMsgNo(intargetMessageResponse.getMsgNo());
//		
//		logger.info("sendSMS:: inTargetUssdTrans:: "+inTargetUssdTrans);
//		}catch(Exception ex){
//			logger.error("sendSMS:: inTargetUssdTrans:: "+inTargetUssdTrans,ex);
//		}finally{
//			
//			daoService.updateObject(inTargetUssdTrans);	
//		}
//		return inTargetUssdTrans;
//	}
	
	public IntargetNotificationMessage getIntargetNotificationMessage(String xml){
		try{
			Unmarshaller unmarshaller=moxyContext.createUnmarshaller();
			return (IntargetNotificationMessage)
					unmarshaller.unmarshal(new StringReader(xml));
		}catch(Exception ex){
			logger.error("getIntargetNotification:: ",ex);
		}		
		return null;
	}
	
}
