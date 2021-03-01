package net.process.request;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.service.BlockSeriesRedisCacheService;
import net.common.service.CommonService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.AdnetworkOperatorConfig;
import net.persist.bean.Adnetworks;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.DeactivationResponse;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

@Service("defaultOperatorService")
public class AbstractOperatorService implements IOperatorService{

	private static final Logger logger = Logger.getLogger(AbstractOperatorService.class);

	@Autowired
	private SubscriberRegService subscriberRegService;
	
	@Autowired
	private RedisCacheService redisCacheService; 
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	@Autowired
	private BlockSeriesRedisCacheService blockSeriesRedisCacheService;
	
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {

		Adnetworks adnetwork = MData.mapAdnetworks.get(adNetworkRequestBean.getAdNetworkId());
		
		  if (adNetworkRequestBean.getMsisdn() == null 
				  || adNetworkRequestBean.getMsisdn().equalsIgnoreCase("")) {
		   logger.info("checkBlocking:: msisdn: msisdn is empty ");
		   adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_WASTE_URL);
		   //Adnetworks adnetwork = MData.mapAdnetworks.get(adNetworkRequestBean.getAdNetworkId());
		   if (adnetwork != null) {
			   adNetworkRequestBean.adnetworkToken
		      .setRedirectToUrl(adnetwork.getWasteUrl() + URLEncoder.encode(adNetworkRequestBean.getToken()));
		   }
		   return true;
		 } 
		
		  AdnetworkOperatorConfig adnetworkOpConfig=  MData.
				  mapAdnetworkOpConfig.get(adNetworkRequestBean.getOpId())
				  .get(adNetworkRequestBean.getAdNetworkId());
		  
		  if (adnetworkOpConfig!=null&&adnetworkOpConfig.getAdBlockStatus()) {
		   adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_WASTE_URL_COMAPIGN_BLOCK);
		   logger.info("checkBlocking:: msisdn: " + adNetworkRequestBean.getMsisdn()+" ,"+MConstants.REDIRECT_TO_WASTE_URL_COMAPIGN_BLOCK);
		  // Adnetworks adnetwork =MData.mapAdnetworks.get(adNetworkRequestBean.getAdNetworkId());
		   if (adnetwork != null) {
			   adNetworkRequestBean.adnetworkToken
		      .setRedirectToUrl(adnetwork.getWasteUrl() + URLEncoder.encode(adNetworkRequestBean.getToken()));
		   }
		   return true;
		  }

		  List<String> list=Arrays.asList(new String[]{
				  MUtility.find7DigitMobileNo(adNetworkRequestBean.getMsisdn()),
				  MUtility.find11DigitMobileNo(adNetworkRequestBean.getMsisdn())					
				  });
			  
			  long time=System.currentTimeMillis();
			  if(blockSeriesRedisCacheService.isBlockSeries(list))  {
			   logger.error("checkBlocking msisdn:::true: "+adNetworkRequestBean.getMsisdn());
			   adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_WASTE_URL_BLOCKSERIES);
			   if (adnetwork != null) {
			    //adnetworkToken
			      //.setRedirectToUrl(adnetwork.getWasteUrl() + URLEncoder.encode(adNetworkRequestBean.getToken()));
				   adNetworkRequestBean.adnetworkToken
			    .setRedirectToUrl(adnetwork.getWasteUrl());
			   }
			   logger.error("listBlockSeries:: blocked msisdn: " + adNetworkRequestBean.getMsisdn()+", black list time:: "+(System.currentTimeMillis()-time));
			   return true;
			  }
			  
			  
		  if (adnetworkOpConfig!=null&&adnetworkOpConfig.getDuplicateBlockStatus() != null
		      && adnetworkOpConfig.getDuplicateBlockStatus()) {
//		     Integer counter = redisCacheService.getIntValue(adNetworkRequestBean.adnetworkToken.
//		    		 getSource());
		     Integer msisdnCounter = redisCacheService.getIntValue(adNetworkRequestBean.adnetworkToken.
		    		 getMsisdn());
		     
		   //  logger.debug("checkBlocking:: msisdn: " + adNetworkRequestBean.getMsisdn() + ", cache counter: " + counter);
		     if ((msisdnCounter!=null&&msisdnCounter>0)){      
		    	 adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_WASTE_URL_DUPLICATE_MISISDN);
		     // Adnetworks adnetwork = MData.mapAdnetworks.get(adNetworkRequestBean.getAdNetworkId());
		      if (adnetwork != null) {
		    	  adNetworkRequestBean.adnetworkToken.setRedirectToUrl(
		         adnetwork.getWasteUrl() + URLEncoder.encode(adNetworkRequestBean.getToken()));
		      }
		      logger.debug("checkBlocking:: ip: " + adNetworkRequestBean.adnetworkToken.getSource());
		      return true;
		     }
		    }
		  return false;
		 }

	@Override
	public boolean processBilling(ModelAndView modelAndView,AdNetworkRequestBean adNetworkRequestBean) {
		adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_WASTE_URL);
		modelAndView.setView(new RedirectView(commonService.getWasteUrl(adNetworkRequestBean)));
		return false;
	  }

	@Override
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean) {
		    
		   boolean isSubscribed=subscriberRegService.isSubscribed(adNetworkRequestBean.getMsisdn());
		    
		   if(isSubscribed){
			logger.debug("campaign:: already subscribed, ");
			adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_WASTE_URL_ALREADY_SUBSCRIBED);
			adNetworkRequestBean.adnetworkToken.setRedirectToUrl("" + "?substatus=true");
		}	
		   return isSubscribed;
	  }
	

	@Override
	public DeactivationResponse deactivation(ModelAndView modelAndView,
			SubscriberReg subscriberReg) {
	     logger.info("deactivation:: not any process");
		 return null;
	}

	
//	@Override
//	public IOtp sendOtp(ModelAndView modelAndView, String msisdn,
//			Integer operatorId, Integer serviceId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendOtp(ModelAndView modelAndView,
			AdNetworkRequestBean adNetworkRequestBean) {
		logger.info("sendOtp:::: default implementation ");
		return false;
	}

	@Override
	public boolean validateOtp(ModelAndView modelAndView, 
			AdNetworkRequestBean adNetworkRequestBean) {
		logger.info("validateOtp:::: default implementation ");
		return false;
	}

	@Override
	public Timestamp getTimeByOperator(Integer opId) {
		return new Timestamp(System.currentTimeMillis());
	}
	}
