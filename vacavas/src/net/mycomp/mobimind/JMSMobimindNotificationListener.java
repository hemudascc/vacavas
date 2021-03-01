package net.mycomp.mobimind;

import java.sql.Timestamp;
import java.util.Objects;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.persist.bean.LiveReport;
import net.process.bean.CGToken;
import net.util.MConstants;
import net.util.MUtility;


public class JMSMobimindNotificationListener implements MessageListener {

	private static final Logger logger = Logger
			.getLogger(JMSMobimindNotificationListener.class);

	

	@Autowired
	private IDaoService daoService;
	
	
	
	@Autowired
	private LiveReportFactoryService liveReportFactoryService;

	@Autowired
	private RedisCacheService redisCacheService;
	
	@Override
	public void onMessage(Message m) {

		MobimindNotification mobimindNotification = null;
		LiveReport liveReport = null;
		boolean update = false;
		long time = System.currentTimeMillis();
		CGToken cgToken=new CGToken("");
		try {

			ObjectMessage objectMessage = (ObjectMessage) m;
			mobimindNotification = (MobimindNotification) objectMessage
					.getObject();
			
			logger.info("mobimindNotification::::: "+mobimindNotification);
			
			cgToken=new CGToken(Objects.toString(
					redisCacheService.getObjectCacheValue(MobimindConstant.MOBIMIND_CACHE_PREFIX
							+mobimindNotification.getMsisdn())));
			if(cgToken.getCampaignId()<0){
				cgToken=new CGToken(mobimindNotification.getTrxId());
			}
			MobimindServiceConfig mobimindServiceConfig=MobimindConstant.
					mapChannelIdToMobimindServiceConfig.get(mobimindNotification.getChannelId());
			
			if(mobimindServiceConfig==null){
				mobimindServiceConfig=MobimindConstant.listMobimindServiceConfig.get(0);				
			}
		  
		   liveReport=new LiveReport(mobimindServiceConfig.getCcOperatorId(),
				   new Timestamp(System.currentTimeMillis())
			 ,cgToken.getCampaignId(),mobimindServiceConfig.getServiceId(),0); 
	
		    liveReport.setProductId(MobimindConstant.MOBIMIND_PRODUCT_ID);
			liveReport.setTokenId(cgToken.getTokenId());
			liveReport.setToken(cgToken.getCGToken());
			liveReport.setMsisdn(mobimindNotification.getMsisdn());
			liveReport.setCircleId(0);
			mobimindNotification.setToken(cgToken.getCGToken());
			mobimindNotification.setTokenId(cgToken.getTokenId());
			mobimindNotification.setAction(MobimindSubcriberStatus.
					findAction(mobimindNotification.getNotificationStatus()));
			double priceAmount=MUtility.toDouble(Objects.toString(mobimindNotification.getPrice()),-1);
			
			if(mobimindNotification.getAction().equalsIgnoreCase(MobimindConstant.RECYCLED_SUBCRIBER)){				
				 liveReport.setAction("");				
				 liveReport.setGraceConversionCount(1);//ConversionCount(1);
				
			}else if(mobimindNotification.getAction().equalsIgnoreCase(MConstants.ACT)){			
					 liveReport.setAction(MConstants.ACT);					
					 liveReport.setConversionCount(1);					
					 liveReport.setAmount(priceAmount>0?priceAmount:mobimindServiceConfig.getPrice());
					 liveReport.setNoOfDays(mobimindServiceConfig.getValidity());
					 mobimindNotification.setValidity(liveReport.getNoOfDays());
					 mobimindNotification.setAmount(liveReport.getAmount());
					 
				}else if(mobimindNotification.getAction().equalsIgnoreCase(MConstants.RENEW)){
					liveReport.setAction(MConstants.RENEW);
					liveReport.setRenewalCount(1);					
					 liveReport.setRenewalAmount(priceAmount>0?priceAmount:mobimindServiceConfig.getPrice());
					 liveReport.setNoOfDays(mobimindServiceConfig.getValidity());
					 mobimindNotification.setValidity(liveReport.getNoOfDays());
					 mobimindNotification.setAmount(liveReport.getRenewalAmount());
					 
				}else if(mobimindNotification.getAction().equalsIgnoreCase(MConstants.DCT)){
					liveReport.setAction(MConstants.DCT);			
					liveReport.setDctCount(1);
				}
			
			
		} catch (Exception ex) {
			logger.error("onMessage::::: ", ex);
		} finally {
		
			try {
				if(liveReport.getAction()!=null){
					liveReportFactoryService.process(liveReport);
				}
				mobimindNotification.setSendToAdnetwork(liveReport.getSendConversionCount()>0?true:false);
				
			} catch (Exception ex) {
				logger.error(" fianlly liveReport:: " + liveReport
						+ ", : mobimindNotification:: "
						+ mobimindNotification);
				logger.error("onMessage::::::::::finally " ,ex);
			} finally {
				update = daoService.saveObject(mobimindNotification);
			}
			logger.info("onMessage::::::::::::::::: :: update::live report "
					+ update + ", total time:: "
					+ (System.currentTimeMillis() - time));
		}
	}
}
