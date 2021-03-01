package net.mycomp.messagecloud.gateway;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Objects;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.common.service.SubscriberRegService;
import net.jpa.repository.JPAIntargetUssdTrans;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.LiveReport;
import net.persist.bean.Service;
import net.persist.bean.SubscriberReg;
import net.process.bean.SuscriberIdMsg;


import net.util.MConstants;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class JMSMCGRenewalMessageListener implements MessageListener {

	private static final Logger logger = Logger
			.getLogger(JMSMCGRenewalMessageListener.class);

	

	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private MCGApiService mcgApiService;
	
	@Autowired
	private MCGService mcgService;
	
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;

	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private SubscriberRegService subscriberRegService; 

	@Autowired
	private JPASubscriberReg jpaSubscriberReg; 
	
	@Autowired
	private JPAIntargetUssdTrans jpaIntargetUssdTrans;
	
	@Value("#{ T(java.time.LocalTime).parse('${mcg.renewal.start.time}')}")
	private LocalTime renewalStartTime;

	@Value("#{ T(java.time.LocalTime).parse('${mcg.renewal.stop.time}')}")
	private LocalTime renewalStopTime;
  
	@Override
	public void onMessage(Message m) {

		SuscriberIdMsg suscriberIdMsg=null;
		
		boolean update = false;
		long time = System.currentTimeMillis();
		
		try {

			ObjectMessage objectMessage = (ObjectMessage) m;
			suscriberIdMsg = (SuscriberIdMsg) objectMessage
					.getObject();
			logger.debug("suscriberIdMsg::::::: "+suscriberIdMsg);
			SubscriberReg subscriberReg =jpaSubscriberReg
					.findSubscriberRegById(suscriberIdMsg.getSubscriberId());
			MCGServiceConfig mcgServiceConfig=MCGConstant.mapServiceIdToMCGServiceConfig.get(subscriberReg.getServiceId());
			
			MCGFallbackPricePointConfig mcgFallbackPricePointConfig=MCGConstant
					.findMCGFallbackPricePointConfig(mcgServiceConfig,null);
				
			LocalTime now = LocalTime.now();
			if (now.isAfter(renewalStartTime) && now.isBefore(renewalStopTime)) {

				if (suscriberIdMsg.getAction().equalsIgnoreCase(
						MConstants.RENEW)) {
					int dayDiff = MUtility.noOfDaysDiffrence(new Timestamp(
							System.currentTimeMillis()), subscriberReg
							.getValidityTo());
					
					if (subscriberReg.getStatus() == MConstants.SUBSCRIBED&& dayDiff <= 0&&
							!mcgService.checkBlocking(subscriberReg.getMsisdn())
						) {		
					
					subscriberReg.setLastRenewalRetryDate(new Timestamp(System.currentTimeMillis()));
					jpaSubscriberReg.save(subscriberReg);	
					
					String mtMsg=mcgServiceConfig.getFirstMt();						
					mcgApiService.sendBilledMessage(null,
							subscriberReg.getMsisdn()
							, mcgServiceConfig,mcgFallbackPricePointConfig,
							mtMsg,subscriberReg.getParam1()
							,MConstants.RENEW,MCGConstant.BILLLED_MSG_TYPE1);
					
					 mtMsg=mcgServiceConfig.getSecondMt();						
					mcgApiService.sendBilledMessage(null,
							subscriberReg.getMsisdn()
							, mcgServiceConfig,mcgFallbackPricePointConfig, 
							mtMsg,subscriberReg.getParam1(),MConstants.RENEW,MCGConstant.BILLLED_MSG_TYPE2);
					 mtMsg=mcgServiceConfig.getThirdMt();						
					mcgApiService.sendBilledMessage(null,
							subscriberReg.getMsisdn()
							, mcgServiceConfig,mcgFallbackPricePointConfig,
							mtMsg,subscriberReg.getParam1(),MConstants.RENEW,MCGConstant.BILLLED_MSG_TYPE3);					
				}
			}
			}
		} catch (Exception ex) {
			logger.error("onMessage::::: ", ex);
			
			
		} finally {
			try {
			} catch (Exception ex) {
				logger.error(" fianlly " 
						+ ", : suscriberIdMsg:: "
						+ suscriberIdMsg);
				logger.error("onMessage::::::::::finally " ,ex);
			} finally {
				//update = daoService.saveObject(mcgMoMessage);
			}
			logger.info("onMessage::::::::::::::::: :: update::live report "
					+ update + ", total time:: "
					+ (System.currentTimeMillis() - time));
		}
	}
}
