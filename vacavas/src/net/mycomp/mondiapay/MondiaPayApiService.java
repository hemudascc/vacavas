package net.mycomp.mondiapay;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.common.service.IDaoService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.process.bean.CGToken;
import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;
import net.util.JsonMapper;

@Service("mondiaPayApiService")
public class MondiaPayApiService {
	
	@Autowired
	private IDaoService daoService;
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	private static final Logger logger = Logger.getLogger(MondiaPayApiService.class);
	private HttpURLConnectionUtil httpURLConnectionUtil;
	
	@Value("${mondia.pay.client.token.url}")
	private String clientTokenURL;
	@Value("${mondia.pay.purchase.subscription.url}")
	private String purchaseSubscriptionURL;
	@Value("${mondia.pay.redirect.url}")
	private String redirectURL;
	@Value("${mondia.pay.fetch.user.access.token.url}")
	private String fetchUserAccessTokenURL;
	@Value("${mondia.pay.delete.user.url}")
	private String deleteUserURL;
	@Value("${mondia.pay.get.user.subscription.detail.url}")
	private String getUserSubscriptionDetailURL;
	@Value("${mondia.pay.encoded.portal.url}")
	private String encodedPortalURL;
	
	
	@PostConstruct
	public void init(){
		httpURLConnectionUtil=new HttpURLConnectionUtil();
		
	}
	
	public String getAccessToken(String token) {
		
		MondiaPayTrans mondiaPayTrans = new MondiaPayTrans(true);
		Map<String,String> headerMap=new HashMap<>();
		JSONObject json=null;
		try {
			String url = clientTokenURL.replaceAll("<clientId>", MondiaPayConstant.CLIENT_ID).replaceAll("<clientSecret>", MondiaPayConstant.CLIENT_SECRET);
			headerMap.put("accept", "application/json");
			headerMap.put("content-type", "application/x-www-form-urlencoded");
			CGToken cgToken = new CGToken(token);
			mondiaPayTrans.setRequestType(MondiaPayConstant.CLIENT_TOKEN);
			mondiaPayTrans.setToken(token);
			mondiaPayTrans.setRequest("request url:"+url+" headers : "+headerMap);
			mondiaPayTrans.setTokenId(cgToken.getTokenId());
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(url,null,headerMap);
			mondiaPayTrans.setResponseCode(httpResponse.getResponseCode());
			mondiaPayTrans.setResposne(httpResponse.getResponseStr());
			json = new JSONObject(httpResponse.getResponseStr());
		} catch (Exception e) {
			logger.error("error getting client token"+e);
		}finally {
			daoService.saveObject(mondiaPayTrans);
		}
		return json.getString("access_token");
	}
	
	public String purchaseSubscription(String token, String authorization) {
		
		MondiaPayTrans mondiaPayTrans = new MondiaPayTrans(true);
		Map<String,String> headerMap=new HashMap<>();
		Map<String, String> requestMap = new HashMap<>();
		try {
			headerMap.put("Accept", "application/json");
			headerMap.put("Content-Type", "application/json");
			headerMap.put("Authorization", "Bearer "+authorization);
			requestMap.put("redirect", redirectURL.replaceAll("<token>", token));
			requestMap.put("subscriptionTypeId", MondiaPayConstant.SUBSCRIPTION_TYPE_ID);
			String requestJson = JsonMapper.getObjectToJson(requestMap);
			CGToken cgToken = new CGToken(token);
			mondiaPayTrans.setRequestType(MondiaPayConstant.PURCHASE_SUBSCRIPTION);
			mondiaPayTrans.setToken(token);
			mondiaPayTrans.setTokenId(cgToken.getTokenId());
			mondiaPayTrans.setRequest("request url: "+purchaseSubscriptionURL+" requestMap: "+requestJson+"headers"+headerMap);
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(purchaseSubscriptionURL,requestJson,headerMap);
			mondiaPayTrans.setResponseCode(httpResponse.getResponseCode());
			mondiaPayTrans.setResposne(httpResponse.getResponseStr());
		} catch (Exception e) {
			logger.error("ERROR Purchase Subscription token="+token+" authorization="+authorization,e);
		}finally {
			daoService.saveObject(mondiaPayTrans);
		}
		return mondiaPayTrans.getResposne();
	}
	
	public JSONObject fetchUserAccessToken(String code, String token) {
		MondiaPayTrans mondiaPayTrans = new MondiaPayTrans(true);
		Map<String,String> headerMap=new HashMap<>();
		String portal=encodedPortalURL.replaceAll("<token>", token);
		JSONObject json=null;
		try {
			String url = fetchUserAccessTokenURL.replaceAll("<clientId>", MondiaPayConstant.CLIENT_ID)
					.replaceAll("<clientSecret>", MondiaPayConstant.CLIENT_SECRET).replaceAll("<code>", code)
					.replaceAll("<portalurl>", portal);
			headerMap.put("Accept", "application/json");
			headerMap.put("Content-Type", "application/x-www-form-urlencoded");
			CGToken cgToken = new CGToken(token);
			mondiaPayTrans.setRequestType(MondiaPayConstant.ACCESS_TOKEN);
			mondiaPayTrans.setToken(token);
			mondiaPayTrans.setRequest("request url:"+url+" headers : "+headerMap);
			mondiaPayTrans.setTokenId(cgToken.getTokenId());
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPPOSTRequest(url,null,headerMap);
			mondiaPayTrans.setResponseCode(httpResponse.getResponseCode());
			mondiaPayTrans.setResposne(httpResponse.getResponseStr());
			json = new JSONObject(httpResponse.getResponseStr());
			
		} catch (Exception e) {
			logger.error("ERROR fetching user access token code="+code+" token"+token,e);
		}finally {
			daoService.saveObject(mondiaPayTrans);
		}
		return json;
	}
	
	public JSONObject getUserSubscriptionDetails(String userAccessToken, String token) {
		MondiaPayTrans mondiaPayTrans = new MondiaPayTrans(true);
		Map<String,String> headerMap=new HashMap<>();
		JSONArray jsonArray=null;
		JSONObject json=null;
		try {
			String url = getUserSubscriptionDetailURL.replaceAll("<subscription_tpye_id>", MondiaPayConstant.SUBSCRIPTION_TYPE_ID);
			headerMap.put("Authorization", "Bearer "+userAccessToken);
			CGToken cgToken = new CGToken(token);
			mondiaPayTrans.setRequestType(MondiaPayConstant.SUBSCRIPTION_DETAIL);
			mondiaPayTrans.setToken(token);
			mondiaPayTrans.setRequest("request url:"+url+" headers : "+headerMap);
			mondiaPayTrans.setTokenId(cgToken.getTokenId());
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPGETRequest(url, headerMap);
			mondiaPayTrans.setResponseCode(httpResponse.getResponseCode());
			mondiaPayTrans.setResposne(httpResponse.getResponseStr());
			jsonArray = new JSONArray(httpResponse.getResponseStr());
			json = jsonArray.getJSONObject(0);
		} catch (Exception e) {
			logger.error("ERROR fetching user access token code="+userAccessToken+" token"+token,e);
		}finally {
			daoService.saveObject(mondiaPayTrans);
		}
		return json;
	}
	
	public String deleteUser(String token,Integer productId) {
		MondiaPayTrans mondiaPayTrans = new MondiaPayTrans(true);
		Map<String,String> headerMap=new HashMap<>();
		JSONObject json =null;
		String status="204 NO CONTENT";
		try {
			SubscriberReg subscriberReg =jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(token, productId);
			
			JSONObject userSubscriptionDetails = getUserSubscriptionDetails(subscriberReg.getParam2()
					, subscriberReg.getToken());
			String subscriptionId= Objects.toString(userSubscriptionDetails.get("id"));
			String url = deleteUserURL.replaceAll("<subscription_tpye_id>", subscriptionId);
			headerMap.put("Authorization", "Bearer "+subscriberReg.getParam2());
			CGToken cgToken = new CGToken(token);
			mondiaPayTrans.setRequestType(MondiaPayConstant.CANCEL_SUBSCRIPTION);
			mondiaPayTrans.setToken(token);
			mondiaPayTrans.setRequest("request url:"+url+" headers : "+headerMap);
			mondiaPayTrans.setTokenId(cgToken.getTokenId());
			HTTPResponse  httpResponse = httpURLConnectionUtil.makeHTTPDeleteRequest(url, headerMap);
			mondiaPayTrans.setResponseCode(httpResponse.getResponseCode());
			mondiaPayTrans.setResposne(httpResponse.getResponseStr());
			json = new JSONObject(httpResponse.getResponseStr());
			status=json.getString("Status");
		} catch (Exception e) {
			logger.error("error while unsubscribe token"+token,e);
		}finally {
			daoService.saveObject(mondiaPayTrans);
		}
		return status;
	}
	
	/*
	 * public static void main(String args[]) { JSONArray jsonArray = new JSONArray(
	 * "[{\"typeId\":66730001,\"startDate\":\"2020-12-24T09:20:14Z\",\"id\":247468012,\"created\":\"2020-12-24T09:20:14Z\",\"lastModified\":\"2020-12-24T09:20:14Z\",\"attributes\":[],\"state\":\"ACTIVE\",\"subState\":\"NONE\"}]"
	 * ); JSONObject json = jsonArray.getJSONObject(0); System.out.println(json);
	 * 
	 * }
	 */

}
