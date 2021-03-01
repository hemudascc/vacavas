package net.common.service;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.jpa.repository.JPASubscriberReg;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.BlockSeries;
import net.persist.bean.DuplicateRequest;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.process.request.OperatorRequestService;
import net.util.MConstants;
import net.util.MUtility;
import net.util.SubscriptionType;

@Service("subscriberRegService")
public class SubscriberRegService {

	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private JPASubscriberReg jpaSubscriberReg;
	
	@Autowired
	private OperatorRequestService operatorRequestService;
	
	@Autowired
	private BlockSeriesRedisCacheService blockSeriesRedisCacheService;
	
	private static final Logger logger = Logger
			.getLogger(SubscriberRegService.class);

//	public SubscriberReg findOrCreateSubscriberByAct(String msisdn,
//			AdnetworkToken adnetworkToken, Integer circleId, String action,
//			Integer opId, String lcID, int noOfDays,double amount,Integer serviceId,String param1) {
	
	public SubscriberReg findOrCreateSubscriberByAct(String msisdn,
			AdnetworkToken adnetworkToken,LiveReport liveReport ) {
		

//		SubscriberReg subscriberReg = jpaSubscriberReg.				
//				findSubscriberRegByMsisdnAndServiceId(msisdn,
//				serviceId);
		
		 SubscriberReg subscriberReg = operatorRequestService.
				 searchSubscriber(liveReport.getOperatorId(),msisdn,liveReport.getServiceId(),
						 liveReport.getProductId());
		 
		if (subscriberReg != null) {

			subscriberReg.setServiceId(liveReport.getServiceId());
			
			if (subscriberReg.getStatus() == MConstants.SUBSCRIBED) {
				subscriberReg.setDuplicateRequest(true);
			}
			subscriberReg.setSubscriptionType(SubscriptionType.getSubscriptionType(liveReport.getAmount(), MConstants.ACT));
			
			subscriberReg.setAction(MConstants.ACT);
			subscriberReg.setStatus(MConstants.SUBSCRIBED);
			subscriberReg.setStatusDescp(MConstants.SUBSCRIBED_DESC);
			subscriberReg.setLastUpdate(liveReport.getReportDate());
			subscriberReg.setSubDate(liveReport.getReportDate());
			if(liveReport.getParam1()!=null){
				subscriberReg.setParam1(liveReport.getParam1());
				}
				if(liveReport.getParam2()!=null){
				subscriberReg.setParam2(liveReport.getParam2());
				}
				if(liveReport.getParam3()!=null){
				subscriberReg.setParam3(liveReport.getParam3());
				}
			
			subscriberReg.setMode(liveReport.getMode());
			subscriberReg.setProductId(liveReport.getProductId());
			if (adnetworkToken != null) {
				subscriberReg.setCampaignId(adnetworkToken.getCampaignId());
				subscriberReg.setTokenId(adnetworkToken.getTokenId());
				subscriberReg.setToken(adnetworkToken.getToken());
			} else {
				subscriberReg.setTokenId(null);
				subscriberReg.setCampaignId(null);
			}
			Timestamp ts = liveReport.getReportDate();
			subscriberReg.setValidityFrom(ts);
			subscriberReg.setValidityTo(MUtility.addNumberOfDay(ts, liveReport.getNoOfDays()));
			boolean isUpdate = daoService.updateObject(subscriberReg);
			logger.info("onMessage::chargingCallback::ACT:: subscriber Reg isUpdate: "
					+ isUpdate);
			
		} else {

			subscriberReg = new SubscriberReg();
			subscriberReg.setParam1(liveReport.getParam1());
			subscriberReg.setProductId(liveReport.getProductId());
			subscriberReg.setMode(liveReport.getMode());
			subscriberReg.setAction(MConstants.ACT);
			subscriberReg.setSubscriptionType(SubscriptionType.getSubscriptionType(liveReport.getAmount(), MConstants.ACT));			
			if (adnetworkToken != null) {
				subscriberReg.setCampaignId(adnetworkToken.getCampaignId());
				subscriberReg.setTokenId(adnetworkToken.getTokenId());
			}
			subscriberReg.setOperatorId(liveReport.getOperatorId());
			subscriberReg.setCircleId(liveReport.getCircleId());
			subscriberReg.setMsisdn(msisdn);
			subscriberReg.setStatus(MConstants.SUBSCRIBED);
			subscriberReg.setStatusDescp(MConstants.SUBSCRIBED_DESC);
			subscriberReg.setRegDate(liveReport.getReportDate());
			subscriberReg.setSubDate(liveReport.getReportDate());
			subscriberReg.setServiceId(liveReport.getServiceId());
			subscriberReg.setTotalDownloadCount(0);
			subscriberReg.setLastDownloadDate(null);
			subscriberReg.setLastActivity("");
			subscriberReg.setUnsubDate(null);
			if(liveReport.getParam1()!=null){
				subscriberReg.setParam1(liveReport.getParam1());
				}
				if(liveReport.getParam2()!=null){
				subscriberReg.setParam2(liveReport.getParam2());
				}
				if(liveReport.getParam3()!=null){
				subscriberReg.setParam3(liveReport.getParam3());
				}
			Timestamp ts = liveReport.getReportDate();
			subscriberReg.setValidityFrom(ts);
			subscriberReg.setValidityTo(MUtility.addNumberOfDay(ts,liveReport.getNoOfDays()));
			boolean isUpdate = daoService.saveObject(subscriberReg);
			logger.info("onMessage::  subscriber Reg 22 isUpdate: " + isUpdate);
		}
		return subscriberReg;
	}

	public SubscriberReg updateDeactivation(String msisdn,int productId) {

		//SubscriberReg subscriberReg = daoService.searchSubscriber(msisdn);
		SubscriberReg subscriberReg =jpaSubscriberReg.
				findSubscriberRegByMsisdnAndProductId(msisdn, productId);
		
		if (subscriberReg != null
				&& subscriberReg.getStatus() == MConstants.SUBSCRIBED) {
			// LocalDate today = LocalDate.now();
			//long days = -1;
			// LocalDate subDate=null;
			Timestamp subDate = null;
			if (subscriberReg.getSubDate() != null) {
				// subDate =
				// subscriberReg.getSubDate().toLocalDateTime().toLocalDate();
				subDate = subscriberReg.getSubDate();
				// days = ChronoUnit.DAYS.between(today, subDate);

			}
			subscriberReg.setAction(MConstants.DCT);
			if (MUtility.isChurn(subscriberReg.getOperatorId(), subDate)) {// Same
				subscriberReg.setChurn(true);
				
			}
			addToBlockSeries(subscriberReg);
		
			subscriberReg.setStatus(MConstants.UNSUBSCRIBED);
			subscriberReg.setStatusDescp(MConstants.UNSUBSCRIBED_DESC);
			subscriberReg.setSubscriptionType(SubscriptionType.getSubscriptionType(0d,
					MConstants.DCT));
			subscriberReg
					.setUnsubDate(new Timestamp(System.currentTimeMillis()));
			subscriberReg.setLastUpdate(new Timestamp(System
					.currentTimeMillis()));
			boolean isUpdate = daoService.updateObject(subscriberReg);
			logger.info("onMessage::  subscriber Reg unsubscribe isUpdate: "
					+ isUpdate);
		} else {

			DuplicateRequest duplicateRequest = new DuplicateRequest();
			duplicateRequest.setMsisdn(msisdn);
			duplicateRequest.setAction(MConstants.DCT);
			duplicateRequest.setCreateTime(new Timestamp(System
					.currentTimeMillis()));
			duplicateRequest.setSubscribeDetail(subscriberReg);
			daoService.saveObject(duplicateRequest);
			logger.debug("onMessage::chargingCallback::NOT Subscribed user");
		}
		return subscriberReg;
	}

	public SubscriberReg updateRenewal(String msisdn,LiveReport liveReport) {
		
		SubscriberReg subscriberReg = jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn,
				liveReport.getProductId());
		
		//SubscriberReg subscriberReg = daoService.searchSubscriber(msisdn);
		if (subscriberReg != null
				&& subscriberReg.getStatus() == MConstants.SUBSCRIBED) {
			
			Timestamp subDate = null;
			if (subscriberReg.getSubDate() != null) {
				subDate = subscriberReg.getSubDate();
			}			
//			if(subscriberReg.getSubscriptionType()!=null&&subscriberReg.
//					getSubscriptionType().equalsIgnoreCase(SubscriptionType.ZERO_PRICE_ACTIVATION.description)) {
//				int day=MUtility.noOfDaysDiffrence(subDate,new Timestamp(System.currentTimeMillis()));
//				liveReport.setRenewalType(day,liveReport.getRenewalAmount());				
//			}
			
			subscriberReg.setSubscriptionType(SubscriptionType.getSubscriptionType(liveReport.getAmount(),
					MConstants.RENEW));
			Timestamp ts = liveReport.getReportDate();
			subscriberReg.setValidityFrom(ts);
			subscriberReg.setValidityTo(MUtility.addNumberOfDay(ts, liveReport.getNoOfDays()));
			if(liveReport.getParam1()!=null){
			subscriberReg.setParam1(liveReport.getParam1());
			}
			if(liveReport.getParam2()!=null){
			subscriberReg.setParam2(liveReport.getParam2());
			}
			if(liveReport.getParam3()!=null){
			subscriberReg.setParam3(liveReport.getParam3());
			}
			boolean isUpdate = daoService.updateObject(subscriberReg);
			logger.info("onMessage::  subscriber Reg updateRenewal isUpdate: "
					+ isUpdate);
		}else{
			try{
			findOrCreateSubscriberByAct(liveReport.getMsisdn(),
					null,
					liveReport);
			}catch(Exception ex){
				logger.error("updateRenewal:: findOrCreateSubscriberByAct ",ex);
			}	}
		return subscriberReg;
	}

	private void addToBlockSeries(SubscriberReg subscriberReg) {
		BlockSeries blockSeries = new BlockSeries();
		// blockSeries.setCircle(subscriberReg.getCircleId());
		blockSeries.setOpId(subscriberReg.getOperatorId());
		blockSeries.setSeriesNo(subscriberReg.getMsisdn());
		blockSeries.setSource("BY_CHURN");
		blockSeries.setStatus(true);
		daoService.saveObject(blockSeries);
		blockSeriesRedisCacheService.putBlockSeriesValue(subscriberReg.getMsisdn(),"1");
	}
	
	public boolean isSubscribed(String msisdn){	
		SubscriberReg subscriberReg = daoService.searchSubscriber(msisdn);
		if (subscriberReg != null && 
				subscriberReg.getSubscriberId() != null &&
				subscriberReg.getSubscriberId() > 0
				&& subscriberReg.getStatus() == MConstants.SUBSCRIBED) {
			logger.debug("campaign:: already subscribed, " + subscriberReg);
			return  true;
		}
		
		return false;		
	}
	
	public boolean isSubscribedBySericeId(String msisdn,int productId){	
		SubscriberReg subscriberReg = jpaSubscriberReg.findSubscriberRegByMsisdnAndProductId(msisdn,
				productId);
		if (subscriberReg != null && 
				subscriberReg.getSubscriberId() != null &&
				subscriberReg.getSubscriberId() > 0
				&& subscriberReg.getStatus() == MConstants.SUBSCRIBED) {
			logger.debug("campaign:: already subscribed, " + subscriberReg);
			return  true;
		}
		return false;		
	}
}
