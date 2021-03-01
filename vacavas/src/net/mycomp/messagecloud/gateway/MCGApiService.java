package net.mycomp.messagecloud.gateway;

import java.util.Map;
import java.util.Objects;

import net.common.jms.JMSService;
import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;
import net.util.JsonMapper;
import net.util.MConstants;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("mcgApiService")
public class MCGApiService {

	private static final Logger logger = Logger.getLogger(JMSMCGService.class);
	
	
	private final String mtContentPushUrl;
	
	private final String mtBillingPushUrl;
	private final String hlrUrl;
	
	private HttpURLConnectionUtil httpURLConnectionUtil;
	
	@Autowired
	private JMSService jmsService;
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	private RedisCacheService redisCacheService; 
	
	@Autowired
	private MCGService mcgService;
	
	@Autowired
	public MCGApiService(
			@Value("${message.cloud.gateway.mt.content.sms.url}")String mtContentPushUrl,
			@Value("${message.cloud.gateway.mt.billing.sms.url}")String mtBillingPushUrl,
			@Value("${message.cloud.gateway.hlr.url}")String hlrUrl
			){
		this.mtContentPushUrl=mtContentPushUrl;
		this.mtBillingPushUrl=mtBillingPushUrl;
		this.hlrUrl=hlrUrl;
		httpURLConnectionUtil=new HttpURLConnectionUtil();	
	}
	
	public  MCGMTMessage sendContentMessage(String msisdn,MCGServiceConfig mcgServiceConfig,
			MCGFallbackPricePointConfig mcgFallbackPricePointConfig
			,String msg,String token,String action, String messageId, String network){
	
		MCGMTMessage mcgMTMessage=new MCGMTMessage(true);
		try{
			mcgMTMessage.setToken(token);
			
			msg=MCGConstant.getMsg(msg, mcgServiceConfig,
					mcgFallbackPricePointConfig.getPricePoint(),
					mcgFallbackPricePointConfig.getValidity(),  msisdn);	
		msisdn=MCGConstant.formatMsisdn(msisdn);	
	    mcgMTMessage.setAction(action);
	    mcgMTMessage.setType(action);
		mcgMTMessage.setNumber(msisdn);
		mcgMTMessage.setMessage(msg);
		mcgMTMessage.setTitle(mcgServiceConfig.getShortCode());
		mcgMTMessage.setCc(mcgServiceConfig.getCompanyCode());
		mcgMTMessage.setEkey(mcgServiceConfig.getEkey());
		if(Objects.isNull(network)) {
			String networkName = findNetworkName(msisdn);
			if(networkName.equalsIgnoreCase("Swisscom")) {
				mcgMTMessage.setNetwork("SWISSCOM8CH4");
			}else {
				mcgMTMessage.setNetwork("INTERNATIONAL");
			}
		}else {
			mcgMTMessage.setNetwork(network);	
		}
		//https://client.txtnation.com/gateway.php?
		//number=<number>&message=<message>&title=<title>&cc=<cc>&ekey=<ekey>
	String url=	mtContentPushUrl
		.replaceAll("<number>",MUtility.urlEncoding(mcgMTMessage.getNumber()))
		.replaceAll("<message>",MUtility.urlEncoding( mcgMTMessage.getMessage()))
		.replaceAll("<title>",MUtility.urlEncoding( mcgMTMessage.getTitle()))
		.replaceAll("<cc>", MUtility.urlEncoding(mcgMTMessage.getCc()))
		.replaceAll("<network>", MUtility.urlEncoding(mcgMTMessage.getNetwork()))
		.replaceAll("<messageid>", MUtility.urlEncoding(messageId+"|"+mcgMTMessage.getId()))
		.replaceAll("<ekey>", MUtility.urlEncoding(mcgMTMessage.getEkey()));
	mcgMTMessage.setRequestUrl(url);
	
	HTTPResponse httpResponse =httpURLConnectionUtil.sendHttpsGet(url,null);
	mcgMTMessage.setResponseCode(""+httpResponse.getResponseCode());
	mcgMTMessage.setResponse(httpResponse.getResponseStr());
	if(token!=null){
		redisCacheService.putObjectCacheValueByEvictionDay(MCGConstant.MSG_CLOUD_CACHE_PREFIX+msisdn
				, token, 10);
	}
	
		}catch(Exception ex){
			logger.error("Exception:: ",ex);
		}finally{
			logger.info("mcgMTMessage:: "+mcgMTMessage);
			daoService.updateObject(mcgMTMessage);
		}
		return  mcgMTMessage;
	}
   

	public  MCGMTMessage sendBilledMessage(String msessageId,String msisdn,
			MCGServiceConfig mcgServiceConfig,
			MCGFallbackPricePointConfig mcgFallbackPricePointConfig,
			String msg,String networkName,String action,String msgType){
		
		MCGMTMessage mcgMTMessage=new MCGMTMessage(true);
		try{
			
			if(mcgService.checkBlocking(msisdn)){
				mcgMTMessage.setMessage("Blocked number .so not send blling content ");
				return mcgMTMessage;
			}
			
		msisdn=MCGConstant.formatMsisdn(msisdn);	
	    msg=MCGConstant.getMsg(msg, mcgServiceConfig,
				mcgFallbackPricePointConfig.getPricePoint(),
				mcgFallbackPricePointConfig.getValidity(),  msisdn);
	   String token=Objects.toString(redisCacheService.getObjectCacheValue(MCGConstant.MSG_CLOUD_CACHE_PREFIX+msisdn
				));
	    mcgMTMessage.setToken(token);	   
		mcgMTMessage.setAction(action);
		mcgMTMessage.setType(msgType);
		mcgMTMessage.setNumber(msisdn);
		mcgMTMessage.setServiceConfigId(mcgServiceConfig.getId());
		mcgMTMessage.setFallbackServiceConfigId(mcgFallbackPricePointConfig.getId());		
		mcgMTMessage.setMessage(msg);
		//mcgMTMessage.setTitle(mcgServiceConfig.getServiceName());
		mcgMTMessage.setTitle(mcgServiceConfig.getShortCode());
		mcgMTMessage.setCc(mcgServiceConfig.getCompanyCode());
		mcgMTMessage.setEkey(mcgServiceConfig.getEkey());
	
		if(mcgFallbackPricePointConfig.getReply()==1){
			mcgMTMessage.setReply(""+mcgFallbackPricePointConfig.getReply());					
		}else{
			MCGCGToken mcgCGToken=new MCGCGToken("R",mcgMTMessage.getId(),
					mcgServiceConfig.getId(),
					mcgFallbackPricePointConfig.getId());
			
			mcgMTMessage.setReply(""+mcgFallbackPricePointConfig.getReply());		
			mcgMTMessage.setMessageId(mcgCGToken.getCGToken());
		}
		
		mcgMTMessage.setNetwork(networkName);
		mcgMTMessage.setCurrency(mcgServiceConfig.getCurrency());
		mcgMTMessage.setValue(""+mcgFallbackPricePointConfig.getPricePoint());
		//https://client.txtnation.com/gateway.php?number=<number>&message=<message>&
		//title=<title>&cc=<cc>&ekey=<ekey>&reply=<reply>&id=<messageid>&
		//network=<network>&currency=<currency>&value=<value>
	String url=	mtBillingPushUrl
		.replaceAll("<number>",MUtility.urlEncoding(mcgMTMessage.getNumber()))
		.replaceAll("<message>",MUtility.urlEncoding( mcgMTMessage.getMessage()))
		.replaceAll("<title>",MUtility.urlEncoding( mcgMTMessage.getTitle()))
		.replaceAll("<cc>", MUtility.urlEncoding(mcgMTMessage.getCc()))
		.replaceAll("<ekey>", MUtility.urlEncoding(mcgMTMessage.getEkey()))
		.replaceAll("<messageid>", MUtility.urlEncoding(mcgServiceConfig.getConfigServiceName()+"|"+mcgMTMessage.getId()))
		.replaceAll("<network>", MUtility.urlEncoding(mcgMTMessage.getNetwork()))
		.replaceAll("<currency>", MUtility.urlEncoding(mcgMTMessage.getCurrency()))
		.replaceAll("<value>", MUtility.urlEncoding(mcgMTMessage.getValue()))
		.replaceAll("<reply>", MUtility.urlEncoding("0"))
		;
	//reply =0 for billing change suggested by Liam on 05-02-2021
	mcgMTMessage.setRequestUrl(url);
	HTTPResponse httpResponse =httpURLConnectionUtil.sendHttpsGet(url,null);
	mcgMTMessage.setResponseCode(""+httpResponse.getResponseCode());
	mcgMTMessage.setResponse(httpResponse.getResponseStr());
	
	}catch(Exception ex){
			logger.error("Exception:: ",ex);
		}finally{
			daoService.updateObject(mcgMTMessage);
		}
		return  mcgMTMessage;
	}
	
	
	public  String  findNetworkName(String msisdn){
		
		String networkName=null;
		try{
		
		//https://client.txtnation.com/gateway.php?number=<number>&message=<message>&
		//title=<title>&cc=<cc>&ekey=<ekey>&reply=<reply>&id=<messageid>&
		//network=<network>&currency=<currency>&value=<value>
	String url=	hlrUrl
		.replaceAll("<msisdn>",MUtility.urlEncoding(msisdn));

	HTTPResponse httpResponse =httpURLConnectionUtil.sendHttpsGet(url,null);
	if(httpResponse.getResponseCode()==200){
		Map map=JsonMapper.getJsonToObject(httpResponse.getResponseStr(), Map.class);
		String mcc=(String)map.get("mcc");
		//Integer mnc=MUtility.toInt((String)map.get("mnc"),0);
		String mnc=(String)map.get("mnc");
		return MCGConstant.mapMccMncToMccMncNetwork.get(mcc+mnc).getNetworkName();
	}
	}catch(Exception ex){
			logger.error("Exception:: ",ex);
		}finally{
			
		}
		return  networkName;
	}
}
