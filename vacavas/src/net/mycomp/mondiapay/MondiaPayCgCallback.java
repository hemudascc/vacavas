package net.mycomp.mondiapay;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_mondia_pay_cg_callback")
public class MondiaPayCgCallback implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="token")
	private String token;
	@Column(name="authorization_code")
	private String authorizationCode;
	@Column(name="access_token")
	private String accessToken;
	@Column(name="refresh_token ")
	private String refreshToken ;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="status")
	private Boolean status;
	
	public MondiaPayCgCallback() {}
	public MondiaPayCgCallback(Boolean status) {
		this.status = status;
		this.createTime = new Timestamp(System.currentTimeMillis());
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
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
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
		return "MondiaPayCgCallback [id=" + id + ", token=" + token + ", authorizationCode=" + authorizationCode
				+ ", accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", createTime=" + createTime
				+ ", status=" + status + "]";
	}
}
