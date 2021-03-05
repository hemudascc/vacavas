package net.mycomp.altruist;

import java.net.URLEncoder;
import java.sql.Timestamp;
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

@Service
public class AltruistApiService {
	private static final Logger logger = Logger.getLogger(AltruistApiService.class);

	@Value("${altruist.pin.send.url}")
	private String pinSendURL;
	@Value("${altruist.pin.verify.url}")
	private String pinVerifyURL;
	@Value("${altruist.unsubscribe.url}")
	private String unsubscribeURL;
	@Value("${altruist.sms.push.url}")
	private String smsPushURL;
	private HttpURLConnectionUtil httpURLConnectionUtil;
	@Autowired
	private IDaoService daoService;
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	@Autowired
	RedisCacheService redisCacheService;
	@Autowired
	private SubscriberRegService subscriberRegService;
	
	@PostConstruct
	public void init(){
		httpURLConnectionUtil=new HttpURLConnectionUtil();

	}

	public String pinSend(String token, String msisdn,String channel) {
		Map<String,String> headerMap=new HashMap<>();
		Map<String, String> requestMap = new HashMap<>();
		CGToken cgToken = new CGToken(token);
		AltruistTrans altruistTrans = new AltruistTrans(true);
		VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
		AltruistServiceConfig altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig.get(vwServiceCampaignDetail.getServiceId());
		headerMap.put("Content-Type", "application/json");
		try {
			altruistTrans.setMsisdn(msisdn);
			altruistTrans.setRequestType(AltruistConstant.PIN_SEND);
			altruistTrans.setToken(token);
			altruistTrans.setTokenId(cgToken.getTokenId());
			requestMap.put("msisdn", AltruistUtil.encrypt(AltruistUtil.formatMsisdn(msisdn)));
			requestMap.put("user", AltruistUtil.encrypt(altruistServiceConfig.getUserName()));
			requestMap.put("password", AltruistUtil.encrypt(altruistServiceConfig.getPassword()));
			requestMap.put("txnid", token);
			requestMap.put("packageId", AltruistUtil.encrypt(Objects.toString(altruistServiceConfig.getPackageId())));
			requestMap.put("channel", channel);
			requestMap.put("sourceIP", "");
			requestMap.put("adPartnerName", "Google ads");
			requestMap.put("pubId", "2");
			String requestJson = JsonMapper.getObjectToJson(requestMap);
			altruistTrans.setRequest("request url: "+pinSendURL+" requestMap: "+requestJson+"headers"+headerMap);
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(pinSendURL,requestJson,headerMap);
			altruistTrans.setResponseCode(httpResponse.getResponseCode());
			altruistTrans.setResposne(httpResponse.getResponseStr());
			if(Objects.nonNull(httpResponse.getResponseStr())) {
				if(httpResponse.getResponseStr().contains("pin_sent")) {
					String[] responseArray = httpResponse.getResponseStr().split("\\|");
					String uniqueToken=responseArray[1];
					redisCacheService.putObjectCacheValueByEvictionDay(AltruistConstant.ALTRUIST_UNIQUE_TOKEN_PREFIX+msisdn, uniqueToken, 1);
					return "1";
				}else if(httpResponse.getResponseStr().contains("Invalid_MSISDN")) {
					return "2";
				}else if(httpResponse.getResponseStr().contains("PIN_LIMIT_EXCEDEED")) {
					return "3";
				}else {
					return "0";
				}
			}

		} catch (Exception e) {
			logger.error("error while sending pin to msisdn="+msisdn+" token="+token);
		}finally {
			daoService.saveObject(altruistTrans);
		}
		return "0";
	}

	public String verifyPin(String token, String msisdn,String pin, String channel) {
		Map<String,String> headerMap=new HashMap<>();
		Map<String, String> requestMap = new HashMap<>();
		CGToken cgToken = new CGToken(token);
		AltruistTrans altruistTrans = new AltruistTrans(true);
		VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
		AltruistServiceConfig altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig.get(vwServiceCampaignDetail.getServiceId());
		headerMap.put("Content-Type", "application/json");
		LiveReport liveReport=null;
		try {
			liveReport = new LiveReport(MConstants.ALTRUIST_ETISALAT_UAE_OPERATOR_ID, new Timestamp(System.currentTimeMillis()),
					cgToken.getCampaignId(), altruistServiceConfig.getServiceId(), 4);
			liveReport.setMsisdn(msisdn);
			liveReport.setTokenId(cgToken.getTokenId());
			liveReport.setToken(token);
			liveReport.setAction(MConstants.ACT);
			altruistTrans.setMsisdn(msisdn);
			altruistTrans.setRequestType(AltruistConstant.PIN_VERIFY);
			altruistTrans.setToken(token);
			altruistTrans.setTokenId(cgToken.getTokenId());
			altruistTrans.setPin(pin);
			requestMap.put("msisdn", AltruistUtil.encrypt(AltruistUtil.formatMsisdn(msisdn)));
			requestMap.put("user", AltruistUtil.encrypt(altruistServiceConfig.getUserName()));
			requestMap.put("password", AltruistUtil.encrypt(altruistServiceConfig.getPassword()));
			requestMap.put("txnid", token);
			requestMap.put("packageId", AltruistUtil.encrypt(Objects.toString(altruistServiceConfig.getPackageId())));
			requestMap.put("channel", channel);
			requestMap.put("sourceIP", "");
			requestMap.put("adPartnerName", "Google ads");
			requestMap.put("pubId", "2");
			requestMap.put("pin", AltruistUtil.encrypt(pin));
			requestMap.put("token", Objects.toString(redisCacheService.getObjectCacheValue(AltruistConstant.ALTRUIST_UNIQUE_TOKEN_PREFIX+msisdn)));
			String requestJson = JsonMapper.getObjectToJson(requestMap);
			altruistTrans.setRequest("request url: "+pinVerifyURL+" requestMap: "+requestJson+"headers"+headerMap);
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(pinVerifyURL,requestJson,headerMap);
			altruistTrans.setResponseCode(httpResponse.getResponseCode());
			altruistTrans.setResposne(httpResponse.getResponseStr());	
			if(Objects.nonNull(httpResponse.getResponseStr())) {
				if(httpResponse.getResponseStr().contains("success")) {
					subscriberRegService.findOrCreateSubscriberByAct(msisdn,null, liveReport);
					smsPush(msisdn,token);
					return "1";
				}
			}
		} catch (Exception e) {
			logger.error("error while verifying pin to msisdn="+msisdn+" token="+token);
		}finally {
			daoService.saveObject(altruistTrans);
		}
		return "0";
	}

	public String unsubscribe(String msisdn, Integer productId) {
		AltruistTrans altruistTrans = new AltruistTrans(true);
		CGToken cgToken = new CGToken("");
		try {
			altruistTrans.setMsisdn(msisdn);
			altruistTrans.setRequestType(AltruistConstant.UNSUBSCRIBE);
			SubscriberReg subscriberReg = jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, productId);
			if(Objects.nonNull(subscriberReg) && subscriberReg.getStatus()==1) {
				cgToken = new CGToken(subscriberReg.getToken());
			}else {
				altruistTrans.setResposne("Not Subscribed User");
				return "2";
			}
			VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			AltruistServiceConfig altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			String url= unsubscribeURL.replaceAll("<msisdn>", msisdn)
					.replaceAll("<username>", URLEncoder.encode(altruistServiceConfig.getUserName()))
					.replaceAll("<password>", URLEncoder.encode(altruistServiceConfig.getPassword()))
					.replaceAll("<packageid>", URLEncoder.encode(Objects.toString(altruistServiceConfig.getPackageId())));
			altruistTrans.setRequest("request url: "+url);
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPGETRequest(url, null);
			altruistTrans.setResponseCode(httpResponse.getResponseCode());
			altruistTrans.setResposne(httpResponse.getResponseStr());
			if(Objects.nonNull(httpResponse.getResponseStr())) {
				if(httpResponse.getResponseStr().contains("Deactivation_Success")) {
					return "1";
				}else if(httpResponse.getResponseStr().contains("Already_Deactive")){
					return "2";
				}else {
					return "0";
				}
			}
		} catch (Exception e) {
			logger.error("error while unsubscribing to msisdn="+msisdn);
		}finally {
			daoService.saveObject(altruistTrans);
		}
		return "0";
	}

	private void smsPush(String msisdn,String token) {
		AltruistTrans altruistTrans = new AltruistTrans(true);
		CGToken cgToken = new CGToken(token);
		try {
			VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			AltruistServiceConfig altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			String sms=AltruistConstant.getSubscriptionMessage(altruistServiceConfig,msisdn);
			altruistTrans.setMsisdn(msisdn);
			altruistTrans.setRequestType(AltruistConstant.SEND_SMS);
			String url = smsPushURL.replaceAll("<username>", URLEncoder.encode(altruistServiceConfig.getMtPushLogin()))
					.replaceAll("<password>", URLEncoder.encode(altruistServiceConfig.getMtPushPassword()))
					.replaceAll("<msisdn>", URLEncoder.encode(msisdn))
					.replaceAll("<message>", URLEncoder.encode(sms));
			altruistTrans.setRequest("request url: "+url);
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPGETRequest(url, null);
			altruistTrans.setResponseCode(httpResponse.getResponseCode());
			altruistTrans.setResposne(httpResponse.getResponseStr());
		} catch (Exception e) {
			logger.error("error while sending sms to msisdn="+msisdn);
		}finally {
			daoService.saveObject(altruistTrans);
		}
	}

}
