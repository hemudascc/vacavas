package net.mycomp.macrokiosk.thailand;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MConstants;
import net.util.MData;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class JMSTHMOMessageListener implements MessageListener {
	private static final Logger logger = Logger.getLogger(JMSTHMOMessageListener.class);

	@Autowired
	private MacroKioskFactoryService macroKioskFactoryService;
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private SubscriberRegService subscriberRegService;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;
	
	@Override
	public void onMessage(Message m) {
		
		long time = System.currentTimeMillis();
		logger.info("onMessage::::::::::::::::: " + m);
		ObjectMessage objectMessage = (ObjectMessage) m;
		boolean update = false;		
		THMOMessage moMessage=null;
		LiveReport liveReport=null;
		THConfig thConfig=null;
		try {	
			moMessage=(THMOMessage)objectMessage.getObject();
			logger.info("onMessage:::::::: "+moMessage);
		//	(int operatorId, Timestamp timestamp, Integer adnetworkCampaignId,int serviceId)
			String refId=moMessage.getRefId();
			if(refId==null){
			refId=(String)redisCacheService.
			getObjectCacheValue(ThiaConstant.MO_MESSAGE_CAHCHE_PREFIX+moMessage.getMsisdn());
			moMessage.setRefId(refId);
			}
			logger.info("onMessage 222:::::::: "+moMessage);
			CGToken cgToken=new CGToken(refId);
			
			if(cgToken.getCampaignId()==MConstants.DEFAULT_ADNETWORK_CAMPAIGN_ID){
				thConfig=MacroKioskFactoryService.
						findThConfigByKeyWordAndTelcoId(moMessage.getKeyword().toUpperCase(),
						  moMessage.getTelcoid());
				liveReport=new LiveReport(moMessage.getOpId(),
						moMessage.getCreateTime(), 0,
						thConfig.getServiceId(),0);
			}else{
				VWServiceCampaignDetail vwServiceCampaignDetail=MData.
						mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
				thConfig=	ThiaConstant.mapServiceIdToTHConfig.get(vwServiceCampaignDetail.getServiceId());
				liveReport=new LiveReport(moMessage.getOpId(),
						moMessage.getCreateTime(), 0,
						thConfig.getServiceId(),0);
			}
			logger.info("onMessage::333:::::: "+moMessage);
			liveReport.setAdnetworkCampaignId(cgToken.getCampaignId());
			liveReport.setToken(cgToken.getCGToken());
			liveReport.setTokenId(cgToken.getTokenId());
			liveReport.setProductId(thConfig.getProductId());
			liveReport.setMsisdn(moMessage.getMsisdn());
			moMessage.setTokenId(liveReport.getTokenId());
			moMessage.setToken(liveReport.getToken());
	    	moMessage.setCampaignId(liveReport.getAdnetworkCampaignId());
	    	logger.info("onMessage::444:::::: "+moMessage);
	    	//logger.info("onMessage::444:::::: "+moMessage+", get mode:: "+ThialandSubModeEnum.getMode(moMessage.getChannel()));
	    	//liveReport.setMode(ThialandSubModeEnum.getMode(moMessage.getChannel()));	
	    	if(moMessage.getChannel()!=null){
	    		
	    	if(moMessage.getChannel().equalsIgnoreCase("0")){	    		
	    		liveReport.setMode("SMS");
	    	}else if(moMessage.getChannel().equalsIgnoreCase("1")){
	    		liveReport.setMode("IVR");
	    	}else if(moMessage.getChannel().equalsIgnoreCase("2")){
	    		liveReport.setMode("WAP");	
	    	}
	    	}
	    	logger.info("onMessage::555:::::: "+moMessage);
	    	
	    	if(moMessage.getText().toUpperCase().contains("STOP")){
	    		
	    		logger.info("onMessage::666:::::: "+moMessage);
				liveReport.setAction(MConstants.DCT);	
				liveReport.setDctCount(1);
			}else{
				logger.info("onMessage::777:::::: "+moMessage);
			boolean response=  macroKioskFactoryService.handleSubscriptionMOMessage(moMessage);
			  logger.info("onMessage:::::::: "+moMessage+" ,handleSubscriptionMOMessage::  "+response);
			  liveReport.setNoOfDays(1);
			  liveReport.setConversionCount(1);
			  SubscriberReg subscriberReg = subscriberRegService.findOrCreateSubscriberByAct(
					  liveReport.getMsisdn(),null, liveReport);	
			   liveReport.setAction(MConstants.ACT);
			 }
			
		} catch (Exception e) {
			logger.error("onMessage:::::::::::::::::"  +moMessage+ " , Exception  " , e);
		}finally{ 
			try{
				
				if (liveReport!=null&&liveReport.getAction() != null) {
					liveReport = liveReportFactoryService.process(liveReport);					
				}
		    	moMessage.setAction(liveReport.getAction());		    	
			}catch(Exception ex){
				logger.error("onMessage:::::::::::::::::"  +moMessage+ " , Exception  " , ex);
			}finally{
			update=daoService.updateObject(moMessage);
			}
		}	
		logger.info("onMessage::::::::::::::::: :: update:: " + update+ ", total time:: " + (System.currentTimeMillis() - time));
	}	
}
