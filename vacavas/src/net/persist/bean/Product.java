package net.persist.bean;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_product")
public class Product {

	@Id
	private Integer id;
	@Column(name = "product_name")
	private String productName;	
	
	@Column(name="product_wise_capping_per_day")
	private Integer productWiseCappingPerDay;

	@Column(name="product_wise_hourly_click_capping_redirect_to_cg")
	private Integer productWiseHourlyClickCappingRedirectToCg;

	private Boolean status;
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductWiseCappingPerDay() {
		return productWiseCappingPerDay;
	}
	public void setProductWiseCappingPerDay(Integer productWiseCappingPerDay) {
		this.productWiseCappingPerDay = productWiseCappingPerDay;
	}
	public Integer getProductWiseHourlyClickCappingRedirectToCg() {
		return productWiseHourlyClickCappingRedirectToCg;
	}
	public void setProductWiseHourlyClickCappingRedirectToCg(
			Integer productWiseHourlyClickCappingRedirectToCg) {
		this.productWiseHourlyClickCappingRedirectToCg = productWiseHourlyClickCappingRedirectToCg;
	}
	
}
