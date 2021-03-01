package net.persist.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_callback_dump")
public class VWCallbackDump {

	@Id	
	private Integer id;
	@Column(name = "msisdn")
	private String msisdn;
	@Column(name = "action")
	private String action;
	@Column(name = "amount")
	private Double amount;
	@Column(name = "operator_id")
	private Integer operatorId;
	@Column(name = "operator_name")
	private String operatorName;
	
	
	@Column(name = "service_id")
	private Integer serviceId;
	
	@Column(name = "service_name")
	private String serviceName;
	
	
	@Column(name = "product_id")
	private Integer  productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "campaign_id")
	private Integer  campaignId;
	
	@Column(name = "campaign_name")
	private String campaignName;
	
	@Column(name = "token")
	private String token;
	@Column(name = "token_id")
	private Integer tokenId;
	
	@Column(name = "click_id")
	private String clickId;
	@Column(name = "send_to_adnetwork")
	private String sendToAdnetwork;
	
	@Column(name = "query_str")
	private String queryStr;
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name = "status")
	private Boolean status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getTokenId() {
		return tokenId;
	}
	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
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
	public String getClickId() {
		return clickId;
	}
	public void setClickId(String clickId) {
		this.clickId = clickId;
	}
	public String getSendToAdnetwork() {
		return sendToAdnetwork;
	}
	public void setSendToAdnetwork(String sendToAdnetwork) {
		this.sendToAdnetwork = sendToAdnetwork;
	}
	
}
