package net.mycomp.comviva;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_comviva_he_callback")
public class ComvivaCGTrans {
	//MSISDN=96569687736&Reason=DATA&productId=S-GamShpEwMY2&transID=test0400
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="msisdn")
	private String msisdn;
	@Column(name="reason")
	private String reason;
	@Column(name="product_id")
	private String productId;
	@Column(name="cg_url")
	private String cgURL;
	@Column(name="encrypted_cg_url")
	private String encryptedCgURL;
	@Column(name="token")	
	private String token;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="query_str")
	private String queryStr;
	@Column(name="status")
	private Boolean status;

	public ComvivaCGTrans(){}
	public ComvivaCGTrans(Boolean status) {
		this.createTime=new Timestamp(System.currentTimeMillis());
		this.status=status;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCgURL() {
		return cgURL;
	}
	public void setCgURL(String cgURL) {
		this.cgURL = cgURL;
	}
	public String getEncryptedCgURL() {
		return encryptedCgURL;
	}
	public void setEncryptedCgURL(String encryptedCgURL) {
		this.encryptedCgURL = encryptedCgURL;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ComvivaCGTrans [id=" + id + ", msisdn=" + msisdn + ", reason=" + reason + ", productId=" + productId
				+ ", cgURL=" + cgURL + ", encryptedCgURL=" + encryptedCgURL + ", token=" + token + ", createTime="
				+ createTime + ", queryStr=" + queryStr + ", status=" + status + "]";
	}
}
