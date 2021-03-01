package net.mycomp.mondiapay;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.process.bean.CGToken;
import net.util.MConstants;

public class JMSMondiaPayNotificationListener implements MessageListener{

	private static final Logger logger = Logger.getLogger(JMSMondiaPayNotificationListener.class);
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;
	@Autowired
	private IDaoService daoService;
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	@Autowired
	@Qualifier("jmsMondiaPayService")
	private JMSMondiaPayService jmsMondiaPayService;

	@Override
	public void onMessage(Message message) {
		MondiaPayNotification mondiaPayNotification = null;
		LiveReport liveReport=null;
		String token=null;
		long time = System.currentTimeMillis();
		boolean save = false;
		String action=null;
		CGToken cgToken = null;
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			mondiaPayNotification = (MondiaPayNotification)objectMessage.getObject();
			logger.debug("processing MondiaPayNotification"+mondiaPayNotification);
			List<SubscriberReg> subscriberRegs = jpaSubscriberReg.findSubscriberRegByParam1(Objects.toString(mondiaPayNotification.getSubscriptionId()));
			if(subscriberRegs.size()<1) {
				Thread.sleep(5000L);
				jmsMondiaPayService.saveMondiaPayNotification(mondiaPayNotification);
			}else {
				token = subscriberRegs.get(0).getMsisdn();
				cgToken = new CGToken(token);
				mondiaPayNotification.setToken(token);
				liveReport = new LiveReport(MondiaPayConstant.MONDIA_PAY_OPERATOR_ID, new Timestamp(System.currentTimeMillis()),
						cgToken.getCampaignId(), MondiaPayConstant.MONDIA_PAY_SERVICE_ID, MondiaPayConstant.MONDIA_PAY_PRODUCT_ID);
				liveReport.setTokenId(cgToken.getTokenId());
				liveReport.setToken(cgToken.getCGToken());
				action = MondiaPayConstant.findAction(mondiaPayNotification);
				logger.debug("Action"+action);
				if(Objects.nonNull(action)) {
					if(MConstants.ACT.equals(action)) {
						liveReport.setMsisdn(token);
						liveReport.setAction(MConstants.ACT);
						liveReport.setToken(token);
						liveReport.setTokenId(cgToken.getTokenId());
						liveReport.setAmount(mondiaPayNotification.getAmount());
						liveReport.setConversionCount(1);
					}else if(MConstants.GRACE.equals(action)) {
						liveReport.setMsisdn(token);
						liveReport.setAction(MConstants.GRACE);
						liveReport.setToken(token);
						liveReport.setTokenId(cgToken.getTokenId());
						liveReport.setGraceConversionCount(1);
					} else if(MConstants.RENEW.equals(action)) {
						liveReport.setMsisdn(token);
						liveReport.setAction(MConstants.RENEW);
						liveReport.setToken(token);
						liveReport.setTokenId(cgToken.getTokenId());
						liveReport.setRenewalAmount(mondiaPayNotification.getAmount());
						liveReport.setRenewalCount(1);
					}else if(MConstants.DCT.equals(action)){
						liveReport.setMsisdn(token);
						liveReport.setAction(MConstants.DCT);
						liveReport.setToken(token);
						liveReport.setTokenId(cgToken.getTokenId());
						liveReport.setDctCount(1);
					}
				}
			}
		} catch (Exception e) {
			logger.error("ERROR while processing mondai pay notification"+mondiaPayNotification,e);
		}finally {
			if(liveReport.getAction()!=null) {
				try {
					mondiaPayNotification.setAction(liveReport.getAction());
					if(liveReport.getAction()!=null){
						liveReportFactoryService.process(liveReport);
					}
					mondiaPayNotification.setSendToAdnetwork(liveReport.getSendConversionCount()>0?true:false);
				}catch (Exception e) {
					logger.error(" fianlly liveReport:: " + liveReport
							+ ", : mondiaPayNotification:: "
							+ mondiaPayNotification);
					logger.error("onMessage::::::::::finally " ,e);
				}finally {
					daoService.saveObject(mondiaPayNotification);
				}
				logger.info("onMessage::::::::::::::::: :: update::live report "
						+ save + ", total time:: "
						+ (System.currentTimeMillis() - time));
			}
		}
	}

}
