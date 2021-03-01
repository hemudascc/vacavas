package net.mycomp.mondiapay;

import java.sql.Timestamp;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.common.service.IDaoService;
import net.common.service.SubscriberRegService;
import net.persist.bean.LiveReport;
import net.process.bean.CGToken;
import net.util.MConstants;

@Controller
@RequestMapping("mondiapay")
public class MondiaPayController {

	private static final Logger logger = Logger.getLogger(MondiaPayController.class);

	@Autowired
	@Qualifier("jmsMondiaPayService")
	private JMSMondiaPayService jmsMondiaPayService;

	@Value("${mondia.pay.portal.url}")
	private String portalURL;

	@Autowired
	@Qualifier("mondiaPayApiService")
	private MondiaPayApiService mondiaPayApiService; 
	@Autowired
	private SubscriberRegService subscriberRegService;

	@Autowired
	private IDaoService daoService;

	@RequestMapping("tc")
	public ModelAndView termsAndCondition(ModelAndView modelAndView){
		modelAndView.setViewName("mondiapay/tc");
		return modelAndView;
	}

	@RequestMapping("thankyou")
	public ModelAndView thankYou(ModelAndView modelAndView){
		modelAndView.addObject("portalURL", "http://www.google.com");
		modelAndView.setViewName("mondiapay/thankyou");
		return modelAndView;
	}

	@RequestMapping("lp")
	public ModelAndView lp(ModelAndView modelAndView){
		modelAndView.setViewName("mondiapay/lp");
		return modelAndView;
	}

	@RequestMapping("cgcallback/{token}")
	public ModelAndView cgCallback(ModelAndView modelAndView, HttpServletRequest request, 
			@PathVariable("token") String token){
		String portal=portalURL.replaceAll("<token>", token);
		modelAndView.setViewName("mondiapay/thankyou");
		modelAndView.addObject("portalURL", portal);
		LiveReport liveReport=null;
		MondiaPayCgCallback mondiaPayCgCallback = new MondiaPayCgCallback(true);
		try {
			mondiaPayCgCallback.setToken(token);
			mondiaPayCgCallback.setAuthorizationCode(request.getParameter("code"));
			logger.debug("processing MondiaPayCgCallback"+mondiaPayCgCallback);
			CGToken cgToken = new CGToken(mondiaPayCgCallback.getToken());
			JSONObject response = mondiaPayApiService.fetchUserAccessToken(mondiaPayCgCallback.getAuthorizationCode(),
					mondiaPayCgCallback.getToken());
			JSONObject userSubscriptionDetails = mondiaPayApiService.getUserSubscriptionDetails(response.getString("access_token")
					, token);
			mondiaPayCgCallback.setAccessToken(response.getString("access_token"));
			mondiaPayCgCallback.setRefreshToken(response.getString("refresh_token"));
			liveReport = new LiveReport(MondiaPayConstant.MONDIA_PAY_OPERATOR_ID, new Timestamp(System.currentTimeMillis()),
					cgToken.getCampaignId(), MondiaPayConstant.MONDIA_PAY_SERVICE_ID, MondiaPayConstant.MONDIA_PAY_PRODUCT_ID);
			liveReport.setTokenId(cgToken.getTokenId());
			liveReport.setToken(token);
			liveReport.setMsisdn(token);
			liveReport.setParam1(Objects.toString(userSubscriptionDetails.getInt("id")));
			liveReport.setParam2(request.getParameter("code"));
			liveReport.setParam3(response.getString("access_token"));
			liveReport.setAction(MConstants.ACT);
			subscriberRegService.findOrCreateSubscriberByAct(mondiaPayCgCallback.getToken(), 
					null, liveReport);
		} catch (Exception e) {
			logger.error("error mondia cg callback",e);
		}finally {
			daoService.saveObject(mondiaPayCgCallback);
		}
		return modelAndView;
	}

	@RequestMapping("purchase-subscription")
	@ResponseBody
	public String purchaseSubscription(HttpServletRequest request){
		String token = request.getParameter("token");
		String authorization = request.getParameter("authorization");
		return mondiaPayApiService.purchaseSubscription(token, authorization);
	}

	@RequestMapping("notification")
	@ResponseBody
	public String notification(@RequestBody MondiaPayNotification mondiaPayNotification) {
		try {
			System.out.println("MondiaPayNotification :::::::::::"+mondiaPayNotification);
		} catch (Exception e) {
			logger.error("error while processing mondia notification",e);
		}finally {
			jmsMondiaPayService.saveMondiaPayNotification(mondiaPayNotification);
		}
		return "200 OK";
	}

	@RequestMapping("unsubscribe/{productId}/{token}")
	public ModelAndView unsubscribe(@PathVariable("productId") Integer productId,@PathVariable("token") String token,
			ModelAndView modelAndView) {
		String status = null;
		modelAndView.setViewName("mondiapay/unsubscribe");
		status = mondiaPayApiService.deleteUser(token,productId);
		if(status!=null && status.contains("204 NO CONTENT")) {
			modelAndView.addObject("message", "You have been unsubscribe from the service");
		}else {
			modelAndView.addObject("message", "Failed to unsubscribe from the service");
		}
		return modelAndView;
	}

}
