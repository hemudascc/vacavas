package net.mycomp.intarget;

import java.sql.Timestamp;
import java.util.Objects;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPAIntargetUssdTrans;
import net.persist.bean.LiveReport;
import net.util.MConstants;

public class JMSIntargetOnReceiveSMSListener implements MessageListener {

	private static final Logger logger = Logger
			.getLogger(JMSIntargetOnReceiveSMSListener.class);

	
	
	@Value("${intarger.default.portal.url}")
	private String portalUrl;

	@Autowired
	private IntargetApiService intargetApiService;
	
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
	
	 
	
	@Override
	public void onMessage(Message m) {

		IntargetOnReceiveSMSNotification intargetOnReceiveSMSNotification = null;
		LiveReport liveReport = null;
		boolean update = false;
		long time = System.currentTimeMillis();
		try {

			ObjectMessage objectMessage = (ObjectMessage) m;
			intargetOnReceiveSMSNotification = (IntargetOnReceiveSMSNotification) objectMessage
					.getObject();
			
			logger.debug("intargetOnReceiveSMSNotification::::::: "+intargetOnReceiveSMSNotification);
			InTargetConfig inTargetConfig=IntargetConstant.
					mapServiceCodeToInTargetConfig.
					get(intargetOnReceiveSMSNotification.getServiceCode());
			
			String action=IntargetConstant.
					getAction(intargetOnReceiveSMSNotification.getText(), inTargetConfig);
			 
			if(action.equalsIgnoreCase(MConstants.DCT)){
				liveReport=new LiveReport(MConstants.INTARGET_SAFARICOM_OPERATOR_ID, 
						new Timestamp(System.currentTimeMillis()), 
						-1,inTargetConfig.getServiceId(),0);
				liveReport.setAction(MConstants.DCT);
				liveReport.setMsisdn(intargetOnReceiveSMSNotification.getFromAddr());
				liveReport.setCircleId(0);
				liveReport.setDctCount(1);
			   liveReportFactoryService.process(liveReport);
			   
				String msg=inTargetConfig.getDeactivationMessage().replaceAll("<servicename>",
						inTargetConfig.getServcieName());
			   
				intargetApiService.sendContentSMS(inTargetConfig,
					   intargetOnReceiveSMSNotification.getFromAddr(),IntargetConstant.CONTENT_MSG, 
					   IntargetMessageType.CONTENT_MSG,msg,
					   "","","0");
				
				
				//String subService,String chargeAddr,String description, String adultRating
			   
			}else if(action.equalsIgnoreCase(MConstants.ACT)){				
			
			String msg=IntargetConstant.getMsg(inTargetConfig.getBillingActivationmessage(), inTargetConfig);
			
			String token=Objects.toString(redisCacheService.getObjectCacheValue(
					IntargetConstant.INTRAGET_TRX_PREFIX
					+intargetOnReceiveSMSNotification.getFromAddr()));
			
			intargetApiService.sendSMS(inTargetConfig,
				   intargetOnReceiveSMSNotification.getFromAddr(),MConstants.ACT, 
				   IntargetMessageType.BILLED_MSG,inTargetConfig.getPricePointCode(),msg,
				   token);
			
			}
			
		} catch (Exception ex) {
			logger.error("onMessage::::: ", ex);
		} finally {
			try {
				
			} catch (Exception ex) {
				logger.error(" fianlly liveReport:: " + liveReport
						+ ", : intargetOnReceiveSMSNotification:: "
						+ intargetOnReceiveSMSNotification);
				logger.error("onMessage::::::::::finally " ,ex);
			} finally {
				update = daoService.saveObject(intargetOnReceiveSMSNotification);
			}
			
			logger.info("onMessage::::::::::::::::: :: update::live report "
					+ update + ", total time:: "
					+ (System.currentTimeMillis() - time));
		}
	}
}
