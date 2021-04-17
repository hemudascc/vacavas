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

	@Autowired
	public ComvivaSubscriptionService(
			@Value("${comviva.subscription.url}") String dbillApi) {
		this.dbillApi=dbillApi;
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
