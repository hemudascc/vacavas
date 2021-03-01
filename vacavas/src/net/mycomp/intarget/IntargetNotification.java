package net.mycomp.intarget;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;


@MappedSuperclass

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({IntargetOnResultNotification.class, IntargetOnReceiveSMSNotification.class})
//@XmlRootElement(name="Message")
@XmlDiscriminatorNode("@Type")
public abstract class IntargetNotification implements Serializable{

	@XmlTransient
	@Id
	@GeneratedValue
	private Integer id;
	@XmlPath("@Type")
	@Column(name = "response_type")
	private String responseType;
	
	@XmlPath("Service/text()")
	@Column(name = "service_code")
	private String serviceCode;
	
	@XmlPath("@TOC")
	private String toc;
	
	@XmlPath("@RefNo")
	@Column(name = "ref_no")
	private String refNo;
	@XmlPath("@SeqNo")
	@Column(name = "seq_no")
	private String seqNo;
	
	@XmlPath("Network/@ID")
	@Column(name = "network_id")
	private String networkId;
	@XmlPath("Network/@MCC")
	private String mcc;
	@XmlPath("Network/@MNC")
	private String mnc;
	@XmlPath("ErrCode/text()")
	@Column(name = "err_code")
	private String errCode;
	@XmlPath("ErrText/text()")
	@Column(name = "err_text")
	private String errText;
	
	
	@XmlTransient
	@Column(name="request_xml")
	private String requestXml;
	@XmlTransient
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@XmlTransient
	private Boolean status;
	
	public IntargetNotification(){
		
	}
	
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getToc() {
		return toc;
	}
	public void setToc(String toc) {
		this.toc = toc;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMnc() {
		return mnc;
	}
	public void setMnc(String mnc) {
		this.mnc = mnc;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrText() {
		return errText;
	}
	public void setErrText(String errText) {
		this.errText = errText;
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
public String getRequestXml() {
	return requestXml;
}
public void setRequestXml(String requestXml) {
	this.requestXml = requestXml;
}

public String getServiceCode() {
	return serviceCode;
}

public void setServiceCode(String serviceCode) {
	this.serviceCode = serviceCode;
}

		
}
