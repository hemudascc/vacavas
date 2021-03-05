package net.mycomp.altruist;

import java.sql.Timestamp;
import java.util.Objects;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.persist.bean.LiveReport;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MConstants;
import net.util.MData;

public class JMSAltruistCallbackListener implements MessageListener{

	@Autowired
	private LiveReportFactoryService liveReportFactoryService;
	@Autowired
	private IDaoService daoService;
	private static final Logger logger = Logger.getLogger(JMSAltruistCallbackListener.class);
	@Override
	public void onMessage(Message message) {
		AltruistCallback altruistCallback=null;
		long time = System.currentTimeMillis();
		LiveReport liveReport=null;
		boolean save = false;
		CGToken cgToken = new CGToken("");
		String action = null;
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			altruistCallback = (AltruistCallback)objectMessage.getObject();
			cgToken = new CGToken(altruistCallback.getTransactionId2());
			VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			AltruistServiceConfig altruistServiceConfig = AltruistConstant.mapServiceIdToAltruistServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			liveReport = new LiveReport(MConstants.ALTRUIST_ETISALAT_UAE_OPERATOR_ID, new Timestamp(System.currentTimeMillis()),
					cgToken.getCampaignId(), altruistServiceConfig.getServiceId(), 4);
			liveReport.setTokenId(cgToken.getTokenId());
			liveReport.setToken(cgToken.getCGToken());
			action = AltruistConstant.findAction(altruistCallback);
			logger.debug(altruistCallback);

			if(Objects.nonNull(action)) {
				if(MConstants.ACT.equals(action)) {
					liveReport.setMsisdn(altruistCallback.getMsisdn());
					liveReport.setAction(MConstants.ACT);
					liveReport.setToken(cgToken.getCGToken());
					liveReport.setTokenId(cgToken.getTokenId());
					liveReport.setAmount(Double.valueOf(altruistCallback.getAmount()));
					liveReport.setConversionCount(1);
				}else if(MConstants.GRACE.equals(action)) {
					liveReport.setMsisdn(altruistCallback.getMsisdn());
					liveReport.setAction(MConstants.GRACE);
					liveReport.setToken(cgToken.getCGToken());
					liveReport.setTokenId(cgToken.getTokenId());
					liveReport.setGraceConversionCount(1);
				} else if(MConstants.RENEW.equals(action)) {
					liveReport.setMsisdn(altruistCallback.getMsisdn());
					liveReport.setAction(MConstants.RENEW);
					liveReport.setToken(cgToken.getCGToken());
					liveReport.setTokenId(cgToken.getTokenId());
					liveReport.setRenewalAmount(Double.valueOf(altruistCallback.getAmount()));
					liveReport.setRenewalCount(1);
				}else if(MConstants.DCT.equals(action)){
					liveReport.setMsisdn(altruistCallback.getMsisdn());
					liveReport.setAction(MConstants.DCT);
					liveReport.setToken(cgToken.getCGToken());
					liveReport.setTokenId(cgToken.getTokenId());
					liveReport.setDctCount(1);
				}

			} 
		}catch (Exception e) {
			logger.error("error while saving altruistCallback::"+altruistCallback,e);
		}finally {
			if(liveReport.getAction()!=null) {
				try {
					if(liveReport.getAction()!=null){
						liveReportFactoryService.process(liveReport);
					}
				}catch (Exception e) {
					logger.error(" fianlly liveReport:: " + liveReport
							+ ", : altruistCallback:: "
							+ altruistCallback);
					logger.error("onMessage::::::::::finally " ,e);
				}finally {
					daoService.saveObject(altruistCallback);
				}
				logger.info("onMessage::::::::::::::::: :: update::live report "
						+ save + ", total time:: "
						+ (System.currentTimeMillis() - time));
			}
		}
	}
}