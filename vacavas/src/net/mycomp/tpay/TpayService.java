package net.mycomp.tpay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import net.common.service.RedisCacheService;
import net.jpa.repository.JPATpayServiceConfig;
import net.process.bean.AdNetworkRequestBean;
import net.process.request.AbstractOperatorService;

@Service("tpayService")
public class TpayService extends AbstractOperatorService {

	private static final Logger logger = Logger.getLogger(TpayService.class);

	@Autowired
	JPATpayServiceConfig jpaTpayServiceConfig;
	
	@Autowired
	@Qualifier("tpayApiService")
	private TpayApiService tpayApiService;
	
	@Autowired
	private RedisCacheService redisCacheService;

	@PostConstruct
	public void init() {
		List<TpayServiceConfig> tpayServiceConfigList = jpaTpayServiceConfig.findEnableTpayServiceConfig(true);
		logger.info("Loading Tpay Configuration ....................................");
		TpayConstant.listTpayServiceConfig.addAll(tpayServiceConfigList);
		TpayConstant.mapServiceIdToTpayServiceConfig
				.putAll(tpayServiceConfigList.stream().collect(Collectors.toMap(p -> p.getServiceId(), p -> p)));
		logger.info("TPAY CONFIG" + TpayConstant.mapServiceIdToTpayServiceConfig);

	}

	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
		logger.info("checking blocking..");
		return false;
	}

	@Override
	public boolean processBilling(ModelAndView modelAndView, AdNetworkRequestBean adNetworkRequestBean) {
		try {
			TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
					.get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());

			logger.info("TPAY-CONFIG : " + tpayServiceConfig);

			String lang = adNetworkRequestBean.getQueryString().contains("lang=2") ? TpayConstant.LANG_AR
					: TpayConstant.LANG_EN;

			
			String date = getCurrentTimeStamp() + "Z";
			String message = date+lang;
			logger.info("message: " + message);
			byte[] keyBytes = TpayConstant.SECRET_KEY.getBytes();

			String digest = TpayConstant.PUBLIC_KEY + ":" + TpayUtill.hmacSHA256(message, keyBytes);
			logger.info("Digest:" + digest);
			
			String msisdn = TpayConstant.getMsisdnByOperatorCode(tpayServiceConfig.getOperatorCode());
			
			  //This is the js which is being used for HE 
			  
			  String HEJSURL = TpayConstant.TPAY_JS_API.replaceAll("<date>", date) 
			  										   .replaceAll("<lang>",lang)
			  										   .replaceAll("<digest>", digest);
			  
			  logger.info("JS:" + HEJSURL);
			  
				
				/*
				 * String HEADERAPI = TpayConstant.TPAY_HEADER_API.replaceAll("<date>", date)
				 * .replaceAll("<theme>", TpayConstant.THEME) .replaceAll("<lang>", lang)
				 * .replaceAll("<digest>", digest);
				 */
				 
			  
			  modelAndView.addObject("HEJSURL",HEJSURL);
				/* modelAndView.addObject("HEADERAPI",HEADERAPI); */
			 
			modelAndView.addObject("TpayServiceConfig", tpayServiceConfig);
			modelAndView.addObject("token", adNetworkRequestBean.adnetworkToken.getTokenToCg());
			modelAndView.addObject("lang", lang.equals("en") ? 0 : 2);
			modelAndView.addObject("sessionToken", "");
			modelAndView.addObject("baseURL", "tpay");
			modelAndView.addObject("redirectStatus", false);
			modelAndView.addObject("flow","he");
			modelAndView.setViewName("tpay/lp");

		} catch (Exception e) {
			logger.info("exception: " + e);
		}
		return true;
	}

	@Override
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean) {
		return false;
	}

	@Override
	public boolean checkSub(Integer productId, Integer opid, String msisdn) {
		return false;
	}
	
	@Override
	public boolean sendOtp(ModelAndView modelAndView,AdNetworkRequestBean adNetworkRequestBean) {
		TpayServiceConfig tpayServiceConfig=null;
		JSONObject object = null;
		try {
			tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
					            .get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
			
			String response = tpayApiService.sendPin(adNetworkRequestBean.adnetworkToken.getTokenToCg(), adNetworkRequestBean.getMsisdn(),
					"", "0", "");
			object = new JSONObject(response);
			if(Objects.toString(object.get("operationStatusCode")).equals("0")){
				redisCacheService.putObjectCacheValueByEvictionDay(TpayConstant.TPAY_SUB_CONT_ID_PREFIX+adNetworkRequestBean.getMsisdn(),
				Objects.toString(object.get("subscriptionContractId")), 1);
				return true;
			}
		}catch (Exception e) {
			logger.error("error sending otp tpay"+adNetworkRequestBean.getMsisdn());
		}
		return false;
	}
	
	@Override
	public boolean validateOtp(ModelAndView modelAndView,AdNetworkRequestBean adNetworkRequestBean) {
		
		TpayServiceConfig tpayServiceConfig=null;
		JSONObject object = null;
		String subscriptionContractId=null;
		try {
			subscriptionContractId =Objects.toString(redisCacheService.getObjectCacheValue(TpayConstant.TPAY_SUB_CONT_ID_PREFIX+adNetworkRequestBean.getMsisdn()));
			tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
					            .get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
			
			String response = tpayApiService.validatePin(adNetworkRequestBean.adnetworkToken.getTokenToCg(), adNetworkRequestBean.getMsisdn(),
					subscriptionContractId, adNetworkRequestBean.adnetworkToken.getParam1());
			object = new JSONObject(response);
			if(Objects.toString(object.get("operationStatusCode")).equals("0")){
				String mtSendResponse = tpayApiService.sendWelcomeMT(adNetworkRequestBean.adnetworkToken.getTokenToCg(), adNetworkRequestBean.getMsisdn(),
						subscriptionContractId, 0);
				object = new JSONObject(mtSendResponse);
				if(Objects.toString(object.get("messageDeliveryStatus")).equals("true")) {
					redisCacheService.removeObjectCacheValue(TpayConstant.TPAY_SUB_CONT_ID_PREFIX+adNetworkRequestBean.getMsisdn());
					redisCacheService.putObjectCacheValueByEvictionDay(TpayConstant.TPAY_SUBCONTID_TOKEN_AND_MSISDN_CACHE_PREFIX+subscriptionContractId, adNetworkRequestBean.getMsisdn()+"c"+adNetworkRequestBean.adnetworkToken.getTokenToCg(), 1);
					return true;
				}
			}
		}catch (Exception e) {
			logger.error("error sending otp tpay"+adNetworkRequestBean.getMsisdn());
		}	
		return false;
	}
	
	private static String getCurrentTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}

}