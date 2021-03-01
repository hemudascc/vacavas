package net.process.request;


import java.sql.Timestamp;

import org.springframework.web.servlet.ModelAndView;

import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.DeactivationResponse;

public interface IOperatorService {
	
	public SubscriberReg searchSubscriber(Integer operatorId,String msisdn, 
			Integer serviceId,Integer productId);
	
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean);
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean);
	public boolean processBilling(ModelAndView modelAndView,AdNetworkRequestBean adNetworkRequestBean);
	public DeactivationResponse deactivation(ModelAndView modelAndView,SubscriberReg subscriberReg);
	public boolean sendOtp(ModelAndView modelAndView,AdNetworkRequestBean adNetworkRequestBean);
	public boolean validateOtp(ModelAndView modelAndView,AdNetworkRequestBean adNetworkRequestBean);
	public  Timestamp getTimeByOperator(Integer opId);
	public boolean checkSub(Integer productId,Integer opid,String msisdn);
}
