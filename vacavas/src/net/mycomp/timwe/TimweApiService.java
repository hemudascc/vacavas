package net.mycomp.timwe;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;
import net.util.JsonMapper;
import net.util.MData;

@Service("timweApiService")
public class TimweApiService {

	@Value("${timwe.pin.send.url}")
	private String pinSendURL;
	@Value("${timwe.pin.verify.url}")
	private String pinVerifyURL;
	@Value("${timwe.mt.push.url}")
	private String mtPushURL;
	@Value("${timwe.unsubscription.url}")
	private String unsubscriptionURL;
	
	private static final Logger logger = Logger.getLogger(TimweApiService.class);
	
	private HttpURLConnectionUtil httpURLConnectionUtil;
	@Autowired
	private IDaoService daoService;
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	@Autowired
	RedisCacheService redisCacheService;

	@PostConstruct
	public void init(){
		httpURLConnectionUtil=new HttpURLConnectionUtil();

	}

	public String pinSend(String token, String msisdn) {
		if("vacatest".equals(msisdn)) {
			return "1";
		}
		Map<String,String> headerMap=new HashMap<>();
		Map<String, String> requestMap = new HashMap<>();
		CGToken cgToken = new CGToken("");
		TimweServiceConfig timweServiceConfig = null;
		VWServiceCampaignDetail vwServiceCampaignDetail = null;
		TimweTrans timweTrans = new TimweTrans(Boolean.TRUE);
		headerMap.put("Content-Type", "application/json");
		headerMap.put("apikey", TimweConstant.SUBSCRIPTION_API_KEY);
		headerMap.put("authentication", TimweUtill.getAuthCode(TimweConstant.SUBSCRIPTION_PRE_SHARED_KEY));
		try {
			redisCacheService.putObjectCacheValueByEvictionDay(TimweConstant.TIMWE_TOKEN_MSISDN_CACHE_PREFIX+msisdn, token, 1);
			cgToken = new CGToken(token);
			vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			timweServiceConfig = TimweConstant.mapServiceIdToTimweServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			timweTrans.setMsisdn(msisdn);
			timweTrans.setRequestType(TimweConstant.PIN_SEND);
			timweTrans.setToken(token);
			timweTrans.setTokenId(cgToken.getTokenId());
			String url =pinSendURL.replaceAll("<roleid>",TimweConstant.ROLE_ID);
			requestMap.put("userIdentifier", msisdn);
			requestMap.put("userIdentifierType", "MSISDN");
			requestMap.put("productId", timweServiceConfig.getTimweProductId());
			requestMap.put("mcc", timweServiceConfig.getMcc());
			requestMap.put("mnc", timweServiceConfig.getMnc());
			requestMap.put("entryChannel", "WAP");
			requestMap.put("largeAccount", timweServiceConfig.getShortCode());
			requestMap.put("subKeyword", timweServiceConfig.getSubKey());
			requestMap.put("trackingId", token);
			requestMap.put("clientIP", "");
			requestMap.put("campaignUrl", "");
			String requestJson = JsonMapper.getObjectToJson(requestMap);
			timweTrans.setRequest("request url: "+url+" requestMap: "+requestJson+"  headers"+headerMap);
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(url,requestJson,headerMap);
			timweTrans.setResponseCode(httpResponse.getResponseCode());
			timweTrans.setResposne(httpResponse.getResponseStr());
			TimweResponse timweResponse = JsonMapper.getJsonToObject(httpResponse.getResponseStr(), TimweResponse.class);
			if("SUCCESS".equalsIgnoreCase(timweResponse.getCode())) {
				return "1";
			}
		} catch (Exception e) {
			logger.error("error while sending pin msisdn="+msisdn,e);
		}finally {
			daoService.saveObject(timweTrans);
		}
		return "0";
	}

	public String pinVerify(String msisdn,String pin) {
		
		if("vacatest".equals(msisdn)) {
			return "1";
		}
		
		Map<String,String> headerMap=new HashMap<>();
		Map<String, String> requestMap = new HashMap<>();
		CGToken cgToken = new CGToken("");
		TimweServiceConfig timweServiceConfig = null;
		VWServiceCampaignDetail vwServiceCampaignDetail = null;
		TimweTrans timweTrans = new TimweTrans(Boolean.TRUE);
		headerMap.put("Content-Type", "application/json");
		headerMap.put("apikey", TimweConstant.SUBSCRIPTION_API_KEY);
		headerMap.put("authentication", TimweUtill.getAuthCode(TimweConstant.SUBSCRIPTION_PRE_SHARED_KEY));
		try {
			String token = Objects.toString(redisCacheService.getObjectCacheValue(TimweConstant.TIMWE_TOKEN_MSISDN_CACHE_PREFIX+msisdn));
			cgToken = new CGToken(token);
			vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			timweServiceConfig = TimweConstant.mapServiceIdToTimweServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			timweTrans.setMsisdn(msisdn);
			timweTrans.setRequestType(TimweConstant.PIN_VERIFY);
			timweTrans.setToken(token);
			timweTrans.setTokenId(cgToken.getTokenId());
			String url =pinVerifyURL.replaceAll("<roleid>",TimweConstant.ROLE_ID);
			requestMap.put("userIdentifier", msisdn);
			requestMap.put("userIdentifierType", "MSISDN");
			requestMap.put("productId", timweServiceConfig.getTimweProductId());
			requestMap.put("mcc", timweServiceConfig.getMcc());
			requestMap.put("mnc", timweServiceConfig.getMnc());
			requestMap.put("entryChannel", "WEB");
			requestMap.put("clientIP", "");
			requestMap.put("transactionAuthCode", pin);
			String requestJson = JsonMapper.getObjectToJson(requestMap);
			timweTrans.setRequest("request url: "+url+" requestMap: "+requestJson+"  headers"+headerMap);
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(url,requestJson,headerMap);
			timweTrans.setResponseCode(httpResponse.getResponseCode());
			timweTrans.setResposne(httpResponse.getResponseStr());
			TimweResponse timweResponse = JsonMapper.getJsonToObject(httpResponse.getResponseStr(), TimweResponse.class);
			if("SUCCESS".equalsIgnoreCase(timweResponse.getCode())) {
				return "1";
			}
		} catch (Exception e) {
			logger.error("error while verifing pin msisdn="+msisdn+" pin ="+pin,e);
		}finally {
			daoService.saveObject(timweTrans);
		}
		return "0";
	}
	
	public String mtPush(String msisdn) {
		Map<String,String> headerMap = new HashMap<>();
		Map<String, String> requestMap = new HashMap<>();
		CGToken cgToken = new CGToken("");
		TimweServiceConfig timweServiceConfig = null;
		VWServiceCampaignDetail vwServiceCampaignDetail = null;
		TimweTrans timweTrans = new TimweTrans(Boolean.TRUE);
		headerMap.put("Content-Type", "application/json");
		headerMap.put("apikey", TimweConstant.SEND_MT_API_KEY);
		headerMap.put("authentication", TimweUtill.getAuthCode(TimweConstant.SEND_MT_PRE_SHARED_KEY));
		try {
			String token = Objects.toString(redisCacheService.getObjectCacheValue(TimweConstant.TIMWE_TOKEN_MSISDN_CACHE_PREFIX+msisdn));
			cgToken = new CGToken(token);
			vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			timweServiceConfig = TimweConstant.mapServiceIdToTimweServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			timweTrans.setMsisdn(msisdn);
			timweTrans.setRequestType(TimweConstant.SEND_MT);
			timweTrans.setToken(token);
			timweTrans.setTokenId(cgToken.getTokenId());
			String url =mtPushURL.replaceAll("<roleid>",TimweConstant.ROLE_ID);
			requestMap.put("productId", timweServiceConfig.getTimweProductId());
			requestMap.put("pricepointId", Objects.toString(timweServiceConfig.getPricePointId()));
			requestMap.put("mcc", timweServiceConfig.getMcc());
			requestMap.put("mnc", timweServiceConfig.getMnc());
			requestMap.put("text", TimweConstant.getSubscriptionMessage(timweServiceConfig, msisdn));
			requestMap.put("msisdn", msisdn);
			requestMap.put("largeAccount", timweServiceConfig.getShortCode());
			requestMap.put("priority", "NORMAL");
			requestMap.put("timezone", "Asia/Amman");
			requestMap.put("context", "STATELESS");
			String requestJson = JsonMapper.getObjectToJson(requestMap);
			timweTrans.setRequest("request url: "+url+" requestMap: "+requestJson+"  headers"+headerMap);
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(url,requestJson,headerMap);
			timweTrans.setResponseCode(httpResponse.getResponseCode());
			timweTrans.setResposne(httpResponse.getResponseStr());
			TimweResponse timweResponse = JsonMapper.getJsonToObject(httpResponse.getResponseStr(), TimweResponse.class);
			if("SUCCESS".equalsIgnoreCase(timweResponse.getCode())) {
				return "1";
			}
		} catch (Exception e) {
			logger.error("error while sending mt push="+msisdn,e);
		}finally {
			daoService.saveObject(timweTrans);
		}
		return "0";
	}
	
	public String unsubscribe(String msisdn, Integer productId) {
		Map<String,String> headerMap=new HashMap<>();
		Map<String, String> requestMap = new HashMap<>();
		CGToken cgToken = new CGToken("");
		TimweServiceConfig timweServiceConfig = null;
		VWServiceCampaignDetail vwServiceCampaignDetail = null;
		TimweTrans timweTrans = new TimweTrans(Boolean.TRUE);
		headerMap.put("Content-Type", "application/json");
		headerMap.put("apikey", TimweConstant.SUBSCRIPTION_API_KEY);
		headerMap.put("authentication", TimweUtill.getAuthCode(TimweConstant.SUBSCRIPTION_PRE_SHARED_KEY));
		try {
			
			SubscriberReg subscriberReg = jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, productId);
			if(Objects.isNull(subscriberReg)) {
				return "0";
			}
			String token = subscriberReg.getToken();
			cgToken = new CGToken(token);
			vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			timweServiceConfig = TimweConstant.mapServiceIdToTimweServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			timweTrans.setMsisdn(msisdn);
			timweTrans.setRequestType(TimweConstant.UNSUBSCRIBE);
			timweTrans.setToken(token);
			timweTrans.setTokenId(cgToken.getTokenId());
			String url =unsubscriptionURL.replaceAll("<roleid>",TimweConstant.ROLE_ID);
			requestMap.put("userIdentifier", msisdn);
			requestMap.put("userIdentifierType", "MSISDN");
			requestMap.put("productId", timweServiceConfig.getTimweProductId());
			requestMap.put("mcc", timweServiceConfig.getMcc());
			requestMap.put("mnc", timweServiceConfig.getMnc());
			requestMap.put("entryChannel", "WEB");
			requestMap.put("largeAccount", timweServiceConfig.getShortCode());
			requestMap.put("subKeyword", timweServiceConfig.getSubKey());
			String requestJson = JsonMapper.getObjectToJson(requestMap);
			timweTrans.setRequest("request url: "+url+" requestMap: "+requestJson+"  headers"+headerMap);
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(url,requestJson,headerMap);
			timweTrans.setResponseCode(httpResponse.getResponseCode());
			timweTrans.setResposne(httpResponse.getResponseStr());
			TimweResponse timweResponse = JsonMapper.getJsonToObject(httpResponse.getResponseStr(), TimweResponse.class);
			if("SUCCESS".equalsIgnoreCase(timweResponse.getCode())) {
				return "1";
			}
		} catch (Exception e) {
			logger.error("error while unsubscribing msisdn="+msisdn,e);
		}finally {
			daoService.saveObject(timweTrans);
		}
		return "0";
	}
	
}
