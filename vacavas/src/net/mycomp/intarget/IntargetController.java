package net.mycomp.intarget;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.common.service.SubscriberRegService;
import net.persist.bean.Service;
import net.persist.bean.SubscriberReg;
import net.util.JsonMapper;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("hgate")
public class IntargetController {


	private static final Logger logger = Logger
			.getLogger(IntargetController.class.getName());
	
	@Value("${intarger.thank.url}")
	private String intargerThankUrl;
	
	@Autowired
	private JMSIntargetService jmsIntargetService;
	

	@Autowired
	private IntargetApiService intargetApiService;
	
	@Autowired
	private SubscriberRegService subscriberRegService; 
	
	@RequestMapping(value={"notification","callback"})
	@ResponseBody
	public String notification(ModelAndView modelAndView,HttpServletRequest request){
		try{
		String xml= MUtility.getStringFromRequest(request.getInputStream());
		logger.info("notification::: xml:: "+xml);
		IntargetNotificationMessage intargetNotification=intargetApiService.getIntargetNotificationMessage(xml);
		intargetNotification.getIntargetNotification().setCreateTime(new Timestamp(System.currentTimeMillis()));
		intargetNotification.getIntargetNotification().setStatus(true);
		intargetNotification.getIntargetNotification().setRequestXml(xml);
		if(intargetNotification.getIntargetNotification().getResponseType().
				equalsIgnoreCase("OnReceiveSMS")){
			jmsIntargetService.saveIntargetOnReceiveSMSJMSTemplate(
					(IntargetOnReceiveSMSNotification)intargetNotification.getIntargetNotification());
		}else{
			jmsIntargetService.saveIntargetOnResultJMSTemplate((IntargetOnResultNotification)intargetNotification.getIntargetNotification());
		}
		}catch(Exception ex){
			logger.error("notification", ex);
		}
		return "ok";	
	}
	
	
	@RequestMapping(value={"send/sms/doiapi"})
	@ResponseBody
	public String smsOtptInDoiApi(ModelAndView modelAndView,HttpServletRequest request){
			logger.info("smsOtptInDoiApi:::::::::::::::::: "+request.getQueryString());
			return "ok";		
	}
	
	
	@RequestMapping(value={"send/ussd"})
	@ResponseBody
	public String sendUSSD(ModelAndView modelAndView,HttpServletRequest request,
			HttpServletResponse response){
		     
		response.setHeader("Content-Type", "application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
			String url="";
		    logger.info("sendUSSD:::::::::::::::::: "+request.getQueryString());
			String serviceid=request.getParameter("serviceid");
			String msisdn=request.getParameter("msisdn");
			String token=request.getParameter("token");
			InTargetConfig inTargetConfig=IntargetConstant.mapServiceIdTpInTargetConfig.
					get(MUtility.toInt(serviceid,0));
			Service service=MData.mapServiceIdToService.get(inTargetConfig.getServiceId());
			
			//"254718839218"
		 if(subscriberRegService.isSubscribedBySericeId(msisdn, service.getProductId())){
				url=inTargetConfig.getPortalUrl();
			}else{
			InTargetUssdTrans inTargetUssdTrans=intargetApiService.sendSMS(inTargetConfig, msisdn, 
					MConstants.ACT,IntargetMessageType.USSD_MSG,inTargetConfig.getPricePointCode(),
					"DOI USSD",token);
			url=intargerThankUrl;
			}
		 logger.info("sendUSSD::::::::::::::::: url:: "+url);
		
	   return JsonMapper.getObjectToJson(url);
	   
	}
	
	@RequestMapping(value={"send/content"})
	@ResponseBody
	public String sendContent(ModelAndView modelAndView,HttpServletRequest request,
			HttpServletResponse response){
		     
		
			logger.info("sendContent:::::::::::::::::: "+request.getQueryString());
			String serviceid=request.getParameter("serviceid");
			String msisdn=request.getParameter("msisdn");
			InTargetConfig inTargetConfig=IntargetConstant.mapServiceIdTpInTargetConfig.
					get(MUtility.toInt(serviceid,0));
			
			//"254718839218"
		 
			String msg= IntargetConstant.getMsg(inTargetConfig.getWelcomeActivationMessage(), inTargetConfig);
			intargetApiService.sendContentSMS(inTargetConfig,
					msisdn,IntargetConstant.CONTENT_MSG, 
							   IntargetMessageType.CONTENT_MSG,msg
							   ,"","","0");
			
			
			return "OK";
	}
	
	
	@RequestMapping(value={"thanks"})
	public ModelAndView redirectToThanks(ModelAndView modelAndView,HttpServletRequest request){
		modelAndView.setViewName("intarget/thanks");
		return modelAndView;
	}
	
	
	@RequestMapping(value={"send/billled"})
	@ResponseBody
	public String sendBilled(ModelAndView modelAndView,HttpServletRequest request){
			
		   logger.info("sendUSSD:::::::::::::::::: "+request.getQueryString());
			String serviceid=request.getParameter("serviceid");
			String msisdn=request.getParameter("msisdn");
			String token=request.getParameter("token");
			InTargetConfig inTargetConfig=IntargetConstant.mapServiceIdTpInTargetConfig.
					get(MUtility.toInt(serviceid,0));
			//"254718839218"
			InTargetUssdTrans inTargetUssdTrans=intargetApiService.sendSMS(inTargetConfig, msisdn, 
					MConstants.ACT,IntargetMessageType.BILLED_MSG,
					inTargetConfig.getPricePointCode(),"DOI USSD",token);
			return inTargetUssdTrans.toString();		
	}
	
}
