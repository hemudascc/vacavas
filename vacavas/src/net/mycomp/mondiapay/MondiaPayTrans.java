package net.mycomp.mondiapay;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_mondia_pay_trans")
public class MondiaPayTrans {
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="request_type")
	private String requestType;
	@Column(name="token")
	private String token;
	@Column(name="token_id")
	private Integer tokenId;
	@Column(name="msisdn")
	private String msisdn;
	@Column(name="request", columnDefinition ="TEXT")
	private String request;
	@Column(name="response",columnDefinition ="TEXT")
	private String resposne;
	@Column(name="response_code")
	private Integer responseCode;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="status")
	private Boolean status;
	
	public MondiaPayTrans(){}
	public MondiaPayTrans(Boolean status) {
		this.status = status;
		this.createTime = new Timestamp(System.currentTimeMillis());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResposne() {
		return resposne;
	}
	public void setResposne(String resposne) {
		this.resposne = resposne;
	}
	
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
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
		return "MondiaPayTrans [id=" + id + ", requestType=" + requestType + ", token=" + token + ", tokenId=" + tokenId
				+ ", msisdn=" + msisdn + ", request=" + request + ", resposne=" + resposne + ", responseCode="
				+ responseCode + ", createTime=" + createTime + ", status=" + status + "]";
	}
}