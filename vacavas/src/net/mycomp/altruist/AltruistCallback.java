package net.mycomp.altruist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Call_url")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="tb_altruist_callback")
@Entity
public class AltruistCallback implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="action")
	private String action;
	@Column(name="transaction_id1")
	@XmlElement(name="transaction_id1")
	private String transactionId1;
	@Column(name="transaction_id2")
	@XmlElement(name="transaction_id2")
	private String transactionId2;
	@Column(name="msisdn")
	@XmlElement(name="msisdn")
	private String msisdn;
	@Column(name="package_id")
	@XmlElement(name="package_id")
	private String packageId;
	@Column(name="transaction_type")
	@XmlElement(name="TransactionType")
	private String transactionType;
	@Column(name="ammount")
	@XmlElement(name="Amount")
	private String amount;
	@Column(name="keyword")
	@XmlElement(name="keywrod")
	private String keywrod;
	@Column(name="channel")
	@XmlElement(name="Channel")
	private String channel;

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

	public String getTransactionId1() {
		return transactionId1;
	}

	public void setTransactionId1(String transactionId1) {
		this.transactionId1 = transactionId1;
	}

	public String getTransactionId2() {
		return transactionId2;
	}

	public void setTransactionId2(String transactionId2) {
		this.transactionId2 = transactionId2;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getKeywrod() {
		return keywrod;
	}

	public void setKeywrod(String keywrod) {
		this.keywrod = keywrod;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "AltruistCallback [id=" + id + ", action=" + action + ", transactionId1=" + transactionId1
				+ ", transactionId2=" + transactionId2 + ", msisdn=" + msisdn + ", packageId=" + packageId
				+ ", transactionType=" + transactionType + ", amount=" + amount + ", keywrod=" + keywrod + ", channel="
				+ channel + "]";
	}
}
