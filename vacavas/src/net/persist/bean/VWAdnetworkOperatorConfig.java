package net.persist.bean;


import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.*;


/**
 * The persistent class for the tb_adnetwork_operator_config database table.
 * 
 */
@Entity
@Table(name="vw_adnetwork_operator_config")
@NamedQueries({ @NamedQuery(name = "VWAdnetworkOperatorConfig.findAllAdnConfig", 
query = "SELECT b FROM VWAdnetworkOperatorConfig b where b.status=true order by b.operatorId asc "
		)
	
	})

public class VWAdnetworkOperatorConfig  {
	
	@Id
	@Column(name="adnetwork_operator_config_id", unique = true, nullable = false)
	private Integer adnetworkOperatorConfigId;

	

	@Column(name="ad_network_id")
	private Integer adNetworkId;
	
	@Column(name="network_name")
	private String  adNetworkName;

	@Column(name="circle_id")
	private Integer circleId;

	
	@Column(name="landing_page_status")
	private Boolean landingPageStatus;

	@Column(name="operator_id")
	private Integer operatorId;
	
	@Column(name="operator_name")
	private String operatorName;

	
	
	@Column(name="skip_number")
	private Integer skipNumber;
	@Column(name="status")
	private Boolean status;
	@Column(name="ad_block_status")
	private Boolean adBlockStatus;
	
	@Column(name="duplicate_block_status")
	private Boolean duplicateBlockStatus=Boolean.FALSE;
	
	@Column(name="op_cpa_value")
	private String opCpaValue;
	
	

	public Integer getAdnetworkOperatorConfigId() {
		return adnetworkOperatorConfigId;
	}

	public void setAdnetworkOperatorConfigId(Integer adnetworkOperatorConfigId) {
		this.adnetworkOperatorConfigId = adnetworkOperatorConfigId;
	}

	public Integer getAdNetworkId() {
		return adNetworkId;
	}

	public void setAdNetworkId(Integer adNetworkId) {
		this.adNetworkId = adNetworkId;
	}

	public String getAdNetworkName() {
		return adNetworkName;
	}

	public void setAdNetworkName(String adNetworkName) {
		this.adNetworkName = adNetworkName;
	}

	public Integer getCircleId() {
		return circleId;
	}

	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}

	public Boolean getLandingPageStatus() {
		return landingPageStatus;
	}

	public void setLandingPageStatus(Boolean landingPageStatus) {
		this.landingPageStatus = landingPageStatus;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getSkipNumber() {
		return skipNumber;
	}

	public void setSkipNumber(Integer skipNumber) {
		this.skipNumber = skipNumber;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getAdBlockStatus() {
		return adBlockStatus;
	}

	public void setAdBlockStatus(Boolean adBlockStatus) {
		this.adBlockStatus = adBlockStatus;
	}

	public Boolean getDuplicateBlockStatus() {
		return duplicateBlockStatus;
	}

	public void setDuplicateBlockStatus(Boolean duplicateBlockStatus) {
		this.duplicateBlockStatus = duplicateBlockStatus;
	}

	public String getOpCpaValue() {
		return opCpaValue;
	}

	public void setOpCpaValue(String opCpaValue) {
		this.opCpaValue = opCpaValue;
	}

	
}