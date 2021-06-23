package net.mycomp.comviva;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.common.service.IDaoService;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MData;

public class JMSComvivaCGCallbackListener implements MessageListener{

	@Autowired
	private ComvivaSubscriptionService comvivaSubscriptionService;
	@Autowired
	private IDaoService daoService;
	
	private static final Logger logger = Logger.getLogger(JMSComvivaCGCallbackListener.class);
	@Override
	public void onMessage(Message message) {
		CGToken cgToken = null;
		ComvivaCGCallback comvivaCGCallback=null;
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			comvivaCGCallback = (ComvivaCGCallback)objectMessage.getObject();
			cgToken = new CGToken(comvivaCGCallback.getToken());
			VWServiceCampaignDetail vwServiceCampaignDetail=
					 MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			ComvivaServiceConfig comvivaServiceConfig = ComvivaConstant.mapServiceIdToComvivaServiceConfig.get(vwServiceCampaignDetail.getServiceId());
			if(ComvivaConstant.SUCCESS_RESULT.equalsIgnoreCase(comvivaCGCallback.getResult())) {
				comvivaSubscriptionService.checkSubs(comvivaServiceConfig, comvivaCGCallback);
				comvivaSubscriptionService.subscribe(comvivaServiceConfig, comvivaCGCallback);
				comvivaSubscriptionService.sendMT(comvivaServiceConfig, comvivaCGCallback);
				
			}
		} catch (Exception e) {
			logger.error("onMessage::::: "+comvivaCGCallback,e);
		}finally {
			daoService.saveObject(comvivaCGCallback);
		}
	}
}
