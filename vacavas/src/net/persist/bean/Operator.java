package net.persist.bean;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Transient;

@Table(name = "tb_operators")
@NamedQueries({
	@NamedQuery(name = "Operator.findAllOperator",
query = "SELECT b FROM Operator b "),
 @NamedQuery(name = "Operator.findAllEnabledOperator",
		query = "SELECT b FROM Operator b where b.status=true")})			
				


@Entity
public class Operator implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "operator_id", unique = true, nullable = false)
	private Integer operatorId;
	@Column(name = "operator_name")
	private String operatorName;
	
	@Column(name = "aggregator_id")
	private Integer aggregatorId;
	@Column(name = "country_id")
	private Integer countryId;
	
	@Column(name = "perhour_conversion_capping")
	private Integer perhourConversionCapping;
	@Column(name = "daily_conversion_capping")
	private Integer perDaiilyConversionCapping;
	
	@Column(name = "status")
	private Boolean status;
	
	@Transient
	private boolean blockActivation=false;;
	
	@Transient
	private AtomicInteger activationCount=new AtomicInteger(0);
	@Transient
	private AtomicInteger churnCount=new AtomicInteger(0);
	
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
	public AtomicInteger getChurnCount() {
		return churnCount;
	}
	public void setChurnCount(AtomicInteger churnCount) {
		this.churnCount = churnCount;
	}
	public AtomicInteger getActivationCount() {
		return activationCount;
	}
	public void setActivationCount(AtomicInteger activationCount) {
		this.activationCount = activationCount;
	}
	public boolean isBlockActivation() {
		return blockActivation;
	}
	public void setBlockActivation(boolean blockActivation) {
		this.blockActivation = blockActivation;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getAggregatorId() {
		return aggregatorId;
	}
	public void setAggregatorId(Integer aggregatorId) {
		this.aggregatorId = aggregatorId;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
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
