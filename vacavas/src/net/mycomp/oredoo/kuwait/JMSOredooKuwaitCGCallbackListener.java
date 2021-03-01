package net.mycomp.oredoo.kuwait;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.common.service.IDaoService;
import net.common.service.LiveReportFactoryService;
import net.common.service.RedisCacheService;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.VWServiceCampaignDetail;
import net.util.MData;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class JMSOredooKuwaitCGCallbackListener implements MessageListener{

	private static final Logger logger = Logger.getLogger(JMSOredooKuwaitCGCallbackListener.class);

	@Autowired
	private LiveReportFactoryService liveReportFactoryService;
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private OredooKuwaitSubscriptionService oredooKuwaitSubscriptionService;
	
	@Override
	public void onMessage(Message m) {
		
		OredooKuwaitCGCallback oredooKuwaitCGCallback=null;
		
		try{
		ObjectMessage objectMessage = (ObjectMessage) m;
		oredooKuwaitCGCallback=(OredooKuwaitCGCallback)objectMessage.getObject();
		Long  counter=redisCacheService.getAndIcrementIntValue(OredooKuwaitCGCallback.class.getName()
				+oredooKuwaitCGCallback.getTpcgId(), 1);
		  if(counter!=null&&counter>1){
				logger.debug("onMessage::::::::::::::::: duplicate callback");
				oredooKuwaitCGCallback.setDuplicate(true);	
		  }
		
		  OredooKuwaitCGToken cgToken=new OredooKuwaitCGToken(oredooKuwaitCGCallback.getTransId()); 
		  AdnetworkToken adnetworkToken=daoService.findAdnetworkTokenById(cgToken.getTokenId());
		 
		 VWServiceCampaignDetail vwServiceCampaignDetail=
				 MData.mapCamapignIdToVWServiceCampaignDetail.get(adnetworkToken.getCampaignId());
		 
		 Integer serviceId=redisCacheService.getIntValue(OredoKuwaitConstant.
				 OREDOO_KUWAIT_CG_SERVICE_ID_PREFIX+oredooKuwaitCGCallback.getTransId());
		 logger.info("serviceId from cache:: "+serviceId);
		 OredooKuwaitServiceConfig oredooKuwaitServiceConfig=null;
		 if(serviceId!=null&&serviceId>0){
			  oredooKuwaitServiceConfig=OredoKuwaitConstant.
						 mapServiceIdToOredooKuwaitServiceConfig.get(serviceId);
		 }else{
			 
		  oredooKuwaitServiceConfig=OredoKuwaitConstant.
				 mapServiceIdToOredooKuwaitServiceConfig.get(vwServiceCampaignDetail.getServiceId());
		 }
		 
		if(oredooKuwaitCGCallback.getResult().equalsIgnoreCase(OredoKuwaitConstant.RESULT_SUCCESS)){
			redisCacheService.putObjectCacheValueByEvictionMinute(OredoKuwaitConstant.CACHE_PREFIX_TRANSID+
						oredooKuwaitCGCallback.getMsisdn(),cgToken.getCGToken(),60*24*5);				
			oredooKuwaitSubscriptionService.callSubscriptionApi(oredooKuwaitCGCallback,
					oredooKuwaitServiceConfig);			
		}
		
		}catch(Exception ex){
			logger.error("onMessage::::: "+oredooKuwaitCGCallback,ex);
		}finally{	
			
			daoService.saveObject(oredooKuwaitCGCallback);
			}
		}
	}

