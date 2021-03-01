package net.mycomp.messagecloud.gateway;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_mcg_dlr")
public class MCGDeliveryReport implements Serializable{

	@Id
	@GeneratedValue
	private Integer id;
	private String action;
	@Column(name = "service_id")
	private Integer serviceId;
	
	@Column(name = "message_id")
	private String messageId;
	private String number;
	private String report;
	private String reasonid;	
	@Column(name = "request_str")
	private String requestStr;	
	@Column(name = "token")
	private String token;	
	@Column(name = "create_time")
	private Timestamp createTime;
	private Boolean status;
	
	public MCGDeliveryReport(){}
	
	public MCGDeliveryReport(boolean status){
		
		this.status=status;
		this.createTime=new Timestamp(System.currentTimeMillis());
		
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
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getReasonid() {
		return reasonid;
	}
	public void setReasonid(String reasonid) {
		this.reasonid = reasonid;
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

	public String getRequestStr() {
		return requestStr;
	}

	public void setRequestStr(String requestStr) {
		this.requestStr = requestStr;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	
}
