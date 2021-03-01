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
@Table(name = "tb_actel_dlr")
public class ActelDlr implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "id_application")
	private String idApplication;
	private String country;
	private String operator;
	private String opid;
	private String msisdn;
	private String billedShortCode;
	private String freeShortCode;
	private String smsId;
	private String rate;
	private String action;
	private String type;
	@Column(name = "dlr_status")
	private String dlrStatus;
	private String description;
	private String currency;
	private String lang;
	private String dlrdate;
	private String flow;
	@Column(name = "cc_mode")
	private String ccMode;
	@Column(name = "traffic_source")
	private String trafficSource;
	@Column(name="query_str")
	private String queryStr;
	
	@Column(name="click_id")
	private String clickId;
	@Column(name="callback_url")
	private String callbackUrl;
	@Column(name="response")
	private String response;
	
	@Column(name="token")
	private String token;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	private Boolean status;
	@Column(name = "has_free_trial")
	private String hasFreeTrial;
	@Column(name = "id_billing_request_type")
	private String idBillingRequestType;
	
	public ActelDlr(){}
	
	public ActelDlr(boolean status){
		this.status=status;
		this.createTime=new Timestamp(System.currentTimeMillis());
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdApplication() {
		return idApplication;
	}
	public void setIdApplication(String idApplication) {
		this.idApplication = idApplication;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOpid() {
		return opid;
	}
	public void setOpid(String opid) {
		this.opid = opid;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getBilledShortCode() {
		return billedShortCode;
	}
	public void setBilledShortCode(String billedShortCode) {
		this.billedShortCode = billedShortCode;
	}
	public String getFreeShortCode() {
		return freeShortCode;
	}
	public void setFreeShortCode(String freeShortCode) {
		this.freeShortCode = freeShortCode;
	}
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getDlrdate() {
		return dlrdate;
	}
	public void setDlrdate(String dlrdate) {
		this.dlrdate = dlrdate;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getTrafficSource() {
		return trafficSource;
	}
	public void setTrafficSource(String trafficSource) {
		this.trafficSource = trafficSource;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public String getDlrStatus() {
		return dlrStatus;
	}
	public void setDlrStatus(String dlrStatus) {
		this.dlrStatus = dlrStatus;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

public String getClickId() {
	return clickId;
}

public void setClickId(String clickId) {
	this.clickId = clickId;
}

public String getCallbackUrl() {
	return callbackUrl;
}

public void setCallbackUrl(String callbackUrl) {
	this.callbackUrl = callbackUrl;
}

public String getResponse() {
	return response;
}

public void setResponse(String response) {
	this.response = response;
}

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getCcMode() {
	return ccMode;
}

public void setCcMode(String ccMode) {
	this.ccMode = ccMode;
}

public String getHasFreeTrial() {
	return hasFreeTrial;
}

public void setHasFreeTrial(String hasFreeTrial) {
	this.hasFreeTrial = hasFreeTrial;
}

public String getIdBillingRequestType() {
	return idBillingRequestType;
}

public void setIdBillingRequestType(String idBillingRequestType) {
	this.idBillingRequestType = idBillingRequestType;
}


}
