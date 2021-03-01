package net.mycomp.messagecloud.gateway;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import net.common.service.BlockSeriesRedisCacheService;
import net.common.service.IDaoService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPAMCGServiceConfig;
import net.jpa.repository.JPAMccMncNetwork;
import net.jpa.repository.JPAMessageCloudServiceConfig;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service("mcgService")
public class MCGService extends AbstractOperatorService {

	
	private static final Logger logger = Logger
			.getLogger(MCGService.class.getName());
	
	
	@Autowired
	private JPAMCGServiceConfig jpaMCGServiceConfig;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	@Autowired
	private JPAMccMncNetwork jpaMccMncNetwork;
	
	@Autowired
	private BlockSeriesRedisCacheService blockSeriesRedisCacheService;
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private SubscriberRegService subscriberRegService;
	
   @Autowired
   private JPAMessageCloudServiceConfig jpaMessageCloudServiceConfig;
   
   @Value("${jdbc.db.name}")
	private String dbName;
   
	
	public MCGService(){
		
	}
	
	@PostConstruct
	public void init() {
		try {
		List<MCGServiceConfig> list=jpaMCGServiceConfig.findEnableMCGServiceConfig(true);
		
		MCGConstant.mapIdToMCGServiceConfig.putAll(
				list.stream().collect(
						Collectors.toMap(p -> p.getId(), p -> p))
						);
		
		MCGConstant.mapServiceIdToMCGServiceConfig.putAll(
				list.stream().collect(
						Collectors.toMap(p -> p.getServiceId(), p -> p))
						);
		
		/*
		 * MCGConstant.mapKeywordNetworkToMCGServiceConfig.putAll(
		 * list.stream().collect( Collectors.toMap(p ->
		 * p.getKeyword().toLowerCase()+p.getNetwork().toLowerCase(), p -> p)) );
		 */
		
		Integer id=daoService.
				findNextAutoIncrementId("tb_mcg_mt_message", dbName);
		MCGConstant.inMCGMTMessageIdAtomicInteger.set(id);
		
		 List<MccMncNetwork> listMccMncNetwork=jpaMccMncNetwork.findEnableMccMncNetwork(true);
		
		MCGConstant.mapMccMncToMccMncNetwork.putAll(listMccMncNetwork.stream().collect(
				Collectors.toMap(p -> p.getMcc()+p.getMnc(), p -> p)));
		}
		catch(Exception e) {
			logger.error(e);
			MCGConstant.inMCGMTMessageIdAtomicInteger.set(1);
		}
		
	}
	
	
	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
			
		
		return false;
	}
	
	@Override
	public boolean processBilling(ModelAndView modelAndView,
			AdNetworkRequestBean adNetworkRequestBean) {
	       
		MCGServiceConfig mcgServiceConfig=MCGConstant.mapServiceIdToMCGServiceConfig.get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
//		String lpImage="";
//		if(mcgServiceConfig.getLpImages()!=null&&mcgServiceConfig.getLpImages().size()>0){
//			int index=MConstants.random.nextInt(mcgServiceConfig.getLpImages().size());
//			lpImage=mcgServiceConfig.getLpImages().get(index);
//		}			
		modelAndView.addObject("token",adNetworkRequestBean.adnetworkToken.getTokenToCg());
		modelAndView.addObject("mcgServiceConfig",mcgServiceConfig);
		modelAndView.setViewName(mcgServiceConfig.getLpPages());
		
//		if(adNetworkRequestBean.adnetworkToken.getAdnetworkId()==2){
//			modelAndView.setViewName("mcg/click_to_sms_gogame_lp");	
//		}
		
		 return true;	    	
	}
	
	
	
	@Override
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean) {
		
		boolean isSubscribed= subscriberRegService.isSubscribedBySericeId(adNetworkRequestBean.getMsisdn()
				, adNetworkRequestBean.vwserviceCampaignDetail.getProductId());
		    
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
	
	public boolean checkBlocking(String msisdn) {
    	try{
		 List<String> keys=Arrays.asList(new String[]{msisdn,
				  MUtility.find7DigitMobileNo(msisdn),
				  MUtility.find11DigitMobileNo(msisdn)					
				  });
		 return blockSeriesRedisCacheService.isBlockSeries(keys);
    	}catch(Exception ex){
    		
    	}
		return true;	    
	}
	
	
}
