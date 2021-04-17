package net.mycomp.timwe;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tb_timwe_notification")
public class TimweNotification implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="action")
	private String acton;
	@Column(name="product_id")
	private String productId;
	@Column(name="pricepoint_id")
	private String pricepointId;
	@Column(name="mcc")
	private String mcc;
	@Column(name="mnc")
	private String mnc;
	@Column(name="text")
	private String text;
	@Column(name="entry_channel")
	private String entryChannel;
	@Column(name="msisdn")
	private String msisdn;
	@Column(name="user_identifier")
	private String userIdentifier;
	@Column(name="large_account")
	private String largeAccount;
	@Column(name="mno_delivery_code")
	private String mnoDeliveryCode;
	@Column(name="transaction_uuid")
	private String transactionUUID;
	@Transient
	private List<String> tags;
	@Column(name="notification_type")
	private String notificationType;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="token")
	private String token;
	@Column(name="token_id")
	private Integer tokenId;
	@Column(name="sent_to_adnetwork")
	private Boolean sendToAdnetwork;
	@Column(name="status")
	private Boolean status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActon() {
		return acton;
	}
	public void setActon(String acton) {
		this.acton = acton;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPricepointId() {
		return pricepointId;
	}
	public void setPricepointId(String pricepointId) {
		this.pricepointId = pricepointId;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMnc() {
		return mnc;
	}
	public void setMnc(String mnc) {
		this.mnc = mnc;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEntryChannel() {
		return entryChannel;
	}
	public void setEntryChannel(String entryChannel) {
		this.entryChannel = entryChannel;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getUserIdentifier() {
		return userIdentifier;
	}
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}
	public String getLargeAccount() {
		return largeAccount;
	}
	public void setLargeAccount(String largeAccount) {
		this.largeAccount = largeAccount;
	}
	public String getMnoDeliveryCode() {
		return mnoDeliveryCode;
	}
	public void setMnoDeliveryCode(String mnoDeliveryCode) {
		this.mnoDeliveryCode = mnoDeliveryCode;
	}
	public String getTransactionUUID() {
		return transactionUUID;
	}
	public void setTransactionUUID(String transactionUUID) {
		this.transactionUUID = transactionUUID;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getSendToAdnetwork() {
		return sendToAdnetwork;
	}
	public void setSendToAdnetwork(Boolean sendToAdnetwork) {
		this.sendToAdnetwork = sendToAdnetwork;
	}
	@Override
	public String toString() {
		return "TimweNotification [id=" + id + ", acton=" + acton + ", productId=" + productId + ", pricepointId="
				+ pricepointId + ", mcc=" + mcc + ", mnc=" + mnc + ", text=" + text + ", entryChannel=" + entryChannel
				+ ", msisdn=" + msisdn + ", userIdentifier=" + userIdentifier + ", largeAccount=" + largeAccount
				+ ", mnoDeliveryCode=" + mnoDeliveryCode + ", transactionUUID=" + transactionUUID + ", tags=" + tags
				+ ", notificationType=" + notificationType + ", createTime=" + createTime + ", token=" + token
				+ ", tokenId=" + tokenId + ", sendToAdnetwork=" + sendToAdnetwork + ", status=" + status + "]";
	}
	public void setDefaultVariables() {
		this.status=Boolean.TRUE;
		this.createTime=new Timestamp(System.currentTimeMillis());
	}
}
