package net.mycomp.tpay;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_tpay_notification")
public class TpayNotification implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	private String action;
	private String tpayAction;
	@Column(name="transaction_id")
	private String transactionId;
	@Column(name="error_message")
	private String errorMessage;
	@Column(name="billing_action")
	private String billingAction;
	@Column(name="billing_number")
	private String billingNumber;
	@Column(name="product_id")
	private String productId;
	@Column(name="msisdn")
	private String msisdn;
	@Column(name="digest")
	private String digest;
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
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getBillingAction() {
		return billingAction;
	}
	public void setBillingAction(String billingAction) {
		this.billingAction = billingAction;
	}
	public String getBillingNumber() {
		return billingNumber;
	}
	public void setBillingNumber(String billingNumber) {
		this.billingNumber = billingNumber;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
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
	@Override
	public String toString() {
		return "TpayNotification [id=" + id + ", action=" + action + ", transactionId=" + transactionId
				+ ", errorMessage=" + errorMessage + ", billingAction=" + billingAction + ", billingNumber="
				+ billingNumber + ", productId=" + productId + ", msisdn=" + msisdn + ", digest=" + digest
				+ ", notificationStatus=" + notificationStatus + ", paymentTransactionStatusCode="
				+ paymentTransactionStatusCode + ", tokenId=" + tokenId + ", token=" + token + ", sendToAdnetwork="
				+ sendToAdnetwork + ", validity=" + validity + ", amount=" + amount + ", queryStr=" + queryStr
				+ ", createTime=" + createTime + ", status=" + status + "]";
	}

}
