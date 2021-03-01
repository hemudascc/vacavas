package net.mycomp.mobimind;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_mobimind_cg_callback")
public class MobimindCGCallback implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "cg_msisdn")
	private String cgMsisdn;
	@Column(name = "cg_status")
	private String cgStatus;
	private String token;
	@Column(name = "query_str")
	private String queryStr;
	@Column(name = "create_time")
	private Timestamp createTime;
	private Boolean status;
	
	public MobimindCGCallback(){}
	
	public MobimindCGCallback(boolean status){
		this.status=status;
		this.createTime=new Timestamp(System.currentTimeMillis());
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCgMsisdn() {
		return cgMsisdn;
	}
	public void setCgMsisdn(String cgMsisdn) {
		this.cgMsisdn = cgMsisdn;
	}
	public String getCgStatus() {
		return cgStatus;
	}
	public void setCgStatus(String cgStatus) {
		this.cgStatus = cgStatus;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
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
