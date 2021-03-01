package net.mycomp.actel;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_actel_api_trans")
public class ActelApiTrans {
 
	@Id
	//@GeneratedValue
	private Integer id;
	private String action;
	private String msisdn;
	private String request;
	private String response;
	@Column(name="mode")
	private String mode;
	@Column(name="token")
	private String token;
	
	private Boolean success;
	@Column(name = "create_time")
	private Timestamp createTime;
	private Boolean status;
	
	public ActelApiTrans(){}
	
	public ActelApiTrans(boolean status,String action){
		this.status=status;
		this.action=action;
		this.createTime=new Timestamp(System.currentTimeMillis());
		this.id=ActelConstant.actlelApiTransId.incrementAndGet();
		this.success=false;
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
