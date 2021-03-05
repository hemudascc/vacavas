package net.mycomp.oredoo.kuwait;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.service.CommonService;
import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPAOredooKuwaitFallbackPricePoint;
import net.jpa.repository.JPAOredooKuwaitServiceConfig;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.AdnetworkOperatorConfig;
import net.persist.bean.Operator;
import net.persist.bean.Product;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.DeactivationResponse;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

@Service("oredoKuwaitService")
public class OredoKuwaitService extends AbstractOperatorService {

	private static final Logger logger = Logger
			.getLogger(OredoKuwaitService.class.getName());

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JPAOredooKuwaitServiceConfig jpaOredooKuwaitServiceConfig;
	
	@Autowired
	private JPAOredooKuwaitFallbackPricePoint jpaOredooKuwaitFallbackPricePoint;

	@Autowired
	private OredooKuwaitSubscriptionService oredooKuwaitSubscriptionService;
	
	@Autowired
	private SubscriberRegService subscriberRegService;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	
	@Autowired
	private IDaoService daoService;
	
	@Value("${jdbc.db.name}")
	private String dbName;
	
	
	private final String  cgUrl;
	
	//private final String portalUrl;
	private final String chargeableTypeUrl;
	private final String unsubTemplate;
     private final String localcgUrl;
	private Random random = new Random();
	 private final String imageUrl;
	 
	 @Autowired
	 private RedisCacheService redisCacheService;
	 
	@Autowired
	public OredoKuwaitService(@Value("${oredoo.kuwait.cg.url}") String cgUrl,
			@Value("${oredoo.portal.url}") String portalUrl,
			@Value("${oredoo.msisdn.chargeable.type.url}")String chargeableTypeUrl,
			@Value("${oredoo.kuwait.unsub.msg.template}")String unsubTemplate,
			@Value("${oredoo.kuwait.image.url}")String imageUrl,
			@Value("${oredoo.kuwait.local.cg.url}")String localcgUrl
			) {
		this.cgUrl=cgUrl;
		//this.portalUrl=portalUrl;
		this.chargeableTypeUrl=chargeableTypeUrl;	
		this.unsubTemplate=unsubTemplate;
		this.imageUrl=imageUrl;
		this.localcgUrl=localcgUrl;
	}

	@PostConstruct
	public void init() {
		
			List<OredooKuwaitServiceConfig> list = jpaOredooKuwaitServiceConfig.findEnableOredoConfig(true);
			OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig.putAll(list.stream()
					.collect(Collectors.toMap(p -> p.getServiceId(), p -> p)));
			
			
			OredoKuwaitConstant.mapPlanIdToOredooKuwaitServiceConfig.putAll(list.stream()
					.collect(Collectors.toMap(p -> p.getComvivaServiceId()+p.getPlanId(), p -> p)));
				
		
		  List<OredoKuwaitFallbackPricePoint> listOredoKuwaitFallbackPricePoint =
		  jpaOredooKuwaitFallbackPricePoint.findEnableOredoKuwaitFallbackPricePoint(
		  true);
		  OredoKuwaitConstant.mapServiceIdToListOredoKuwaitFallbackPricePoint.putAll(
		  listOredoKuwaitFallbackPricePoint.stream().collect(
		  Collectors.groupingBy(OredoKuwaitFallbackPricePoint::getServiceId,
		  HashMap::new, Collectors.toCollection(ArrayList::new))));
		 
			OredoKuwaitConstant.oredooKuwaitOCSLogDetailId.set(
					daoService.findNextAutoIncrementId("tb_oredoo_kuwait_ocs_log_detail", dbName));
				      
		
	}
	

	@Override
	public boolean processBilling(ModelAndView modelAndView,
			AdNetworkRequestBean adNetworkRequestBean) {
		try{
		
		OredooKuwaitServiceConfig oredooKuwaitServiceConfig=
				OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig
				.get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
		  modelAndView.addObject("cgUrl",localcgUrl);
		  String token=adNetworkRequestBean.adnetworkToken.getTokenToCg();
		//  modelAndView.addObject("oredooKuwaitServiceConfigMap",OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig);
		  OredooKuwaitCGToken oredooKuwaitCGToken=new OredooKuwaitCGToken(adNetworkRequestBean.adnetworkToken.getTokenId());
		  adNetworkRequestBean.adnetworkToken.setTokenToCg(String.valueOf(oredooKuwaitCGToken.getCGToken()));
		  modelAndView.addObject("transID",oredooKuwaitCGToken.getTokenId());
		 
		  modelAndView.addObject("chargeableTypeUrl",chargeableTypeUrl);
    	  modelAndView.addObject("msisdn",OredoKuwaitConstant.formatMsisdnRemove965(adNetworkRequestBean.getMsisdn()));
    	  modelAndView.addObject("portalUrl",OredoKuwaitConstant.getPortalUrl(
    			  oredooKuwaitServiceConfig.getPortalUrl(),OredoKuwaitConstant.formatMsisdnRemove965(adNetworkRequestBean.getMsisdn()),0));
    	 
    	  modelAndView.setView(new RedirectView(OredoKuwaitConstant.getPortalUrl(
    			  oredooKuwaitServiceConfig.getPortalUrl()
    			  ,OredoKuwaitConstant.formatMsisdnRemove965(adNetworkRequestBean.getMsisdn()),0)));
    	 
    	  
    	  String lpImage="";
  		if(oredooKuwaitServiceConfig.getLpImages()!=null&&oredooKuwaitServiceConfig.getLpImages().size()>0){
  			int index=MConstants.random.nextInt(oredooKuwaitServiceConfig.getLpImages().size());
  			lpImage=imageUrl+oredooKuwaitServiceConfig.getLpImages().get(index);
  		}	
  		
    	  modelAndView.addObject("img",lpImage);
    	  modelAndView.addObject("Wap_mdata", lpImage);
    	  	  
		if(adNetworkRequestBean.getMsisdn()==null||adNetworkRequestBean.getMsisdn().equalsIgnoreCase("NA")){
			
			if(MUtility.toBoolean(adNetworkRequestBean.adnetworkToken.getParam1(),Boolean.TRUE)){
				logger.info("TRUE:::::::::::::::");
				modelAndView.addObject("serviceId",adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
				modelAndView.setViewName(oredooKuwaitServiceConfig.getNotHeView());
				adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_WASTE_URL);
			}else{
				   oredooKuwaitServiceConfig=OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig
						   .get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
				  modelAndView.addObject("oredooKuwaitServiceConfig",oredooKuwaitServiceConfig);
				  modelAndView.addObject("validatemsisdn",false);
				  modelAndView.setViewName(oredooKuwaitServiceConfig.getNotHeAutoWifiView());
				  adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_CG);
			}
			
		}else{
			
			OCSResponse ocsResponse=oredooKuwaitSubscriptionService.checkSubs(adNetworkRequestBean.getMsisdn(),oredooKuwaitServiceConfig);
			logger.info("sub type:: msisdn:: "+adNetworkRequestBean.getMsisdn()+", ocsResponse::"+ocsResponse);
			int serviceId= OredoKuwaitConstant.findServiceId(ocsResponse.getSubsType(), 
					adNetworkRequestBean.vwserviceCampaignDetail.getServiceId(),
					adNetworkRequestBean.vwserviceCampaignDetail.getProductId());
			logger.info("sub type:: msisdn:: "+adNetworkRequestBean.getMsisdn()+", serviceId::"+serviceId);
			
			if(serviceId>0){
				
			  oredooKuwaitServiceConfig=OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig.get(serviceId);
			  modelAndView.addObject("oredooKuwaitServiceConfig",oredooKuwaitServiceConfig);
			  modelAndView.addObject("validatemsisdn",false);
			  
			  logger.info("sub type:: adNetworkRequestBean.getOpId():: "+adNetworkRequestBean.getOpId()
					  +", adNetworkRequestBean.getAdNetworkId()::"+adNetworkRequestBean.getAdNetworkId()
					  +", MData.mapAdnetworkOpConfig:: "+MData.mapAdnetworkOpConfig);
			 			  
			  if(MUtility.toBoolean(adNetworkRequestBean.adnetworkToken.getParam1(),Boolean.TRUE)){
				  modelAndView.setViewName(oredooKuwaitServiceConfig.getHeView());
				  adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_CG);			 
			  }else{				
				  modelAndView.setViewName(oredooKuwaitServiceConfig.getHeAutoView());
				  adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_CG);
			  }
			  
			 }else{
			 modelAndView.setViewName("oredoo/kuwait/exceed_limit");
			 adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_ERROR);
			 }
		}
		modelAndView.addObject("token", token);
		Map<String,Object> map=modelAndView.getModel();
		logger.info("Model Map "+map);
		}catch(Exception ex){
			logger.error("processing billing::: ",ex);
			 
		}
		
		return true;
	}

	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
		try{
			Operator operator = MData.mapIdToOperator.get(adNetworkRequestBean.getOpId());
			Product product = MData.mapIdToProduct.get(adNetworkRequestBean.vwserviceCampaignDetail.getProductId());
			AdnetworkOperatorConfig adnetworkOpConfig = MData.mapAdnetworkOpConfig
					.get(adNetworkRequestBean.getOpId()).get(adNetworkRequestBean.getAdNetworkId());
			
			if(redisCacheService.isCappingOver(adnetworkOpConfig,operator,product
					,adNetworkRequestBean.adnetworkToken.getReqTime())){			
				adNetworkRequestBean.adnetworkToken
				.setAction(MConstants.REDIRECT_TO_WASTE_URL_CONVERSION_CAPPING);
				adNetworkRequestBean.adnetworkToken.setRedirectToUrl(commonService.
						getWasteUrl());
				return true;
			}			
		}catch(Exception ex){
			logger.error("checkBlocking::: ",ex);
			//TODO change to true
			return false;
		}
        
		return false;
	}

	@Override
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean) {

		try{
			SubscriberReg subscriberReg=jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(adNetworkRequestBean.getMsisdn(),
				   adNetworkRequestBean.vwserviceCampaignDetail.getProductId());
		  if(subscriberReg!=null&&subscriberReg.getStatus()==MConstants.SUBSCRIBED){
			OredooKuwaitServiceConfig oredooKuwaitServiceConfig=
					OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig
					.get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
			
			adNetworkRequestBean.adnetworkToken
			.setRedirectToUrl(OredoKuwaitConstant.getPortalUrl(oredooKuwaitServiceConfig.getPortalUrl(),
					OredoKuwaitConstant.formatMsisdnRemove965(adNetworkRequestBean.getMsisdn()),subscriberReg.getSubscriberId()));
			return true;
		    	}
		}catch(Exception ex){
			logger.error("isSubscribed::: ",ex);
		}
		return false;
	}
	
	
	@Override
	public DeactivationResponse deactivation(ModelAndView modelAndView,
			SubscriberReg subscriberReg) {
		OredooKuwaitServiceConfig oredooKuwaitServiceConfig=
				OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig.
				get(subscriberReg.getServiceId());
		OCSResponse ocsResponse= oredooKuwaitSubscriptionService.unSubcription(subscriberReg,
				oredooKuwaitServiceConfig);
		DeactivationResponse deactivationResponse=new DeactivationResponse();
		   
		if(ocsResponse!=null&&ocsResponse.getResult()!=null&&ocsResponse.
				getResult().equalsIgnoreCase(OredoKuwaitConstant.UNSUBCRIPTION_API_RESULT)){
		    	 deactivationResponse.setStatus(true);
		     	String message= unsubTemplate.replaceAll("<productname>", oredooKuwaitServiceConfig.getProductName());
		     	subscriberRegService.updateDeactivation(subscriberReg.getMsisdn(), subscriberReg.getServiceId());
		     	deactivationResponse.setMessgae(message);
			  }else{
				  deactivationResponse.setMessgae("Technical issue. Please try after sometime");
			  }
		logger.info("deactivationResponse:::::::::::: "+deactivationResponse.getMessgae());
		     return deactivationResponse;
		}
	
	public SubscriberReg searchSubscriber(Integer operatorId,String msisdn, 
			Integer serviceId,Integer productId){
		try{
			return jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, productId);
		}catch(Exception ex){
			logger.error("searchSubscriber ",ex);
		}
		return null;
		
	}
	
	}

