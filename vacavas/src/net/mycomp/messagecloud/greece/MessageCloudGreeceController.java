package net.mycomp.messagecloud.greece;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mcggc")
public class MessageCloudGreeceController {
	
	@RequestMapping("lp")
	public ModelAndView lp(ModelAndView modelAndView) {
		modelAndView.setViewName("mcggreece/lp");
		return modelAndView;
	}
	
	@RequestMapping("tc")
	public ModelAndView tc(ModelAndView modelAndView) {
		modelAndView.setViewName("mcggreece/tc");
		return modelAndView;
	}
	
}
