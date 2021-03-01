package net.mycomp.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.jms.JMSService;
import net.common.service.CommonService;
import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.factory.RequestFactory;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.ErrorInfo;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.DeactivationResponse;
import net.process.request.OperatorRequestService;
import net.util.InvalidRequestException;
import net.util.JsonMapper;
import net.util.MConstants;

@Controller
public class CampaignController {
	private static final Logger logger = Logger.getLogger(CampaignController.class);

	@Autowired
	private IDaoService daoService;
	@Autowired
	private JMSService jmsService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private SubscriberRegService subscriberRegService;

	@Autowired
	private RequestFactory requestFactory;

	@Autowired
	private RedisCacheService redisCacheService;


	@Autowired
	private OperatorRequestService operatorRequestService;


	@Autowired
	private JPASubscriberReg jpaSubscriberReg;


	@PostConstruct	
	public void init(){
		logger.info("init");


	}

	@RequestMapping(value = {"sub","fitness"}, method = RequestMethod.GET)
	public ModelAndView campaign(HttpServletRequest request,ModelAndView modelAndView) throws Exception{

		long time = System.currentTimeMillis();
		AdNetworkRequestBean adNetworkRequestBean=null;
		try {
			adNetworkRequestBean= requestFactory.createRequestBean(request,null,null);
			logger.debug("campaign:: adNetworkRequestBean:: " + adNetworkRequestBean);
			long blockTime=System.currentTimeMillis();
			boolean block=operatorRequestService.checkBlocking(adNetworkRequestBean);
			logger.debug("campapaign::  block :: Total Processs Time:: "+(System.currentTimeMillis()-blockTime));
			if(block){

				if(adNetworkRequestBean.adnetworkToken.getRedirectToUrl()!=null){				
					return new ModelAndView(new RedirectView(adNetworkRequestBean.adnetworkToken.getRedirectToUrl()));
				}

				return new ModelAndView("redirect:" +commonService.getWasteUrl(adNetworkRequestBean));
			}

			boolean isSubscribed=operatorRequestService.isSubscribed(adNetworkRequestBean);
			if(isSubscribed){
				request.getSession().setMaxInactiveInterval(12000);
				request.getSession().setAttribute("msisdn", adNetworkRequestBean.getMsisdn());	
				return new ModelAndView("redirect:" +adNetworkRequestBean.adnetworkToken.getRedirectToUrl());
			}

			operatorRequestService.processBilling(modelAndView, adNetworkRequestBean);
		} catch (Exception ex) {
			logger.error("campaign:: exception  query string: "+request.getQueryString(),ex);
			adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_ERROR);
			return new ModelAndView("redirect:" +commonService.getWasteUrl(adNetworkRequestBean));
		} finally {
			if(adNetworkRequestBean==null||adNetworkRequestBean.adnetworkToken==null){
				throw new InvalidRequestException("Invalide request");
			}
			redisCacheService.putIntValue(adNetworkRequestBean.adnetworkToken.getMsisdn(), 1);
			redisCacheService.putIntValue(adNetworkRequestBean.adnetworkToken.getSource(),1);

			boolean isAdnetworkTokenSendToJMS=false;			
			if(adNetworkRequestBean!=null&&
					adNetworkRequestBean.adnetworkToken!=null&&adNetworkRequestBean.vwserviceCampaignDetail!=null){
				adNetworkRequestBean.adnetworkToken.setType(adNetworkRequestBean.vwserviceCampaignDetail.getServiceName());
			}	

			isAdnetworkTokenSendToJMS = jmsService.saveAdnetworkToken(adNetworkRequestBean.adnetworkToken);			
			logger.debug("Total Processs Time:: " + (System.currentTimeMillis() - time)
					+ ", isAdnetworkTokenSendToJMS:: " + isAdnetworkTokenSendToJMS);
		}
		return modelAndView;
	}

	@RequestMapping(value = {"dct"}, method = RequestMethod.GET)
	@ResponseBody
	public String deactivation(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView,
			@RequestParam(name="msisdn",required=false)String msisdn,
			@RequestParam(name="subid",defaultValue="0",required=false)int subId,
			@RequestParam(name="productid",defaultValue="0",required=false)int productId,
			@RequestParam(name="callback",defaultValue="",required=false)String callback) {

		response.setHeader("Content-Type", "application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		SubscriberReg subscriberReg=null;

		if(subId==0){
			if(productId!=0){
				subscriberReg= jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, productId);
			}else{
				List<SubscriberReg> subscriberRegList= jpaSubscriberReg.findSubscriberRegByMsisdn(msisdn);
				if(subscriberRegList!=null&&subscriberRegList.size()>0){
					subscriberReg= subscriberRegList.get(0);
				}
			}
		}else{
			subscriberReg= jpaSubscriberReg.findSubscriberRegById(subId);
		}
		DeactivationResponse deactivationResponse= operatorRequestService.deactivation(modelAndView, subscriberReg);

		if(deactivationResponse==null){
			deactivationResponse=new DeactivationResponse();
			deactivationResponse.setStatus(false);
			deactivationResponse.setMessgae("Error");
		}

		logger.info("deactivation:::msisdn::"+msisdn+" deactivationResponse:: "+deactivationResponse);

		if(deactivationResponse.isStatus()){
			subscriberRegService.updateDeactivation(subscriberReg.getMsisdn(),subscriberReg.getProductId());
		}	
		String json="";
		if(callback.equalsIgnoreCase("")){
			json=JsonMapper.getObjectToJson(deactivationResponse);
		}else{
			json=callback+"("+JsonMapper.getObjectToJson(deactivationResponse)+")";
		}
		return json;
	}

	@RequestMapping(value = {"subdetail"}, method = RequestMethod.GET)
	@ResponseBody
	public Object subdetail(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView,
			@RequestParam(name="msisdn",required=false)String msisdn,
			@RequestParam(name="opid",defaultValue="0",required=false)int opId,
			@RequestParam(name="subid",defaultValue="0",required=false)int subId,
			@RequestParam(name="productid",defaultValue="0",required=false)int productId
			) {
		try{				
			return  JsonMapper.getObjectToJson(jpaSubscriberReg.findSubscriberRegById(subId));

		}catch(Exception ex){
			logger.error("checkSub:: ",ex);

		}
		return MConstants.INACTIVE;
	}


	@RequestMapping(value = {"checksub"}, method = RequestMethod.GET)
	@ResponseBody
	public String checkSub(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView,
			@RequestParam(name="msisdn",required=false)String msisdn,
			@RequestParam(name="opid",defaultValue="0",required=false)int opId,
			@RequestParam(name="subid",defaultValue="0",required=false)int subId,
			@RequestParam(name="productid",defaultValue="0",required=false)int productId
			) {
		try{	
			if("vacatest".equals(msisdn)){
				return MConstants.ACTIVE;
			}
			SubscriberReg subscriberReg=null;
			if(subId==0){
				subscriberReg= jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn, productId);
			}else{
				subscriberReg= jpaSubscriberReg.findSubscriberRegById(subId);
			}
			if(Objects.nonNull(subscriberReg)) {
				productId=subscriberReg.getProductId();
				opId=subscriberReg.getOperatorId();
				msisdn=subscriberReg.getMsisdn();
				return  operatorRequestService.checkSub(productId,opId,msisdn)?MConstants.ACTIVE:MConstants.INACTIVE;
			}
		}catch(Exception ex){
			logger.error("checkSub:: ",ex);			
		}
		return MConstants.INACTIVE;
	}


	//	@RequestMapping(value = {"otp"}, method = RequestMethod.GET)
	//	@ResponseBody
	//	public String otp(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView,
	//			@RequestParam(name="msisdn",required=false)String msisdn,
	//			@RequestParam(name="serviceid",required=false)Integer serviceId,
	//			@RequestParam(name="opid",defaultValue=""+MConstants.ETISALAT_OPERATOR_ID,required=false)Integer opId,
	//			@RequestParam(name="callback",defaultValue="",required=false)String callback) {
	//		
	//		response.setHeader("Content-Type", "application/json");
	//	    
	//		IOtp otp= operatorRequestService.
	//				sendOtp(modelAndView, msisdn,opId , serviceId);		
	//		String json="";
	//		if(callback.equalsIgnoreCase("")){
	//			 json=JsonMapper.getObjectToJson(otp);
	//		}else{
	//		 json=callback+"("+JsonMapper.getObjectToJson(otp)+")";
	//		}
	//		return json;
	//	}


	@RequestMapping(value = {"send/otp"}, method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> sendOtp(HttpServletRequest request,ModelAndView modelAndView) throws Exception{
		//param1=ip consider device ip 
		long time = System.currentTimeMillis();
		AdNetworkRequestBean adNetworkRequestBean=null;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("STATUS", "FAILED");
		map.put("msg", "Pin not send");
		try {
			adNetworkRequestBean= requestFactory.createRequestBean(request,null,null);
			logger.debug("sendOtp::campaign:: adNetworkRequestBean:: " + adNetworkRequestBean);
			long blockTime=System.currentTimeMillis();
			boolean block=operatorRequestService.checkBlocking(adNetworkRequestBean);
			logger.debug("sendOtp:::campapaign::  block :: 	 Processs Time:: "+(System.currentTimeMillis()-blockTime));
			if(block){
				map.put("msg", "Blocked");
				return map;
			}

			boolean isSubscribed=operatorRequestService.isSubscribed(adNetworkRequestBean);
			if(isSubscribed){
				map.put("msg", "Already Subscribed");
				return map;
			}			
			boolean success=operatorRequestService.sendOtp(modelAndView, adNetworkRequestBean);//processBilling(modelAndView, adNetworkRequestBean);
			if(success){
				map.put("STATUS","SUCCESS");
				map.put("msg","Success");
				return map;
			}
		} catch (Exception ex) {
			logger.error("sendOtp::campaign:: exception  query string: "+request.getQueryString(),ex);
			adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_ERROR);
			map.put("msg", "Error");
			return map;
		} finally {


			boolean isAdnetworkTokenSendToJMS=false;			
			if(adNetworkRequestBean!=null&&
					adNetworkRequestBean.adnetworkToken!=null&&adNetworkRequestBean.vwserviceCampaignDetail!=null){
				adNetworkRequestBean.adnetworkToken.setType(adNetworkRequestBean.vwserviceCampaignDetail.getServiceName());
			}	

			isAdnetworkTokenSendToJMS = jmsService.saveAdnetworkToken(adNetworkRequestBean.adnetworkToken);			
			logger.debug("sendOtp::Total Processs Time:: " + (System.currentTimeMillis() - time)
					+ ", isAdnetworkTokenSendToJMS:: " + isAdnetworkTokenSendToJMS);
		}
		return map;
	}


	@RequestMapping(value = {"validate/otp"}, method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object>  validateOtp(HttpServletRequest request,ModelAndView modelAndView) throws Exception{
		//param1=pin,param2=ip
		long time = System.currentTimeMillis();
		AdNetworkRequestBean adNetworkRequestBean=null;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("STATUS","FAIL");
		try {
			adNetworkRequestBean= requestFactory.createRequestBean(request,null,null);
			logger.debug("validateOtp::campaign:: adNetworkRequestBean:: " + adNetworkRequestBean);
			long blockTime=System.currentTimeMillis();
			boolean block=operatorRequestService.checkBlocking(adNetworkRequestBean);
			logger.debug("validateOtp::campapaign::  block :: Total Processs Time:: "+(System.currentTimeMillis()-blockTime));
			if(block){
				map.put("msg", "Blocked");
				return map;
			}

			boolean isSubscribed=operatorRequestService.isSubscribed(adNetworkRequestBean);
			if(isSubscribed){
				map.put("msg", "Already Subscribed");
				return map;

			}			
			boolean success=operatorRequestService.validateOtp(modelAndView,adNetworkRequestBean);//sendOtp(modelAndView, adNetworkRequestBean);//processBilling(modelAndView, adNetworkRequestBean);
			if(success){
				map.put("STATUS","SUCCESS");
				map.put("msg","Success");
				return map;
			}
		} catch (Exception ex) {
			logger.error("validateOtp::campaign:: exception  query string: "+request.getQueryString(),ex);
			adNetworkRequestBean.adnetworkToken.setAction(MConstants.REDIRECT_TO_ERROR);
			map.put("msg", "Error");
			return map;
		} finally {		

			boolean isAdnetworkTokenSendToJMS=false;			
			if(adNetworkRequestBean!=null&&
					adNetworkRequestBean.adnetworkToken!=null&&adNetworkRequestBean.vwserviceCampaignDetail!=null){
				adNetworkRequestBean.adnetworkToken.setType(adNetworkRequestBean.vwserviceCampaignDetail.getServiceName());
			}	
			isAdnetworkTokenSendToJMS = jmsService.saveAdnetworkToken(adNetworkRequestBean.adnetworkToken);			
			logger.debug("validateOtp::Total Processs Time:: " + (System.currentTimeMillis() - time)
					+ ", isAdnetworkTokenSendToJMS:: " + isAdnetworkTokenSendToJMS);
		}
		return map;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ModelAndView error(HttpServletRequest request,Exception ex){
		try{
			logger.error("error:: query  string: "+request.getQueryString()+", Exception:: ", ex);
			ErrorInfo errorInfo=new ErrorInfo();
			errorInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
			errorInfo.setQueryStr(this.getClass().getName()+", query str ="+request.getQueryString());
			errorInfo.setErrorDesc(ex.toString());
			daoService.saveObject(errorInfo);
			//return new ModelAndView("redirect:" +commonService.getWasteUrl());
			return new ModelAndView("error");
		}catch(Exception e){
			logger.error("error",e);
		}
		return new ModelAndView("error" );
	}


}


