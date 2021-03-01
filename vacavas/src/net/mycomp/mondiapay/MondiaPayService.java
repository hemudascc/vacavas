package net.mycomp.mondiapay;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.request.AbstractOperatorService;

@Service("mondiaPayService")
public class MondiaPayService extends AbstractOperatorService {
	private static final Logger logger = Logger.getLogger(MondiaPayService.class.getName());
	@Autowired
	private MondiaPayApiService mondiaPayApiService;
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
		return false;
	}

	@Override
	public boolean processBilling(ModelAndView modelAndView, AdNetworkRequestBean adNetworkRequestBean) {
		String accessToken=null;
		try {
			accessToken = mondiaPayApiService.getAccessToken(adNetworkRequestBean.adnetworkToken.getTokenToCg());
			modelAndView.addObject("token", adNetworkRequestBean.adnetworkToken.getTokenToCg());
			modelAndView.addObject("accessToken", accessToken);
			modelAndView.setViewName("mondiapay/lp");
		} catch (Exception e) {
			logger.error("error",e);
		}
		return true;
	}

	@Override
	public boolean checkSub(Integer productId, Integer opid, String msisdn) {
		try {
			SubscriberReg subscriberReg =jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, productId);
			JSONObject userSubscriptionDetails = mondiaPayApiService.getUserSubscriptionDetails(subscriberReg.getParam3()
					, msisdn);
			return (Objects.toString(userSubscriptionDetails.get("state")).equals("ACTIVE") || Objects.toString(userSubscriptionDetails.get("state")).equals("INACTIVE"));
		} catch (Exception e) {
			logger.error("error fetching user subscription details",e);
		}
		return false;
	}
}
