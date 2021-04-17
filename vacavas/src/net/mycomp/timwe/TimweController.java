package net.mycomp.timwe;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.service.IDaoService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MData;

@Controller
@RequestMapping("timwe")
public class TimweController {

	private static final Logger logger = Logger.getLogger(TimweController.class);

	@Autowired
	@Qualifier("jmsTimweService")
	private JMSTimweService jmsTimweService;
	@Autowired
	private TimweApiService timweApiService;
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;

	@RequestMapping("hecallback/{token}")
	public ModelAndView heCallback(ModelAndView modelAndView,@PathVariable("token") String token,
			HttpServletRequest request) {
		TimweHECallback timweHECallback = new TimweHECallback(true);
		VWServiceCampaignDetail vwServiceCampaignDetail=null;
		TimweServiceConfig timweServiceConfig = null;
		String msisdn = null;
		try {
			msisdn = TimweUtill.dcryptMsisdn(request.getParameter("msisdn"));
			CGToken cgToken = new CGToken(token);
			vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			timweServiceConfig = TimweConstant.mapServiceIdToTimweServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			timweHECallback.setMsisdn(msisdn);
			timweHECallback.setServiceId(vwServiceCampaignDetail.getServiceId());
			timweHECallback.setToken(token);
			timweHECallback.setTokenId(cgToken.getTokenId());
			timweHECallback.setQueryStr(request.getQueryString());
			modelAndView.addObject("msisdn", msisdn);
			modelAndView.addObject("status", 0);
			modelAndView.addObject("token", token);
			modelAndView.addObject("timweServiceConfig", timweServiceConfig);
			modelAndView.setViewName("timwe/lp");
		} catch (Exception e) {
			logger.error("error while processing hecallback",e);
		}finally {
			daoService.saveObject(timweHECallback);
		}
		return modelAndView;
	}

	@RequestMapping("sendpin")
	public ModelAndView sendPin(ModelAndView modelAndView, HttpServletRequest request) {
		VWServiceCampaignDetail vwserviceCampaignDetail = null;
		TimweServiceConfig timweServiceConfig=null;
		try {	
			String token = request.getParameter("token");
			String msisdn = request.getParameter("msisdn");
			CGToken cgToken = new CGToken(token);
			vwserviceCampaignDetail= MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			timweServiceConfig = TimweConstant.mapServiceIdToTimweServiceConfig
					  .get(vwserviceCampaignDetail.getServiceId());
			SubscriberReg subscriberReg =jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, vwserviceCampaignDetail.getProductId());
			if(Objects.nonNull(subscriberReg) && subscriberReg.getStatus()==1) {
				modelAndView.setView(new RedirectView(timweServiceConfig.getPortalURL().replaceAll("<msisdn>", msisdn)));
				return modelAndView;
			}
			modelAndView.addObject("msisdn", msisdn);
			modelAndView.addObject("token", token);
			modelAndView.addObject("timweServiceConfig", timweServiceConfig);
			modelAndView.setViewName("timwe/lp");
			String response = timweApiService.pinSend(token, msisdn);
			if(Objects.nonNull(response) && response.equals("1")) {
				modelAndView.addObject("status", 1);
				modelAndView.setViewName("timwe/verifyotp");
			}else if(Objects.nonNull(response) && response.equals("2")) {
				modelAndView.addObject("status", 1);
			}else if(Objects.nonNull(response) && response.equals("3")) {
				modelAndView.addObject("status", 2);
			}else {
				modelAndView.addObject("status", 3);
			}
		} catch (Exception e) {
			modelAndView.addObject("timweServiceConfig", timweServiceConfig);
			modelAndView.addObject("status", 3);
		}
		return modelAndView;
	}

	@RequestMapping("verifypin")
	public ModelAndView verifyPin(ModelAndView modelAndView, HttpServletRequest request) {
		VWServiceCampaignDetail vwserviceCampaignDetail = null;
		TimweServiceConfig timweServiceConfig=null;
		try {
			String msisdn = request.getParameter("msisdn");
			String pin = request.getParameter("pin");
			String token = request.getParameter("token");
			CGToken cgToken = new CGToken(token);
			vwserviceCampaignDetail= MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			timweServiceConfig = TimweConstant.mapServiceIdToTimweServiceConfig
					  .get(vwserviceCampaignDetail.getServiceId());
			modelAndView.addObject("msisdn", msisdn);
			modelAndView.addObject("token", token);
			modelAndView.addObject("timweServiceConfig", timweServiceConfig);
			String response = timweApiService.pinVerify(msisdn, pin);
			if(Objects.nonNull(response) && response.equals("1")) {
				modelAndView.addObject("portalURL", timweServiceConfig.getPortalURL().replaceAll("<msisdn>", msisdn));
				modelAndView.setViewName("timwe/thankyou");
			}else {
				modelAndView.addObject("status", 0);
				modelAndView.setViewName("timwe/verifyotp");
			}
		} catch (Exception e) {
			modelAndView.addObject("status", 2);
			modelAndView.setViewName("timwe/verifyotp");
		}
		return modelAndView;
	}

	@RequestMapping("notification/{notificationType}/{partnerRole}")
	@ResponseBody
	public TimweResponse notification(@RequestBody TimweNotification timweOptInNotification,
			@PathVariable("partnerRole") String partnerRole,
			@PathVariable("notificationType") String notificationType) {
		try {
			logger.debug("timweOptInNotification::::"+timweOptInNotification+"   partnerRole::::"+partnerRole+"   notificationType::::"+notificationType);
			jmsTimweService.saveTimweNotification(timweOptInNotification, notificationType);
		} catch (Exception e) {
			logger.error("error while processing timwe notification",e);
		}
		return new TimweResponse();
	}
	
	@RequestMapping("unsubscribe")
	public ModelAndView unsubscribe(ModelAndView modelAndView, HttpServletRequest request) {
		try {
			String msisdn = request.getParameter("msisdn");
			Integer productId = Integer.parseInt(request.getParameter("productId"));
			logger.debug("unsubscribing msisdn="+msisdn+" product id"+productId);
			String response = timweApiService.unsubscribe(msisdn, productId);
			modelAndView.setViewName("timwe/unsubscribe");
			if("1".equals(response)) {
				modelAndView.addObject("message", "You have been successfully unsubscribed");
			}else {
				modelAndView.addObject("message", "Failed to unsubscribe from service");
			}
		} catch (Exception e) {
			logger.error("error while processing timwe notification",e);
		}
		return modelAndView;
	}
	
}




/*
 * @RequestMapping("notification/mo/{partnerRole}") public TimweResponse
 * moNotification(@RequestBody TimweNotification timweMoNotification,
 * 
 * @PathVariable("partnerRole") String partnerRole) { return null; }
 * 
 * @RequestMapping("notification/user-optin/{partnerRole}") public TimweResponse
 * userOptInNotification(@RequestBody TimweNotification timweOptInNotification,
 * 
 * @PathVariable("partnerRole") String partnerRole) { return null; }
 * 
 * @RequestMapping("notification/user-optout/{partnerRole}") public
 * TimweResponse UserOptOutNotification(@RequestBody TimweNotification
 * timweOptInNotification,
 * 
 * @PathVariable("partnerRole") String partnerRole) { return null; }
 * 
 * @RequestMapping("notification/user-renewed/{partnerRole}") public
 * TimweResponse userRenewedNotification(@RequestBody TimweNotification
 * timweOptInNotification,
 * 
 * @PathVariable("partnerRole") String partnerRole) { return null; }
 * 
 * @RequestMapping("notification/first-charge/{partnerRole}") public
 * TimweResponse firstChargeNotification(@RequestBody TimweNotification
 * timweOptInNotification,
 * 
 * @PathVariable("partnerRole") String partnerRole) { return null; }
 */