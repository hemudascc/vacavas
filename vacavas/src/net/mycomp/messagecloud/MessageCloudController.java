package net.mycomp.messagecloud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mc")
public class MessageCloudController {
	
	@RequestMapping("gameshub/lp")
	public ModelAndView lp(ModelAndView modelAndView) {
		modelAndView.setViewName("messagecloud/lp");
		return modelAndView;
	}
	
	@RequestMapping("gameshub/terms")
	public ModelAndView terms(ModelAndView modelAndView) {
		modelAndView.setViewName("messagecloud/terms");
		return modelAndView;
	}
	
}
