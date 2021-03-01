package net.mycomp.mobimind;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.jms.JMSService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.persist.bean.LiveReport;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MData;

@Controller
@RequestMapping("mobimind")
public class MobimindController {

	private static final Logger logger = Logger.getLogger(MobimindController.class.getName());
	
	@Value("${mobimind.default.portal.url}")
	private String defaultPortalUrl;
	
	@Autowired
	private JMSMobimindService jmsMobimindService;
	
	@Autowired
	private JMSService jmsService;
	
	@Autowired
	private SubscriberRegService subscriberRegService;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	
	@RequestMapping(value={"cgcallback"},method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView cgCallback(HttpServletRequest request,ModelAndView modelAndView){
			
			MobimindCGCallback mobimindCGCallback=new MobimindCGCallback(true);
			try{	
				//http://192.241.167.189:8080/vacavas/sys/mobimind/cgcallback?CGMSISDN=@MSISDN&CGStatus=@STATUS
				logger.info("cgCallback   "+request.getQueryString());
				mobimindCGCallback.setCgMsisdn(request.getParameter("CGMSISDN"));
				mobimindCGCallback.setCgStatus(request.getParameter("CGStatus"));
				mobimindCGCallback.setToken(request.getParameter("ref"));
				mobimindCGCallback.setQueryStr(request.getQueryString());
				CGToken cgToken=new CGToken(mobimindCGCallback.getToken());
				
				VWServiceCampaignDetail 
				vwServiceCampaignDetail=MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
				MobimindServiceConfig mobimindServiceConfig=MobimindConstant
						.mapServiceIdToMobimindServiceConfig.get(vwServiceCampaignDetail.getServiceId());
				
			   String portalUrl=mobimindServiceConfig.getPortalUrl()+mobimindCGCallback.getCgMsisdn();
			   modelAndView.setView(new RedirectView(portalUrl));
			   if(mobimindCGCallback.getCgStatus()!=null&&
					   (mobimindCGCallback.getCgStatus().equalsIgnoreCase("0")||
							   mobimindCGCallback.getCgStatus().equalsIgnoreCase("5"))){
				   LiveReport liveReport=new LiveReport(vwServiceCampaignDetail.getOpId(),
						   new Timestamp(System.currentTimeMillis()),
						   cgToken.getCampaignId()
						   ,mobimindServiceConfig.getServiceId(),
						   vwServiceCampaignDetail.getProductId());
						   liveReport.setNoOfDays(mobimindServiceConfig.getValidity());	
						   liveReport.setMsisdn(mobimindCGCallback.getCgMsisdn()); 
						   subscriberRegService.findOrCreateSubscriberByAct(mobimindCGCallback.getCgMsisdn(), 
								   null, liveReport);
						   redisCacheService.putObjectCacheValueByEvictionMinute(MobimindConstant.MOBIMIND_CACHE_PREFIX+mobimindCGCallback.getCgMsisdn(), 
								   cgToken.getCGToken(), 60*24);
			   }								
			}catch(Exception ex){
				logger.error("cgCallback ",ex);
				modelAndView.setView(new RedirectView(defaultPortalUrl));
			}finally{
				jmsService.saveObject(mobimindCGCallback);
			}			
			return modelAndView;
		}	
	
	@RequestMapping("notification")
	@ResponseBody
	public String notification(ModelAndView modelAndView,HttpServletRequest request){
		
		logger.info("notification:::::::: "+request.getQueryString());
		MobimindNotification mobimindNotification=new MobimindNotification(true);
		try{
			mobimindNotification.setUser(request.getParameter("User"));
			mobimindNotification.setPassword(request.getParameter("Password"));
			mobimindNotification.setServiceId(request.getParameter("ServiceId")!=null
					?request.getParameter("ServiceId"):(request.getParameter("ServiceID")!=null
					?request.getParameter("ServiceID"):request.getParameter("serviceid")));
			
			mobimindNotification.setNotificationStatus(request.getParameter("Status")!=null
					?request.getParameter("Status")
					:(request.getParameter("status")!=null?request.getParameter("status"):
						request.getParameter("STATUS")));
			
			mobimindNotification.setOperatorId(request.getParameter("OperatorID")!=null?request.getParameter("OperatorID")
					:request.getParameter("operatorid"));
			mobimindNotification.setMsisdn(request.getParameter("MSISDN")!=null?request.getParameter("MSISDN"):
				request.getParameter("msisdn"));
			mobimindNotification.setRequestId(request.getParameter("RequestId")!=null
					?request.getParameter("RequestId"):
						(request.getParameter("requestid")!=null?request.getParameter("requestid")
							:request.getParameter("RequestID")));
			
			mobimindNotification.setChannelId(request.getParameter("ChannelId")!=null
					?request.getParameter("ChannelId"):(request.getParameter("ChannelD")!=null
					?request.getParameter("ChannelD"):(request.getParameter("channelid")!=null?
							request.getParameter("channelid"):request.getParameter("ChannelID"))));
			
			mobimindNotification.setTrxId(request.getParameter("trxid"));
			mobimindNotification.setPrice(request.getParameter("Price")!=null?request.getParameter("Price"):request.getParameter("price"));
			mobimindNotification.setQueryStr(request.getQueryString());
		
		}catch(Exception ex){
			logger.error("notification:: ",ex);
		}finally{
			jmsMobimindService.saveMobimindNotification(mobimindNotification);
		}
		return "ok";
	}		
}
