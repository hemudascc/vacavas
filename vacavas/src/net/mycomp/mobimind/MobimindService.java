package net.mycomp.mobimind;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.jpa.repository.JPAMobimindServiceConfig;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;
import net.util.MUtility;



@Service("mobimindService")
public class MobimindService extends AbstractOperatorService {


	private static final Logger logger = Logger
			.getLogger(MobimindService.class.getName());

	@Value("${mobimind.cg.callback.url}")
	private String cgCallbackUrl;

	@Autowired
	private JPAMobimindServiceConfig jpaMobimindServiceConfig;

	@Autowired
	private JPASubscriberReg jpaSubscriberReg;


	@PostConstruct
	public void init() {

		List<MobimindServiceConfig> list=jpaMobimindServiceConfig.findEnableMobimindServiceConfig(true);

		MobimindConstant.listMobimindServiceConfig.addAll(list);

		MobimindConstant.mapServiceIdToMobimindServiceConfig.putAll(
				list.stream().collect(Collectors.toMap(p -> p.getServiceId(), p -> p)));

		MobimindConstant.mapChannelIdToMobimindServiceConfig.putAll(
				list.stream().collect(Collectors.toMap(p -> p.getChannelId(), p -> p)));
	}


	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
		return false;
	}

	@Override
	public boolean processBilling(ModelAndView modelAndView,
			AdNetworkRequestBean adNetworkRequestBean) {
		try{
			MobimindServiceConfig mobimindServiceConfig = MobimindConstant.mapServiceIdToMobimindServiceConfig
														  .get(adNetworkRequestBean.vwserviceCampaignDetail.getServiceId());		
			
			String cgUrl=mobimindServiceConfig.getCgUrl().replaceAll("<cgcallbackurl>", 
						 MUtility.urlEncoding(cgCallbackUrl+adNetworkRequestBean.adnetworkToken.getTokenToCg()))
						 .replaceAll("<token>", adNetworkRequestBean.adnetworkToken.getTokenToCg());	

			logger.info("cgUrl::: "+cgUrl);

			adNetworkRequestBean.adnetworkToken.setRedirectToUrl(cgUrl);

			adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_CG);

			modelAndView.setView(new RedirectView(cgUrl));

		}catch(Exception ex){
			logger.error("Exception ",ex);	
		}
		return true;	    	
	}

	@Override
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean) {

		return false;
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
