package net.mycomp.messagecloud;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_messagecloud_service_config")
public class MessageCloudServiceConfig {

	@Id
	private Integer id;
	@Column(name="service_id")
	private Integer serviceId;
	
	@Column(name="product_id")
	private Integer productId;
	
	@Column(name="operator_id")
	private Integer operatorId;
	
	@Column(name="campany_name")
	private String campanyName;
	
	@Column(name="network_name")
	private String networkName;
	
	@Column(name = "campaign_name")
	private String campaignName;
	@Column(name = "api_key")
	private String apiKey;
	private Double  value;
	private String currency;
	private String name;
	private String description;
	@Column(name = "callback_url")
	private String callbackUrl;
	@Column(name = "success_url")
	private String successUrl;
	@Column(name = "cancel_url")
	private String cancelUrl;
	@Column(name = "sub_repeat")
	private Integer subRepeat;
	@Column(name = "sub_period")
	private Integer subPeriod;	
	@Column(name = "sub_period_units")
	private String subPeriodUnits;
	@Column(name = "sub_free_period")
	private Integer subFreePeriod;
	@Column(name = "sub_free_period_units")
	private String subFreePeriodUnits;
	
	@Column(name="cg_url_template")
	private String cgUrlTemplate;
	
	@Column(name="portal_url")
	private String portalUrl;
	
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
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	public Integer getSubRepeat() {
		return subRepeat;
	}
	public void setSubRepeat(Integer subRepeat) {
		this.subRepeat = subRepeat;
	}
	public Integer getSubPeriod() {
		return subPeriod;
	}
	public void setSubPeriod(Integer subPeriod) {
		this.subPeriod = subPeriod;
	}
	public String getSubPeriodUnits() {
		return subPeriodUnits;
	}
	public void setSubPeriodUnits(String subPeriodUnits) {
		this.subPeriodUnits = subPeriodUnits;
	}
	public Integer getSubFreePeriod() {
		return subFreePeriod;
	}
	public void setSubFreePeriod(Integer subFreePeriod) {
		this.subFreePeriod = subFreePeriod;
	}
	public String getSubFreePeriodUnits() {
		return subFreePeriodUnits;
	}
	public void setSubFreePeriodUnits(String subFreePeriodUnits) {
		this.subFreePeriodUnits = subFreePeriodUnits;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getCampanyName() {
		return campanyName;
	}
	public void setCampanyName(String campanyName) {
		this.campanyName = campanyName;
	}
	public String getCgUrlTemplate() {
		return cgUrlTemplate;
	}
	public void setCgUrlTemplate(String cgUrlTemplate) {
		this.cgUrlTemplate = cgUrlTemplate;
	}
	public String getPortalUrl() {
		return portalUrl;
	}
	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
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
	
}
