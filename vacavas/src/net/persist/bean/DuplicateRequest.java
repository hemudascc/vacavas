package net.persist.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import net.util.JpaConverterJson;

import javax.persistence.Id;

@Entity
@Table(name = "tb_duplicate_charging_callback")
public class DuplicateRequest {

	@Id
	@GeneratedValue
	private Integer id;
	private String msisdn;
	private String action;	
	@Convert(converter = JpaConverterJson.class)
	private Object subscribeDetail;	
	@Column(name="create_time")
	private Timestamp createTime;
	private Boolean status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Object getSubscribeDetail() {
		return subscribeDetail;
	}
	public void setSubscribeDetail(Object subscribeDetail) {
		this.subscribeDetail = subscribeDetail;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
