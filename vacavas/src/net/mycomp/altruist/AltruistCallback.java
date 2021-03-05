package net.mycomp.altruist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Call_url")
@XmlAccessorType(XmlAccessType.FIELD)
public class AltruistCallback implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@XmlElement(name="transaction_id1")
	private String transactionId1;
	@XmlElement(name="transaction_id2")
	private String transactionId2;
	@XmlElement(name="msisdn")
	private String msisdn;
	@XmlElement(name="package_id")
	@Column(name="package_id")
	private String packageId;
	@XmlElement(name="TransactionType")
	private String transactionType;
	@XmlElement(name="Amount")
	private String amount;
	@XmlElement(name="keywrod")
	private String keywrod;
	@XmlElement(name="Channel")
	private String channel;
	
	
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
		return "AltruistCallback [transactionId1=" + transactionId1 + ", transactionId2=" + transactionId2 + ", msisdn="
				+ msisdn + ", packageId=" + packageId + ", transactionType=" + transactionType + ", amount=" + amount
				+ ", keywrod=" + keywrod + ", channel=" + channel + "]";
	}
}
