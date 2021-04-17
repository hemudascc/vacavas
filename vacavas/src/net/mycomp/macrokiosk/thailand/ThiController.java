package net.mycomp.macrokiosk.thailand;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import net.common.jms.JMSService;
import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.ErrorInfo;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("th")
public class ThiController {

	private static final Logger logger = Logger.getLogger(ThiController.class);

	@Value("#{ T(java.time.LocalTime).parse('${thia.ais.renewal.start.time}')}")
	private LocalTime renewalStartTime;

	@Value("#{ T(java.time.LocalTime).parse('${thia.ais.renewal.stop.time}')}")
	private LocalTime renewalStopTime;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	@Value("${thialand.mo.send.url}")
	private String moUrl;
	
	@Value("${thialand.to.cg.url}")
	private String toCgUrl;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg; 
	
//	@Autowired
//	private ThiaRenewalScheduler thiaRenewalScheduler;
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private JMSService jmsService;
	
	
	@Autowired
	private JMSThialandService jmsThialandService;
	
	
	@Autowired
	private THServiceApi thServiceApi;
	
	
	@Autowired
	private MacroKioskFactoryService macroKioskFactoryService;
	
	@RequestMapping("dn")
	@ResponseBody
	public String deliveryNotification(HttpServletRequest request){
		DeliveryNotification deliveryNotification=null;
		try{
		logger.info("deliveryNotification:::::::::::::::::::::::::::::::: "+request.getQueryString());
		//// http://www.yourdomainDNurl/receive.aspx?mtid=123296707&moid=1234567&
		// msisdn=66874111222&shortcode=4541889&telcoid=1&countryid=3&datetime=2010-06-15
		// 10:10:10&status=OK
		 deliveryNotification=new DeliveryNotification();
		deliveryNotification.setMtid(request.getParameter("mtid"));
		deliveryNotification.setMoid(request.getParameter("moid"));		
		deliveryNotification.setMsisdn(request.getParameter("msisdn"));
		deliveryNotification.setDatetime(ThiaConstant.convertStringToTimestamp(request.getParameter("datetime")));
		deliveryNotification.setShortcode(request.getParameter("shortcode"));	
		deliveryNotification.setTelcoId(MUtility.toInt(request.getParameter("telcoid"),0));
		deliveryNotification.setCountryId(MUtility.toInt(request.getParameter("countryid"),0));		
		deliveryNotification.setQueryStr(request.getQueryString());
		deliveryNotification.setCreateTime(new Timestamp(System.currentTimeMillis()));
		deliveryNotification.setStatus(request.getParameter("status"));		
		deliveryNotification.setOpId(ThialandOperatorTelcoidMap.getOperatorId(deliveryNotification.getTelcoId()));
		
		}catch(Exception ex){
			logger.error("exception",ex);
		}finally{
			jmsThialandService.saveDeliveryNotification(deliveryNotification);
		}
//Yash		if(deliveryNotification.getTelcoId()==ThialandOperatorTelcoidMap.TRUEMOVE_HUTCHISON.getTelcoId()){
//		return "1";	
//		}
		return "-1";
	}
	
		
	@RequestMapping("mo")
	@ResponseBody
	public String mo(HttpServletRequest request){
		THMOMessage thialandMOMessage=null;
		try{
		logger.info("mo:::::::::::::::::::::::::::::::: "+request.getQueryString());		
		 thialandMOMessage=new THMOMessage(true);		
		thialandMOMessage.setMsisdn(request.getParameter("from"));
		
		thialandMOMessage.setTime(request.getParameter("time"));
		thialandMOMessage.setReqTime(ThiaConstant.convertStringToTimestamp(thialandMOMessage.getTime()));
		thialandMOMessage.setShortcode(request.getParameter("shortcode"));
		thialandMOMessage.setMoid(request.getParameter("moid"));
		thialandMOMessage.setMsgid(request.getParameter("msgid"));
		thialandMOMessage.setTelcoid(MUtility.toInt(request.getParameter("telcoid"),0));
		thialandMOMessage.setChannel(request.getParameter("channel"));
		thialandMOMessage.setRefId(request.getParameter("refid"));
		thialandMOMessage.setQueryStr(request.getQueryString());
		thialandMOMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
		thialandMOMessage.setOpId(ThialandOperatorTelcoidMap.getOperatorId(thialandMOMessage.getTelcoid()));
		thialandMOMessage.setText(request.getParameter("text"));
		if(thialandMOMessage.getText()!=null&&thialandMOMessage.getText().
				toUpperCase().contains("STOP")){
			thialandMOMessage.setKeyword(thialandMOMessage.getText().split(" ")[1]);
		}else{
			thialandMOMessage.setKeyword(thialandMOMessage.getText());
		}
		}catch(Exception ex){
			logger.error("Exception",ex);
		}finally{
		jmsThialandService.saveMOMessage(thialandMOMessage);	
		}
		return "-1";
	}
	
	@RequestMapping("unsub")
	@ResponseBody
	public String  unsub(HttpServletRequest request, ModelAndView modelAndView){
		logger.info("unsub:::::::::: "+request.getQueryString());
		return "-1";
	}
			
	@RequestMapping("/callback/{token}")
	public ModelAndView callBackUrl(
			@PathVariable(value="token") String  token
			,HttpServletRequest request, ModelAndView modelAndView) {
	
		String msisdn = null;
		ThiaHeCallbackTrans thiaHeCallbackTrans=null;
		try{
		    //http://mk.com/Test.aspx?Status=Success&TelcoID=4&Msisdn=66874111222
			
			 thiaHeCallbackTrans=new ThiaHeCallbackTrans(true);
			 
			logger.info("  callBackUrl:: query String :: "+request.getQueryString());	
			msisdn=request.getParameter("Msisdn");			
		    int telcoId=MUtility.toInt(request.getParameter("TelcoID"),0);
		    String status=request.getParameter("Status");
		    CGToken cgToken=new CGToken(token);
		    
		    thiaHeCallbackTrans.setMsisdn(msisdn);
		    thiaHeCallbackTrans.setTelcoId(telcoId);
		    thiaHeCallbackTrans.setResult(status);
		    thiaHeCallbackTrans.setToken(token);
		    thiaHeCallbackTrans.setCampaignId(cgToken.getCampaignId());;
		    thiaHeCallbackTrans.setTokenId(cgToken.getTokenId());
		    thiaHeCallbackTrans.setQueryStr(request.getQueryString());
		    
		VWServiceCampaignDetail vwServiceCampaignDetail=MData.mapCamapignIdToVWServiceCampaignDetail.
				get(cgToken.getCampaignId());
		logger.info("He callback:: vwServiceCampaignDetail::::: "+vwServiceCampaignDetail);
		modelAndView.addObject("refid",token);
		THConfig thConfig=ThiaConstant.mapServiceIdToTHConfig
				.get(vwServiceCampaignDetail.getServiceId());
		
		if(status.equalsIgnoreCase("Success")){
			modelAndView.addObject("result",1);
//			THConfig thConfig=ThiaConstant.mapproductIdTelcoIdToTHConfig
//					.get(vwServiceCampaignDetail.getProductId()+""+telcoId);
			
			redisCacheService.
			putObjectCacheValueByEvictionMinute(ThiaConstant.MO_MESSAGE_CAHCHE_PREFIX+msisdn, 
					token, 5*60);
			
			modelAndView.addObject("thConfig",thConfig);
			SubscriberReg subscriberReg=jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn,
					vwServiceCampaignDetail.
					getProductId());
			if(subscriberReg==null||subscriberReg.getStatus()!=MConstants.SUBSCRIBED){	
				if(vwServiceCampaignDetail.getAdNetworkId()!=1){
					String url=toCgUrl
							.replace("<configid>", ""+thConfig.getId())
							.replace("<refid>", token);
					modelAndView.clear();
					modelAndView.setView(new RedirectView(url));    
				}else{
				  modelAndView.setViewName(thConfig.getLpPages());	
				} 
				  modelAndView.addObject("token",token);
				return modelAndView;  
			}else{
				logger.info("  callBackUrl:: redirect to :: "+MConstants.REDIRECT_TO_WASTE_URL_ALREADY_SUBSCRIBED);
				String portalUrl = thConfig.getPortalUrl().replaceAll("<subid>", msisdn);
				return new ModelAndView(new RedirectView(portalUrl));
			}
		}else{
			modelAndView.addObject("result",0);			
			List<THConfig> listTHConfig= ThiaConstant.listTHConfig.stream().
			filter(p->p.getProductId().intValue()==vwServiceCampaignDetail.getProductId())
			.collect(Collectors.toList());
			logger.info("HECallback::: listTHConfig::::::::: "+listTHConfig);
			modelAndView.addObject("listTHConfig",listTHConfig);			
			modelAndView.addObject("thConfig",listTHConfig.get(0));
			modelAndView.addObject("info","Msisdn is missing");
			modelAndView.setViewName(listTHConfig.get(0).getLpPages());	
			if(vwServiceCampaignDetail.getAdNetworkId()!=1){
				String url=toCgUrl
						.replace("<configid>", ""+thConfig.getId())
						.replace("<refid>", token);
				modelAndView.clear();
				modelAndView.setView(new RedirectView(url));
			}
			return modelAndView;
	    }		
		
		}catch(Exception ex){
			logger.error("callBackUrl:::: ",ex);
		}finally{
			jmsService.saveObject(thiaHeCallbackTrans);
		}	
		return modelAndView;
	}  
	
	@RequestMapping("tocg")
	public ModelAndView toCG(ModelAndView modelAndView,HttpServletRequest  request){
		
		Integer configId=MUtility.toInt(request.getParameter("configid"), 0);
		String refId=request.getParameter("refid");
		THConfig thConfig=ThiaConstant.mapIdToTHConfig.get(configId);
		String authToken=thServiceApi.getToken(thConfig, refId);
		//http://mis.etracker.cc/THwap/WAPMORequest.aspx?
		//Telcoid=1&Shortcode=1932339&Keyword=ring&refid=12345abcde&AuthToken=10100000000
		
		String url=moUrl+"?Telcoid="+thConfig.getTelcoId()+"&Shortcode="+thConfig.getShortcode()
		+"&Keyword="+thConfig.getKeyword()+"&refid="+refId+"&AuthToken="+authToken;
		logger.info("toMo:: url: "+url);
		modelAndView.setView(new RedirectView(url));
		return modelAndView;	    
	}
	

	@RequestMapping("send/renewal")
	@ResponseBody
	public String sendRenewal(ModelAndView modelAndView,HttpServletRequest  request){
//		thiaRenewalScheduler.sendAisRenewalBilled();
		return "OK";
	}
	
	

	
	@RequestMapping("send/renewal/manual")
	@ResponseBody
	public String sendRenewalByMsisdn(ModelAndView modelAndView,HttpServletRequest  request){
		
		SubscriberReg subscriberReg=jpaSubscriberReg
				.findSubscriberRegByMsisdnAndProductId(request.getParameter("msisdn"),
				4);
		 macroKioskFactoryService.sendSubscriptionRenewalRequest(subscriberReg);
		 return "ok";
	}
	
	
	@RequestMapping("check/value")
	@ResponseBody
	public String checkValue(ModelAndView modelAndView,HttpServletRequest  request){
		LocalTime now = LocalTime.now();
		boolean check=now.isAfter(renewalStartTime) && now.isBefore(renewalStopTime);
		String msisdn=request.getParameter("msisdn");
	String key=ThiaConstant.THIALAND_RETRY_BILLING_PREFIX+ThiaConstant.retryBillingDate
				+msisdn;		
		Integer counter=(Integer)redisCacheService.getObjectCacheValue(key);
		return "renewalStartTime:: "+renewalStartTime+",renewalStopTime::  "
		+renewalStopTime+", now:: "+now+" , check :: "+check+" , key:: "+key+", counter:: "+counter;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ModelAndView error(HttpServletRequest request,Exception ex){
		logger.error("error:: request path: "+request.getRequestURI()+" ,query  string: "+request.getQueryString()+", Exception:: ",ex);
		
		ErrorInfo errorInfo=new ErrorInfo();
		errorInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
		errorInfo.setQueryStr("request path:"+request.getRequestURI()+", query str ="+request.getQueryString());
		errorInfo.setErrorDesc(ex.toString());
		daoService.saveObject(errorInfo);
		return new ModelAndView("error");
	}	
	
  }

