package net.common.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.LiveReport;
import net.persist.bean.VWServiceCampaignDetail;
import net.util.MData;

public class JMSAdnetworkTokenListener implements MessageListener {

	private static final Logger logger = Logger.getLogger(JMSAdnetworkTokenListener.class);

	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;
	
	@Override
	public void onMessage(Message m) {
		
		long time = System.currentTimeMillis();
		logger.debug("onMessage::::::::::::::::: " + m);
		ObjectMessage objectMessage = (ObjectMessage) m;
		AdnetworkToken adnetworkToken = null;
		boolean update = false;
		LiveReport liveReport=null;
		VWServiceCampaignDetail vwServiceCampaignDetail=null;
		try {
			 adnetworkToken = (AdnetworkToken) objectMessage.getObject();
			 logger.debug("onMessage::::::::::::::::: " + adnetworkToken);
			 vwServiceCampaignDetail=MData.mapCamapignIdToVWServiceCampaignDetail.
					 get(adnetworkToken.getCampaignId());
			 
			
			 
		} catch (Exception e) {
			logger.error("onMessage:::::::::::::::::adnetworkToken:: " + adnetworkToken + " , Exception  " + e);
		} finally {
			try{
			 update = daoService.updateObject(adnetworkToken);
			 logger.info("onMessage::::::::::::::::: :: update:: " + update);
			 liveReport=liveReportFactoryService.
					 createLiveReportFromAdnetworkToken(vwServiceCampaignDetail,adnetworkToken);
			 logger.debug("onMessage:: liveReport:: "+liveReport);
			}catch(Exception ex){
				logger.error("onMessage:: campaignDetails:: "+vwServiceCampaignDetail+", adnetworkToken:: "+adnetworkToken+", liveReport:: "+liveReport,ex);
			}
		}		
		logger.debug("onMessage::::::::::::::::: :: update:: " + update+ ", total time:: " + (System.currentTimeMillis() - time));
	}	
}
