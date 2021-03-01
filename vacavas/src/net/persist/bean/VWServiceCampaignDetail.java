package net.persist.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Table(name = "vw_service_campaign_detail")
@NamedQueries({ @NamedQuery(name = "VWServiceCampaignDetail.findEnableVWServiceCampaignDetail", 
query = "SELECT b FROM VWServiceCampaignDetail b where "
		+ "b.serviceStatus=:serviceStatus and b.campaignDetailsStatus=:campaignDetailsStatus ")
	})
public class VWServiceCampaignDetail implements Serializable{

	@Column(name = "service_id", nullable = false)
	private Integer serviceId;
	@Column(name = "service_name")
	private String serviceName;
	@Column(name = "service_desc")
	private String serviceDesc;
	private Integer validity;
	@Column(name = "price_point")
	private Integer pricePoint;
	@Column(name = "service_status")
	private Boolean serviceStatus;
	@Id
	@Column(name = "campaign_id", nullable = false)
	private Integer campaignId;
	@Column(name = "campaign_name")
	private String  campaignName;
	
	@Column(name = "ad_network_id", nullable = false)
	private Integer adNetworkId;
	@Column(name = "callback_url")
	private String  callbackUrl;
	@Column(name = "reg_date")
	private Timestamp  regDate;
	@Column(name = "campaign_details_status")
	private Boolean campaignDetailsStatus;
	
	@Column(name = "op_id", nullable = false)
	private Integer opId;
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "product_name")
	private String productName;
	
	
	@Transient
	private Integer smartCampaignGroupId;
	
public String toString() {
		
        Field[] fields = this.getClass().getDeclaredFields();
        String str = this.getClass().getName();
        try {
            for (Field field : fields) {
                str += field.getName() + "=" + field.get(this) + ",";
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        } catch (IllegalAccessException ex) {
            System.out.println(ex);
        }
        return str;
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
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public Integer getPricePoint() {
		return pricePoint;
	}
	public void setPricePoint(Integer pricePoint) {
		this.pricePoint = pricePoint;
	}
	public Boolean getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(Boolean serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	public Integer getAdNetworkId() {
		return adNetworkId;
	}
	public void setAdNetworkId(Integer adNetworkId) {
		this.adNetworkId = adNetworkId;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public Boolean getCampaignDetailsStatus() {
		return campaignDetailsStatus;
	}
	public void setCampaignDetailsStatus(Boolean campaignDetailsStatus) {
		this.campaignDetailsStatus = campaignDetailsStatus;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
	public Integer getSmartCampaignGroupId() {
		return smartCampaignGroupId;
	}
	public void setSmartCampaignGroupId(Integer smartCampaignGroupId) {
		this.smartCampaignGroupId = smartCampaignGroupId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
