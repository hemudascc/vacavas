package net.mycomp.intarget;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_intarget_config")
public class InTargetConfig {

	@Id
	private Integer id;
	@Column(name = "service_id")
	private Integer serviceId;
	@Column(name="operator_id")
	private Integer operatorId;
	
	@Column(name = "service_code")
	private String serviceCode;
	
	@Column(name = "servcie_name")
	private String servcieName;
	@Column(name="network_id")
	private Integer networkId;
	private String userName;
	private String password;
	@Column(name="keyword")
	private String keyword;
	@Column(name="unsub_keyword")
	private String unsubKeyword;
	
	@Column(name="price_point")
	private Double pricePoint;
	@Column(name="price_point_code")
	private String pricePointCode;
	
	@Column(name="currency_desc")
	private String currencyDesc;
	
	private Integer validity;
	@Column(name="validity_code")
	private String validityCode;
	
	@Column(name = "validity_desc")
	private String validityDesc;
	@Column(name = "ticket_value")
	private String ticketValue;
	
	@Column(name="portal_url")
	private String portalUrl;
	
	@Column(name="billing_activation_message")
	private String billingActivationmessage;
	
	@Column(name="welcome_activation_message")
	private String welcomeActivationMessage;
	
	@Column(name="deactivation_message")
	private String deactivationMessage;
	
	
	private Boolean status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public String getServcieName() {
		return servcieName;
	}
	public void setServcieName(String servcieName) {
		this.servcieName = servcieName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTicketValue() {
		return ticketValue;
	}
	public void setTicketValue(String ticketValue) {
		this.ticketValue = ticketValue;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getPortalUrl() {
		return portalUrl;
	}
	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Double getPricePoint() {
		return pricePoint;
	}
	public void setPricePoint(Double pricePoint) {
		this.pricePoint = pricePoint;
	}
	public String getCurrencyDesc() {
		return currencyDesc;
	}
	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public String getValidityDesc() {
		return validityDesc;
	}
	public void setValidityDesc(String validityDesc) {
		this.validityDesc = validityDesc;
	}
	public String getValidityCode() {
		return validityCode;
	}
	public void setValidityCode(String validityCode) {
		this.validityCode = validityCode;
	}
	public String getPricePointCode() {
		return pricePointCode;
	}
	public void setPricePointCode(String pricePointCode) {
		this.pricePointCode = pricePointCode;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public Integer getNetworkId() {
		return networkId;
	}
	public void setNetworkId(Integer networkId) {
		this.networkId = networkId;
	}
	
	public String getUnsubKeyword() {
		return unsubKeyword;
	}
	public void setUnsubKeyword(String unsubKeyword) {
		this.unsubKeyword = unsubKeyword;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getBillingActivationmessage() {
		return billingActivationmessage;
	}
	public void setBillingActivationmessage(String billingActivationmessage) {
		this.billingActivationmessage = billingActivationmessage;
	}
	public String getWelcomeActivationMessage() {
		return welcomeActivationMessage;
	}
	public void setWelcomeActivationMessage(String welcomeActivationMessage) {
		this.welcomeActivationMessage = welcomeActivationMessage;
	}
	public String getDeactivationMessage() {
		return deactivationMessage;
	}
	public void setDeactivationMessage(String deactivationMessage) {
		this.deactivationMessage = deactivationMessage;
	}
	
	
}
