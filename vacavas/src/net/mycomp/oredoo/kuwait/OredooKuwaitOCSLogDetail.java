package net.mycomp.oredoo.kuwait;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_oredoo_kuwait_ocs_log_detail")
public class OredooKuwaitOCSLogDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue
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
	
	public OredooKuwaitOCSLogDetail(){}
	public OredooKuwaitOCSLogDetail(Boolean status){
		
		this.id=OredoKuwaitConstant.oredooKuwaitOCSLogDetailId.incrementAndGet();
		this.createDate=new Timestamp(System.currentTimeMillis());
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
}
