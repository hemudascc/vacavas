package net.mycomp.intarget;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_intarget_ussd_trans")
public class InTargetUssdTrans {

	@Id
	//@GeneratedValue
	private Integer id;
	private String msisdn;
	@Column(name = "service_id")
	private Integer serviceId;
	@Column(name = "action")
	private String action;
	@Column(name="msg_type")
	private String msgType;
	
	@Column(name = "request_str")
	private String requestStr;
	@Column(name = "response_str")
	private String responseStr;
	
	@Column(name="msg_no")
	private String msgNo;
	@Column(name="seq_no")
	private String seqNo;
	
	@Column(name="tag")
	private String tag;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	private Boolean status;

	public InTargetUssdTrans(){}
	
	public InTargetUssdTrans(boolean status){
		
	this.id=IntargetConstant.inTargetUssdTransIdAtomicInteger.incrementAndGet();
	createTime=new Timestamp(System.currentTimeMillis());
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

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getRequestStr() {
		return requestStr;
	}

	public void setRequestStr(String requestStr) {
		this.requestStr = requestStr;
	}

	public String getResponseStr() {
		return responseStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
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

public String getMsgNo() {
	return msgNo;
}

public void setMsgNo(String msgNo) {
	this.msgNo = msgNo;
}

public String getSeqNo() {
	return seqNo;
}

public void setSeqNo(String seqNo) {
	this.seqNo = seqNo;
}

public String getAction() {
	return action;
}

public void setAction(String action) {
	this.action = action;
}

public String getTag() {
	return tag;
}

public void setTag(String tag) {
	this.tag = tag;
}

public String getMsgType() {
	return msgType;
}

public void setMsgType(String msgType) {
	this.msgType = msgType;
}

	
	}
