package net.mycomp.tpay;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tb_tpay_request")
public class TpayRequest {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String action;
	@Column(name = "msisdn")
	private String msisdn;
	private String token;
	@Column(name = "campaign_id")
	private Integer campaignId;
    private String request;
    @Column(name = "response_code")
	private Integer responseCode;
    private String response;
    @Column(name = "create_time")
	private Timestamp createTime;
    private Boolean status;
    
    public TpayRequest() {}
	public TpayRequest(Boolean status,String action) {
		this.action = action;
		this.status = status;
		this.createTime=new Timestamp(System.currentTimeMillis());
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "TpaySubscriptionContractRequest [id=" + id + ", action=" + action + ", msisdn=" + msisdn + ", token="
				+ token + ", campaignId=" + campaignId + ", request=" + request + ", responseCode=" + responseCode
				+ ", response=" + response + ", createTime=" + createTime + ", status=" + status + "]";
	}
}
