package net.persist.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_subscribers_reg")
@NamedQueries({ @NamedQuery(name = "SubscriberReg.findSubscriberRegByMsisdn", query = "SELECT b FROM SubscriberReg b "
		+ " where b.msisdn=:msisdn "),
		 @NamedQuery(name = "SubscriberReg.searchSubscriberByLcId", 
		 query = "SELECT b FROM SubscriberReg b  where b.param1=:lifeCycleId ")})


public class SubscriberReg implements Serializable{

	@Id
	@Column(name = "subscriber_id", unique = true, nullable = false)
	@GeneratedValue
	private Integer subscriberId;
	@Column(name = "operator_id")
	private Integer operatorId;
	@Column(name = "circle_id")
	private Integer circleId;
	private String msisdn;
	private Integer status;
	@Column(name = "status_descp")
	private String statusDescp;
	@Column(name = "reg_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp regDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "validity_from")
	private Timestamp validityFrom;
	@Column(name = "validity_to")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp validityTo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "sub_date")
	private Timestamp subDate;
	@Column(name = "service_id")
	private Integer serviceId;
	@Column(name = "total_download_count")
	private Integer totalDownloadCount;
	@Column(name = "last_download_date")
	private Timestamp lastDownloadDate;
	@Column(name = "last_activity")
	private String lastActivity;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "unsub_date")
	private Timestamp unsubDate;
	@Column(name = "campaign_id")
	private Integer campaignId;
	@Column(name = "token")
	private String token;
	@Column(name = "last_update")
	private Timestamp lastUpdate;

	
	@Column(name = "last_renewal_retry_date")
	private Timestamp lastRenewalRetryDate;
	
	@Column(name = "token_id")
	private Integer tokenId;	
	
	@Column(name = "param1")
	private String param1;
	
	@Column(name = "subscription_type")
	private String subscriptionType;
	@Column(name="product_id")
	private Integer productId;
	
	@Column(name = "param2")
	private String param2;
	@Column(name = "param3")
	private String param3;
	
	@Column(name = "mode")
	private String mode;
	
	@Transient
	private String action;//ACT,DCT
	
	@Transient
	private boolean churn;
	@Transient
	private boolean duplicateRequest;
	
	public String toString() {

		Field[] fields = this.getClass().getDeclaredFields();
		String str = this.getClass().getName();
		try {
			for (Field field : fields) {
				str += field.getName() + "=" + field.get(this) + ",";
			}
		} catch (IllegalArgumentException ex) {
			System.out.println(ex);
		} catch (IllegalAccessException ex) {
			System.out.println(ex);
		}
		return str;
	}

	public Integer getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(Integer subscriberId) {
		this.subscriberId = subscriberId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getCircleId() {
		return circleId;
	}

	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusDescp() {
		return statusDescp;
	}

	public void setStatusDescp(String statusDescp) {
		this.statusDescp = statusDescp;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public Timestamp getSubDate() {
		return subDate;
	}

	public void setSubDate(Timestamp subDate) {
		this.subDate = subDate;
	}

	public Integer getTotalDownloadCount() {
		return totalDownloadCount;
	}

	public void setTotalDownloadCount(Integer totalDownloadCount) {
		this.totalDownloadCount = totalDownloadCount;
	}

	public Timestamp getLastDownloadDate() {
		return lastDownloadDate;
	}

	public void setLastDownloadDate(Timestamp lastDownloadDate) {
		this.lastDownloadDate = lastDownloadDate;
	}

	public String getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(String lastActivity) {
		this.lastActivity = lastActivity;
	}

	public Timestamp getUnsubDate() {
		return unsubDate;
	}

	public void setUnsubDate(Timestamp unsubDate) {
		this.unsubDate = unsubDate;
	}

	public Integer getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	
	public boolean isChurn() {
		return churn;
	}

	public void setChurn(boolean churn) {
		this.churn = churn;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isDuplicateRequest() {
		return duplicateRequest;
	}

	public void setDuplicateRequest(boolean duplicateRequest) {
		this.duplicateRequest = duplicateRequest;
	}

	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public Timestamp getValidityFrom() {
		return validityFrom;
	}

	public void setValidityFrom(Timestamp validityFrom) {
		this.validityFrom = validityFrom;
	}

	public Timestamp getValidityTo() {
		return validityTo;
	}

	public void setValidityTo(Timestamp validityTo) {
		this.validityTo = validityTo;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public Timestamp getLastRenewalRetryDate() {
		return lastRenewalRetryDate;
	}

	public void setLastRenewalRetryDate(Timestamp lastRenewalRetryDate) {
		this.lastRenewalRetryDate = lastRenewalRetryDate;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
