package net.mycomp.timwe;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_timwe_hecallback")
public class TimweHECallback {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="token")
	private String token;
	@Column(name="token_id")
	private Integer tokenId;
	@Column(name="msisdn")
	private String msisdn;
	@Column(name="service_id")
	private Integer serviceId;
	@Column(name="query_str")
	private String queryStr;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="status")
	private Boolean status;
	
	TimweHECallback(){}
	TimweHECallback(Boolean status){
		this.status=status;
		this.createTime=new Timestamp(System.currentTimeMillis());
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getTokenId() {
		return tokenId;
	}
	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
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
	@Override
	public String toString() {
		return "TimweHECallback [id=" + id + ", token=" + token + ", tokenId=" + tokenId + ", msisdn=" + msisdn
				+ ", serviceId=" + serviceId + ", queryStr=" + queryStr + ", createTime=" + createTime + ", status="
				+ status + "]";
	}
}
