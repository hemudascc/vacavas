package net.mycomp.comviva;

import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ocsResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class OCSResponse {

	@XmlElement(name="sequenceNo")
	private String sequenceNo;
	@XmlElement(name="scpTransactionId")
	private String scpTransactionId;
	@XmlElement(name="result")
	private String result;
	@XmlElement(name="errorCode")
	private String errorCode;
	@XmlElement(name="callingParty")
	private String callingParty;
	@XmlElement(name="subsType")
	private String subsType;
	
	@XmlElement(name="serviceType")
	private String serviceType;
	
	
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

	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getCallingParty() {
		return callingParty;
	}
	public void setCallingParty(String callingParty) {
		this.callingParty = callingParty;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSubsType() {
		return subsType;
	}

	public void setSubsType(String subsType) {
		this.subsType = subsType;
	}

	public String getScpTransactionId() {
		return scpTransactionId;
	}

	public void setScpTransactionId(String scpTransactionId) {
		this.scpTransactionId = scpTransactionId;
	}
	
}
