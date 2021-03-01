package net.mycomp.actel;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.jms.JMSService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MConstants;
import net.util.MData;

@Controller
@RequestMapping("actel")
public class ActelController {

	private static final Logger logger = Logger
			.getLogger(ActelController.class.getName());

	@Autowired
	private JMSActelService jmsActelService;

	@Autowired
	private ActelApiService actelApiService;

	@Autowired
	private JPASubscriberReg jpaSubscriberReg;

	@Autowired
	private SubscriberRegService subscriberRegService;

	@Autowired
	private JMSService jmsService;

	@Autowired
	private RedisCacheService redisCacheService;

	@Value("actel.he.url")
	private String heURL;

	@Value("actel.he.callback.url")
	private String heCallbackURL;

	@RequestMapping(path="dlr",method={RequestMethod.POST,RequestMethod.GET})	
	@ResponseBody
	public String dlr(HttpServletRequest request,ModelAndView modelAndView){
		logger.info("dn::::::: querystring: "+request.getQueryString());
		ActelDlr actelDlr =new ActelDlr(true);
		try{
			actelDlr.setQueryStr(request.getQueryString());			    
			actelDlr.setIdApplication(request.getParameter("id_application"));
			actelDlr.setCountry(request.getParameter("country"));
			actelDlr.setOperator(request.getParameter("operator"));
			actelDlr.setOpid(request.getParameter("opid"));
			actelDlr.setMsisdn(request.getParameter("msisdn"));
			actelDlr.setBilledShortCode(request.getParameter("billedshortcode"));
			actelDlr.setFreeShortCode(request.getParameter("freeshortcode"));
			actelDlr.setSmsId(request.getParameter("smsid"));
			actelDlr.setRate(request.getParameter("rate"));
			actelDlr.setAction(request.getParameter("action"));
			actelDlr.setType(request.getParameter("type"));
			actelDlr.setDlrStatus(request.getParameter("status"));
			actelDlr.setDescription(request.getParameter("description"));		   
			actelDlr.setCurrency(request.getParameter("currency"));
			actelDlr.setLang(request.getParameter("lang"));
			actelDlr.setDlrdate(request.getParameter("dlrdate"));
			actelDlr.setFlow(request.getParameter("flow"));
			actelDlr.setTrafficSource(request.getParameter("traffic_source"));
			actelDlr.setClickId(request.getParameter("clickid"));
			actelDlr.setHasFreeTrial(request.getParameter("hasfreetrial"));
			actelDlr.setIdBillingRequestType(request.getParameter("id_billing_request_type"));
		}catch(Exception ex){
			logger.error("dlr:: ",ex);
		}finally{
			jmsActelService.saveActelDlr(actelDlr);
		}
		return "0";
	}


	@RequestMapping(path="cgcallback",method={RequestMethod.POST,RequestMethod.GET})	
	@ResponseBody
	public String cgcallback(HttpServletRequest request,ModelAndView modelAndView){
		
		logger.info("cgcallback::::::: querystring: "+request.getQueryString());

		return "0";
	}



	@RequestMapping(value={"web/send/otp"},method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView webSendOTP(HttpServletRequest request,ModelAndView modelAndView,
			@RequestParam(name="l",defaultValue="0")Integer lang){

		try{
			modelAndView.setViewName("actel/msisdn_missing");
			modelAndView.addObject("l",lang);
			logger.info("webSendOTP:::::::: 11 msisdn:: "+request.getParameter("msisdn"));
			String msisdn=ActelConstant.formatMsisdn(request.getParameter("msisdn"));
			String token=request.getParameter("token");	
			logger.info("webSendOTP:::::::: msisdn:: "+msisdn+", token:: "+token);
			CGToken cgToken=new CGToken(token);
			VWServiceCampaignDetail vwserviceCampaignDetail=MData
					.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());

			ActelServiceConfig actelServiceConfig=ActelConstant
					.mapServiceIdToActelServiceConfig.get(vwserviceCampaignDetail.getServiceId());

			SubscriberReg subscriberReg=  jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(
					msisdn, 
					vwserviceCampaignDetail.getProductId());

			if(subscriberReg!=null&&subscriberReg.getStatus()==MConstants.SUBSCRIBED){

				modelAndView.setView(new RedirectView(ActelConstant.getPortalUrl
						(actelServiceConfig.getPortalUrl()
								,msisdn))); 
			}else{
				modelAndView.addObject("token",token);
				modelAndView.addObject("msisdn",msisdn);
				modelAndView.addObject("actelServiceConfig",actelServiceConfig);

				ActelApiTrans actelApiTrans=actelApiService.sendOTP(actelServiceConfig, msisdn,token, "WEB");
				if(actelApiTrans.getSuccess()){
					modelAndView.setViewName("actel/wap_otp");
					modelAndView.addObject("otpinfo","We have sent you a PIN code on your phone number");
				}else{
					//modelAndView.setViewName("actel/msisdn_missing");
					modelAndView.addObject("otpinfo","Please enter valid mobile number");
				}

			}

		}catch(Exception  ex){
			logger.error("Exception" ,ex);
		}

		return modelAndView;
	}

	@RequestMapping(value={"web/send/otp/validation"},method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView sendOTPValidation(HttpServletRequest request,ModelAndView modelAndView,
			@RequestParam(name="l",defaultValue="0")Integer lang){
		try{
			modelAndView.setViewName("actel/wap_otp");
			modelAndView.addObject("l",lang);
			String msisdn=ActelConstant.formatMsisdn(request.getParameter("msisdn"));
			String token=request.getParameter("token");
			String pin=request.getParameter("pin"); 
			String ip=request.getRemoteAddr();
			logger.info("webSendOTP:::::::: msisdn:: "+msisdn+", token:: "+token+", pin:: "+pin);
			CGToken cgToken=new CGToken(token);
			VWServiceCampaignDetail vwserviceCampaignDetail=MData
					.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());

			ActelServiceConfig actelServiceConfig=ActelConstant
					.mapServiceIdToActelServiceConfig.get(vwserviceCampaignDetail.getServiceId());

			SubscriberReg subscriberReg=  jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(
					msisdn, 
					vwserviceCampaignDetail.getProductId());

			if(subscriberReg!=null&&subscriberReg.getStatus()==MConstants.SUBSCRIBED){

				modelAndView.setView(new RedirectView(ActelConstant.getPortalUrl
						(actelServiceConfig.getPortalUrl()
								,msisdn))); 
			}else{
				modelAndView.addObject("token",token);
				modelAndView.addObject("msisdn",msisdn);
				modelAndView.addObject("actelServiceConfig",actelServiceConfig);
				modelAndView.setViewName("actel/wap_otp");
				ActelApiTrans actelApiTrans=actelApiService.validateOTP(actelServiceConfig, msisdn,token, "WEB", pin, ip);

				if(actelApiTrans.getSuccess()){
					//modelAndView.addObject("otpinfo","We sent you a PIN code on your phone number");
					LiveReport liveReport=new LiveReport(vwserviceCampaignDetail.getOpId(),
							new Timestamp(System.currentTimeMillis()),
							cgToken.getCampaignId()
							,vwserviceCampaignDetail.getServiceId(),
							vwserviceCampaignDetail.getProductId());
					liveReport.setNoOfDays(actelServiceConfig.getFreePeriodDays());						   
					subscriberRegService.findOrCreateSubscriberByAct(msisdn,null, liveReport);
					modelAndView.addObject("portalurl",actelServiceConfig.getPortalUrl()
							+"?msisdn="+msisdn);
					modelAndView.setViewName("actel/final");

				}else{
					modelAndView.addObject("otpinfo","Please enter valid pin");
				}

			}

		}catch(Exception  ex){
			logger.error("Exception" ,ex);
		}
		return modelAndView;

	}


	@RequestMapping(value={"change/lang"},method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView changeLang(HttpServletRequest request,ModelAndView modelAndView,
			@RequestParam(name="l",defaultValue="0")Integer lang){
		try{
			String page=request.getParameter("page");

			modelAndView.setViewName("actel/"+page);
			modelAndView.addObject("l",lang);
			String msisdn=ActelConstant.formatMsisdn(request.getParameter("msisdn"));
			String token=request.getParameter("token");
			CGToken cgToken=new CGToken(token);
			VWServiceCampaignDetail vwserviceCampaignDetail=MData
					.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());

			ActelServiceConfig actelServiceConfig=ActelConstant
					.mapServiceIdToActelServiceConfig.get(vwserviceCampaignDetail.getServiceId());

			//String pin=request.getParameter("pin"); 
			modelAndView.addObject("token",token);
			modelAndView.addObject("msisdn",msisdn);
			modelAndView.addObject("actelServiceConfig",actelServiceConfig);

		}catch(Exception  ex){
			logger.error("Exception" ,ex);
		}
		return modelAndView;

	}


	@RequestMapping(path="send/otp",method={RequestMethod.POST,RequestMethod.GET})	
	@ResponseBody
	public String sendOTP(HttpServletRequest request,ModelAndView modelAndView){
		try{
			logger.info("sendOTP querystr:: "+request.getQueryString());
			String msisdn=request.getParameter("msisdn");
			String mode=request.getParameter("mode");
		//	Integer cmpid=MUtility.toInt(request.getParameter("cmpid"),0);
			/*
			 * VWServiceCampaignDetail vwServiceCampaignDetail=
			 * MData.mapCamapignIdToVWServiceCampaignDetail.get(cmpid);
			 */logger.info("sendOTP querystr:: "+request.getQueryString());
			ActelServiceConfig actelServiceConfig=ActelConstant.mapServiceIdToActelServiceConfig
					.get(52);

			return actelApiService.sendOTP(actelServiceConfig, msisdn,null, mode).getSuccess()?"1":"0";
		}catch(Exception ex){
			logger.error("sendOTP:: ",ex);
		}
		return "0";
	}

	@RequestMapping(path="validate/otp",method={RequestMethod.POST,RequestMethod.GET})	
	@ResponseBody
	public String validateOTP(HttpServletRequest request,ModelAndView modelAndView){
		try{
			logger.info("validateOTP querystr:: "+request.getQueryString());
			String msisdn=request.getParameter("msisdn");
			String mode=request.getParameter("mode");
			String ip=request.getRemoteAddr();
			String pin=request.getParameter("pin");
			/*
			 * Integer cmpid=MUtility.toInt(request.getParameter("cmpid"),0);
			 * VWServiceCampaignDetail vwServiceCampaignDetail=
			 * MData.mapCamapignIdToVWServiceCampaignDetail.get(cmpid);
			 */	ActelServiceConfig actelServiceConfig=ActelConstant.mapServiceIdToActelServiceConfig
					.get(52);

			return actelApiService.validateOTP(actelServiceConfig, msisdn,null, mode,pin,ip).getSuccess()?"1":"0";
		}catch(Exception ex){
			logger.error("sendOTP:: ",ex);
		}
		return "0";
	}



	@RequestMapping("/he/callback/{token}")
	public ModelAndView heCallbackHandler(HttpServletRequest request, ModelAndView modelAndView,
			@PathVariable(value = "token")String token) {
		ActelHECallback actelHECallback = null;
		try{
			logger.info("actel he callback request:"+request.getQueryString());
			String msisdn=request.getParameter("msisdn").replaceAll("nomsisdn", "");
			String country=request.getParameter("country");
			String operatorid=request.getParameter("operatorid");
			String operator=request.getParameter("operator"); 
			String queryStr = request.getQueryString();

			actelHECallback = new ActelHECallback(msisdn, country, operatorid, operator, token, queryStr);

			CGToken cgToken=new CGToken(token);
			VWServiceCampaignDetail vwServiceCampaignDetail=
					MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			ActelServiceConfig actelServiceConfig=ActelConstant
					.mapServiceIdToActelServiceConfig.get(vwServiceCampaignDetail.getServiceId());

			String cgURL = actelServiceConfig.getCgUrl()
					.replaceAll("<application_id>", actelServiceConfig.getIdApplication())
					.replaceAll("<image_url>",actelServiceConfig.getLpImages())
					.replaceAll("<token>", token)
					.replaceAll("<msisdn>", msisdn);
			logger.info("redirecting to cg url: "+cgURL);

			redisCacheService.putObjectCacheValueByEvictionMinute(ActelConstant.ACTEL_DU_MSISDN_CACHE_PREFIX+token, msisdn, 2000);

			modelAndView.setView(new RedirectView(cgURL));	
		}catch (Exception e) {
			logger.error("HE Callback Error"+e);
		}finally {
			jmsService.saveObject(actelHECallback);
		}

		return modelAndView;
	}


	@RequestMapping(value="tocg")
	public ModelAndView toCg(ModelAndView modelAndView, HttpServletRequest request) {
		String token = request.getParameter("token");
		String lang = request.getParameter("l");
		String serviceId = request.getParameter("serviceId");
		String msisdn = request.getParameter("msisdn");
		System.out.println("Token:"+token+" lang:"+lang+" serviceId:"+serviceId);
		CGToken cgToken = new CGToken(token);	
		VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
		ActelServiceConfig actelServiceConfig = ActelConstant
				.mapServiceIdToActelServiceConfig.get(vwServiceCampaignDetail.getServiceId());
		String cgURL = actelServiceConfig.getCgUrl()
				.replaceAll("<application_id>", actelServiceConfig.getIdApplication())
				.replaceAll("<image_url>",actelServiceConfig.getLpImages())
				.replaceAll("<token>", token)
				.replaceAll("<msisdn>", msisdn);
		logger.info("redirecting to cg url: "+cgURL);

		redisCacheService.putObjectCacheValueByEvictionMinute(ActelConstant.ACTEL_DU_MSISDN_CACHE_PREFIX+token, msisdn, 2000);

		modelAndView.setView(new RedirectView(cgURL));
		return modelAndView;
	}

	@RequestMapping(value="notification")
	public ModelAndView duCGCallback(ModelAndView modelAndView, HttpServletRequest request) {
		logger.info("du CG Callback queryString : "+ request.getQueryString());
		try {
			String token = request.getParameter("correlatorId");
			String status = request.getParameter("statusCode");
			CGToken cgToken=new CGToken(token);
			VWServiceCampaignDetail vwserviceCampaignDetail=MData
					.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			ActelServiceConfig actelServiceConfig = ActelConstant
					.mapServiceIdToActelServiceConfig.get(vwserviceCampaignDetail.getServiceId());
			modelAndView.addObject("actelServiceConfig", actelServiceConfig);
			if("1".equals(status) && actelServiceConfig!=null){
				modelAndView.addObject("actelServiceConfig", actelServiceConfig);
				modelAndView.setViewName("actel/thank_you_du");
			}else {
				modelAndView.setViewName("actel/failed");
			}
		} catch (Exception e) {
			logger.error("error du CG Callback redirecting to failed page");
			modelAndView.setViewName("actel/failed");
		}
		return modelAndView;
	}

	@RequestMapping("lp/{lang}/{campId}/{token}")
	public ModelAndView preLandingPage(ModelAndView modelAndView, 
			@PathVariable(value = "lang")String lang,
			@PathVariable(value = "campId")Integer campId,
			@PathVariable(value = "token")String token
			) {

		VWServiceCampaignDetail vwserviceCampaignDetail=MData
				.mapCamapignIdToVWServiceCampaignDetail.get(campId);

		System.out.println(vwserviceCampaignDetail.getServiceId());
		ActelServiceConfig actelServiceConfig = ActelConstant
				.mapServiceIdToActelServiceConfig.get(vwserviceCampaignDetail.getServiceId());
		modelAndView.addObject("config", actelServiceConfig);
		modelAndView.addObject("token", token);
		modelAndView.addObject("l", lang);
		modelAndView.setViewName("actel/prelanding");
		return modelAndView;
	}

}

