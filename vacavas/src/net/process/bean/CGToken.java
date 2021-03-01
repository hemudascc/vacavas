package net.process.bean;

import net.util.MConstants;

public class CGToken {

	private long time;
	private int tokenId;
	private int campaignId;
	
	public CGToken(long time,int tokenId,int campaignId){
		this.time=time;
		this.tokenId=tokenId;
		this.campaignId=campaignId;
	}
	
	public String getCGToken(){
		 return time + MConstants.TOKEN_SEPERATOR
				+ tokenId + MConstants.TOKEN_SEPERATOR + 
				campaignId;
	}
	
	public CGToken(String str){
		try{
			String token[]=str.split(MConstants.TOKEN_SEPERATOR);
			this.time=Long.parseLong(token[0]);
			this.tokenId=Integer.parseInt(token[1]);
			this.campaignId=Integer.parseInt(token[2]);			
		}catch(Exception ex){
			campaignId=MConstants.DEFAULT_ADNETWORK_CAMPAIGN_ID;
			try{
//			String token[]=str.split("-");
//			this.time=Long.parseLong(token[0]);
//			this.tokenId=Integer.parseInt(token[1]);
//			this.campaignId=Integer.parseInt(token[2]);
			}catch(Exception e){
				//campaignId=MConstants.DEFAULT_ADNETWORK_CAMPAIGN_ID;
			}
			
		}
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	
	
	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
}
