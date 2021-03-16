package net.mycomp.comviva;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_comviva_cgcallback")
public class ComvivaCGCallback implements Serializable{

	private static final long serialVersionUID = 1L;
	//MSISDN=69945504&Result=CLOSE&Reason=Consent_Closed_by_user&productId=S-ArabViEwMY2&transID=108299638&TPCGID=&Songname=null&FLOW=DATAFLOW
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="msisdn")
	private String msisdn;
	@Column(name="result")
	private String result;
	@Column(name="reason")
	private String reason;
	@Column(name="product_id")
	private String productId;
	@Column(name="token")
	private String token;
	@Column(name="tp_cg_id")
	private String tpCgId;
	@Column(name="song_name")
	private String songName;
	@Column(name="flow")
	private String flow;
	@Column(name="query_str")
	private String queryStr;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="status")
	private Boolean status;
	
	public ComvivaCGCallback() {}
	public ComvivaCGCallback(Boolean status) {
		this.createTime = new Timestamp(System.currentTimeMillis());
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTpCgId() {
		return tpCgId;
	}
	public void setTpCgId(String tpCgId) {
		this.tpCgId = tpCgId;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	
	@Override
	public String toString() {
		return "ComvivaCGCallback [id=" + id + ", msisdn=" + msisdn + ", result=" + result + ", reason=" + reason
				+ ", productId=" + productId + ", token=" + token + ", tpCgId=" + tpCgId + ", songName=" + songName
				+ ", flow=" + flow + ", queryStr=" + queryStr + "]";
	}
}
