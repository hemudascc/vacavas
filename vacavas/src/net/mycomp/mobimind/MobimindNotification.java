package net.mycomp.mobimind;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_mobimind_notification")
public class MobimindNotification implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	private String action;
	private String user;
	private String password;
	@Column(name = "service_id")
	private String serviceId;
	@Column(name = "notification_status")
	private String notificationStatus;
	@Column(name = "operator_id")
	private String operatorId;
	private String msisdn;
	@Column(name = "request_id")
	private String requestId;
	@Column(name = "channel_id")
	private String channelId;
	
	@Column(name = "price")
	private String price;
	
	private String reference;	
	@Column(name = "trx_id")
	private String trxId;
	
	@Column(name = "token_id")
	private Integer tokenId;
	@Column(name = "token")
	private String token;
	@Column(name = "send_to_adnetwork")
	private Boolean sendToAdnetwork;
	

	@Column(name = "validity")
	private Integer validity;

	@Column(name = "amount")
	private Double amount;
	
	
	@Column(name = "query_str")
	private String queryStr;
	@Column(name = "create_time")
	private Timestamp createTime;
	private Boolean status;
	
	public MobimindNotification(){}
	public MobimindNotification(boolean status){
		this.createTime=new Timestamp(System.currentTimeMillis());
		this.status=status;
		this.sendToAdnetwork=false;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getNotificationStatus() {
		return notificationStatus;
	}
	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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
public Integer getTokenId() {
	return tokenId;
}
public void setTokenId(Integer tokenId) {
	this.tokenId = tokenId;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public Boolean getSendToAdnetwork() {
	return sendToAdnetwork;
}
public void setSendToAdnetwork(Boolean sendToAdnetwork) {
	this.sendToAdnetwork = sendToAdnetwork;
}
public Integer getValidity() {
	return validity;
}
public void setValidity(Integer validity) {
	this.validity = validity;
}
public Double getAmount() {
	return amount;
}
public void setAmount(Double amount) {
	this.amount = amount;
}
public String getTrxId() {
	return trxId;
}
public void setTrxId(String trxId) {
	this.trxId = trxId;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}

	
}
