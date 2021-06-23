package net.mycomp.comviva;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.common.service.IDaoService;
import net.persist.bean.SubscriberReg;
import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;

@Service
public class ComvivaSubscriptionService {

	private HttpURLConnectionUtil httpURLConnectionUtil;
	private  JAXBContext jaxbContextOCSRequest;
	private JAXBContext jaxbContextOCSResponse;
	private String dbillApi;
	private String subMessageTemplate;

	@Autowired
	public ComvivaSubscriptionService(
			@Value("${comviva.subscription.url}") String dbillApi,
			@Value("${comviva.sub.msg.template}") String subMessageTemplate
			) {
		this.dbillApi=dbillApi;
		this.subMessageTemplate=subMessageTemplate;
		httpURLConnectionUtil=new HttpURLConnectionUtil();
		try{
			jaxbContextOCSRequest = JAXBContext.newInstance(OCSRequest.class);   
			jaxbContextOCSResponse = JAXBContext.newInstance(OCSResponse.class);  
		}catch(Exception ex){
			logger.error("OredooKuwaitSubscriptionService",ex);
		}
	}

	@Autowired
	private IDaoService daoService;
	private static final Logger logger = Logger.getLogger(ComvivaSubscriptionService.class);

	public boolean subscribe(ComvivaServiceConfig comvivaServiceConfig, ComvivaCGCallback comvivaCGCallback) {
		ComvivaOCSLogDetail comvivaOCSLogDetail = new ComvivaOCSLogDetail(true);
		OCSRequest ocsRequest=new OCSRequest();
		try {
			comvivaOCSLogDetail.setMsisdn(comvivaCGCallback.getMsisdn());
			comvivaOCSLogDetail.setAction(ComvivaConstant.SUBSCRIBE);
			ocsRequest.setRequestType(ComvivaDBillRequestType.SUBSCRIPTION.requestType);
			ocsRequest.setServiceId(Objects.toString(comvivaServiceConfig.getComvivaServiceId()));
			ocsRequest.setServiceNode(comvivaServiceConfig.getServiceNode());
			ocsRequest.setSequenceNo(comvivaCGCallback.getTpCgId());
			ocsRequest.setCallingParty(comvivaCGCallback.getMsisdn());
			ocsRequest.setServiceType(comvivaServiceConfig.getServiceType());
			ocsRequest.setBearerId(comvivaServiceConfig.getRequestMode());
			ocsRequest.setPlanId(comvivaServiceConfig.getPlanId());
			ocsRequest.setAsyncFlag("Y");
			ocsRequest.setSubscriptionFlag("S");
			Marshaller jaxbMarshaller = jaxbContextOCSRequest.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(ocsRequest, sw);
			comvivaOCSLogDetail.setRequet(sw.toString());
			HTTPResponse  httpResponse= httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(dbillApi, sw.toString());
			comvivaOCSLogDetail.setResponse(dbillApi+":response code: "+httpResponse.getResponseCode()+": response: "+ httpResponse.getResponseStr());
			if(httpResponse.getResponseCode()==200){
				StringReader sr = new StringReader(httpResponse.getResponseStr());	
				OCSResponse  ocsResponse= (OCSResponse)jaxbContextOCSResponse.createUnmarshaller().unmarshal(sr);
				logger.info("ocsResponse:::::::::::: "+ocsResponse);
			}
		} catch (Exception e) {
			logger.error("error while susbscribing comviva:: "+comvivaCGCallback,e);
		}finally {
			daoService.updateObject(comvivaOCSLogDetail);
		}
		return true;
	}

	public OCSResponse checkSubs(ComvivaServiceConfig comvivaServiceConfig, ComvivaCGCallback comvivaCGCallback){

		ComvivaOCSLogDetail comvivaOCSLogDetail = new ComvivaOCSLogDetail(true);
		OCSRequest  ocsRequest=new OCSRequest();
		OCSResponse  ocsResponse = null;
		try{
			comvivaOCSLogDetail.setMsisdn(comvivaCGCallback.getMsisdn());
			comvivaOCSLogDetail.setAction(ComvivaConstant.CHECK_SUB);
			ocsRequest.setRequestType(ComvivaDBillRequestType.SUB_CHECK.requestType);
			ocsRequest.setServiceNode(comvivaServiceConfig.getServiceNode());
			ocsRequest.setSequenceNo(Objects.toString(comvivaOCSLogDetail.getId()));		
			ocsRequest.setCallingParty(comvivaCGCallback.getMsisdn());
			ocsRequest.setServiceId(Objects.toString(comvivaServiceConfig.getComvivaServiceId()));
			ocsRequest.setServiceType(comvivaServiceConfig.getServiceType());
			ocsRequest.setBearerId(comvivaServiceConfig.getRequestMode());
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
			comvivaOCSLogDetail.setRequet(sw.toString());
			HTTPResponse  httpResponse= httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(dbillApi, sw.toString());
			comvivaOCSLogDetail.setResponse(dbillApi+":response code: "+httpResponse.getResponseCode()+": response: "+ httpResponse.getResponseStr());
			if(httpResponse.getResponseCode()==200){
				StringReader sr = new StringReader(httpResponse.getResponseStr());	
				ocsResponse= (OCSResponse)jaxbContextOCSResponse.createUnmarshaller().unmarshal(sr);
				logger.info("ocsResponse:::::::::::: "+ocsResponse);
			}
		}catch(Exception ex){
			logger.error("checkSubs:: ",ex);
		}finally{
			daoService.updateObject(comvivaOCSLogDetail);
		}
		return ocsResponse;
	}

	public OCSResponse sendMT(ComvivaServiceConfig comvivaServiceConfig, ComvivaCGCallback comvivaCGCallback){

		ComvivaOCSLogDetail comvivaOCSLogDetail= new ComvivaOCSLogDetail(Boolean.TRUE);
		OCSRequest ocsRequest=new OCSRequest();
		OCSResponse  ocsResponse = new OCSResponse();
		String sms=null;
		try{
			sms=ComvivaConstant.getSubMessage(subMessageTemplate, comvivaServiceConfig);
			comvivaOCSLogDetail.setMsisdn(comvivaCGCallback.getMsisdn());
			comvivaOCSLogDetail.setAction(ComvivaConstant.SMS_MT);
			ocsRequest.setRequestType(ComvivaDBillRequestType.MT_SMS.requestType);
			ocsRequest.setServiceNode(comvivaServiceConfig.getServiceNode());
			ocsRequest.setSequenceNo(Objects.toString(comvivaOCSLogDetail.getId()));		
			ocsRequest.setCallingParty(comvivaCGCallback.getMsisdn());
			ocsRequest.setServiceId(comvivaServiceConfig.getComvivaServiceId());
			ocsRequest.setServiceType(comvivaServiceConfig.getServiceType());
			//ocsRequest.setBearerId(oredooKuwaitServiceConfig.getReqMode());
			ocsRequest.setSubscriptionFlag("S");
			ocsRequest.setAsyncFlag("N");

			ocsRequest.setOptionalParameter1("cli#"+comvivaServiceConfig.getShortCode());
			ocsRequest.setOptionalParameter7("ipr#P");
			ocsRequest.setOptionalParameter8("reqSource#"+comvivaServiceConfig.getShortCode());
			ocsRequest.setOptionalParameter11("<![CDATA[msgText#"+sms+"]]>");
			logger.info("sendMT:: ocsRequest::: "+ocsRequest);
			Marshaller jaxbMarshaller = jaxbContextOCSRequest.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(ocsRequest, sw);	
			comvivaOCSLogDetail.setRequet(sw.toString());
			HTTPResponse  httpResponse= httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(dbillApi, sw.toString());
			comvivaOCSLogDetail.setResponse(dbillApi+":"+httpResponse.getResponseStr());
			logger.info("sendMT:: httpResponse::: "+httpResponse);
			if(httpResponse.getResponseCode()==200){
				StringReader sr = new StringReader(httpResponse.getResponseStr());	
				ocsResponse= (OCSResponse)jaxbContextOCSResponse.createUnmarshaller().unmarshal(sr);
				logger.info("ocsResponse:::::::::::: "+ocsResponse);
			}
		}catch(Exception e){
			logger.error("sendMT:: ",e);
		}finally{
			daoService.updateObject(comvivaOCSLogDetail);
		}
		return ocsResponse;

	}

	public OCSResponse unsubscribe(SubscriberReg subscriberReg, 
			ComvivaServiceConfig comvivaServiceConfig){

		ComvivaOCSLogDetail comvivaOCSLogDetail=null;
		OCSResponse  ocsResponse=null;
		try{

			comvivaOCSLogDetail=new ComvivaOCSLogDetail(true);
			comvivaOCSLogDetail.setMsisdn(subscriberReg.getMsisdn());
			comvivaOCSLogDetail.setAction(ComvivaConstant.UNSUBSCRIBE);
			OCSRequest ocsRequest=new OCSRequest();
			ocsRequest.setRequestType(ComvivaDBillRequestType.UNSUBSCRIPTION.requestType);
			ocsRequest.setServiceNode(comvivaServiceConfig.getServiceNode());
			//ocsRequest.setSequenceNo(Objects.toString(comvivaOCSLogDetail.getId()));
			ocsRequest.setSequenceNo(Objects.toString(System.currentTimeMillis()));
			ocsRequest.setCallingParty(subscriberReg.getMsisdn());
			ocsRequest.setServiceType(comvivaServiceConfig.getServiceType());
			ocsRequest.setServiceId(comvivaServiceConfig.getComvivaServiceId());
			ocsRequest.setBearerId(comvivaServiceConfig.getRequestMode());
			ocsRequest.setPlanId(comvivaServiceConfig.getPlanId());
			ocsRequest.setAsyncFlag("Y");
			ocsRequest.setSubscriptionFlag("S");
			Marshaller jaxbMarshaller = jaxbContextOCSRequest.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(ocsRequest, sw);	
			comvivaOCSLogDetail.setRequet(sw.toString());
			HTTPResponse  httpResponse= httpURLConnectionUtil.makeHTTPPOSTRequestWithXML(dbillApi, sw.toString());
			comvivaOCSLogDetail.setResponse(dbillApi+":response code: "+httpResponse.getResponseCode()+": response: "+ httpResponse.getResponseStr());
			if(httpResponse.getResponseCode()==200){
				StringReader sr = new StringReader(httpResponse.getResponseStr());	
				ocsResponse= (OCSResponse)jaxbContextOCSResponse.createUnmarshaller().unmarshal(sr);
				logger.info("ocsResponse:::::::::::: "+ocsResponse);
			}
		}catch(Exception ex){
			logger.error("unSubcription:: ",ex);
		}finally{
			daoService.updateObject(comvivaOCSLogDetail);
		}
		return   ocsResponse;

	}


}
