package net.mycomp.altruist;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import net.jpa.repository.JPAAltruistServiceConfig;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;

@Service("altruistService")
public class AltruistService extends AbstractOperatorService{
	
	@Autowired
	private JPAAltruistServiceConfig jpaAltruistServiceConfig;
	@Autowired
	private AltruistApiService altruistApiService;
	@Autowired
	private JPASubscriberReg jpaSubscriberReg; 
	
	private static final Logger logger = Logger.getLogger(AltruistService.class);
	
	@PostConstruct
	void init() {
		List<AltruistServiceConfig> list = jpaAltruistServiceConfig.findEnableAltruistServiceConfig(true);
		AltruistConstant.mapServiceIdToAltruistServiceConfig.putAll(
				list.stream().collect(Collectors.toMap(AltruistServiceConfig :: getServiceId, Function.identity())));
	}
	

	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
		return false;
	}

	@Override
	public boolean processBilling(ModelAndView modelAndView, AdNetworkRequestBean adNetworkRequestBean) {
		AltruistServiceConfig altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig
				  .get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
		adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_CG);
		modelAndView.addObject("token", adNetworkRequestBean.adnetworkToken.getTokenToCg());
		modelAndView.addObject("altruistServiceConfig", altruistServiceConfig);
		modelAndView.addObject("lang", 0);
		modelAndView.addObject("status",0);
		modelAndView.setViewName("altruist/lp");
		return true;
	}

	@Override
	public boolean checkSub(Integer productId, Integer opid, String msisdn) {
		try {
			SubscriberReg subscriberReg =jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, productId);
			if(Objects.nonNull(subscriberReg))
				return subscriberReg.getStatus()==1;
		} catch (Exception e) {
			logger.error("error fetching user subscription details",e);
		}
		return false;
	}

	@Override
	public boolean sendOtp(ModelAndView modelAndView, AdNetworkRequestBean adNetworkRequestBean) {
		String msisdn = adNetworkRequestBean.getMsisdn();
		String token = adNetworkRequestBean.adnetworkToken.getTokenToCg();
		try { 
			String response = altruistApiService.pinSend(token, msisdn, "App");
			return "1".equals(response);
		} catch (Exception e) {
			logger.error("error while sending otp"+msisdn);
		}
		return false;
	}

	@Override
	public boolean validateOtp(ModelAndView modelAndView, AdNetworkRequestBean adNetworkRequestBean) {
		String msisdn = adNetworkRequestBean.getMsisdn();
		String token = adNetworkRequestBean.adnetworkToken.getTokenToCg();
		String pin = adNetworkRequestBean.adnetworkToken.getParam1();
		try {
			String response = altruistApiService.verifyPin(token, msisdn,pin, "App");
			return "1".equals(response);
		} catch (Exception e) {
			logger.error("error while sending otp"+msisdn);
		}
		return false;
	}
	
	
	
}
