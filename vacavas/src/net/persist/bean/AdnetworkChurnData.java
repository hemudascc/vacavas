package net.persist.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

public class AdnetworkChurnData {

	private long id;
	//private int adnetworkid;
	private int adnetworkCampaignId;	
	private String pubId="NA";
	private String circleName="NA";
	private String device="NA";
	private int activationCount;
	private int churnCount;
	private Timestamp churnDate;
	//private int hour;
	private boolean status=true;
	
	public AdnetworkChurnData(int adnetworkCampaignId,String pubId,String circleName,String device){
		
		this.adnetworkCampaignId=adnetworkCampaignId;
		this.churnDate=new Timestamp(System.currentTimeMillis());
		
		if(!StringUtils.isEmpty(pubId)){
			this.pubId=pubId;
		}
		
		if(!StringUtils.isEmpty(circleName)){
			this.circleName=circleName;
		}
		if(!StringUtils.isEmpty(device)){
			this.device=device;
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public int getAdnetworkCampaignId() {
		return adnetworkCampaignId;
	}
	public void setAdnetworkCampaignId(int adnetworkCampaignId) {
		this.adnetworkCampaignId = adnetworkCampaignId;
	}
	
	public String getPubId() {
		return pubId;
	}
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	
	public int getChurnCount() {
		return churnCount;
	}
	public void setChurnCount(int churnCount) {
		this.churnCount = churnCount;
	}
	public Timestamp getChurnDate() {
		return churnDate;
	}
	public void setChurnDate(Timestamp churnDate) {
		this.churnDate = churnDate;
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getActivationCount() {
		return activationCount;
	}
	public void setActivationCount(int activationCount) {
		this.activationCount = activationCount;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
	
}
