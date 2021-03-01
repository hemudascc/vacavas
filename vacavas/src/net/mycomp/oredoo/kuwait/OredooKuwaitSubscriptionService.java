package net.mycomp.oredoo.kuwait;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import net.common.service.IDaoService;
import net.persist.bean.SubscriberReg;
import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;
import net.util.MUtility;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service("oredooKuwaitSubscriptionService")
public class OredooKuwaitSubscriptionService {

	private static final Logger logger = Logger
			.getLogger(OredooKuwaitSubscriptionService.class.getName());

	
	private final  String dbillApi;
	private final String jksFilePath;
	
	
	private HttpURLConnectionUtil httpURLConnectionUtil;
	
   private  JAXBContext jaxbContextOCSRequest;  
   private JAXBContext jaxbContextOCSResponse;
	
	@Autowired
	private IDaoService daoService;
   
   @Autowired
	public OredooKuwaitSubscriptionService(
	@Value("${oredoo.kuwait.subscription.service.api}")	String dbillApi,
	@Value("${oredoo.kuwait.jks.file.path}") String jksFilePath
	
	
	){
		this.dbillApi=dbillApi;
		this.jksFilePath=jksFilePath;
		
		httpURLConnectionUtil=new HttpURLConnectionUtil();
		
		try{
		 jaxbContextOCSRequest = JAXBContext.newInstance(OCSRequest.class);   
	     jaxbContextOCSResponse = JAXBContext.newInstance(OCSResponse.class);  
		}catch(Exception ex){
			logger.error("OredooKuwaitSubscriptionService",ex);
		}
	}
	
	public OredooKuwaitOCSLogDetail callSubscriptionApi(OredooKuwaitCGCallback oredooKuwaitCGCallback,
			OredooKuwaitServiceConfig oredooKuwaitServiceConfig){
		
		OredooKuwaitOCSLogDetail oredooKuwaitOCSLogDetail=null;
		try{
		
		oredooKuwaitOCSLogDetail=new OredooKuwaitOCSLogDetail(true);
		oredooKuwaitOCSLogDetail.setMsisdn(oredooKuwaitCGCallback.getMsisdn());
		oredooKuwaitOCSLogDetail.setAction(OredoKuwaitConstant.SUBCRIPTION_API);
		OCSRequest ocsRequest=new OCSRequest();
		ocsRequest.setRequestType(OredooKuwaitDbillRequestType.SUBSCRIPTION.requestType);
		ocsRequest.setServiceId(oredooKuwaitServiceConfig.getComvivaServiceId());
		ocsRequest.setServiceNode(oredooKuwaitServiceConfig.getServiceNode());
		ocsRequest.setSequenceNo(oredooKuwaitCGCallback.getTpcgId());
		ocsRequest.setCallingParty(oredooKuwaitCGCallback.getMsisdn());
		ocsRequest.setServiceType(oredooKuwaitServiceConfig.getServiceType());
		ocsRequest.setBearerId(oredooKuwaitServiceConfig.getReqMode());
		ocsRequest.setPlanId(oredooKuwaitServiceConfig.getPlanId());
		ocsRequest.setAsyncFlag("Y");
		ocsRequest.setSubscriptionFlag("S");
		 Marshaller jaxbMarshaller = jaxbContextOCSRequest.createMarshaller();
	     jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	     StringWriter sw = new StringWriter();
	     jaxbMarshaller.marshal(ocsRequest, sw);	
	     oredooKuwaitOCSLogDetail.setRequet(sw.toString());
	     HTTPResponse  httpResponse= httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(dbillApi, sw.toString());
	    oredooKuwaitOCSLogDetail.setResponse(dbillApi+":response code: "+httpResponse.getResponseCode()+": response: "+ httpResponse.getResponseStr());
		oredooKuwaitCGCallback.setSubscriptonApiResponse(httpResponse.getResponseStr());
		if(httpResponse.getResponseCode()==200){
			 StringReader sr = new StringReader(httpResponse.getResponseStr());	
		     OCSResponse  ocsResponse= (OCSResponse)jaxbContextOCSResponse.createUnmarshaller().unmarshal(sr);
		     logger.info("ocsResponse:::::::::::: "+ocsResponse);
		}
	}catch(Exception ex){
		logger.error("callSubscriptionApi:: ",ex);
	}finally{
		daoService.updateObject(oredooKuwaitOCSLogDetail);
	}
		return oredooKuwaitOCSLogDetail;
	}
	
	public OCSResponse unSubcription(SubscriberReg subscriberReg,
			OredooKuwaitServiceConfig oredooKuwaitServiceConfig
			){
		
		OredooKuwaitOCSLogDetail oredooKuwaitOCSLogDetail=null;
		OCSResponse  ocsResponse=null;
		try{
		
		oredooKuwaitOCSLogDetail=new OredooKuwaitOCSLogDetail(true);
		oredooKuwaitOCSLogDetail.setMsisdn(subscriberReg.getMsisdn());
		oredooKuwaitOCSLogDetail.setAction(OredoKuwaitConstant.UNSUBCRIPTION_API);
		OCSRequest ocsRequest=new OCSRequest();
		ocsRequest.setRequestType(OredooKuwaitDbillRequestType.UNSUBSCRIPTION.requestType);
		ocsRequest.setServiceNode(oredooKuwaitServiceConfig.getServiceNode());
		ocsRequest.setSequenceNo(""+oredooKuwaitOCSLogDetail.getId());
		ocsRequest.setCallingParty(subscriberReg.getMsisdn());
		ocsRequest.setServiceType(oredooKuwaitServiceConfig.getServiceType());
		ocsRequest.setServiceId(oredooKuwaitServiceConfig.getComvivaServiceId());
		ocsRequest.setBearerId(oredooKuwaitServiceConfig.getReqMode());
		ocsRequest.setPlanId(oredooKuwaitServiceConfig.getPlanId());
		ocsRequest.setAsyncFlag("Y");
		ocsRequest.setSubscriptionFlag("S");
		 Marshaller jaxbMarshaller = jaxbContextOCSRequest.createMarshaller();
	     jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	     StringWriter sw = new StringWriter();
	     jaxbMarshaller.marshal(ocsRequest, sw);	
	     oredooKuwaitOCSLogDetail.setRequet(sw.toString());
	     HTTPResponse  httpResponse= httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(dbillApi, sw.toString());
	  //  oredooKuwaitOCSLogDetail.setResponse(dbillApi+":"+httpResponse.getResponseStr());
	    oredooKuwaitOCSLogDetail.setResponse(dbillApi+":response code: "+httpResponse.getResponseCode()+": response: "+ httpResponse.getResponseStr());
	    
	    //oredooKuwaitCGCallback.setSubscriptonApiResponse(httpResponse.getResponseStr());
		if(httpResponse.getResponseCode()==200){
			 StringReader sr = new StringReader(httpResponse.getResponseStr());	
		       ocsResponse= (OCSResponse)jaxbContextOCSResponse.createUnmarshaller().unmarshal(sr);
		     logger.info("ocsResponse:::::::::::: "+ocsResponse);
		}
	}catch(Exception ex){
		logger.error("unSubcription:: ",ex);
	}finally{
		daoService.updateObject(oredooKuwaitOCSLogDetail);
	}
		return   ocsResponse;
	
	}
	
	
	public OredooKuwaitOCSLogDetail sendMT_NOTUSed(OredooKuwaitCGNotification oredooKuwaitCGNotification,
			OredooKuwaitServiceConfig oredooKuwaitServiceConfig,String msg){
		
		OredooKuwaitOCSLogDetail oredooKuwaitOCSLogDetail=null;
		try{
		
		oredooKuwaitOCSLogDetail=new OredooKuwaitOCSLogDetail(true);
		oredooKuwaitOCSLogDetail.setMsisdn(oredooKuwaitCGNotification.getMsisdn());
		oredooKuwaitOCSLogDetail.setAction(OredoKuwaitConstant.SMS_API);
		
		OCSRequest ocsRequest=new OCSRequest();
		ocsRequest.setRequestType(OredooKuwaitDbillRequestType.MT_SMS.requestType);
		ocsRequest.setServiceNode(oredooKuwaitServiceConfig.getServiceNode());
		ocsRequest.setSequenceNo(oredooKuwaitCGNotification.getSequenceNo());		
		ocsRequest.setCallingParty(oredooKuwaitCGNotification.getMsisdn());
		ocsRequest.setServiceId(oredooKuwaitServiceConfig.getComvivaServiceId());
		ocsRequest.setServiceType(oredooKuwaitServiceConfig.getServiceType());
		//ocsRequest.setBearerId(oredooKuwaitServiceConfig.getReqMode());
		ocsRequest.setSubscriptionFlag("S");
		ocsRequest.setAsyncFlag("N");
		
		ocsRequest.setOptionalParameter1("cli#"+oredooKuwaitServiceConfig.getShortCode());
		ocsRequest.setOptionalParameter7("ipr#P");
		ocsRequest.setOptionalParameter8("reqSource#"+oredooKuwaitServiceConfig.getShortCode());
		ocsRequest.setOptionalParameter11("<![CDATA[msgText#"+msg+"]]>");
		logger.info("sendMT:: ocsRequest::: "+ocsRequest);
		 Marshaller jaxbMarshaller = jaxbContextOCSRequest.createMarshaller();
	     jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	     StringWriter sw = new StringWriter();
	     jaxbMarshaller.marshal(ocsRequest, sw);	
	     oredooKuwaitOCSLogDetail.setRequet(sw.toString());
	     HTTPResponse  httpResponse= httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(dbillApi, sw.toString());
	    oredooKuwaitOCSLogDetail.setResponse(dbillApi+":"+httpResponse.getResponseStr());
		logger.info("sendMT:: httpResponse::: "+httpResponse);
		if(httpResponse.getResponseCode()==200){
			 StringReader sr = new StringReader(httpResponse.getResponseStr());	
		     OCSResponse  ocsResponse= (OCSResponse)jaxbContextOCSResponse.createUnmarshaller().unmarshal(sr);
		     logger.info("ocsResponse:::::::::::: "+ocsResponse);
		}
	}catch(Exception ex){
		logger.error("sendMT:: ",ex);
	}finally{
		daoService.updateObject(oredooKuwaitOCSLogDetail);
	}
		return oredooKuwaitOCSLogDetail;
	
	}

	
	
	public OCSResponse checkSubs(String msisdn,OredooKuwaitServiceConfig oredooKuwaitServiceConfig){
	
		OredooKuwaitOCSLogDetail oredooKuwaitOCSLogDetail=null;
		OCSResponse  ocsResponse=null;
		try{
		
		oredooKuwaitOCSLogDetail=new OredooKuwaitOCSLogDetail(true);
		oredooKuwaitOCSLogDetail.setMsisdn(msisdn);
		oredooKuwaitOCSLogDetail.setAction(OredoKuwaitConstant.SMS_API);
		
		OCSRequest ocsRequest=new OCSRequest();
		ocsRequest.setRequestType(OredooKuwaitDbillRequestType.SUB_CHECK.requestType);
		ocsRequest.setServiceNode(oredooKuwaitServiceConfig.getServiceNode());
		ocsRequest.setSequenceNo(""+oredooKuwaitOCSLogDetail.getId());		
		ocsRequest.setCallingParty(msisdn);
		ocsRequest.setServiceId(oredooKuwaitServiceConfig.getComvivaServiceId());
		ocsRequest.setServiceType(oredooKuwaitServiceConfig.getServiceType());
		ocsRequest.setBearerId(oredooKuwaitServiceConfig.getReqMode());
		ocsRequest.setSubscriptionFlag("S");
		ocsRequest.setAsyncFlag("N");
		ocsRequest.setOptionalParameter12(null);
		ocsRequest.setPlanId(null);
		ocsRequest.setOptionalParameter4("balanceFlag#-1");
		ocsRequest.setOptionalParameter9("languageId#en");
		ocsRequest.setPlanId("-1");
		
		Marshaller jaxbMarshaller = jaxbContextOCSRequest.createMarshaller();
	     jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    StringWriter sw = new StringWriter();
	     jaxbMarshaller.marshal(ocsRequest, sw);	
	     logger.info("checksub :: ocsRequest::: "+ocsRequest);
	     oredooKuwaitOCSLogDetail.setRequet(sw.toString());
	     HTTPResponse  httpResponse= httpURLConnectionUtil
	    		 .makeHTTPPOSTRequestWithXML(dbillApi,sw.toString());
	     
	    oredooKuwaitOCSLogDetail.setResponse(dbillApi+":"+httpResponse.getResponseStr());
	    logger.info("checksub :: httpResponse::: "+httpResponse);
	    
		if(httpResponse.getResponseCode()==200){
			 StringReader sr = new StringReader(httpResponse.getResponseStr());	
		       ocsResponse= (OCSResponse)jaxbContextOCSResponse.createUnmarshaller().unmarshal(sr);
		     logger.info("ocsResponse:::::::::::: "+ocsResponse);
		}
	}catch(Exception ex){
		logger.error("checkSubs:: ",ex);
	}finally{
		daoService.updateObject(oredooKuwaitOCSLogDetail);
	}
		return ocsResponse;
	}
	
	
	
	public String checkSubsToPublisher(String msisdn,OredooKuwaitServiceConfig oredooKuwaitServiceConfig){
		
		OredooKuwaitOCSLogDetail oredooKuwaitOCSLogDetail=null;
		
		String response="";
		try{
		
		oredooKuwaitOCSLogDetail=new OredooKuwaitOCSLogDetail(true);
		oredooKuwaitOCSLogDetail.setMsisdn(msisdn);
		oredooKuwaitOCSLogDetail.setAction(OredoKuwaitConstant.SMS_API+"-PUB");
		
		OCSRequest ocsRequest=new OCSRequest();
		ocsRequest.setRequestType(OredooKuwaitDbillRequestType.SUB_CHECK.requestType);
		ocsRequest.setServiceNode(oredooKuwaitServiceConfig.getServiceNode());
		ocsRequest.setSequenceNo(""+oredooKuwaitOCSLogDetail.getId());		
		ocsRequest.setCallingParty(msisdn);
		ocsRequest.setServiceId(oredooKuwaitServiceConfig.getComvivaServiceId());
		ocsRequest.setServiceType(oredooKuwaitServiceConfig.getServiceType());
		ocsRequest.setBearerId(oredooKuwaitServiceConfig.getReqMode());
		ocsRequest.setSubscriptionFlag("S");
		ocsRequest.setAsyncFlag("N");
		
		Marshaller jaxbMarshaller = jaxbContextOCSRequest.createMarshaller();
	     jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	     StringWriter sw = new StringWriter();
	     jaxbMarshaller.marshal(ocsRequest, sw);	
	     oredooKuwaitOCSLogDetail.setRequet(sw.toString());
	     logger.info("checkSubsToPublisher :: ocsRequest::: "+ocsRequest);
	     HTTPResponse  httpResponse= httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(dbillApi,
	    		 sw.toString());
	    oredooKuwaitOCSLogDetail.setResponse(dbillApi+":"+httpResponse.getResponseStr());
	    logger.info("checkSubsToPublisher :: httpResponse::: "+httpResponse);
		if(httpResponse.getResponseCode()==200){
			// StringReader sr = new StringReader(httpResponse.getResponseStr());	
		     //  ocsResponse= (OCSResponse)jaxbContextOCSResponse.createUnmarshaller().unmarshal(sr);
			 response=httpResponse.getResponseStr();
		     logger.info("checkSubsToPublisher::::::::::::checkSubsToPublisher:: "+response);
		}
	}catch(Exception ex){
		logger.error("checkSubsToPublisher:: ",ex);
	}finally{
		daoService.updateObject(oredooKuwaitOCSLogDetail);
	}
		return response;
	}
	
	 public static String parseSoapResponse(String str) {
		   String response="";
	        String rootPath = "/Envelope/Body/CheckSubs_typeResponse/";
	         try {
	            //Create DocumentBuilderFactory for reading xml file
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            InputStream stream = new ByteArrayInputStream(str.getBytes());
	            Document doc = builder.parse(stream);
	            
	            // Create XPathFactory for creating XPath Object
	            XPathFactory xPathFactory = XPathFactory.newInstance();

	            // Create XPath object from XPathFactory
	            XPath xpath = xPathFactory.newXPath();

	            XPathExpression xPathExpr = xpath.compile(rootPath + "CheckSubs_typeResult/text()");
	            Object result = xPathExpr.evaluate(doc, XPathConstants.STRING);
	            response=result.toString();
	            
	        } catch (Exception e) {
	            logger.error("parseSoapResponse::  :: Exception " , e);
	        }finally{
	      
	        //activationCallback.setCallbackResp(str);
	        
	        }
	        return response;
	    }

	 public static void main(String arg[]){
		 String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><CheckSubs_typeResponse xmlns=\"http://tempuri.org/\">2-21930041</CheckSubs_typeResponse></soap:Body></soap:Envelope>";
		 String data=parseSoapResponse(xml);
		 System.out.println("data:: "+data);
		 
		 logger.info("checkSubs:::::: subsTypeResult:: "+data);
			String type="";
			if(data!=null&&data.contains("-")){
				String arr[]=data.split("-");
				switch(arr[0]){
				case "2":
					type=OredoKuwaitConstant.PREPAID;				
				break;
				case "1":
					type=OredoKuwaitConstant.POSTPAID;
				break;
			}
				System.out.println(type);
	 }
			
	 }
	 
	 public String encryptCGQueryStr(OredooKuwaitServiceConfig oredooKuwaitServiceConfig,
			 String queryStr) {
		 
		   String encyptedQueryStr="";
		   try{
			// TODO Auto-generated method stub
			String checksum ="";
			String secretEncriptionKeyAlias="com";
			String secretEncriptionKeyStorePassword="com@123";
			String secretEncriptionKeyPassword="com@123";
			String cpId="Collectcent";//oredooKuwaitServiceConfig.getCpId();
			//String jksFilePath="C:\\Users\\mobitize\\Desktop\\numero\\alphamovil\\oredo\\encryption\\keyFile.jks";
			//String requestParam="MSISDN=67057839&productID=S-HolZonEwMY2&pName=HollywoodZone&pPrice=800.0&pVal=7&CpId="+cpId+"&CpPwd=mt2%40543&CpName=Mt2&ismID=367&transID="+System.currentTimeMillis()+"&reqMode=WAP&reqType=Subscription&cpBgColor=&sRenewalPrice=800.0&sRenewalValidity=7&Wap_mdata=http%253A%252F%252F45.114.143.164%252Fadpoke%252Fimages%252Foredoo%252FThe_Shady_Truth_About_Miranda_Kerr.jpg";
		     //requestParam=JsonMapper.getObjectToJson(map);
			
			 Mac sha256HMAC = Mac.getInstance("HmacSHA256");
				SecretKeySpec secretkey = new SecretKeySpec
						(cpId.getBytes(), "HmacSHA256");
				
				sha256HMAC.init(secretkey);
//				String check=Base64.encodeBase64String(sha256HMAC.
//						doFinal(queryStr.getBytes()));
				
				checksum=URLEncoder.encode(Base64.encodeBase64String(sha256HMAC.
						doFinal(queryStr.getBytes())),"utf-8");
				File file = new File(jksFilePath);
				InputStream keystoreStream = new FileInputStream(file); 
				KeyStore keystore = KeyStore.getInstance("JCEKS");
				keystore.load(keystoreStream, secretEncriptionKeyStorePassword.toCharArray()); 
			    Key key = keystore.getKey(secretEncriptionKeyAlias, secretEncriptionKeyPassword.toCharArray());
			    keystoreStream.close();
			   IvParameterSpec iv = new IvParameterSpec(new byte[16]);
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
				 cipher.init(Cipher.ENCRYPT_MODE, key,iv); 		     
			    byte[] encryptedMessageInBytes = cipher.doFinal(queryStr.getBytes()); 
			    String encriptedRequestParam=Base64.encodeBase64String(encryptedMessageInBytes);
				 
				 encyptedQueryStr="CpId="+MUtility.urlEncoding(cpId)
						+"&requestParam="+encriptedRequestParam
						+"&checksum="+checksum;//+"&request_locale="+duConfig.getRequestLocale();
			
		   }catch(Exception ex){
			   logger.error("Exception:: ",ex);
		   }
		   return encyptedQueryStr;
		}
}


