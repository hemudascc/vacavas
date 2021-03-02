package net.mycomp.macrokiosk.thailand;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_th_hecallback_trans")
public class ThiaHeCallbackTrans implements Serializable{

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "token_id")
	private Integer tokenId;
	private String token;
	@Column(name = "campaign_id")
	private int campaignId;
	private String msisdn;
	@Column(name = "telco_id")
	private Integer telcoId;
	private String 	result;
	@Column(name="query_str")
	private String 	queryStr;
	@Column(name = "create_time")
	private Timestamp createTime;
	private Boolean status;
	
	public ThiaHeCallbackTrans(){}
	
	public ThiaHeCallbackTrans(boolean status){
		this.status=status;
		this.createTime=new Timestamp(System.currentTimeMillis());
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	} 

	public void setToken(String token) {
		this.token = token;
	}

	
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Integer getTelcoId() {
		return telcoId;
	}

	public void setTelcoId(Integer telcoId) {
		this.telcoId = telcoId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
}
