package net.mycomp.comviva;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.service.SubscriberRegService;
import net.jpa.repository.JPAComvivaServiceConfig;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.DeactivationResponse;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;

@Service("comvivaService")
public class ComvivaService extends AbstractOperatorService{
	
	private static final Logger logger = Logger.getLogger(ComvivaService.class);
	
	@Value("${comviva.he.url}")
	private String comvivaHEURL;
	@Value("${comviva.unsub.msg.template}")
	private String unsubTemplate;
	@Autowired
	JPAComvivaServiceConfig jpaComvivaServiceConfig;
	@Autowired
	private JPASubscriberReg jpaSubscriberReg; 
	@Autowired
	private ComvivaSubscriptionService comvivaSubscriptionService;
	@Autowired
	private SubscriberRegService subscriberRegService;
	
	@PostConstruct
	public void init() {
		List<ComvivaServiceConfig> comvivaServiceConfigs = jpaComvivaServiceConfig.findAll();
		ComvivaConstant.mapServiceIdToComvivaServiceConfig.putAll(
				comvivaServiceConfigs.stream().collect(Collectors.toMap(ComvivaServiceConfig :: getServiceId, Function.identity())));
	}
	
	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
		return false;
	}

	@Override
	public boolean processBilling(ModelAndView modelAndView, AdNetworkRequestBean adNetworkRequestBean) {
		//http://singlehe.ooredoo.com.kw:9989/SingleSiteHE/getHE?
		//productID=<product_id>&pName=<product_name>&CpId=<cp_id>
		//&CpPwd=<cp_pwd>&CpName=<cp_name>&transID=<token>
		ComvivaServiceConfig comvivaServiceConfig = ComvivaConstant.mapServiceIdToComvivaServiceConfig
													.get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());
		String heURL = comvivaHEURL.replaceAll("<product_id>", comvivaServiceConfig.getComvivaProductId())
									.replaceAll("<product_name>", comvivaServiceConfig.getProductName())
									.replaceAll("<cp_id>", comvivaServiceConfig.getCpId())
									.replaceAll("<cp_pwd>", comvivaServiceConfig.getCpPwd())
									.replaceAll("<cp_name>", comvivaServiceConfig.getCpName())
									.replaceAll("<token>", adNetworkRequestBean.adnetworkToken.getTokenToCg());
		logger.debug("redirecting to he url="+heURL);
		adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_CG);
		modelAndView.setView(new RedirectView(heURL));
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
	public DeactivationResponse deactivation(ModelAndView modelAndView,
			SubscriberReg subscriberReg) {
		ComvivaServiceConfig comvivaServiceConfig=
				ComvivaConstant.mapServiceIdToComvivaServiceConfig
				.get(subscriberReg.getServiceId());
		OCSResponse ocsResponse= comvivaSubscriptionService.unsubscribe(subscriberReg,
				comvivaServiceConfig);
		DeactivationResponse deactivationResponse=new DeactivationResponse();
		   
		if(ocsResponse!=null&&ocsResponse.getResult()!=null&&ocsResponse.
				getResult().equalsIgnoreCase(ComvivaConstant.SUCCESS_UNSUBSCRIBE)){
		    	 deactivationResponse.setStatus(true);
		     	String message= unsubTemplate.replaceAll("<productname>", comvivaServiceConfig.getProductName());
		     	subscriberRegService.updateDeactivation(subscriberReg.getMsisdn(), subscriberReg.getServiceId());
		     	deactivationResponse.setMessgae(message);
			  }else{
				  deactivationResponse.setMessgae("Technical issue. Please try after sometime");
			  }
		logger.info("deactivationResponse:::::::::::: "+deactivationResponse.getMessgae());
		     return deactivationResponse;
		}
}
