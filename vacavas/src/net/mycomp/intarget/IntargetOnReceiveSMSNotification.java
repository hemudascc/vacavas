package net.mycomp.intarget;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.persistence.Entity;
import javax.persistence.Table;
@XmlRootElement(name="OnReceiveSMS")
//@XmlRootElement(name="Message")
@XmlDiscriminatorValue(value="OnReceiveSMS")
@Entity
@Table(name = "tb_intarget_onreceivesms_notification")
public class IntargetOnReceiveSMSNotification extends IntargetNotification implements Serializable{

	@XmlPath("OnReceiveSMS/@SeqNo")	
	@Column(name = "onreceivesms_seqno")
	private String onreceivesmsSeqNo;
	
	@XmlPath("OnReceiveSMS/@Sent")	
	@Column(name = "onreceivesms_sent")
	private String sent;
	@XmlPath("OnReceiveSMS/@FromAddr")	
	@Column(name = "onreceivesms_fromaddr")
	private String fromAddr;
	@XmlPath("OnReceiveSMS/@ToAddr")	
	@Column(name = "onreceivesms_toaddr")
	private String toAddr;
	@XmlPath("OnReceiveSMS/@ToTag")	
	@Column(name = "onreceivesms_totag")
	private String toTag;
	@XmlPath("OnReceiveSMS/@Value")	
	@Column(name = "onreceivesms_value")
	private String value;
//	@XmlPath("OnReceiveSMS/@NetworkID")	
//	@Column(name = "network_id")
//	private String networkID;
	@XmlPath("OnReceiveSMS/@AdultRating")	
	@Column(name = "onreceivesms_adultrating")
	private String adultRating;
	
	@XmlPath("OnReceiveSMS/@DataCoding")	
	@Column(name = "onreceivesms_datacoding")
	private String dataCoding;
	
	@XmlPath("OnReceiveSMS/@EsmClass")	
	@Column(name = "onreceivesms_esmclass")
	private String esmClass;
	
	@XmlPath("OnReceiveSMS/Content/text()")	
	@Column(name = "onreceivesms_text")
	private String text;

	public IntargetOnReceiveSMSNotification(){
		super();
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
	


	public String getSent() {
		return sent;
	}

	public void setSent(String sent) {
		this.sent = sent;
	}

	public String getFromAddr() {
		return fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public String getToTag() {
		return toTag;
	}

	public void setToTag(String toTag) {
		this.toTag = toTag;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

	public String getAdultRating() {
		return adultRating;
	}

	public void setAdultRating(String adultRating) {
		this.adultRating = adultRating;
	}

	public String getDataCoding() {
		return dataCoding;
	}

	public void setDataCoding(String dataCoding) {
		this.dataCoding = dataCoding;
	}

	public String getEsmClass() {
		return esmClass;
	}

	public void setEsmClass(String esmClass) {
		this.esmClass = esmClass;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOnreceivesmsSeqNo() {
		return onreceivesmsSeqNo;
	}

	public void setOnreceivesmsSeqNo(String onreceivesmsSeqNo) {
		this.onreceivesmsSeqNo = onreceivesmsSeqNo;
	}
	
	
}
