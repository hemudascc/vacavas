package net.mycomp.intarget;

import java.sql.Timestamp;
import java.util.Objects;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPAIntargetUssdTrans;
import net.persist.bean.LiveReport;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class JMSIntargetOnResultListener implements MessageListener {

	private static final Logger logger = Logger
			.getLogger(JMSIntargetOnResultListener.class);

	

	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;

	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private SubscriberRegService subscriberRegService; 

	@Autowired
	private JPAIntargetUssdTrans jpaIntargetUssdTrans;
	
	@Autowired
	private IntargetApiService intargetApiService;
	
  
	@Override
	public void onMessage(Message m) {

		IntargetOnResultNotification intargetOnResultNotification = null;
		LiveReport liveReport = null;
		boolean update = false;
		long time = System.currentTimeMillis();
		InTargetUssdTrans inTargetUssdTrans=null;
		
		try {

			ObjectMessage objectMessage = (ObjectMessage) m;
			intargetOnResultNotification = (IntargetOnResultNotification) objectMessage
					.getObject();
			
			logger.debug("intargetOnResultNotification::::::: "+intargetOnResultNotification);
			
			if (intargetOnResultNotification.getOnResultText()
					.equalsIgnoreCase(IntargetMTMessageResultText.RECEIPTED.getStatus())) {
			
			inTargetUssdTrans=jpaIntargetUssdTrans.
					findInTargetUssdTrans(MUtility.toInt(intargetOnResultNotification.getRefNo(), 0));
			CGToken cgToken=new CGToken(inTargetUssdTrans.getTag());
			liveReport=new LiveReport(MConstants.INTARGET_SAFARICOM_OPERATOR_ID, 
							new Timestamp(System.currentTimeMillis()), 
							cgToken.getCampaignId(),inTargetUssdTrans.getServiceId(),0);
			
			liveReport.setMsisdn(inTargetUssdTrans.getMsisdn());
			VWServiceCampaignDetail vwServiceCampaignDetail=MData.mapCamapignIdToVWServiceCampaignDetail
						.get(cgToken.getCampaignId());
			InTargetConfig inTargetConfig=	IntargetConstant.mapServiceIdTpInTargetConfig.get(vwServiceCampaignDetail.getServiceId());
			
			if(inTargetUssdTrans.getAction().equalsIgnoreCase(MConstants.ACT)){
					 liveReport.setToken(cgToken.getCGToken());
					 liveReport.setTokenId(cgToken.getTokenId());
					 liveReport.setAction(MConstants.ACT);
					 liveReport.setAmount(inTargetConfig.getPricePoint());
					 liveReport.setConversionCount(1);
					 liveReport.setNoOfDays(inTargetConfig.getValidity());
					 
			String msg= IntargetConstant.getMsg(inTargetConfig.getWelcomeActivationMessage(), inTargetConfig);
			intargetApiService.sendContentSMS(inTargetConfig,
					inTargetUssdTrans.getMsisdn(),IntargetConstant.CONTENT_MSG, 
							   IntargetMessageType.CONTENT_MSG,msg,
							   "","","0");
			
			
					 
				 }else if(inTargetUssdTrans.getAction().equalsIgnoreCase(MConstants.RENEW)){
						liveReport.setAction(MConstants.RENEW);
						liveReport.setRenewalAmount(inTargetConfig.getPricePoint());
						liveReport.setRenewalCount(1);
						liveReport.setNoOfDays(inTargetConfig.getValidity());
				   }
				 }
			
		} catch (Exception ex) {
			logger.error("onMessage::::: ", ex);
			try{
			if (intargetOnResultNotification.getOnResultText()
					.equalsIgnoreCase(IntargetMTMessageResultText.RECEIPTED.getStatus())) {
			subscriberRegService.findOrCreateSubscriberByAct(
					inTargetUssdTrans.getMsisdn(), null,
					liveReport);
			}
			}catch(Exception e){
				logger.error("onMessage::::: second:: ", e);
			}
			
		} finally {
			try {
				if(liveReport.getAction()!=null){
					liveReportFactoryService.process(liveReport);
				}
			} catch (Exception ex) {
				logger.error(" fianlly liveReport:: " + liveReport
						+ ", : intargetOnResultNotification:: "
						+ intargetOnResultNotification);
				logger.error("onMessage::::::::::finally " ,ex);
			} finally {
				update = daoService.saveObject(intargetOnResultNotification);
			}
			logger.info("onMessage::::::::::::::::: :: update::live report "
					+ update + ", total time:: "
					+ (System.currentTimeMillis() - time));
		}
	}
}
