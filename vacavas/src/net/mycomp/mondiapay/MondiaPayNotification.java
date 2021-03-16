package net.mycomp.mondiapay;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tb_mondia_pay_notification")
public class MondiaPayNotification implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="uuid")
	private String uuid;
	@Column(name="purchase_token")
	private String purchaseToken;
	@Column(name="subscription_id")
	private Integer subscriptionId;
	@Column(name="subscription_type_id")
	private Integer subscriptionTypeId; 
	@Column(name="channel")
	private String channel;
	@Column(name="event")
	private String event;
	@Column(name="reason")
	private String reason;
	@JsonProperty("status")
	@Column(name="mondia_status")
	private String mondiaStatus;
	@Column(name="sub_status")
	private String subStatus;
	@Column(name="start_date")
	private String startDate;
	@Column(name="amount")
	private Double amount;
	@Column(name="currency")
	private String currency;
	@Column(name="token")
	private String token;
	@Column(name="action")
	private String action;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="sent_to_adnetwork")
	private Boolean sendToAdnetwork;
	
	@JsonProperty("price")
	private void unpackNested(Map<String,Object> price) {
        this.amount = Double.valueOf(Objects.toString(price.get("amount")));
        this.currency = Objects.toString(price.get("currency"));
		this.createTime=new Timestamp(System.currentTimeMillis());
		this.sendToAdnetwork=false;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPurchaseToken() {
		return purchaseToken;
	}

	public void setPurchaseToken(String purchaseToken) {
		this.purchaseToken = purchaseToken;
	}

	public Integer getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Integer getSubscriptionTypeId() {
		return subscriptionTypeId;
	}

	public void setSubscriptionTypeId(Integer subscriptionTypeId) {
		this.subscriptionTypeId = subscriptionTypeId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMondiaStatus() {
		return mondiaStatus;
	}

	public void setMondiaStatus(String mondiaStatus) {
		this.mondiaStatus = mondiaStatus;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Boolean getSendToAdnetwork() {
		return sendToAdnetwork;
	}

	public void setSendToAdnetwork(Boolean sendToAdnetwork) {
		this.sendToAdnetwork = sendToAdnetwork;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "MondiaPayNotification [id=" + id + ", uuid=" + uuid + ", purchaseToken=" + purchaseToken
				+ ", subscriptionId=" + subscriptionId + ", subscriptionTypeId=" + subscriptionTypeId + ", channel="
				+ channel + ", event=" + event + ", reason=" + reason + ", mondiaStatus=" + mondiaStatus
				+ ", subStatus=" + subStatus + ", startDate=" + startDate + ", amount=" + amount + ", currency="
				+ currency + ", token=" + token + ", action=" + action + ", createTime=" + createTime
				+ ", sendToAdnetwork=" + sendToAdnetwork + "]";
	}
	
	
}
