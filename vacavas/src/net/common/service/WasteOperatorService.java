package net.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.process.bean.AdNetworkRequestBean;
import net.process.request.AbstractOperatorService;
import net.util.MConstants;

@Service("wasteOperatorService")
public class WasteOperatorService extends  AbstractOperatorService{

	@Autowired
	private CommonService commonService;
	
	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
	
		return false;
	}
	
	@Override
	public boolean processBilling(ModelAndView modelAndView,AdNetworkRequestBean adNetworkRequestBean) {
		adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_WASTE_URL);
		modelAndView.setView(new RedirectView(commonService.getWasteUrl(adNetworkRequestBean)));
		return false;
	  }
	
}
