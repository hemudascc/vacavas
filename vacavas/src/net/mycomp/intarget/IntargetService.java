package net.mycomp.intarget;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import net.common.service.IDaoService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPAIntargetServiceConfig;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;

@Service("intargetService")
public class IntargetService extends AbstractOperatorService {

	
	private static final Logger logger = Logger
			.getLogger(IntargetService.class.getName());
	
	@Autowired
	private JPAIntargetServiceConfig jpaIntargetServiceConfig;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	@Autowired
	private SubscriberRegService subscriberRegService;
	
   @Autowired
   private IDaoService daoService;
   
	@Value("${jdbc.db.name}")
	private String dbName;
   
	
	public IntargetService(){
	}
	
	@PostConstruct
	public void init() {
		List<InTargetConfig> list=jpaIntargetServiceConfig.findEnableInTargetConfig(true);
		IntargetConstant.mapServiceIdTpInTargetConfig.putAll(
				list.stream().collect(
						Collectors.toMap(p -> p.getServiceId(), p -> p))
						);
		
		IntargetConstant.mapServiceCodeToInTargetConfig.putAll(
				list.stream().collect(
						Collectors.toMap(p -> p.getServiceCode(), p -> p))
						);
		
		Integer id=daoService.
				findNextAutoIncrementId("tb_intarget_ussd_trans", dbName);
		
		IntargetConstant.inTargetUssdTransIdAtomicInteger.set(id==null?0:id);
		
	}
	
	
	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
			
		
		return false;
	}
	
	@Override
	public boolean processBilling(ModelAndView modelAndView,
			AdNetworkRequestBean adNetworkRequestBean) {
		
		  InTargetConfig inTargetConfig=IntargetConstant.mapServiceIdTpInTargetConfig.get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
		  modelAndView.addObject("inTargetConfig", inTargetConfig);
		  modelAndView.addObject("token", adNetworkRequestBean.adnetworkToken.getTokenToCg());
		  modelAndView.setViewName("intarget/landingpage");
		  return true;	    	
	}
	
	
	
	@Override
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean) {
		
		boolean isSubscribed= subscriberRegService.isSubscribedBySericeId(adNetworkRequestBean.getMsisdn()
				, adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
		    
		   if(isSubscribed){
			logger.debug("campaign:: already subscribed, ");
			adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_WASTE_URL_ALREADY_SUBSCRIBED);
			adNetworkRequestBean.adnetworkToken.setRedirectToUrl("" + "?substatus=true");		
		}
		   return isSubscribed;
	}
	
	@Override
	public boolean checkSub(Integer productId,Integer opid,String msisdn) {
		
		List<SubscriberReg> list=jpaSubscriberReg.findSubscriberRegByMsisdn(msisdn);
		logger.info("checkSub:::::::::: list of subscriberreg "+list);
		SubscriberReg subscriberReg=null;
		if(list!=null&&list.size()>0){
			subscriberReg=list.get(0);
		}
		logger.info("checkSub:::::::::: subscriberReg: "+subscriberReg);
		if(subscriberReg!=null&&subscriberReg.getStatus()==MConstants.SUBSCRIBED){
			return true;
		}		
		return false;
	}
}
