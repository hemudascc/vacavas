package net.mycomp.comviva;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_comviva_ocs_log_detail")
public class ComvivaOCSLogDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "msisdn")
	private String msisdn;
	@Column(name = "action")
	private String action;
	@Column(name = "request")
	private String requet;
	@Column(name = "response")
	private String response;
	@Column(name = "create_date")
	private Timestamp createDate;
	@Column(name = "status")
	private Boolean status;
	public ComvivaOCSLogDetail(){}
	public ComvivaOCSLogDetail(Boolean status) {
		this.status = status;
		this.createDate = new Timestamp(System.currentTimeMillis());
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getRequet() {
		return requet;
	}
	public void setRequet(String requet) {
		this.requet = requet;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "ComvivaOCSLogDetail [id=" + id + ", msisdn=" + msisdn + ", action=" + action + ", requet=" + requet
				+ ", response=" + response + ", createDate=" + createDate + ", status=" + status + "]";
	}
}
