package net.persist.bean;

import java.lang.reflect.Field;




/*@Entity
@Table(name="tb_campaign_details")
@NamedQueries({
@NamedQuery(name = "SearchCampaign.findCampaignDetailByCmpId", query = "SELECT b FROM SearchCampaign b WHERE b.systemCampaignId=campaignId")
 })*/
public class SearchCampaign{

	private Integer systemCampaignId;
	private String campaignName;
	private Integer adNetworkId;
	private Integer operatorId;
	private Integer circleId;
	private Integer packageId;
	private Integer packageDetailId;
	private Integer serviceId;
	private Integer portalId;
	private Integer systemadRefId;
	private String callbackURL;
	private Integer subscriberId;
	private Integer campaignCDRId;
	private Integer existingCustomer;
	
	
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


	
	
	public Integer getSystemCampaignId() {
		return systemCampaignId;
	}
	public void setSystemCampaignId(Integer systemCampaignId) {
		this.systemCampaignId = systemCampaignId;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public Integer getAdNetworkId() {
		return adNetworkId;
	}
	public void setAdNetworkId(Integer adNetworkId) {
		this.adNetworkId = adNetworkId;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public Integer getCircleId() {
		return circleId;
	}
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	public Integer getPackageDetailId() {
		return packageDetailId;
	}
	public void setPackageDetailId(Integer packageDetailId) {
		this.packageDetailId = packageDetailId;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getPortalId() {
		return portalId;
	}
	public void setPortalId(Integer portalId) {
		this.portalId = portalId;
	}
	public Integer getSystemadRefId() {
		return systemadRefId;
	}
	public void setSystemadRefId(Integer systemadRefId) {
		this.systemadRefId = systemadRefId;
	}
	public String getCallbackURL() {
		return callbackURL;
	}
	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}
	public Integer getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(Integer subscriberId) {
		this.subscriberId = subscriberId;
	}
	public Integer getCampaignCDRId() {
		return campaignCDRId;
	}
	public void setCampaignCDRId(Integer campaignCDRId) {
		this.campaignCDRId = campaignCDRId;
	}
	public Integer getExistingCustomer() {
		return existingCustomer;
	}
	public void setExistingCustomer(Integer existingCustomer) {
		this.existingCustomer = existingCustomer;
	}
}
