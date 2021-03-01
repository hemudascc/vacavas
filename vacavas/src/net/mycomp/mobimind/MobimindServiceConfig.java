package net.mycomp.mobimind;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_mobimind_service_config")
public class MobimindServiceConfig {

	@Id
	private Integer id;
	@Column(name = "service_id")
	private Integer serviceId;
	
	@Column(name = "cc_operator_id")
	private Integer ccOperatorId;
	
	@Column(name = "portal_url")
	private String portalUrl;
	
	@Column(name = "cg_url")
	private String cgUrl;
	
	@Column(name = "mobimind_service_id")
	private String mobimindServiceId;
	
	@Column(name = "validity")
	private Integer validity;
	
	
	@Column(name="country")
	private String country;
	@Column(name="operator_name")
	private String operatorName;
	@Column(name="service_name")
	private String serviceName;
	@Column(name="short_code")
	private String shortCode;
	@Column(name="price")
	private Double price;
	@Column(name="billing_type")
	private String billingType;
	@Column(name="billing_sequence")
	private String billingSequence;	
	@Column(name="channel_id")
	private String channelId;	
	
	private Boolean status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMobimindServiceId() {
		return mobimindServiceId;
	}
	public void setMobimindServiceId(String mobimindServiceId) {
		this.mobimindServiceId = mobimindServiceId;
	}

	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	public String getBillingSequence() {
		return billingSequence;
	}
	public void setBillingSequence(String billingSequence) {
		this.billingSequence = billingSequence;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Integer getCcOperatorId() {
		return ccOperatorId;
	}
	public void setCcOperatorId(Integer ccOperatorId) {
		this.ccOperatorId = ccOperatorId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPortalUrl() {
		return portalUrl;
	}
	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}
	public String getCgUrl() {
		return cgUrl;
	}
	public void setCgUrl(String cgUrl) {
		this.cgUrl = cgUrl;
	}
}
