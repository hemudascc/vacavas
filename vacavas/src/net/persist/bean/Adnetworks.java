	package net.persist.bean;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;

@Entity
@Table(name = "tb_adnetworks")
@NamedQueries({ @NamedQuery(name = "Adnetworks.findAllEnableAdnetworks", 
query = "SELECT b FROM Adnetworks b where b.status=:status")
	})
public class Adnetworks {
	
	@Id
	@Column(name = "ad_network_id", unique = true, nullable = false)
	private Integer adNetworkId;         
	@Column(name = "network_name")
	private String networkName;          
	@Column(name = "network_descp")
	private String networkDescp;         
	@Column(name = "reg_date")
	private Timestamp regDate;              
	             
	private Boolean status;    
	            
	@Column(name = "callback_url_template")
	private String callbackUrlTemplate;
	
	@Column(name = "dct_callback_url_template")
	private String dctCallbackUrlTemplate;
	
	@Column(name = "renewal_callback_url_template")
	private String renewalCallbackUrlTemplate;
	
	@Column(name="waste_url")
	private String wasteUrl;
	
	public Integer getAdNetworkId() {
		return adNetworkId;
	}
	public void setAdNetworkId(Integer adNetworkId) {
		this.adNetworkId = adNetworkId;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkDescp() {
		return networkDescp;
	}
	public void setNetworkDescp(String networkDescp) {
		this.networkDescp = networkDescp;
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
	
	public String getCallbackUrlTemplate() {
		return callbackUrlTemplate;
	}
	public void setCallbackUrlTemplate(String callbackUrlTemplate) {
		this.callbackUrlTemplate = callbackUrlTemplate;
	}
	public String getWasteUrl() {
		return wasteUrl;
	}
	public void setWasteUrl(String wasteUrl) {
		this.wasteUrl = wasteUrl;
	}
	public String getDctCallbackUrlTemplate() {
		return dctCallbackUrlTemplate;
	}
	public void setDctCallbackUrlTemplate(String dctCallbackUrlTemplate) {
		this.dctCallbackUrlTemplate = dctCallbackUrlTemplate;
	}
	public String getRenewalCallbackUrlTemplate() {
		return renewalCallbackUrlTemplate;
	}
	public void setRenewalCallbackUrlTemplate(String renewalCallbackUrlTemplate) {
		this.renewalCallbackUrlTemplate = renewalCallbackUrlTemplate;
	}	
}
