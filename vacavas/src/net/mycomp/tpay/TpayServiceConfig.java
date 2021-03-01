package net.mycomp.tpay;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_tpay_service_config")
public class TpayServiceConfig {
	
	@Id
	private Integer id;
	
	@Column(name="service_id")
	private Integer serviceId;
	
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "operator_id")
	private Integer operatorId;
	
	@Column(name="service_name")
	private String serviceName;
	
	@Column(name="operator_code")
	private String operatorCode;
	
	@Column(name="subscription_plan_id")
	private String subscriptionPlanId;
	
	@Column(name="catalog_name")
	private String catalogName;
	
	@Column(name="payment_product_id")
	private String paymentProductId;
	
	@Column(name="country")
	private String country;
	
	@Column(name="operator_name")
	private String operatorName;
	
	@Column(name="short_code")
	private String shortCode;
	
	@Column(name="unsub_keyword")
	private String unsubKeyword;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="price")
	private String price;
	
	@Column(name="billing_sequence")
	private String billingSequence;
	
	@Column(name="portal_url")
	private String protalUrl;
	
	@Column(name="validity")
	private Integer validity; 
	
	@Column(name="status")
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getSubscriptionPlanId() {
		return subscriptionPlanId;
	}

	public void setSubscriptionPlanId(String subscriptionPlanId) {
		this.subscriptionPlanId = subscriptionPlanId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getPaymentProductId() {
		return paymentProductId;
	}

	public void setPaymentProductId(String paymentProductId) {
		this.paymentProductId = paymentProductId;
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

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBillingSequence() {
		return billingSequence;
	}

	public void setBillingSequence(String billingSequence) {
		this.billingSequence = billingSequence;
	}

	public String getProtalUrl() {
		return protalUrl;
	}

	public void setProtalUrl(String protalUrl) {
		this.protalUrl = protalUrl;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getUnsubKeyword() {
		return unsubKeyword;
	}

	public void setUnsubKeyword(String unsubKeyword) {
		this.unsubKeyword = unsubKeyword;
	}

	public Integer getValidity() {
		return validity;
	}

	public void setValidity(Integer validity) {
		this.validity = validity;
	}

	@Override
	public String toString() {
		return "TpayServiceConfig [id=" + id + ", serviceId=" + serviceId + ", productId=" + productId + ", operatorId="
				+ operatorId + ", serviceName=" + serviceName + ", operatorCode=" + operatorCode
				+ ", subscriptionPlanId=" + subscriptionPlanId + ", catalogName=" + catalogName + ", paymentProductId="
				+ paymentProductId + ", country=" + country + ", operatorName=" + operatorName + ", shortCode="
				+ shortCode + ", unsubKeyword=" + unsubKeyword + ", currency=" + currency + ", price=" + price
				+ ", billingSequence=" + billingSequence + ", protalUrl=" + protalUrl + ", validity=" + validity
				+ ", status=" + status + "]";
	}
	
}
