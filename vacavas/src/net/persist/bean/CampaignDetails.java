package net.persist.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;

@Entity
@Table(name = "tb_campaign_details")

@NamedQueries({
	@NamedQuery(name = "CampaignDetails.findEnableCampaignDetails", 
			query = "SELECT b FROM CampaignDetails b WHERE b.status=:status"),
	})

public class CampaignDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "campaign_id", unique = true, nullable = false)
	private Integer campaignId;       
	@Column(name = "campaign_name")
	private String campaignName;  	
	@Column(name = "ad_network_id")
	private Integer adNetworkId;   
	@Column(name = "service_id")
	private Integer serviceId;  
	@Column(name = "callback_url")
	private String callbackURL;  
	@Column(name = "reg_date")
	private Timestamp  regDate;   
	private Boolean status;
	
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

	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
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
	
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
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

	public String getCallbackURL() {
		return callbackURL;
	}

	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}    


}
