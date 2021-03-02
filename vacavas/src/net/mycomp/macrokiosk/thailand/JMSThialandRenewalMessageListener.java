package net.mycomp.macrokiosk.thailand;

import java.sql.Timestamp;
import java.time.LocalTime;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.SubscriberReg;
import net.process.bean.SuscriberIdMsg;
import net.util.MConstants;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class JMSThialandRenewalMessageListener implements MessageListener {

	private static final Logger logger = Logger
			.getLogger(JMSThialandRenewalMessageListener.class);

	@Autowired
	private JPASubscriberReg jpaSubscriberReg;

	@Autowired
	private RedisCacheService redisCacheService;
	

	@Autowired
	private IDaoService daoService;

	@Autowired
	private MacroKioskFactoryService macroKioskFactoryService;
	

	@Value("#{ T(java.time.LocalTime).parse('${thia.ais.renewal.start.time}')}")
	private LocalTime renewalStartTime;

	@Value("#{ T(java.time.LocalTime).parse('${thia.ais.renewal.stop.time}')}")
	private LocalTime renewalStopTime;
	
	

	@Override
	public void onMessage(Message m) {

		SuscriberIdMsg suscriberIdMsg = null;

		try {

			ObjectMessage objectMessage = (ObjectMessage) m;
			suscriberIdMsg = (SuscriberIdMsg) objectMessage
					.getObject();

			SubscriberReg subscriberReg = jpaSubscriberReg
					.findSubscriberRegById(suscriberIdMsg
							.getSubscriberId());
			
			LocalTime now = LocalTime.now();
			String key=ThiaConstant.THIALAND_RETRY_BILLING_PREFIX+ThiaConstant.retryBillingDate
					+subscriberReg.getMsisdn();
			
			Integer counter=(Integer)redisCacheService.getObjectCacheValue(key);
			if(counter==null){
				counter=0;				
			}
			
			redisCacheService.putObjectCacheValueByEvictionMinute(key,++counter
					, 16*60);//EvictionDay(key,++counter,1);
			
			if (now.isAfter(renewalStartTime) && now.isBefore(renewalStopTime)&&counter<3) {
				
				if (suscriberIdMsg.getAction().equalsIgnoreCase(
						MConstants.RENEW)) {

					
					int dayDiff = MUtility.noOfDaysDiffrence(new Timestamp(
							System.currentTimeMillis()), subscriberReg
							.getValidityTo());

					if (subscriberReg.getStatus() == MConstants.SUBSCRIBED) {
						
						 macroKioskFactoryService.sendSubscriptionRenewalRequest(subscriberReg);
						logger.info("sendRenewalBilled::::::::sent msisdn::  "+ subscriberReg.getMsisdn());
						subscriberReg.setLastRenewalRetryDate(new Timestamp(
								System.currentTimeMillis()));
						daoService.updateObject(subscriberReg);
					}
				}
			}else{
				logger.error("thialand ais renewal time expired or renewal counter:"
			+counter+":::::not send msisdn to renewal "+subscriberReg
						+ " ");
			}
		
		} catch (Exception ex) {
			logger.error("onMessage::::: ", ex);
		}
	}
}
