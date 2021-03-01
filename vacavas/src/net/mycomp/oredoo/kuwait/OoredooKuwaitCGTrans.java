package net.mycomp.oredoo.kuwait;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_ooredo_kuwait_cg_trans")
public class OoredooKuwaitCGTrans implements Serializable{

	@Id
	@GeneratedValue
	private Integer id;
	private String msisdn;
	private String ip;
	@Column(name = "x_forwarded_for")
	private String xForwardedFor;
	@Column(name = "request_data")
	private String requestData;
	@Column(name = "request_encypt_data")
	private String requestEncyptData;
	private String token;
	@Column(name = "token_id")
	private Integer tokenId;
	@Column(name = "create_time")
	private Timestamp createTime;
	private Boolean  status;
	
	public OoredooKuwaitCGTrans(){}
	public OoredooKuwaitCGTrans(boolean status){
		this.status=status;
		this.createTime=new Timestamp(System.currentTimeMillis());
	}
	
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getxForwardedFor() {
		return xForwardedFor;
	}
	public void setxForwardedFor(String xForwardedFor) {
		this.xForwardedFor = xForwardedFor;
	}
	public String getRequestData() {
		return requestData;
	}
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	public String getRequestEncyptData() {
		return requestEncyptData;
	}
	public void setRequestEncyptData(String requestEncyptData) {
		this.requestEncyptData = requestEncyptData;
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
