package net.mycomp.tpay;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;
import net.util.JsonMapper;
import net.util.MConstants;
import net.util.MData;

@Service("tpayApiService")
public class TpayApiService {

	private static final Logger logger = Logger.getLogger(TpayApiService.class);

	private HttpURLConnectionUtil httpURLConnectionUtil;

	@Autowired
	private IDaoService daoService;

	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	@Autowired
	private SubscriberRegService subscriberRegService;

	@Autowired
	private RedisCacheService redisCacheService;
	
	private RestTemplate restTemplate;

	@PostConstruct
	public void init(){
		httpURLConnectionUtil=new HttpURLConnectionUtil();
		restTemplate = new RestTemplate();
	}


	public List<SubscriberReg> getSubscriberRegBySubscriptionContractId(String subscriptionContractId) {
		return jpaSubscriberReg.findSubscriberRegByParam1(subscriptionContractId);
	} 

	public String sendPin(String token, String msisdn, String sessionToken, String lang,String headerEnrichmentReferenceCode) {

		TpayRequest tpaySubscriptionContractRequest = null;

		try {
			tpaySubscriptionContractRequest = createSubscriptionContractRequest(token, msisdn,"SEND_PIN", sessionToken, lang,headerEnrichmentReferenceCode);
		} catch (Exception e) {
			logger.error("error"+e);
		}
		finally {
			daoService.saveObject(tpaySubscriptionContractRequest);
		}
		return tpaySubscriptionContractRequest.getResponse();
	}

	public String resendPin(String token, String msisdn, String subscriptionContractId, String lang) {

		TpayRequest tpaySubscriptionContractRequest = null;

		try {
			tpaySubscriptionContractRequest = createResendPinRequest(token, msisdn,"RESEND_PIN",subscriptionContractId,lang);
		} catch (Exception e) {
			logger.error("error"+e);
		}
		finally {
			daoService.saveObject(tpaySubscriptionContractRequest);
		}
		return tpaySubscriptionContractRequest.getResponse();
	}
	
	
	public String validatePin(String token, String msisdn, String subscriptionContractId, String pin) {
		TpayRequest tpayRequest = null;

		try {
			tpayRequest = createValidationRequest(token, msisdn,"VALIDATE_PIN",subscriptionContractId,pin);
		} catch (Exception e) {
			logger.error("error"+e);
		}
		finally {
			daoService.saveObject(tpayRequest);
		}
		return tpayRequest.getResponse();
	}

	public String unsubscribe(String msisdn,String lang,String token) {
		TpayRequest tpayRequest = null;
		String contractId=null;
		try {
			
			SubscriberReg subscriberReg = daoService.searchSubscriber(msisdn);
			if (subscriberReg != null && 
					subscriberReg.getSubscriberId() != null &&
					subscriberReg.getSubscriberId() > 0
					&& subscriberReg.getStatus() == MConstants.SUBSCRIBED) {
				contractId = subscriberReg.getParam1();
			}
			
			tpayRequest = createUnsubscribeRequest(contractId,"UNSUBSCRIBE",msisdn,lang,token);
		
		} catch (Exception e) {
			logger.error("error"+e);
		}
		finally {
			daoService.saveObject(tpayRequest);
		}
		return tpayRequest.getResponse();
	}
		
	private TpayRequest createUnsubscribeRequest(String subscriptionContractId, String action, String msisdn, String lang,String token) {
		
		TpayRequest  tpaySubscriptionContractRequest = new TpayRequest(true,action);

		Map<String,String> headerMap=new HashMap<String,String>();

		CGToken cgtoken = new CGToken(token);
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");
		
		if(subscriptionContractId==null) {
			logger.info("can not proceed request for unsubscription msisdn"+msisdn);
			tpaySubscriptionContractRequest.setResponseCode(400);
			tpaySubscriptionContractRequest.setResponse("51");
			tpaySubscriptionContractRequest.setCampaignId(cgtoken.getCampaignId());
			tpaySubscriptionContractRequest.setMsisdn(msisdn);
			tpaySubscriptionContractRequest.setToken(token);
			return tpaySubscriptionContractRequest;
		}
		
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("subscriptionContractId", subscriptionContractId);
		requestMap.put("signature", createSignature(requestMap,subscriptionContractId,token));
		requestMap.put("lang", lang.equals("1")?"2":"1");
		String request = JsonMapper.getObjectToJson(requestMap);

		tpaySubscriptionContractRequest.setRequest("Request URL : "+TpayConstant.CANCEL_SUBSCRIPTION_CONTARCT_URL+"; Request : "+request+"; Headers:"+headerMap);

		logger.info("requesting for unsubscription "+TpayConstant.CANCEL_SUBSCRIPTION_CONTARCT_URL+" : "+request);

		HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(TpayConstant.CANCEL_SUBSCRIPTION_CONTARCT_URL, request,headerMap);

		tpaySubscriptionContractRequest.setResponseCode(httpResponse.getResponseCode());
		tpaySubscriptionContractRequest.setResponse(httpResponse.getResponseStr());
		tpaySubscriptionContractRequest.setCampaignId(cgtoken.getCampaignId());
		tpaySubscriptionContractRequest.setMsisdn(msisdn);
		tpaySubscriptionContractRequest.setToken(token);
		return tpaySubscriptionContractRequest;

		
	}

	public String sendWelcomeMT(String token, String msisdn, String subscriptionContractId, Integer lang) {
		
		TpayRequest tpaySubscriptionContractRequest = null;
		
		try {
			CGToken cgToken=new CGToken(token);
			VWServiceCampaignDetail 
			vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
					.get(vwServiceCampaignDetail.getServiceId());
			
			LiveReport liveReport=new LiveReport(vwServiceCampaignDetail.getOpId(),
					   new Timestamp(System.currentTimeMillis()),
					   cgToken.getCampaignId()
					   ,tpayServiceConfig.getServiceId(),
					   vwServiceCampaignDetail.getProductId());
			 liveReport.setNoOfDays(tpayServiceConfig.getValidity());	
			 liveReport.setMsisdn(msisdn);
			 liveReport.setParam1(subscriptionContractId);
			 liveReport.setParam2(tpayServiceConfig.getOperatorCode());
			 liveReport.setParam3(token);
			 subscriberRegService.findOrCreateSubscriberByAct(msisdn,
					   null, liveReport);
			 redisCacheService.putObjectCacheValueByEvictionMinute(TpayConstant.TPAY_TEMP_SUBSCRIBE + msisdn, token,60*24);
			tpaySubscriptionContractRequest = createSendWelcomeMtRequest(token, msisdn,"SEND_WELCOME_MT",lang);
		} catch (Exception e) {
			logger.error("error"+e);
		}
		finally {
			daoService.saveObject(tpaySubscriptionContractRequest);
		}
		return tpaySubscriptionContractRequest.getResponse();
	}
	
	public String sendContentMT(String msisdn,Integer lang,String token) {
		
		TpayRequest tpaySubscriptionContractRequest = null;
		
		try {
			List<SubscriberReg> subscriberReg = jpaSubscriberReg.findSubscriberRegByMsisdn(msisdn);
			if(subscriberReg!=null) {
				tpaySubscriptionContractRequest = createSendContentRequest(msisdn,"SEND_CONTENT_MT",lang, subscriberReg.get(0),token);
			}
		} catch (Exception e) {
			logger.error("error"+e);
		}
		finally {
			daoService.saveObject(tpaySubscriptionContractRequest);
		}
		return tpaySubscriptionContractRequest.getResponse();
	}
	
	private TpayRequest createSendContentRequest(String msisdn, String action, Integer lang, SubscriberReg subscriberReg,String token) {
		Map<String,String> headerMap=new HashMap<String,String>();
		TpayRequest  tpaySubscriptionContractRequest = new TpayRequest(true,action);
		CGToken cgToken=new CGToken(token);
		VWServiceCampaignDetail 
		vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
		TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
				.get(vwServiceCampaignDetail.getServiceId());
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");
		
		String message = "";
		if(lang==0) { 
			message =	TpayConstant.CONTENT_MESSAGE_SMS_ENG
					 .replaceAll("<portalurl>", tpayServiceConfig.getProtalUrl())
					 .replaceAll("<msisdn>", msisdn)  
					 .replaceAll("<lang>","1");
		}else {
			message = TpayConstant.CONTENT_MESSAGE_SMS_ARB
					.replaceAll("<portalurl>", tpayServiceConfig.getProtalUrl())
					.replaceAll("<msisdn>", msisdn)
					.replaceAll("<lang>","2");
		}
		
		Map<String, String> requestMap = new HashMap<>();
		
		requestMap.put("messageBody", message);
		requestMap.put("msisdn", msisdn);
		requestMap.put("operatorCode", subscriberReg.getParam2());
		requestMap.put("signature", TpayUtill.CalculateDigest(tpayServiceConfig.getPublicKey(), message+msisdn+subscriberReg.getParam2(), tpayServiceConfig.getPrivateKey()));
		
		String request = JsonMapper.getObjectToJson(requestMap);

		tpaySubscriptionContractRequest.setRequest("Request URL : "+TpayConstant.SMS_URL+"; Request : "+request+"; Headers:"+headerMap);

		logger.info("sending welcome message "+TpayConstant.SMS_URL+" : "+request);

		//HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(TpayConstant.SMS_URL, request,headerMap);
		HttpEntity<Map<String, String>> request1 = new HttpEntity<>(requestMap);
		ResponseEntity<String>  response = restTemplate.postForEntity(TpayConstant.SMS_URL, request1, String.class);
		
		tpaySubscriptionContractRequest.setResponseCode(response.getStatusCode().value());
		tpaySubscriptionContractRequest.setResponse(response.getBody());
		tpaySubscriptionContractRequest.setCampaignId(cgToken.getCampaignId());
		tpaySubscriptionContractRequest.setMsisdn(msisdn);
		tpaySubscriptionContractRequest.setToken(token);
		logger.info("response:"+response.getBody());

		return tpaySubscriptionContractRequest;
		
		
	}



	private TpayRequest createSendWelcomeMtRequest(String token, String msisdn,String action, Integer lang) {
		
		CGToken cgToken = new CGToken(token);

		VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());

		TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig.get(vwServiceCampaignDetail.getServiceId());
		
		TpayRequest  tpaySubscriptionContractRequest = new TpayRequest(true,action);

		Map<String,String> headerMap=new HashMap<String,String>();

		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");
		
		String message = "";
		if(lang==0) { 
			message =	TpayConstant.WELCOME_MESSAGE_SMS_ENG.replaceAll("<price>", tpayServiceConfig.getPrice())
					 .replaceAll("<portalurl>", tpayServiceConfig.getProtalUrl())
					 .replaceAll("<servicename>", tpayServiceConfig.getServiceName())
					 .replaceAll("<currency>", tpayServiceConfig.getCurrency())
					 .replaceAll("<msisdn>", msisdn)
					 .replaceAll("<billing_sequence>",tpayServiceConfig.getBillingSequence())
					 .replaceAll("<unsub_keyword>", tpayServiceConfig.getUnsubKeyword())
					 .replaceAll("<shortcode>", tpayServiceConfig.getShortCode())
					 .replaceAll("<lang>","1")
					 .replaceAll("<subid>",msisdn);
	}else {
		message = TpayConstant.WELCOME_MESSAGE_SMS_ARB.replaceAll("<price>", tpayServiceConfig.getPrice())
				 .replaceAll("<portalurl>", tpayServiceConfig.getProtalUrl())
				 .replaceAll("<servicename>", tpayServiceConfig.getServiceName())
				 .replaceAll("<currency>", tpayServiceConfig.getCurrency())
				 .replaceAll("<msisdn>", msisdn)
				 .replaceAll("<billing_sequence>",tpayServiceConfig.getBillingSequence())
				 .replaceAll("<unsub_keyword>", tpayServiceConfig.getUnsubKeyword())
				 .replaceAll("<shortcode>", tpayServiceConfig.getShortCode())
				 .replaceAll("<lang>","2")
				 .replaceAll("<subid>",msisdn);
		}
		
		Map<String, String> requestMap = new HashMap<>();
		
		requestMap.put("messageBody", message);
		requestMap.put("msisdn", msisdn);
		requestMap.put("operatorCode", tpayServiceConfig.getOperatorCode());
		requestMap.put("signature", TpayUtill.CalculateDigest(tpayServiceConfig.getPublicKey(), message+msisdn+tpayServiceConfig.getOperatorCode(), tpayServiceConfig.getPrivateKey()));
		
		String request = JsonMapper.getObjectToJson(requestMap);

		tpaySubscriptionContractRequest.setRequest("Request URL : "+TpayConstant.SMS_URL+"; Request : "+request+"; Headers:"+headerMap);

		logger.info("sending welcome message "+TpayConstant.SMS_URL+" : "+request);

		//HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(TpayConstant.SMS_URL, request,headerMap);
		HttpEntity<Map<String, String>> request1 = new HttpEntity<>(requestMap);
		ResponseEntity<String>  response = restTemplate.postForEntity(TpayConstant.SMS_URL, request1, String.class);
		
		tpaySubscriptionContractRequest.setResponseCode(response.getStatusCode().value());
		tpaySubscriptionContractRequest.setResponse(response.getBody());
		tpaySubscriptionContractRequest.setToken(token);
		tpaySubscriptionContractRequest.setMsisdn(msisdn);
		tpaySubscriptionContractRequest.setCampaignId(cgToken.getCampaignId());
		
		logger.info("response:"+response.getBody());

		return tpaySubscriptionContractRequest;
		
	}
	
	private TpayRequest createValidationRequest(String token, String msisdn,String action ,String subscriptionContractId,String pin) {
		
		CGToken cgToken = new CGToken(token);
		
		TpayRequest  tpaySubscriptionContractRequest = new TpayRequest(true,action);
		
		Map<String,String> headerMap=new HashMap<String,String>();

		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");
		
		tpaySubscriptionContractRequest.setMsisdn(msisdn);
		tpaySubscriptionContractRequest.setToken(token);
		tpaySubscriptionContractRequest.setCampaignId(cgToken.getCampaignId());
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("subscriptionContractId", subscriptionContractId);
		requestMap.put("pinCode", pin);
		requestMap.put("signature", createSignature(requestMap,subscriptionContractId+pin,token));
		String request = JsonMapper.getObjectToJson(requestMap);

		tpaySubscriptionContractRequest.setRequest("Request URL : "+TpayConstant.PIN_VALIDATE_URL+"; Request : "+request+"; Headers:"+headerMap);

		logger.info("validating pin "+TpayConstant.PIN_VALIDATE_URL+" : "+request);

		HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(TpayConstant.PIN_VALIDATE_URL, request,headerMap);

		tpaySubscriptionContractRequest.setResponseCode(httpResponse.getResponseCode());
		tpaySubscriptionContractRequest.setResponse(httpResponse.getResponseStr());

		return tpaySubscriptionContractRequest;
		
	}
	
	public TpayRequest createResendPinRequest(String token, String msisdn,String action ,String subscriptionContractId, String lang) {
		
		CGToken cgToken = new CGToken(token);
		TpayRequest  tpaySubscriptionContractRequest = new TpayRequest(true,action);
		
		Map<String,String> headerMap=new HashMap<String,String>();

		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");
		
		tpaySubscriptionContractRequest.setMsisdn(msisdn);
		tpaySubscriptionContractRequest.setToken(token);
		tpaySubscriptionContractRequest.setCampaignId(cgToken.getCampaignId());
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("subscriptionContractId", subscriptionContractId);
		requestMap.put("signature", createSignature(requestMap,subscriptionContractId,token));
		requestMap.put("lang", lang.equals("1")?"2":"1");
		String request = JsonMapper.getObjectToJson(requestMap);

		tpaySubscriptionContractRequest.setRequest("Request URL : "+TpayConstant.PIN_RESEND_URL+"; Request : "+request+"; Headers:"+headerMap);

		logger.info("requesting for pin "+TpayConstant.PIN_RESEND_URL+" : "+request);

		HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(TpayConstant.PIN_RESEND_URL, request,headerMap);

		tpaySubscriptionContractRequest.setResponseCode(httpResponse.getResponseCode());
		tpaySubscriptionContractRequest.setResponse(httpResponse.getResponseStr());

		return tpaySubscriptionContractRequest;
		
	}

	private TpayRequest createSubscriptionContractRequest(String token, String msisdn, String action, String sessionToken, String lang,String headerEnrichmentReferenceCode) {

		CGToken cgToken = new CGToken(token);

		VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());

		TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig.get(vwServiceCampaignDetail.getServiceId());

		TpayRequest  tpaySubscriptionContractRequest = new TpayRequest(true,action);

		Map<String,String> headerMap=new HashMap<String,String>();

		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");

		tpaySubscriptionContractRequest.setMsisdn(msisdn);
		tpaySubscriptionContractRequest.setToken(token);
		tpaySubscriptionContractRequest.setCampaignId(cgToken.getCampaignId());
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("customerAccountNumber","tpayvaca");
		requestMap.put("msisdn",msisdn);
		requestMap.put("operatorCode",tpayServiceConfig.getOperatorCode());
		requestMap.put("subscriptionPlanId",tpayServiceConfig.getSubscriptionPlanId());
		requestMap.put("initialPaymentproductId",tpayServiceConfig.getPaymentProductId());
		requestMap.put("initialPaymentDate", getCurrentTimeStamp()+"Z");
		requestMap.put("operatorCode",tpayServiceConfig.getOperatorCode());
		requestMap.put("executeInitialPaymentNow","false");
		requestMap.put("executeRecurringPaymentNow","false");
		requestMap.put("recurringPaymentproductId",tpayServiceConfig.getPaymentProductId());
		requestMap.put("productCatalogName",tpayServiceConfig.getCatalogName());
		requestMap.put("autoRenewContract","true");
		requestMap.put("sendVerificationSMS","true");
		requestMap.put("allowMultipleFreeStartPeriods","false");
		requestMap.put("contractStartDate",getCurrentTimeStamp()+"Z");
		requestMap.put("contractEndDate",getNextWeekTimeStamp()+"Z");
		requestMap.put("language", lang.equals("1")?"2":"1");
		requestMap.put("headerEnrichmentReferenceCode", headerEnrichmentReferenceCode);
		requestMap.put("smsId", ""); 
		requestMap.put("lang", lang.equals("1")?"2":"1"); 
		requestMap.put("signature", createSignature(requestMap,null,token));
		 requestMap.put("sessionToken", sessionToken); 
		String request = JsonMapper.getObjectToJson(requestMap);

		tpaySubscriptionContractRequest.setRequest("Request URL : "+TpayConstant.PIN_URL+"; Request : "+request+"; Headers:"+headerMap);

		logger.info("requesting for pin "+TpayConstant.PIN_URL+" : "+request);

		HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(TpayConstant.PIN_URL, request,headerMap);

		tpaySubscriptionContractRequest.setResponseCode(httpResponse.getResponseCode());
		tpaySubscriptionContractRequest.setResponse(httpResponse.getResponseStr());

		return tpaySubscriptionContractRequest;
	}

	private String createSignature(Map<String,String> requestMap, String message,String token) {
		CGToken cgToken=new CGToken(token);
		VWServiceCampaignDetail 
		vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
		TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
				.get(vwServiceCampaignDetail.getServiceId());
		logger.info("CreateSignature  : tpayServiceConfig:  "+tpayServiceConfig);
		if(message==null) {
			message = requestMap.get("customerAccountNumber")+requestMap.get("msisdn")+requestMap.get("operatorCode")+
					requestMap.get("subscriptionPlanId")+requestMap.get("initialPaymentproductId")+
					requestMap.get("initialPaymentDate")+requestMap.get("executeInitialPaymentNow")+
					requestMap.get("recurringPaymentproductId")+requestMap.get("productCatalogName")+
					requestMap.get("executeRecurringPaymentNow")+requestMap.get("contractStartDate")+
					requestMap.get("contractEndDate")+requestMap.get("autoRenewContract")+requestMap.get("language")+
					requestMap.get("sendVerificationSMS")+requestMap.get("allowMultipleFreeStartPeriods")+
					requestMap.get("headerEnrichmentReferenceCode")+requestMap.get("smsId");
		}
		byte[] key = tpayServiceConfig.getPrivateKey().getBytes();
		String digest = tpayServiceConfig.getPublicKey()+":"+TpayUtill.hmacSHA256(message, key);
		return digest;
	}

	private static String getCurrentTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}
	
	private static String getNextWeekTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(addDays(date, 10*365));
	}
	
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

}
