package net.mycomp.macrokiosk.thailand;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import net.common.service.IDaoService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPASubscriberReg;
import net.jpa.repository.JPAThConfig;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Service("thialandService")
public class ThialandService  extends AbstractOperatorService {

	
	private static final Logger logger = Logger
			.getLogger(ThialandService.class.getName());
	
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	@Autowired
	private SubscriberRegService subscriberRegService;
	
   @Autowired
   private IDaoService daoService;
   
   @Autowired
   private JPAThConfig jpaThConfig;
   
   
   private final  String redirectUrl;
   
   private String callBackUrl;
   
   @Value("${jdbc.db.name}")
	private String dbName;
     
   
   @Autowired
   public ThialandService( @Value("${thialand.microkiosk.redirecturl}") String redirectUrl,
		   @Value("${thialand.msisdn.forwarding.callback.url}")String callBackUrl){
	   this.redirectUrl=redirectUrl;
	   this.callBackUrl=callBackUrl;
   }
   
   
   @PostConstruct
   public void init(){
	   
	   List<THConfig> listTHConfig=jpaThConfig.findEnableTHConfig(true);	
	   
	   ThiaConstant.listTHConfig.addAll(listTHConfig);
	   
	   ThiaConstant.mapIdToTHConfig.putAll(listTHConfig.stream().collect(
				Collectors.toMap(p -> p.getId(), p -> p)));
	   
	   ThiaConstant.mapServiceIdToTHConfig.putAll(listTHConfig.stream().collect(
						Collectors.toMap(p -> p.getServiceId(), p -> p)));	
	   
	   ThiaConstant.mapKeywordTelcoIdToTHConfig.putAll(listTHConfig.stream().collect(
				Collectors.toMap(p -> p.getKeyword()+""+p.getTelcoId(), p -> p)));	
	   
//	   ThiaConstant.mapproductIdTelcoIdToTHConfig.putAll(listTHConfig.stream().collect(
//				Collectors.toMap(p -> p.getProductId()+""+p.getTelcoId(), p -> p)));	
	   
	   Integer id=daoService.
				findNextAutoIncrementId("tb_th_mo_message", dbName);		
	   ThiaConstant.moMessageIdAtomicInteger.set(id);
	   
	    id=daoService.
				findNextAutoIncrementId("tb_th_mt_message", dbName);		
	   ThiaConstant.mtMessageIdAtomicInteger.set(id);
	   
		 
   }
   
   
	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
			
		
		return false;
	}
	
	@Override
	public boolean processBilling(ModelAndView modelAndView,
			AdNetworkRequestBean adNetworkRequestBean) {
		
			THConfig thConfig=ThiaConstant.mapServiceIdToTHConfig.get(
					adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
			
			logger.info("processBilling::: redirectUrl:: "+redirectUrl);
			String cgUrl=redirectUrl.replaceAll("<keyword>",thConfig.getKeyword()).
					   replaceAll("<shortcode>",thConfig.getShortcode())
					   .replaceAll("<callbackurl>",
							   callBackUrl+"/"+adNetworkRequestBean.adnetworkToken.getTokenToCg());
			logger.info("processBilling::::::::::cgUrl:: "+cgUrl);
			logger.info("processBilling:::::::::: thConfig:: "+thConfig);
			adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_CG);
			//modelAndView.setView(new RedirectView(url));
			//modelAndView.addObject("thConfig",thConfig);
			//modelAndView.addObject("cgUrl",cgUrl);
			//modelAndView.setViewName("th/lp");
			modelAndView.setView(new RedirectView(cgUrl));
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
