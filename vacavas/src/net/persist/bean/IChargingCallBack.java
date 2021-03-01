package net.persist.bean;

import java.sql.Timestamp;

public interface IChargingCallBack {
	
	public String getToken();
	public void setToken(String token);
	public void setCampaignId(Integer campaignId);
	public Integer getCampaignId();
	public void setSendToAdnetwork(Boolean sendToAdnetwork);
	public Boolean getSendToAdnetwork();
	public void setActKey(String actKey);
	public String getActKey();
	public void setDuplicate(Boolean duplicate);
	public Boolean getDuplicate();
	public String getAction();
	public void setAction(String action);
	public Timestamp getCreateTime();
	public void setCreateTime(Timestamp createTime);
	public  Integer getTokenId();
	public  void setTokenId(Integer tokenId);
	public  void setSendToAdnetworkBY(String sendToAdnetworkBY);
}
