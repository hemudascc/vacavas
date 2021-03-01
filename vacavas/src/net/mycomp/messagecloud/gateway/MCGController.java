package net.mycomp.messagecloud.gateway;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.common.service.RedisCacheService;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MData;
import net.util.MUtility;

@Controller
@RequestMapping("mcg")
public class MCGController {

	private static final Logger logger = Logger
			.getLogger(MCGController.class.getName());
	
	@Autowired
	private JMSMCGService jmsMCGService;
	
	@Autowired
	private MCGApiService mcgApiService;
	
	@Autowired
	private MCGRenewalScheduler mcgRenewalScheduler;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	@RequestMapping("mo/{serviceId}")
	@ResponseBody
	public String mo(ModelAndView modelAndView,HttpServletRequest request,
			@PathVariable(value="serviceId")Integer serviceId){
		//action=mpush_ir_message&message_id=1083545875&id=1083545875&billing=MT&
		//country=UK&number=447445566731&network=THREE14UK&shortcode=68899&message=hello+world
		
		MCGMoMessage mcgMoMessage=new MCGMoMessage(true);
		mcgMoMessage.setServiceId(serviceId);
		mcgMoMessage.setAction(request.getParameter("action"));
		mcgMoMessage.setMessageId(request.getParameter("id"));
		mcgMoMessage.setNumber(request.getParameter("number"));
		mcgMoMessage.setNetwork(request.getParameter("network"));
		mcgMoMessage.setMessage(request.getParameter("message"));
		mcgMoMessage.setShortcode(request.getParameter("shortcode"));
		mcgMoMessage.setCountry(request.getParameter("country"));
		mcgMoMessage.setBilling(request.getParameter("billing"));
		mcgMoMessage.setQueryStr(request.getQueryString());
		
		mcgMoMessage.setQueryStr(request.getQueryString());
		logger.info("mo::::::::: "+mcgMoMessage);
		jmsMCGService.savemoMessage(mcgMoMessage);
		return "OK";
	}

	@RequestMapping("dlr/{serviceId}")
	@ResponseBody
	public String dlr(ModelAndView modelAndView,HttpServletRequest request,
			@PathVariable(value="serviceId")Integer serviceId){
		//action=mpush_ir_message&message_id=1083545875&id=1083545875&billing=MT&
		//country=UK&number=447445566731&network=THREE14UK&shortcode=68899&message=hello+world
		
		MCGDeliveryReport mcgDeliveryReport=new MCGDeliveryReport(true);
		mcgDeliveryReport.setServiceId(serviceId);
		mcgDeliveryReport.setAction(request.getParameter("action"));
		mcgDeliveryReport.setMessageId(request.getParameter("id"));
		mcgDeliveryReport.setNumber(request.getParameter("number"));
		mcgDeliveryReport.setReport(request.getParameter("report"));
		mcgDeliveryReport.setReasonid(request.getParameter("reason_id"));
		mcgDeliveryReport.setRequestStr(request.getQueryString());
		logger.info("dlr::::::::: "+mcgDeliveryReport);
		jmsMCGService.saveDeliveryReport(mcgDeliveryReport);
		return "OK";
	}

	
	@RequestMapping(path={"lp"},method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView fromLp(ModelAndView modelAndView,HttpServletRequest request){
		
		logger.info("fromLp:::::: "+request.getQueryString());
		synchronized (MCGConstant.lock) {
			
		
		try{
		String token=request.getParameter("token");
		String msisdn=MCGConstant.formatMsisdn(request.getParameter("msisdn"));
		CGToken  cgToken=new CGToken(token);
		logger.info("fromLp:::::: token:: "+token+" ,msisdn: "+msisdn);
		VWServiceCampaignDetail vwServiceCampaignDetail=MData.mapCamapignIdToVWServiceCampaignDetail.
				get(cgToken.getCampaignId());
		
		MCGServiceConfig mcgServiceConfig=MCGConstant.
				mapServiceIdToMCGServiceConfig.get(vwServiceCampaignDetail.getServiceId());
		MCGFallbackPricePointConfig mcgFallbackPricePointConfig=MCGConstant.
				findMCGFallbackPricePointConfig(mcgServiceConfig,null);
		String msg=mcgServiceConfig.getOptInSms();			
		modelAndView.addObject("msisdn",msisdn);
		modelAndView.addObject("token",token);
		modelAndView.addObject("mcgServiceConfig",mcgServiceConfig);
		if(MCGConstant.valid(msisdn)){
			
			boolean isSendSms=false;
			         //MUtility.toBoolean(Objects.toString(redisCacheService
					//.getObjectCacheValue(MCGConstant.MSG_CLOUD_SUBINFO_CONTENT_CACHE_PREFIX
					//+mcgServiceConfig.getServiceId()+msisdn)),false);
			
			MCGMTMessage mcgMTMessage=null;
			if(!isSendSms){
			 mcgMTMessage=mcgApiService.sendContentMessage(msisdn, 
				mcgServiceConfig, mcgFallbackPricePointConfig, msg,
				token,MCGConstant.SUBINFO_CONTENT_MSG_TYPE,token,null);
			}
			
			if(mcgMTMessage!=null&&mcgMTMessage.getResponse()!=null
					&&mcgMTMessage.getResponse().equalsIgnoreCase("SUCCESS")){
				redisCacheService.putObjectCacheValueByEvictionMinute(
						MCGConstant.MSG_CLOUD_SUBINFO_CONTENT_CACHE_PREFIX
						+mcgServiceConfig.getServiceId()+msisdn
						, true, 30*60);
			}
			
		modelAndView.setViewName(mcgServiceConfig.getLpPages2());
		}else{
			modelAndView.setViewName(mcgServiceConfig.getLpPages());
		}
		
		}catch(Exception ex){
			logger.error("fromLp:::: ",ex);
		}
		}
		return modelAndView;
	}
	
	
	@RequestMapping("termcondtion")	
	public ModelAndView termCondition(HttpServletRequest request,ModelAndView modelAndView){
		try{
		String serviceid=request.getParameter("serviceid");
		logger.info("tigoGamePadTermCondition::::::::: serviceid:: "+serviceid);
		MCGServiceConfig mcgServiceConfig=MCGConstant.
				mapServiceIdToMCGServiceConfig.get(MUtility.toInt(serviceid, 0));		
		modelAndView.setViewName(mcgServiceConfig.getTermConditionPage());
		}catch(Exception ex){
			logger.error("termCondition:: ",ex);
		}
		return modelAndView;
	}
	
	@RequestMapping("networkname")	
	@ResponseBody
	public String getNetworkName(HttpServletRequest request,ModelAndView modelAndView){
		String networkName=null;
		try{
		String msisdn=request.getParameter("msisdn");
		 networkName=mcgApiService.findNetworkName(msisdn);
		
		}catch(Exception ex){
			logger.error("getNetworkName:: ",ex);
		}
		return networkName;
	}
	
	@RequestMapping(path={"sendcontent"},method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String sendContent(ModelAndView modelAndView,HttpServletRequest request){
		
		logger.info("sendContent:::::: "+request.getQueryString());
		String response="0";
		try{
		String token=request.getParameter("token");
		String msisdn=MCGConstant.formatMsisdn(request.getParameter("msisdn"));
		CGToken  cgToken=new CGToken(token);
		logger.info("sendContent:::::: token:: "+token+" ,msisdn: "+msisdn);
		VWServiceCampaignDetail vwServiceCampaignDetail=MData.mapCamapignIdToVWServiceCampaignDetail.
				get(cgToken.getCampaignId());
		
		MCGServiceConfig mcgServiceConfig=MCGConstant.
				mapServiceIdToMCGServiceConfig.get(vwServiceCampaignDetail.getServiceId());
		MCGFallbackPricePointConfig mcgFallbackPricePointConfig=MCGConstant.
				findMCGFallbackPricePointConfig(mcgServiceConfig,null);
		String msg=mcgServiceConfig.getOptInSms();			
		modelAndView.addObject("msisdn",msisdn);
		modelAndView.addObject("token",token);
		modelAndView.addObject("mcgServiceConfig",mcgServiceConfig);
		if(MCGConstant.valid(msisdn)){
			response="1";
		mcgApiService.sendContentMessage(msisdn, 
				mcgServiceConfig, mcgFallbackPricePointConfig, msg,token,
				MCGConstant.SUBINFO_CONTENT_MSG_TYPE, token, null);
		//modelAndView.setViewName(mcgServiceConfig.getLpPages2());
		}else{
			//modelAndView.setViewName(mcgServiceConfig.getLpPages());
		}
		
		}catch(Exception ex){
			logger.error("sendContent:::: ",ex);
		}
		return response;
	}
	
	@RequestMapping(path={"sendrenewal"},method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String sendRenewal(ModelAndView modelAndView,HttpServletRequest request){
		mcgRenewalScheduler.sendRenewalBilled();
		return "ok";
	
	}
}
