package net.mycomp.actel;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_actel_he_callback")
public class ActelHECallback implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	private String msisdn;
	private String country;
	private String operatorid;
	private String operator;
	private String token;
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name="query_str")
	private String queryStr;
	
	public ActelHECallback() {
		
	}
	
	public ActelHECallback(String msisdn, String country, String operatorid, String operator, String token,
			String queryStr) {
		super();
		this.msisdn = msisdn;
		this.country = country;
		this.operatorid = operatorid;
		this.operator = operator;
		this.token = token;
		this.queryStr = queryStr;
		this.createTime = new Timestamp(System.currentTimeMillis());
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



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getOperatorid() {
		return operatorid;
	}



	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}



	public String getOperator() {
		return operator;
	}



	public void setOperator(String operator) {
		this.operator = operator;
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



	public String toString() {

		Field[] fields = this.getClass().getDeclaredFields();
		String str = this.getClass().getName();
		try {
			for (Field field : fields) {
				str += field.getName() + "=" + field.get(this) + ",";
			}
		} catch (IllegalArgumentException ex) {
			System.out.println(ex);
		} catch (IllegalAccessException ex) {
			System.out.println(ex);
		}
		return str;
	}


}
