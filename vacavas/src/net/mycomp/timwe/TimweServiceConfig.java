package net.mycomp.timwe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_timwe_service_config")
public class TimweServiceConfig {
	
	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	@Column(name="service_id")
	private Integer serviceId;
	@Column(name="service_name")
	private String serviceName;
	@Column(name="product_id")
	private Integer productId;
	@Column(name="operator_id")
	private Integer operatorId;
	@Column(name="sub_key")
	private String subKey;
	@Column(name="unsub_key")
	private String unsubKey;
	@Column(name="short_code")
	private String shortCode;
	@Column(name="pricepoint_id")
	private String pricePointId;
	@Column(name="timwe_product_id")
	private String timweProductId;
	@Column(name="mcc")
	private String mcc;
	@Column(name="mnc")
	private String mnc;
	@Column(name="channel")
	private String channel;
	@Column(name="context")
	private String context;
	@Column(name="validity")
	private Integer validity;
	@Column(name="price")
	private String price;
	@Column(name="validity_desc")
	private String validityDesc;
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
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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
	public String getSubKey() {
		return subKey;
	}
	public void setSubKey(String subKey) {
		this.subKey = subKey;
	}
	public String getUnsubKey() {
		return unsubKey;
	}
	public void setUnsubKey(String unsubKey) {
		this.unsubKey = unsubKey;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public String getPricePointId() {
		return pricePointId;
	}
	public void setPricePointId(String pricePointId) {
		this.pricePointId = pricePointId;
	}
	public String getTimweProductId() {
		return timweProductId;
	}
	public void setTimweProductId(String timweProductId) {
		this.timweProductId = timweProductId;
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
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getValidityDesc() {
		return validityDesc;
	}
	public void setValidityDesc(String validityDesc) {
		this.validityDesc = validityDesc;
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
		return "TimweServiceConfig [id=" + id + ", serviceId=" + serviceId + ", serviceName=" + serviceName
				+ ", productId=" + productId + ", operatorId=" + operatorId + ", subKey=" + subKey + ", unsubKey="
				+ unsubKey + ", shortCode=" + shortCode + ", pricePointId=" + pricePointId + ", timweProductId="
				+ timweProductId + ", mcc=" + mcc + ", mnc=" + mnc + ", channel=" + channel + ", context=" + context
				+ ", validity=" + validity + ", price=" + price + ", validityDesc=" + validityDesc + ", portalURL="
				+ portalURL + ", status=" + status + "]";
	}
	
}