package net.mycomp.actel;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.service.IDaoService;
import net.jpa.repository.JPAActelServiceConfig;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.DeactivationResponse;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;

@Service("actelService")
public class ActelService extends AbstractOperatorService{

	
	private static final Logger logger = Logger
			.getLogger(ActelService.class.getName());
	
	@Autowired
	private JPAActelServiceConfig jpaActelServiceConfig;
	
	@Autowired
	private IDaoService daoService;
	
	@Value("${jdbc.db.name}")
	private String dbName;
	
	

	private final String heCallbackURL;
	
	private final String heURL;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	@Autowired
	 public ActelService(	@Value("${actel.he.callback.url}")String heCallbackURL, @Value("${actel.he.url}")String heURL) {
		this.heCallbackURL=heCallbackURL;
		this.heURL = heURL;
	}
	
	@PostConstruct
	public void init(){		
		
	List<ActelServiceConfig> list=jpaActelServiceConfig.findEnableActelServiceConfig(true);
	ActelConstant.listActelConfig.addAll(list);
	
	ActelConstant.mapServiceIdToActelServiceConfig.putAll(list.stream().
			collect(Collectors.toMap(p -> p.getServiceId(), p -> p)));
	
	
	ActelConstant.actlelApiTransId.
			set(daoService.findNextAutoIncrementId("tb_actel_api_trans", 
					dbName));
	
	}
	
	
	@Override
	public boolean processBilling(ModelAndView modelAndView,
			AdNetworkRequestBean adNetworkRequestBean) {
		try{
			ActelServiceConfig actelServiceConfig= 
					ActelConstant.mapServiceIdToActelServiceConfig
				.get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
			
	  modelAndView.addObject("token", adNetworkRequestBean.adnetworkToken.getTokenToCg());
	  modelAndView.addObject("actelServiceConfig", actelServiceConfig);
	  modelAndView.addObject("l", 0);
	  modelAndView.setViewName("actel/msisdn_missing");
	  
	  if(adNetworkRequestBean.vwserviceCampaignDetail.getOpId()==MConstants.ACTEL_DU_OPERATOR_ID) {
	 String url =heURL.replaceAll("<application_id>", actelServiceConfig.getIdApplication())
		.replaceAll("<callbackurl>", heCallbackURL+adNetworkRequestBean.adnetworkToken.getTokenToCg());
	  logger.info("redirected to he url"+url);	  
	  modelAndView.setView(new RedirectView(url));	  
	  }
	
		}catch(Exception ex){
		logger.error("Exception ",ex);	
		}
		
		return true;	    	
		 
	}
	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
		return false;
	}
	
	@Override
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean) {
		    
		
		   return false;
	  }
	

	@Override
	public DeactivationResponse deactivation(ModelAndView modelAndView,
			SubscriberReg subscriberReg) {
	     logger.info("deactivation:: not any process");
		 return null;
	}

	@Override
	public SubscriberReg searchSubscriber(Integer operatorId, String msisdn,
			Integer serviceId,Integer productId) {
		try{
			return jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, productId);
		}catch(Exception ex){
			logger.error("searchSubscriber ",ex);
		}
		return null;
	}

	@Override
	public boolean checkSub(Integer productId,Integer opid,String msisdn) {
		return false;
	}
	
	
}
