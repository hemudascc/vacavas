package net.mycomp.oredoo.kuwait;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.common.jms.JMSService;
import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPAOredooCGNotification;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.JsonMapper;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.eclipse.persistence.sessions.coordination.ServiceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("ord")
public class OredoKuwaitController {

	private static final Logger logger = Logger
			.getLogger(OredoKuwaitController.class.getName());
	
	@Autowired
	private OredooKuwaitSubscriptionService oredooKuwaitSubscriptionService;
	
	@Autowired
	private JMSOredooKuwaitService jmsOredooKuwaitService;
	
	@Autowired
	private JPAOredooCGNotification jpaOredooCGNotification;
	
	@Autowired
	private SubscriberRegService subscriberRegService;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private JMSService jmsService;
	
	@Autowired
	private IDaoService daoService;
	
//	private final String portalUrl;
	
	private final String msisdnHeaderUrl;
	
	private final String  cgUrl;
	
	@Autowired
	public OredoKuwaitController(@Value("${oredo.kuwait.msisdn.header.url}") 
	                            String msisdnHeaderUrl,
	                           // @Value("${oredoo.portal.url}") String portalUrl,
	                            @Value("${oredoo.kuwait.cg.url}") String cgUrl){
		this.msisdnHeaderUrl=msisdnHeaderUrl;
		//this.portalUrl=portalUrl;
		this.cgUrl=cgUrl;
	}
	
	@RequestMapping("cmp")	
	public ModelAndView findMsisdnHeader(HttpServletRequest request,ModelAndView modelAndView){
		 
		String encodedQueryString=MUtility.getBase64EncodedString(request.getQueryString());	
		String headerUrl=msisdnHeaderUrl.replaceAll("<query>", encodedQueryString);		
		//modelAndView.addObject("msisdnHeaderUrl", headerUrl);
		logger.info("findMsisdnHeader::::::: "+headerUrl+" , query string: "+request.getQueryString());
		modelAndView.setView(new RedirectView(headerUrl));
		return modelAndView;
	}
	
	@RequestMapping("cgcallback")
	public ModelAndView cgCalback(ModelAndView modelAndView,HttpServletRequest request){
		logger.error("cgCalback:::::::: "+request.getQueryString());
		OredooKuwaitCGCallback oredooKuwaitCGCallback=new OredooKuwaitCGCallback();
		String portalUrl="";
		try{
		oredooKuwaitCGCallback.setMsisdn(OredoKuwaitConstant.formatMsisdnAdd965(request.getParameter("MSISDN")));
		oredooKuwaitCGCallback.setResult(request.getParameter("Result"));
		oredooKuwaitCGCallback.setReason(request.getParameter("Reason"));
		oredooKuwaitCGCallback.setProductId(request.getParameter("productId"));
		oredooKuwaitCGCallback.setTransId(request.getParameter("transID"));
		oredooKuwaitCGCallback.setTpcgId(request.getParameter("TPCGID"));
		oredooKuwaitCGCallback.setSongName(request.getParameter("Sonngname"));
		oredooKuwaitCGCallback.setQueryStr(request.getQueryString());
		
		
		 OredooKuwaitCGToken cgToken=new OredooKuwaitCGToken(oredooKuwaitCGCallback.getTransId()); 
		  AdnetworkToken adnetworkToken=daoService.findAdnetworkTokenById(cgToken.getTokenId());
		 
		 VWServiceCampaignDetail vwServiceCampaignDetail=
				 MData.mapCamapignIdToVWServiceCampaignDetail.get(adnetworkToken.getCampaignId());
		 OredooKuwaitServiceConfig oredooKuwaitServiceConfig=OredoKuwaitConstant.
				 mapServiceIdToOredooKuwaitServiceConfig.get(vwServiceCampaignDetail.getServiceId());
		 if(oredooKuwaitCGCallback.getResult()!=null
				 &&oredooKuwaitCGCallback.getResult().equalsIgnoreCase("SUCCESS"))
				// &&vwServiceCampaignDetail.getProductId()==25)
			 {
			 
			 LiveReport liveReport=new LiveReport(vwServiceCampaignDetail.getOpId(),
					 oredooKuwaitCGCallback.getCreateTime(), vwServiceCampaignDetail.getCampaignId(),
					 vwServiceCampaignDetail.getServiceId(), vwServiceCampaignDetail.getProductId());
			 liveReport.setNoOfDays(0);
			 SubscriberReg subscriberReg= subscriberRegService
					 .findOrCreateSubscriberByAct(oredooKuwaitCGCallback.getMsisdn(),
					 adnetworkToken, liveReport);
			 portalUrl=OredoKuwaitConstant.getPortalUrl(
					 oredooKuwaitServiceConfig.getPortalUrl(),"",subscriberReg.getSubscriberId());
			 modelAndView.setView(new RedirectView(portalUrl));
				modelAndView.addObject("portalUrl", portalUrl)  ;
				//modelAndView.setViewName("google_success_all");
				modelAndView.setViewName("oredoo/kuwait/success");
			 return modelAndView;
		 }
		 portalUrl=
				 OredoKuwaitConstant.getPortalUrl(
						 oredooKuwaitServiceConfig.getPortalUrl(),oredooKuwaitCGCallback.getMsisdn(),0);
		 modelAndView.setView(new RedirectView(portalUrl));
		//modelAndView.setView(new RedirectView(portalUrl+"?msisdn="+oredooKuwaitCGCallback.getMsisdn()));
		}catch(Exception ex){
			logger.error("exception ",ex);
		}finally{
			oredooKuwaitCGCallback.setRedirectTo(portalUrl);
			jmsOredooKuwaitService.saveOredooKuwaitCGCallback(oredooKuwaitCGCallback);	
		}
		return modelAndView;
	}

	@RequestMapping("cgnotification")
	@ResponseBody	
	public String cgNotificaton(HttpServletRequest request){
		
		logger.error("cgNotificaton:::::::: "+request.getQueryString());
		OredooKuwaitCGNotification oredooKuwaitCGNotification=new OredooKuwaitCGNotification();
		oredooKuwaitCGNotification.setMsisdn(OredoKuwaitConstant.formatMsisdnAdd965(
				request.getParameter("callingParty")));
		oredooKuwaitCGNotification.setRequestPlan(request.getParameter("requestPlan"));
		oredooKuwaitCGNotification.setComvivaServiceId(request.getParameter("serviceId"));
		oredooKuwaitCGNotification.setSequenceNo(request.getParameter("sequenceNo"));
		oredooKuwaitCGNotification.setChargeAmount(MUtility.toDouble(request.getParameter("chargeAmount"),0));
		oredooKuwaitCGNotification.setValidityDays(MUtility.toInt(request.getParameter("validityDays"),0));
		oredooKuwaitCGNotification.setOperationId(request.getParameter("operationId"));
		oredooKuwaitCGNotification.setBearerId(request.getParameter("bearerId"));
		oredooKuwaitCGNotification.setShortcode(request.getParameter("shortcode"));
		oredooKuwaitCGNotification.setCreateddate(request.getParameter("createddate"));
		oredooKuwaitCGNotification.setSmsKey(request.getParameter("key"));
		oredooKuwaitCGNotification.setErrorCode(request.getParameter("errorCode"));
		oredooKuwaitCGNotification.setResult(request.getParameter("result"));
		oredooKuwaitCGNotification.setQueryStr(request.getQueryString());
		jmsOredooKuwaitService.saveOredooKuwaitCGNotification(oredooKuwaitCGNotification);
		return "OK";
	}
	
	@ResponseBody
	@RequestMapping("chargeable")
	public String isMsisdnChargeable(HttpServletResponse response,
			@RequestParam(name="msisdn") String msisdn,
			@RequestParam(name="serviceid",defaultValue="0",required=false)Integer serviceId){
		logger.info("isMsisdnChargeable:: "+msisdn);
		SubType subType=new SubType();
		response.setHeader("Content-Type", "application/json");
		boolean isSubscribed=subscriberRegService.isSubscribed(msisdn);
		subType.setSubscribed(isSubscribed);
		OredooKuwaitServiceConfig oredooKuwaitServiceConfig=OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig.get(serviceId);
		OCSResponse ocsResponse=oredooKuwaitSubscriptionService.checkSubs(msisdn,oredooKuwaitServiceConfig);
		int finalServiceId=OredoKuwaitConstant.findServiceId(ocsResponse.getSubsType(), serviceId
				,oredooKuwaitServiceConfig.getCcProductId());
		if(finalServiceId>0){
			subType.setChargeable(true);			
			 oredooKuwaitServiceConfig=OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig.get(finalServiceId);
			subType.setOredooKuwaitServiceConfig(oredooKuwaitServiceConfig);
			subType.setMsisdn(OredoKuwaitConstant.formatMsisdnRemove965(msisdn));
		}
		logger.info("isMsisdnChargeable:: "+subType);
		String json=JsonMapper.getObjectToJson(subType);
		return json;
	}	
	
	@RequestMapping("sendmt")
	public String sendMTMessage(@RequestParam(name="id")Integer id){		
		OredooKuwaitCGNotification oredooKuwaitCGNotification=jpaOredooCGNotification.findOredooKuwaitCGNotificationById(id);
		return "OK";
	}
	
	@RequestMapping("cg")
	public ModelAndView toCg(HttpServletRequest request,ModelAndView modelAndView,
			@RequestParam(name="serviceid",defaultValue="0",required=false)Integer serviceId){		
		OoredooKuwaitCGTrans ooredooKuwaitCGTrans=new OoredooKuwaitCGTrans(true);
		try{
		Enumeration<String> en = request.getHeaderNames();
		StringBuilder headersStr = new StringBuilder();
		Map<String, String> headerMap = new HashMap<String, String>();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			headersStr.append(key + "=" + request.getHeader(key) + " ,");
			headerMap.put(key, request.getHeader(key));
		}	
		String transId=request.getParameter("transID");
		String token=request.getParameter("token");
		
		ooredooKuwaitCGTrans.setIp(request.getRemoteAddr());
		ooredooKuwaitCGTrans.setxForwardedFor(headerMap.get("x-forwarded-for"));
	    
	     ooredooKuwaitCGTrans.setMsisdn(request.getParameter("MSISDN"));
	     OredooKuwaitCGToken oredooKuwaitCGToken=new OredooKuwaitCGToken(transId);
	     ooredooKuwaitCGTrans.setTokenId(oredooKuwaitCGToken.getTokenId());
	     
		CGToken cgToken=new CGToken(token);
		redisCacheService.putIntValue(OredoKuwaitConstant.OREDOO_KUWAIT_CG_SERVICE_ID_PREFIX+transId,
				serviceId, 120);
		
		OredooKuwaitServiceConfig oredooKuwaitServiceConfig=
				OredoKuwaitConstant.mapServiceIdToOredooKuwaitServiceConfig.get(serviceId);		
		
		String queryStr="MSISDN="+request.getParameter("MSISDN")
				+"&productID="+request.getParameter("productID")
				+"&pName="+request.getParameter("pName")+""
				+ "&pPrice="+request.getParameter("pPrice")
				 +"&pVal="+request.getParameter("pVal")
				 +"&CpId="+request.getParameter("CpId")+
				 "&CpPwd="+request.getParameter("CpPwd")+""
				+ "&CpName="+request.getParameter("CpName")
				+"&ismID="+request.getParameter("ismID")+""
				+ "&transID="+request.getParameter("transID")
				+"&reqMode="+request.getParameter("reqMode")
				+"&reqType="+request.getParameter("reqType")
				 +"&cpBgColor="+request.getParameter("cpBgColor")
				 +"&sRenewalPrice="+request.getParameter("sRenewalPrice")
				 +"&sRenewalValidity="+request.getParameter("sRenewalValidity");
		      if(cgToken.getCampaignId()==170){
		    	  queryStr+="&Wap_mdata="+MUtility.urlEncoding(
		    			  "http://192.241.167.189:8080/vacavas/sys/resources/ooredoo/%22+onerror=%22return+SubmitForm%28%29%3B%22+alt=%22gameshop.png");
		      }else{
		    	  queryStr+="&Wap_mdata="+request.getParameter("Wap_mdata");
		      }
		      ooredooKuwaitCGTrans.setRequestData(queryStr);
		String url=cgUrl+"?"+
				oredooKuwaitSubscriptionService.encryptCGQueryStr(oredooKuwaitServiceConfig,
						queryStr);
		ooredooKuwaitCGTrans.setRequestEncyptData(url);
		logger.info("toCg:::::::ip: "+request.getRemoteAddr()+" , ::::headerMap:: "+headerMap+"::: "
				+"request param:: "+request.getQueryString()
				+" , queryStr:: "+queryStr
				+", tocg url: "+url);
		
		modelAndView.setView(new RedirectView(url));
		}catch(Exception ex){
			logger.error("exception :: ",ex);
		}finally{
			jmsService.saveObject(ooredooKuwaitCGTrans);
		}
		return modelAndView;
	}
	
	
	@RequestMapping("report")	
	public ModelAndView report(ModelAndView modelAndView){		
		
		Map<String,List<LiveReport>> map=daoService.findOredooKuwaitCGNotificationReport(null, null);
		List<LiveReport> listCurrentDateReportList=map.get("currentDateReportList");
		List<LiveReport> listOredooReport=map.get("oredooReport");
		Map<String,LiveReport> mapReport=listCurrentDateReportList.stream().
		collect(Collectors.toMap(LiveReport::getReportDateStr, a ->a));
		Iterator<String> itr=mapReport.keySet().iterator();
		Map<Double,Integer> amountType=new HashMap<Double,Integer>();
		while(itr.hasNext()){
		   LiveReport liveReport=mapReport.get(itr.next());
		   for(LiveReport l: listOredooReport){
			   if(l.getReportDateStr().equalsIgnoreCase(liveReport.getReportDateStr())){
				   if(liveReport.getMapAmountCount()==null){
					   liveReport.setMapAmountCount(new HashMap<Double,Integer>());
				   }
				   liveReport.getMapAmountCount().put(l.getAmount(), l.getConversionCount());
				   amountType.put(l.getAmount(), l.getConversionCount());
			   }
		   }
		}		
		modelAndView.addObject("mapReport", new TreeMap<String,LiveReport>(mapReport));
		modelAndView.addObject("amountType", amountType);
		modelAndView.setViewName("oredoo_adv_report");
		return modelAndView;
	}
	
	
	@RequestMapping("/cache")
	@ResponseBody
	public String checkKey(@RequestParam("key")String key){
		String str=key;
		try {
			str+=", value: "+redisCacheService.getObjectCacheValue(key);
		} catch (Exception e) {
			
			str=e.toString();
		}		
		return str;
		
	}
	
	
	@RequestMapping(value="/checksub", produces = { MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public OCSResponse checkSub(@RequestParam("msisdn")String msisdn){
		
		String str="";
		OCSResponse  ocsResponse=null;
		try {
			OredooKuwaitServiceConfig oredooKuwaitServiceConfig=OredoKuwaitConstant
					.mapServiceIdToOredooKuwaitServiceConfig.get(23);
			  ocsResponse=oredooKuwaitSubscriptionService.checkSubs(msisdn, oredooKuwaitServiceConfig);
		} catch (Exception e) {
			
			str=e.toString();
		}		
		return ocsResponse;
	}
	@RequestMapping(value="/pub/checksub", produces = { MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public String checkSubPublisher(@RequestParam("msisdn")String msisdn){
		String str="";
		try {
			OredooKuwaitServiceConfig oredooKuwaitServiceConfig=OredoKuwaitConstant
					.mapServiceIdToOredooKuwaitServiceConfig.get(23);
			str=oredooKuwaitSubscriptionService.checkSubsToPublisher(msisdn, oredooKuwaitServiceConfig);
		} catch (Exception e) {
			str=e.toString();
		}
		return str;
	}
}
