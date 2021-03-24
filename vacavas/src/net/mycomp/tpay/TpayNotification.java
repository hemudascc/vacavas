package net.mycomp.tpay;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_tpay_vaca_notification")
public class TpayNotification implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="action")
	private String action;
	@Column(name="tpay_action")
	private String tpayAction;
	@Column(name="msisdn")
	private String msisdn;
	@Column(name = "notification_status")
	private String notificationStatus;
	@Column(name="payment_transaction_status_code")
	private String paymentTransactionStatusCode;
	@Column(name = "token_id")
	private Integer tokenId;
	@Column(name = "token")
	private String token;
	@Column(name = "send_to_adnetwork")
	private Boolean sendToAdnetwork;
	@Column(name = "validity")
	private Integer validity;
	@Column(name = "amount")
	private String amount;
	@Column(name = "query_str")
	private String queryStr;
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name="subscription_contract_id")
	private String subscriptionContractId;
	private Boolean status;
	@Column(name="product_catalog_name")
	private String productCatalogName;
	
	public TpayNotification() {}
	public TpayNotification(Boolean status) {
		this.createTime=new Timestamp(System.currentTimeMillis());
		this.status=status;
		this.sendToAdnetwork=false;
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
	public String getTpayAction() {
		return tpayAction;
	}
	public void setTpayAction(String tpayAction) {
		this.tpayAction = tpayAction;
	}
	public String getNotificationStatus() {
		return notificationStatus;
	}
	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	public String getPaymentTransactionStatusCode() {
		return paymentTransactionStatusCode;
	}
	public void setPaymentTransactionStatusCode(String paymentTransactionStatusCode) {
		this.paymentTransactionStatusCode = paymentTransactionStatusCode;
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
	public Boolean getSendToAdnetwork() {
		return sendToAdnetwork;
	}
	public void setSendToAdnetwork(Boolean sendToAdnetwork) {
		this.sendToAdnetwork = sendToAdnetwork;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	
	public String getSubscriptionContractId() {
		return subscriptionContractId;
	}
	public void setSubscriptionContractId(String subscriptionContractId) {
		this.subscriptionContractId = subscriptionContractId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getProductCatalogName() {
		return productCatalogName;
	}
	public void setProductCatalogName(String productCatalogName) {
		this.productCatalogName = productCatalogName;
	}
	
	@Override
	public String toString() {
		return "TpayNotification [id=" + id + ", action=" + action + ", tpayAction=" + tpayAction + ", msisdn=" + msisdn
				+ ", notificationStatus=" + notificationStatus + ", paymentTransactionStatusCode="
				+ paymentTransactionStatusCode + ", tokenId=" + tokenId + ", token=" + token + ", sendToAdnetwork="
				+ sendToAdnetwork + ", validity=" + validity + ", amount=" + amount + ", queryStr=" + queryStr
				+ ", createTime=" + createTime + ", subscriptionContractId=" + subscriptionContractId + ", status="
				+ status + ", productCatalogName=" + productCatalogName + "]";
	}	
}