package net.persist.bean;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import javax.persistence.*;


/**
 * The persistent class for the tb_adnetwork_operator_config database table.
 * 
 */
@Entity
@Table(name="tb_adnetwork_operator_config")
//@NamedQuery(name="TbAdnetworkOperatorConfig.findAll", query="SELECT t FROM AdnetworkOperatorConfig t")
@NamedQuery(name = "AdnetworkOperatorConfig.findAdnetworkOperatorConfigById", 
query = "SELECT b FROM AdnetworkOperatorConfig b where b.adnetworkOperatorConfigId=:adnetworkOperatorConfigId ")

public class AdnetworkOperatorConfig  {
	
	@Id
	@Column(name="adnetwork_operator_config_id", unique = true, nullable = false)
	private Integer adnetworkOperatorConfigId;

	
	@Column(name="ad_network_id")
	private Integer adNetworkId;

	@Column(name="circle_id")
	private Integer circleId;

	
	@Column(name="landing_page_status")
	private Boolean landingPageStatus;

	@Column(name="operator_id")
	private Integer operatorId;
	
	@Column(name="skip_number")
	private Integer skipNumber;
	@Column(name="status")
	private Boolean status;
	
	@Column(name="op_cpa_value")
	private Double opCpaValue;
	
	@Column(name="duplicate_block_status")
	private Boolean duplicateBlockStatus=Boolean.FALSE;
	
	@Column(name="ad_block_status")
	private Boolean adBlockStatus;
	
	
	
	@Column(name="zero_price_activation_send")
	private Boolean zeroPriceActivationSend;
	
	@Column(name = "perhour_conversion_capping")
	private Integer perhourConversionCapping;
	@Column(name = "daily_conversion_capping")
	private Integer perDaiilyConversionCapping;
	
//	@Transient
//	public AtomicInteger atomicActSkipNumber=new AtomicInteger(1);
//
//	@Transient
//	public AtomicInteger atomicActReverseSkipNumber=new AtomicInteger(1);
	
	
	@Transient
	public AtomicIntegerArray atomicActSkipNumber=new AtomicIntegerArray(3);
	

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
	
	public Boolean getDuplicateBlockStatus() {
		return duplicateBlockStatus;
	}



	public void setDuplicateBlockStatus(Boolean duplicateBlockStatus) {
		this.duplicateBlockStatus = duplicateBlockStatus;
	}



	public Boolean getAdBlockStatus() {
		return adBlockStatus;
	}



	public void setAdBlockStatus(Boolean adBlockStatus) {
		this.adBlockStatus = adBlockStatus;
	}



	


	public AtomicIntegerArray getAtomicActSkipNumber() {
		return atomicActSkipNumber;
	}



	public void setAtomicActSkipNumber(AtomicIntegerArray atomicActSkipNumber) {
		this.atomicActSkipNumber = atomicActSkipNumber;
	}



	public Boolean getZeroPriceActivationSend() {
		return zeroPriceActivationSend;
	}



	public void setZeroPriceActivationSend(Boolean zeroPriceActivationSend) {
		this.zeroPriceActivationSend = zeroPriceActivationSend;
	}



	public Double getOpCpaValue() {
		return opCpaValue;
	}



	public void setOpCpaValue(Double opCpaValue) {
		this.opCpaValue = opCpaValue;
	}



	public Integer getPerhourConversionCapping() {
		return perhourConversionCapping;
	}



	public void setPerhourConversionCapping(Integer perhourConversionCapping) {
		this.perhourConversionCapping = perhourConversionCapping;
	}



	public Integer getPerDaiilyConversionCapping() {
		return perDaiilyConversionCapping;
	}



	public void setPerDaiilyConversionCapping(Integer perDaiilyConversionCapping) {
		this.perDaiilyConversionCapping = perDaiilyConversionCapping;
	}
	
	
	}