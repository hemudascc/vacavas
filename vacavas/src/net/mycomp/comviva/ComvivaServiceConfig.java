package net.mycomp.comviva;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_comviva_service_config")
public class ComvivaServiceConfig {
	
	@Id
	@Column(name="id")
	private Integer id;
	@Column(name="service_id")
	private Integer serviceId;
	@Column(name="product_id")
	private Integer productId;
	@Column(name="service_name")
	private String serviceName;
	@Column(name="short_code")
	private String shortCode;
	@Column(name="cp_name")
	private String cpName;
	@Column(name="service_node")
	private String serviceNode;
	@Column(name="plan_id")
	private String planId;
	@Column(name="service_type")
	private String serviceType;
	@Column(name="comviva_service_id")
	private String comvivaServiceId;
	@Column(name="comviva_product_id")
	private String comvivaProductId;
	@Column(name="product_name")
	private String productName;
	@Column(name="cp_id")
	private String cpId;
	@Column(name="cp_pwd")
	private String cpPwd;
	@Column(name="request_mode")
	private String requestMode;
	@Column(name="request_type")
	private String requestType;
	@Column(name="ism_id")
	private Integer ismId;
	@Column(name="price")
	private Double price;
	@Column(name="validity")
	private Integer validity;
	@Column(name="lp_image")
	private String lpImage;
	@Column(name="sub_keyword")
	private String subKeyword;
	@Column(name="unsub_keyword")
	private String unsubKeyword;
	@Column(name="portal_url")
	private String portalURL;
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
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public String getServiceNode() {
		return serviceNode;
	}
	public void setServiceNode(String serviceNode) {
		this.serviceNode = serviceNode;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getComvivaServiceId() {
		return comvivaServiceId;
	}
	public void setComvivaServiceId(String comvivaServiceId) {
		this.comvivaServiceId = comvivaServiceId;
	}
	public String getComvivaProductId() {
		return comvivaProductId;
	}
	public void setComvivaProductId(String comvivaProductId) {
		this.comvivaProductId = comvivaProductId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getCpPwd() {
		return cpPwd;
	}
	public void setCpPwd(String cpPwd) {
		this.cpPwd = cpPwd;
	}
	public String getRequestMode() {
		return requestMode;
	}
	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public Integer getIsmId() {
		return ismId;
	}
	public void setIsmId(Integer ismId) {
		this.ismId = ismId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public String getLpImage() {
		return lpImage;
	}
	public void setLpImage(String lpImage) {
		this.lpImage = lpImage;
	}
	public String getSubKeyword() {
		return subKeyword;
	}
	public void setSubKeyword(String subKeyword) {
		this.subKeyword = subKeyword;
	}
	public String getUnsubKeyword() {
		return unsubKeyword;
	}
	public void setUnsubKeyword(String unsubKeyword) {
		this.unsubKeyword = unsubKeyword;
	}
	public String getPortalURL() {
		return portalURL;
	}
	public void setPortalURL(String portalURL) {
		this.portalURL = portalURL;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ComvivaServiceConfig [id=" + id + ", serviceId=" + serviceId + ", productId=" + productId
				+ ", serviceName=" + serviceName + ", shortCode=" + shortCode + ", cpName=" + cpName + ", serviceNode="
				+ serviceNode + ", planId=" + planId + ", serviceType=" + serviceType + ", comvivaServiceId="
				+ comvivaServiceId + ", comvivaProductId=" + comvivaProductId + ", productName=" + productName
				+ ", cpId=" + cpId + ", cpPwd=" + cpPwd + ", requestMode=" + requestMode + ", requestType="
				+ requestType + ", ismId=" + ismId + ", price=" + price + ", validity=" + validity + ", lpImage="
				+ lpImage + ", subKeyword=" + subKeyword + ", unsubKeyword=" + unsubKeyword + ", portalURL=" + portalURL
				+ ", status=" + status + "]";
	}
}