package net.mycomp.timwe;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.jpa.repository.JPATimweServiceConfig;
import net.process.bean.AdNetworkRequestBean;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;

@Service("timweService")
public class TimweService extends AbstractOperatorService{

	@Autowired
	private JPATimweServiceConfig jpaTimweServiceConfig;
	@Autowired
	private TimweApiService timweApiService;

	@Value("${timwe.he.url}")
	private String heURL;
	@Value("${timwe.he.callback.url}")
	private String heCallbackURL;


	private static final Logger logger = Logger.getLogger(TimweService.class);

	@PostConstruct
	void init() {
		List<TimweServiceConfig> list = jpaTimweServiceConfig.findEnableTimweServiceConfig(true);
		TimweConstant.mapServiceIdToTimweServiceConfig.putAll(
				list.stream().collect(Collectors.toMap(TimweServiceConfig :: getServiceId, Function.identity())));
	}

	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
		return false;
	}

	@Override
	public boolean processBilling(ModelAndView modelAndView, AdNetworkRequestBean adNetworkRequestBean) {
		adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_CG);
		String callbackURL = heCallbackURL.replaceAll("<token>", adNetworkRequestBean.adnetworkToken.getTokenToCg());
		String url = heURL.replaceAll("<redirecturl>", callbackURL);
		logger.debug("redirecting user with token="+adNetworkRequestBean.adnetworkToken.getTokenToCg()+"  to"+url);
		modelAndView.setView(new RedirectView(url));
		return true;
	}



}
