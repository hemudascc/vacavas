package net.mycomp.messagecloud.gateway;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_mcg_fallback_pricepoint_config")
public class MCGFallbackPricePointConfig {

	@Id
	private Integer id;
	@Column(name = "mcg_service_config_id")
	private Integer mcgServiceConfigId;
	@Column(name = "price_point")
	private Double pricePoint;
	private Integer validity;
	@Column(name = "reply")
	private Integer reply;	
	private Integer sequence;
	private Boolean status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMcgServiceConfigId() {
		return mcgServiceConfigId;
	}
	public void setMcgServiceConfigId(Integer mcgServiceConfigId) {
		this.mcgServiceConfigId = mcgServiceConfigId;
	}
	public Double getPricePoint() {
		return pricePoint;
	}
	public void setPricePoint(Double pricePoint) {
		this.pricePoint = pricePoint;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getReply() {
		return reply;
	}
	public void setReply(Integer reply) {
		this.reply = reply;
	}
}
