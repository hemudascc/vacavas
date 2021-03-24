package net.mycomp.altruist;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MData;

@Controller
@RequestMapping("altr")
public class AltruistController {
	
	@Autowired
	private AltruistApiService altruistApiService;
	
	@Autowired
	@Qualifier("jmsAltruistService")
	private JMSAltruistService jmsAltruistService;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg; 
	
	private static final Logger logger = Logger.getLogger(AltruistController.class);
	
	@RequestMapping(value="send-pin", method=RequestMethod.POST)
	public ModelAndView sendPin(HttpServletRequest request, ModelAndView modelAndView) {
		VWServiceCampaignDetail vwserviceCampaignDetail = null;
		AltruistServiceConfig altruistServiceConfig=null;
		try {	
			String token = request.getParameter("token");
			String msisdn = request.getParameter("msisdn");
			String lang = request.getParameter("lang");
			CGToken cgToken = new CGToken(token);
			vwserviceCampaignDetail= MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig
					  .get(vwserviceCampaignDetail.getServiceId());
			SubscriberReg subscriberReg =jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, vwserviceCampaignDetail.getProductId());
			if(Objects.nonNull(subscriberReg) && subscriberReg.getStatus()==1) {
				modelAndView.setView(new RedirectView(altruistServiceConfig.getPortalURL().replaceAll("<msisdn>", msisdn)));
				return modelAndView;
			}
			modelAndView.addObject("msisdn", msisdn);
			modelAndView.addObject("token", token);
			modelAndView.addObject("lang", lang);
			modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
			String response = altruistApiService.pinSend(token, msisdn, "web");
			if(Objects.nonNull(response) && response.equals("1")) {
				modelAndView.addObject("status", 1);
				modelAndView.setViewName("altruist/verifyotp");
			}else if(Objects.nonNull(response) && response.equals("2")) {
				modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
				modelAndView.addObject("status", 1);
				modelAndView.setViewName("altruist/lp");
			}else if(Objects.nonNull(response) && response.equals("3")) {
				modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
				modelAndView.addObject("status", 2);
				modelAndView.setViewName("altruist/lp");
			}else {
				modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
				modelAndView.addObject("status", 3);
				modelAndView.setViewName("altruist/lp");
			}
		} catch (Exception e) {
			modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
			modelAndView.addObject("status", 3);
			modelAndView.setViewName("altruist/lp");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="verify-pin", method=RequestMethod.POST)
	public ModelAndView verifyPin(HttpServletRequest request, ModelAndView modelAndView) {
		VWServiceCampaignDetail vwserviceCampaignDetail = null;
		AltruistServiceConfig altruistServiceConfig=null;
		try {
			String token = request.getParameter("token");
			String msisdn = request.getParameter("msisdn");
			String lang = request.getParameter("lang");
			String pin = request.getParameter("pin");
			CGToken cgToken = new CGToken(token);
			vwserviceCampaignDetail= MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig
					  .get(vwserviceCampaignDetail.getServiceId());
			modelAndView.addObject("msisdn", msisdn);
			modelAndView.addObject("token", token);
			modelAndView.addObject("lang", lang);
			modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
			String response = altruistApiService.verifyPin(token, msisdn, pin, "web");
			if(Objects.nonNull(response) && response.equals("1")) {
				modelAndView.addObject("portalURL", altruistServiceConfig.getPortalURL().replaceAll("<msisdn>", msisdn));
				modelAndView.setViewName("altruist/thankyou");
			}else {
				modelAndView.addObject("status", 0);
				modelAndView.setViewName("altruist/verifyotp");
			}
		} catch (Exception e) {
			modelAndView.addObject("status", 2);
			modelAndView.setViewName("altruist/verifyotp");
		}
		return modelAndView;
	}
	
	@RequestMapping("unsubscribe/{productId}/{msisdn}")
	public ModelAndView unsubscribe(@PathVariable("productId") Integer productId,@PathVariable("msisdn") String msisdn,
			ModelAndView modelAndView) {
		String status = null;
		modelAndView.setViewName("altruist/unsubscribe");
		status = altruistApiService.unsubscribe(msisdn,productId);
		if(Objects.nonNull(status) && "1".equals(status)) {
			modelAndView.addObject("message", "You have been unsubscribe from the service");
		}else {
			modelAndView.addObject("message", "Failed to unsubscribe from the service");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="callback",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> callback(@RequestBody AltruistCallback altruistCallback) {
		try {
			jmsAltruistService.saveAltruistCallback(altruistCallback);
		} catch (Exception e) {
			logger.error("error while saving callback altruist "+altruistCallback);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="change-lang")
	public ModelAndView changeLang(@RequestParam Integer lang,
			@RequestParam String token, ModelAndView modelAndView) throws UnsupportedEncodingException {
		CGToken cgToken = new CGToken(token);
		VWServiceCampaignDetail vwserviceCampaignDetail= MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
		AltruistServiceConfig altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig
				  .get(vwserviceCampaignDetail.getServiceId());
		modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
		modelAndView.addObject("lang",lang);
		modelAndView.addObject("status",0);
		modelAndView.addObject("token",token);
		modelAndView.setViewName("altruist/lp");
		return modelAndView;
	}
	
	@RequestMapping("tc")
	public ModelAndView tc(ModelAndView modelAndView) {
		modelAndView.setViewName("altruist/tc");
		return modelAndView;
	}
	
	@RequestMapping(value="send-sms")
	@ResponseBody
	public String sendSms(@RequestParam String msisdn, @RequestParam String token) {
		altruistApiService.smsPush(msisdn, token);
		return "OK";
	}
}
