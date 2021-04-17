/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.adnetwork.callback.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Objects;

import net.common.service.IDaoService;
import net.persist.bean.AdnetworkOperatorConfig;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.Adnetworks;
import net.persist.bean.LiveReport;
import net.persist.bean.VWServiceCampaignDetail;
import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;
import net.util.MConstants;
import net.util.MData;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adnetworkCallbackService")
public class AdnetworkCallbackService {

	private static final Logger logger = Logger.getLogger(AdnetworkCallbackService.class);

	@Autowired
	private IDaoService daoService;

//	public AdnetworkToken sendAdnetworkCallBack(String action, String msisdn, Integer tokenId, 
//			Integer opId,Double amount) {
		public AdnetworkToken sendAdnetworkCallBack(LiveReport liveReport) {
		AdnetworkToken adnetworkToken = null;
		String url = null;
		try {

			
			logger.info("sendAdnetworkCallBack::   : ");
        	logger.info("sendAdnetworkCallBack:: Callback  : "+liveReport.getTokenId());
			adnetworkToken = daoService.findAdnetworkTokenById(liveReport.getTokenId());
			logger.info("sendAdnetworkCallBack::adnetworkToken:: " + adnetworkToken);
			VWServiceCampaignDetail vwServiceCampaignDetail = 
					MData.mapCamapignIdToVWServiceCampaignDetail.get(adnetworkToken.getCampaignId());
			logger.info("sendAdnetworkCallBack::campaignDetails:: " + vwServiceCampaignDetail);
			Adnetworks adnetworks = MData.mapAdnetworks.get(vwServiceCampaignDetail.getAdNetworkId());
			logger.info("sendAdnetworkCallBack::adnetworks:: " + adnetworks);
			
			AdnetworkOperatorConfig adnetworkOperatorConfig = MData.mapAdnetworkOpConfig
					.get(liveReport.getOperatorId()).get(adnetworks.getAdNetworkId());
			
			logger.info("sendAdnetworkCallBack::adnetworkOperatorConfig " + adnetworkOperatorConfig);
				
			boolean isSendToAdnetwork = false;

			if (liveReport.getConversionCount()<=0) {
				//adnetworkToken.setDuplicateConversion(true);
				logger.debug("sendAdnetworkCallBack::conversion count "+liveReport.getConversionCount()+" not send conversion");
				return adnetworkToken;
			}
			
		if (adnetworkToken != null && adnetworkToken.getConversionReceived() != null
					&& adnetworkToken.getConversionReceived()) {
				adnetworkToken.setDuplicateConversion(true);
				logger.debug("sendAdnetworkCallBack::adnetworkToken != null && adnetworkToken.getConversionReceived() != null:: " );
				return adnetworkToken;
			}
			
			

			if (liveReport.getAction() != null && liveReport.getAction().equalsIgnoreCase(MConstants.ACT)) {
				logger.debug("sendAdnetworkCallBack::isSendToAdnetwork:act " + isSendToAdnetwork);

			

//				if (adnetworkOperatorConfig.getReverseStatus() != null
//						&& adnetworkOperatorConfig.getReverseStatus()) {
//					isSendToAdnetwork = (adnetworkOperatorConfig.atomicActReverseSkipNumber
//							.getAndUpdate(n -> n < adnetworkOperatorConfig.getReverseSkipNumber() ? n + 1 : 1)
//							% adnetworkOperatorConfig.getReverseSkipNumber()) == 0;
//					logger.debug("sendAdnetworkCallBack::isSendToAdnetwork::::: " + isSendToAdnetwork);
//					logger.debug("sendAdnetworkCallBack::isSendToAdnetwork::::: isSendToAdnetwork" + adnetworkOperatorConfig+",adnetworkToken:"+adnetworkToken);
//				} else if(adnetworkOperatorConfig.getStatus()!=null&&
//						adnetworkOperatorConfig.getStatus()){
//					isSendToAdnetwork = (adnetworkOperatorConfig.getSkipNumber() <= 1)
//							|| ((adnetworkOperatorConfig.atomicActSkipNumber
//									.getAndUpdate(n -> n < adnetworkOperatorConfig.getSkipNumber() ? n + 1 : 1)
//									% adnetworkOperatorConfig.getSkipNumber()) > 0);
//					logger.debug("sendAdnetworkCallBack::isSendToAdnetwork::::: " + isSendToAdnetwork);
//					logger.debug("sendAdnetworkCallBack:"
//							+ ":isSendToAdnetwork::::: isSendToAdnetwork" + adnetworkOperatorConfig+",adnetworkToken:"+adnetworkToken+",isSendToAdnetwork:"+isSendToAdnetwork);
//					
//				}			
			
			if (liveReport.getAction() != null && liveReport.getAction().equalsIgnoreCase(MConstants.ACT)&&
					(liveReport.getAmount()>0||(adnetworkOperatorConfig.getZeroPriceActivationSend()!=null
					&&adnetworkOperatorConfig.getZeroPriceActivationSend()))) {	
				
					isSendToAdnetwork=isSendActMoreThanZeroPricePointAdnetworkCallBack(adnetworkToken, adnetworkOperatorConfig);
				}			
			} 

			if (isSendToAdnetwork && adnetworkToken != null && adnetworkToken.getToken() != null
					&& adnetworkToken.getConversionSendToAdntework() != null
					&& !adnetworkToken.getConversionSendToAdntework()) {
				
				logger.debug("sendAdnetworkCallBack:: url : " + adnetworkToken.getTokenId()+",opconfig id"+adnetworkOperatorConfig.getAdnetworkOperatorConfigId());  
				String urlTemplate = adnetworks.getCallbackUrlTemplate();
				logger.info("sendAdnetworkCallBack:: urlTemplate 1 : " + urlTemplate);
			    urlTemplate = urlTemplate.replaceAll("<token>",
			    		URLEncoder.encode(adnetworkToken.getToken(), "utf-8"))
			    		.replaceAll("<payout>",
			    		URLEncoder.encode(Objects.toString(liveReport.getAmount()), "utf-8"));
			    
				logger.info("sendAdnetworkCallBack:: urlTemplate 2 : " + urlTemplate);
				logger.debug("sendAdnetworkCallBack:: url : " + adnetworkToken.getTokenId()+",opconfig id"+adnetworkOperatorConfig.getAdnetworkOperatorConfigId()+"::urlTemplate"+urlTemplate);
				
				if(adnetworkToken.getPubId()!=null){
				   urlTemplate = urlTemplate.replaceAll("<pubid>", 
						   URLEncoder.encode(adnetworkToken.getPubId(), "utf-8"));
				}
				
			
				
				if(adnetworkOperatorConfig.getOpCpaValue()!=null){
					url=urlTemplate.replaceAll("<cpa>",""+ adnetworkOperatorConfig.getOpCpaValue());					
				}
				else{
					url=urlTemplate;
				} 
				
				logger.debug("sendAdnetworkCallBack:: url : " + adnetworkToken.getTokenId()+",opconfig id"+adnetworkOperatorConfig.getAdnetworkOperatorConfigId()+"::urlTemplate"+url);
				logger.info("sendAdnetworkCallBack:: url : " + url);
               
				
				
				
				
				adnetworkToken.setAdnetworkCallbackUrl(url);
				HttpURLConnectionUtil httpURLConnectionUtil = new HttpURLConnectionUtil();
				HTTPResponse hTTPResponse = httpURLConnectionUtil.sendGet(url);
				
				adnetworkToken.setAdnetworkCallbackResponse(
						hTTPResponse.getResponseCode() + ":" + hTTPResponse.getResponseStr());
				if (hTTPResponse.getResponseCode() == 200) {
					adnetworkToken.setConversionSendToAdntework(true);
					adnetworkToken.setConversionSendToAdnteworkTime(new Timestamp(System.currentTimeMillis()));
				}

			} else {

				adnetworkToken.setConversionSendToAdntework(false);
				adnetworkToken.setConversionSendToAdnteworkTime(null);
				//logger.debug("sendAdnetworkCallBack:: is send 0 ");
				logger.debug("sendAdnetworkCallBack:: is send 0 " + adnetworkToken.getTokenId()+",opconfig id"+adnetworkOperatorConfig.getAdnetworkOperatorConfigId()+"::url:"+url);
			}

		} catch (Exception ex) {
			logger.error("sendAdnetworkCallBack:: Exception : " + ex+"adnetworkToken::"+adnetworkToken);
		} finally {
			if (adnetworkToken != null && !adnetworkToken.isDuplicateConversion() && liveReport.getAction() != null && liveReport.getAction().equalsIgnoreCase(MConstants.ACT)) {
				adnetworkToken.setConversionReceived(true);
				adnetworkToken.setConversionReceivedTime(new Timestamp(System.currentTimeMillis()));
				daoService.updateObject(adnetworkToken);
			}
		}
		return adnetworkToken;
	}

	public boolean sendChurnDCTAdnetworkCallBack(Integer opId, AdnetworkToken adnetworkToken) {

		boolean success = false;
		try {

			if (!adnetworkToken.getConversionSendToAdntework()) {
				return false;
			}
			if (adnetworkToken.getDctAdnetworkCallbackTime() != null) {
				return false;
			}

			VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(adnetworkToken.getCampaignId());
			Adnetworks adnetworks = MData.mapAdnetworks.get(vwServiceCampaignDetail.getAdNetworkId());
			String dctCallBackTemplate = adnetworks.getDctCallbackUrlTemplate();

			dctCallBackTemplate = dctCallBackTemplate.replaceAll("<token>",
					URLEncoder.encode(adnetworkToken.getToken(), "utf-8"));
			logger.info("sendAdnetworkCallBack:: urlTemplate 2 : " + dctCallBackTemplate);
			dctCallBackTemplate = dctCallBackTemplate.replaceAll("<dt>", URLEncoder
					.encode(MConstants.dfYYYYMMDDhhmmTZD.format(new Timestamp(System.currentTimeMillis())), "utf-8"));
			adnetworkToken.setDctAdnetworkCallbackUrl(dctCallBackTemplate);

			HttpURLConnectionUtil httpURLConnectionUtil = new HttpURLConnectionUtil();
			HTTPResponse hTTPResponse = httpURLConnectionUtil.sendGet(dctCallBackTemplate);
			adnetworkToken.setDctAdnetworkCallbackTime(new Timestamp(System.currentTimeMillis()));
			adnetworkToken.setDctAdnetworkCallbackResponse(
					hTTPResponse.getResponseCode() + ":" + hTTPResponse.getResponseStr());
			if (hTTPResponse.getResponseCode() == 200) {
				success = true;
			}
		} catch (Exception ex) {
			success = false;
			logger.error("sendChurnDCTAdnetworkCallBack:: Exception::  " + ex);
		} finally {
			daoService.updateObject(adnetworkToken);
		}
		return success;
	}

		
	
	public boolean sendManualAdnetworkCallBack(Integer opId, AdnetworkToken adnetworkToken,String cronType) throws Exception{
		boolean success = false;
		String url=null;
		try {
			
			logger.debug("sendAdnetworkCallBack::first");
			if (adnetworkToken.getConversionSendToAdntework()) {
				return false;
			}	
			logger.debug("sendAdnetworkCallBack::first1");
			VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(adnetworkToken.getCampaignId());
			Adnetworks adnetworks = MData.mapAdnetworks.get(vwServiceCampaignDetail.getAdNetworkId());
			
			AdnetworkOperatorConfig adnetworkOperatorConfig = MData.mapAdnetworkOpConfig
					.get(opId).get(adnetworks.getAdNetworkId());
			
			logger.debug("sendAdnetworkCallBack::adnetworkOperatorConfig::");
			String urlTemplate="";
			if(cronType==MConstants.AUTOSENT_CRON){
				urlTemplate="http://mazzatv.in/jcms-voda/postpacktest.jsp";
			}
			else {
			
			urlTemplate = adnetworks.getCallbackUrlTemplate();
			}
			
			logger.info("sendAdnetworkCallBack:: urlTemplate 1 : " + urlTemplate);
			

			urlTemplate = urlTemplate.replaceAll("<token>", URLEncoder.encode(adnetworkToken.getToken(), "utf-8"));
			logger.info("sendAdnetworkCallBack:: urlTemplate 2 : " + urlTemplate);
			logger.debug("sendAdnetworkCallBack:: url : " + adnetworkToken.getTokenId()+",opconfig id"+adnetworkOperatorConfig.getAdnetworkOperatorConfigId()+"::urlTemplate"+urlTemplate);
			
			if(adnetworkToken.getPubId()!=null){
			   urlTemplate = urlTemplate.replaceAll("<pubid>", URLEncoder.encode(adnetworkToken.getPubId(), "utf-8"));
			}
			
			if(adnetworkOperatorConfig.getOpCpaValue()!=null){
				url=urlTemplate.replaceAll("<cpa>",""+ adnetworkOperatorConfig.getOpCpaValue());
				
			}
			else{
				url=urlTemplate;
			} 
			
			logger.debug("sendAdnetworkCallBack:: url : " + adnetworkToken.getTokenId()+",opconfig id"+adnetworkOperatorConfig.getAdnetworkOperatorConfigId()+"::urlTemplate"+url);
			logger.info("sendAdnetworkCallBack:: url : " + url);
           
			
			
			
			
			adnetworkToken.setAdnetworkCallbackUrl(url);
			HttpURLConnectionUtil httpURLConnectionUtil = new HttpURLConnectionUtil();
			HTTPResponse hTTPResponse = httpURLConnectionUtil.sendGet(url);
			
			adnetworkToken.setAdnetworkCallbackResponse(
					hTTPResponse.getResponseCode() + ":" + hTTPResponse.getResponseStr());
			if (hTTPResponse.getResponseCode() == 200) {
				adnetworkToken.setConversionSendToAdntework(true);
				adnetworkToken.setConversionSendToAdnteworkTime(new Timestamp(System.currentTimeMillis()));
				success = true;
			}

		else {

			adnetworkToken.setConversionSendToAdntework(false);
			adnetworkToken.setConversionSendToAdnteworkTime(null);
			//logger.debug("sendAdnetworkCallBack:: is send 0 ");
			logger.debug("sendAdnetworkCallBack:: is send 0 " + adnetworkToken.getTokenId()
					+",opconfig id"+adnetworkOperatorConfig.getAdnetworkOperatorConfigId()+"::url:"+url);
		}

		} catch (Exception ex) {
			success = false;
			logger.error("sendManualAdnetworkCallBack:: Exception::  " + ex);
			throw ex;
			
		} finally {
				daoService.updateObject(adnetworkToken);
		}		
		return success;
	}
	
	public boolean isSendActMoreThanZeroPricePointAdnetworkCallBack(AdnetworkToken adnetworkToken,
			AdnetworkOperatorConfig adnetworkOperatorConfig
			) {		
		boolean isSendToAdnetwork=!(adnetworkOperatorConfig.atomicActSkipNumber.
				 getAndUpdate(2, n->n>=adnetworkOperatorConfig.atomicActSkipNumber.get(1)?1:n+1)
				 <=adnetworkOperatorConfig.atomicActSkipNumber.get(0));
		return isSendToAdnetwork;
	}

	public static void main(String arg[]){
		
	}
	
	}
