package net.mycomp.tpay;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MData;

@RestController
@RequestMapping("tpay")
public class TpayController {

	private static final Logger logger = Logger.getLogger(TpayController.class);

	@Autowired
	private JMSTpayService jmsTpayService;
	  
	@Autowired 
	private SubscriberRegService subscriberRegService;
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private RedisCacheService redisCacheService;

	@Autowired
	@Qualifier("tpayApiService")
	private TpayApiService tpayApiService;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;

	
	@RequestMapping(value = "/notification")
	public String tpayNotification(HttpServletRequest request) {
		logger.info("Notification received " + request.getQueryString());
		TpayNotification tpayNotification = new TpayNotification(true);
		try {
			tpayNotification.setTpayAction(request.getParameter("action"));
			tpayNotification.setAmount(request.getParameter("collectedAmount"));
			tpayNotification.setMsisdn(request.getParameter("msisdn"));
			tpayNotification.setNotificationStatus(request.getParameter("status"));
			tpayNotification.setPaymentTransactionStatusCode(request.getParameter("paymentTransactionStatusCode"));
			tpayNotification.setQueryStr(request.getQueryString());
			tpayNotification.setSubscriptionContractId(request.getParameter("subscriptionContractId"));
			tpayNotification.setProductCatalogName(request.getParameter("productCatalogName"));
		} catch (Exception e) {
			logger.error("notification:: ", e);
		} finally {
			jmsTpayService.saveTpayNotification(tpayNotification);
		}
		return "CALLBACK ACKNOWELDGED";

	}
	@RequestMapping("send-pin")
	public String sendPin(@RequestParam String token, @RequestParam String msisdn,
			@RequestParam(defaultValue = "") String sessionToken,@RequestParam String lang, 
			@RequestParam(defaultValue = "") String headerEnrichmentReferenceCode) {
		String response = null;
		JSONObject object = null;
		logger.info("sending pin to user msisdn : " + msisdn + " token : " + token);
		try {
			if(msisdn !=null) {
				msisdn = msisdn.trim();
			}
			CGToken cgToken = new CGToken(token);

			VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail
					.get(cgToken.getCampaignId());

			TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
					.get(vwServiceCampaignDetail.getServiceId());
			redisCacheService.putObjectCacheValueByEvictionMinute(TpayConstant.TPAY_CACHE_PREFIX + msisdn, token,
					60 * 24);
			response = tpayApiService.sendPin(token, msisdn, sessionToken,lang,headerEnrichmentReferenceCode);
			object = new JSONObject(response);
			object.put("portalUrl", tpayServiceConfig.getProtalUrl().replaceAll("<msisdn>", msisdn)
					.replaceAll("<lang>", lang.equals("1")?"2":"1"));
			logger.info("pin sent to user msisdn : " + msisdn + " token : " + token);
		} catch (Exception e) {
			logger.info("some exception while sending pin to misidn :" + msisdn);
		}
		return object.toString();
	}

	@RequestMapping("resend-pin")
	public String resendPin(@RequestParam String token, @RequestParam String msisdn,
			@RequestParam String subscriptionContractId, @RequestParam String lang) {

		String response = null;
		logger.info("resending pin to user msisdn : " + msisdn + " token : " + token);
		try {
			if(msisdn !=null) {
				msisdn = msisdn.trim();
			}
			response = tpayApiService.resendPin(token, msisdn, subscriptionContractId,lang);

			logger.info("pin resent to user msisdn : " + msisdn + " token : " + token);
		} catch (Exception e) {
			logger.info("some exception while sending pin to misidn :" + msisdn);
		}
		return response;
	}

	@RequestMapping("validate-pin")
	public String validatePin(@RequestParam String token, @RequestParam String msisdn,
			@RequestParam String subscriptionContractId, @RequestParam String pin, @RequestParam String lang) {
		String response = null;
		JSONObject object = null;
		logger.info("validate pin to user subscriptionContractId : " + subscriptionContractId + " pin : " + pin);
		try {
			if(msisdn !=null) {
				msisdn = msisdn.trim();
			}
			if(pin !=null) {
				pin = pin.trim();
			}
			redisCacheService.putObjectCacheValueByEvictionMinute(
					TpayConstant.TPAY_CACHE_PREFIX + subscriptionContractId, msisdn, 60 * 24);
			CGToken cgToken = new CGToken(token);

			VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail
					.get(cgToken.getCampaignId());

			TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
					.get(vwServiceCampaignDetail.getServiceId());

			response = tpayApiService.validatePin(token, msisdn, subscriptionContractId, pin);
			object = new JSONObject(response);
			object.put("portalUrl", tpayServiceConfig.getProtalUrl().replaceAll("<msisdn>", msisdn)
					.replaceAll("<lang>", lang.equals("1")?"2":"1").replaceAll("<subid>", msisdn));
			// response = response.substring(0, response.length()-1)+","":"++"}";

			logger.info("pin validation to user subscriptionContractId : " + subscriptionContractId + " pin : " + pin);
		} catch (Exception e) {
			logger.info("some exception while validating pin to misidn :" + msisdn);
		}
		return object.toString();
	}

	@RequestMapping("send-welcome-mt")  
	public String sendWelcomeMT(@RequestParam String token, @RequestParam String msisdn,  
			@RequestParam String subscriptionContractId, @RequestParam Integer lang) {

		String response = null;
		logger.info("Sending welcome message to user msisdn : " + msisdn + " token : " + token
				+ " subscriptionContractId: " + subscriptionContractId+" lang: "+lang);
		try {
			if(msisdn !=null) {
				msisdn = msisdn.trim();
			}
			response = tpayApiService.sendWelcomeMT(token, msisdn, subscriptionContractId,lang);

			logger.info("Sending welcome message to user msisdn : " + msisdn + " token : " + token
					+ " subscriptionContractId: " + subscriptionContractId+" lang: "+lang+" Response:"+response);
		} catch (Exception e) {
			logger.info("some exception while sending welcome message to misidn :" + msisdn);
		}
		return response;

	}
	
	@RequestMapping("send-content-mt")
	public String sendContentMT(@RequestParam String msisdn, @RequestParam Integer lang,@RequestParam String token) {

		String response = null;
		logger.info("Sending Content message to user msisdn : " + msisdn+" lang: "+lang);
		try {
			if(msisdn !=null) {
				msisdn = msisdn.trim();
			}
			response = tpayApiService.sendContentMT(msisdn, lang,token);

			logger.info("Sent Content message to user msisdn : " + msisdn+" lang: "+lang);	
		} catch (Exception e) {
			logger.info("some exception while sending content message to misidn :" + msisdn);
		}
		return response;

	}

	@RequestMapping("unsub")
	public ModelAndView confirmUnsubscribe(HttpServletRequest request, ModelAndView modelAndView) {
		String msisdn = request.getParameter("msisdn");
		String lang = request.getParameter("lang");
		int productId = Integer.parseInt(request.getParameter("productid"));
		SubscriberReg subscriberReg = jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn,productId );
		modelAndView.setViewName("tpay/unsubscribe");
		modelAndView.addObject("msisdn", msisdn);
		if(subscriberReg!=null && subscriberReg.getStatus()==1) {
			TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
					.get(subscriberReg.getServiceId());
			CGToken cgToken = new CGToken(subscriberReg.getParam3());
			modelAndView.addObject("lpImageUrl", tpayServiceConfig.getLpImageUrl());
			modelAndView.addObject("portalUrl", tpayServiceConfig.getProtalUrl().replaceAll("<msisdn>", msisdn).replaceAll("<lang>", lang).replaceAll("<subid>", msisdn));	
			modelAndView.addObject("campId", cgToken.getCampaignId());	
			modelAndView.addObject("token", subscriberReg.getParam3());
			modelAndView.addObject("productId", subscriberReg.getProductId());
		}
		modelAndView.addObject("lang", lang);
		return modelAndView;
	}

	@RequestMapping("unsubscribe")
	public String unsubscribe(@RequestParam String msisdn, @RequestParam String lang,@RequestParam String token,@RequestParam String productId ) {

		String response = null;
		logger.info("unsubscribing user msisdn : " + msisdn);
		try {
			if(msisdn !=null) {
				msisdn = msisdn.trim();
			}
			response = tpayApiService.unsubscribe(msisdn, lang,token,Integer.parseInt(productId));

			logger.info("unsubscribing user msisdn : " + msisdn);
		} catch (Exception e) {
			logger.info("some exception while unsubscribing user msisdn : " + msisdn);
		}
		return response;

	}

	@RequestMapping("change-lang")
	public ModelAndView changeLang(@RequestParam String token, @RequestParam Integer lang, @RequestParam String sessionToken, ModelAndView modelAndView) {
		
		CGToken cgToken=new CGToken(token);
		VWServiceCampaignDetail 
		vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
		TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
				.get(vwServiceCampaignDetail.getServiceId());
		String langString=lang==0?"en":"ar";
		String date = getCurrentTimeStamp() + "Z";
		String message = date+langString;
		logger.info("message: " + message);
		byte[] keyBytes = tpayServiceConfig.getPrivateKey().getBytes();

		String digest = tpayServiceConfig.getPublicKey() + ":" + TpayUtill.hmacSHA256(message, keyBytes);
		logger.info("Digest:" + digest);
		
	//	String msisdn = TpayConstant.getMsisdnByOperatorCode(tpayServiceConfig.getOperatorCode());
		
		  //This is the js which is being used for HE 
		  
		  String HEJSURL = TpayConstant.TPAY_JS_API.replaceAll("<date>", date)
				  								   .replaceAll("<lang>",langString)
		  										   .replaceAll("<digest>", digest);
		  
		modelAndView.addObject("HEJSURL",HEJSURL);
		modelAndView.addObject("TpayServiceConfig", tpayServiceConfig);
		modelAndView.addObject("token", token);
		modelAndView.addObject("lang", lang);
		modelAndView.addObject("sessionToken", "");
		modelAndView.addObject("baseURL", "");
		modelAndView.addObject("redirectStatus", true);
		modelAndView.addObject("flow","he");
		modelAndView.setViewName("tpay/lp");
		return modelAndView;

	}
	
	@RequestMapping("prelander")
	public ModelAndView prelander(ModelAndView modelAndView, HttpSession session) {
		session.setAttribute("jspath", "test.js");
		modelAndView.setView(new RedirectView("./test"));
		return modelAndView;
	}
	@RequestMapping("test")
	public ModelAndView test(ModelAndView modelAndView,HttpSession session) {
		modelAndView.addObject("jspath", session.getAttribute("jspath"));
		modelAndView.setViewName("tpay/prelander");
		
		return modelAndView;
	}
	@RequestMapping("redirect-portal/{subscriptionContractId}/{lang}/{token}")
	public ModelAndView redirectPortal(@PathVariable("subscriptionContractId") String subscriptionContractId,
										@PathVariable("lang") String lang,@PathVariable("token") String token, ModelAndView modelAndView) {
		String portalURL="http://subscriptionContractId";
		CGToken cgToken=new CGToken(token);
		VWServiceCampaignDetail 
		vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
		TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
				.get(vwServiceCampaignDetail.getServiceId());
		List<SubscriberReg> subscriberRegs = tpayApiService.getSubscriberRegBySubscriptionContractId(subscriptionContractId);
		if(subscriberRegs!=null) {
			portalURL = tpayServiceConfig.getProtalUrl().replaceAll("<msisdn>", subscriberRegs.get(0).getMsisdn())
					.replaceAll("<lang>", lang.equals("0")?"1":"2")
					.replaceAll("<subid>", subscriptionContractId);
		}
		modelAndView.setView(new RedirectView(portalURL));
		return modelAndView;
	}
	
	@RequestMapping("check-sub")
	public Integer checkSub(@RequestParam String msisdn) {	
		return subscriberRegService.isSubscribed(msisdn)?1:0;
	}
	@RequestMapping("wifi-flow")
	public ModelAndView wifiFlow(ModelAndView modelAndView, @RequestParam String token, @RequestParam Integer lang) {	
		CGToken cgToken=new CGToken(token);
		VWServiceCampaignDetail 
		vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
		TpayServiceConfig tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig
				.get(vwServiceCampaignDetail.getServiceId());
		String langString=lang==0?"en":"ar";
		String date = getCurrentTimeStamp() + "Z";
		String message = date+langString;
		logger.info("message: " + message);
		byte[] keyBytes = tpayServiceConfig.getPrivateKey().getBytes();

		String digest = tpayServiceConfig.getPublicKey() + ":" + TpayUtill.hmacSHA256(message, keyBytes);
		logger.info("Digest:" + digest);
		
	//	String msisdn = TpayConstant.getMsisdnByOperatorCode(tpayServiceConfig.getOperatorCode());
		
		  //This is the js which is being used for HE 
		  
		  String HEJSURL = TpayConstant.TPAY_JS_API.replaceAll("<date>", date)
				  								   .replaceAll("<lang>",langString)
		  										   .replaceAll("<digest>", digest);

		  
		modelAndView.addObject("HEJSURL",HEJSURL);
		modelAndView.addObject("TpayServiceConfig", tpayServiceConfig);
		modelAndView.addObject("token", token);
		modelAndView.addObject("lang", lang);
		modelAndView.addObject("sessionToken", "");
		modelAndView.addObject("baseURL", "");
		modelAndView.addObject("redirectStatus", true);
		modelAndView.addObject("flow","wifi");
		modelAndView.setViewName("tpay/lp");
		return modelAndView;
	}

	private static String getCurrentTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}
}
