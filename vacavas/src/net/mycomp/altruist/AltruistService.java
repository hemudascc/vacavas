package net.mycomp.altruist;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import net.jpa.repository.JPAAltruistServiceConfig;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.DeactivationResponse;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;

@Service("altruistService")
public class AltruistService extends AbstractOperatorService{
	
	@Autowired
	private JPAAltruistServiceConfig jpaAltruistServiceConfig;
	
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
		modelAndView.setViewName("altruist/lp");
		return true;
	}

	@Override
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean) {
		// TODO Auto-generated method stub
		return super.isSubscribed(adNetworkRequestBean);
	}

	@Override
	public DeactivationResponse deactivation(ModelAndView modelAndView, SubscriberReg subscriberReg) {
		// TODO Auto-generated method stub
		return super.deactivation(modelAndView, subscriberReg);
	}

	@Override
	public boolean checkSub(Integer productId, Integer opid, String msisdn) {
		// TODO Auto-generated method stub
		return super.checkSub(productId, opid, msisdn);
	}

	@Override
	public boolean sendOtp(ModelAndView modelAndView, AdNetworkRequestBean adNetworkRequestBean) {
		// TODO Auto-generated method stub
		return super.sendOtp(modelAndView, adNetworkRequestBean);
	}

	@Override
	public boolean validateOtp(ModelAndView modelAndView, AdNetworkRequestBean adNetworkRequestBean) {
		// TODO Auto-generated method stub
		return super.validateOtp(modelAndView, adNetworkRequestBean);
	}
	
	
	
}
