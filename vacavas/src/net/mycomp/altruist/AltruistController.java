package net.mycomp.altruist;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MData;

@Controller
@RequestMapping("altr")
public class AltruistController {
	
	@Autowired
	private AltruistApiService altruistApiService;
	
	@RequestMapping(value="send-pin", method=RequestMethod.POST)
	public ModelAndView sendPin(HttpServletRequest request, ModelAndView modelAndView) {
		VWServiceCampaignDetail vwserviceCampaignDetail = null;
		AltruistServiceConfig altruistServiceConfig=null;
		try {	
			String token = request.getParameter("token");
			String msisdn = request.getParameter("msisdn");
			modelAndView.addObject("msisdn", msisdn);
			modelAndView.addObject("token", token);
			CGToken cgToken = new CGToken(token);
			vwserviceCampaignDetail= MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig
					  .get(vwserviceCampaignDetail.getServiceId());
					String response = altruistApiService.pinSend(token, msisdn, "web");
			if(Objects.nonNull(response) && response.equals("1")) {
				modelAndView.addObject("msg", "Please Enter the pin you received to activate your subscription");
				modelAndView.setViewName("altruist/otpverify");
			}else if(Objects.nonNull(response) && response.equals("2")) {
				modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
				modelAndView.addObject("msg", "Please Enter a valid mobile number");
				modelAndView.setViewName("altruist/lp");
			}else if(Objects.nonNull(response) && response.equals("3")) {
				modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
				modelAndView.addObject("msg", "You have exceded your limit.");
				modelAndView.setViewName("altruist/lp");
			}else {
				modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
				modelAndView.addObject("msg", "Could not send pin please try again");
				modelAndView.setViewName("altruist/lp");
			}
		} catch (Exception e) {
			modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
			modelAndView.addObject("msg", "Could not send pin please try again");
			modelAndView.setViewName("altruist/lp");
		}
		return modelAndView;
	}
	
}
