package net.factory;

import net.persist.bean.AdnetworkChurnData;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.LiveReport;


public class AdnetworkChurnDataFactory {

	
	public static AdnetworkChurnData createAdnetworkChurnDataForActivation(LiveReport liveReport,
			AdnetworkToken adnetworkToken){
		String pubId=null;
		if(liveReport.isDuplicateRequest()){
			return null;
		}
		if(adnetworkToken!=null){
			pubId=adnetworkToken.getPubId();
		}
		AdnetworkChurnData adnetworkChurnData=new AdnetworkChurnData(liveReport.getAdnetworkCampaignId(),
				pubId,liveReport.getCircleName(),null);
		adnetworkChurnData.setActivationCount(1);
		return adnetworkChurnData;
	}
	
	public static AdnetworkChurnData createAdnetworkChurnDataForChurnDeactivation(LiveReport liveReport,
			AdnetworkToken adnetworkToken){
		//(int adnetworkCampaignId,String pubId,String circleName,String device)
		String pubId=null;
		if(liveReport.isDuplicateRequest()){
			return null;
		}
		
		if(adnetworkToken!=null){
			pubId=adnetworkToken.getPubId();
		}
		
		AdnetworkChurnData adnetworkChurnData=new AdnetworkChurnData(liveReport.getAdnetworkCampaignId(),
				pubId,liveReport.getCircleName(),null);
		adnetworkChurnData.setChurnCount(1);
		return adnetworkChurnData;
	}
}

