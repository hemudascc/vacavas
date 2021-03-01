package net.mycomp.oredoo.kuwait;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ocsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class OCSRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name="requestType")
	private String requestType;
	@XmlElement(name="serviceNode")
	private String serviceNode;
	@XmlElement(name="sequenceNo")
	private String sequenceNo;
	@XmlElement(name="callingParty")
	private String callingParty;
	@XmlElement(name="serviceType")
	private String serviceType;
	@XmlElement(name="serviceId")
	private String serviceId;
	@XmlElement(name="bearerId")
	private String bearerId;
	@XmlElement(name="chargeAmount")
	private Integer chargeAmount;
	@XmlElement(name="planId")
	private String planId;
	@XmlElement(name="asyncFlag")
	private String asyncFlag;
	@XmlElement(name="renewalFlag")
	private String renewalFlag;
	@XmlElement(name="bundleType")
	private String bundleType;
	@XmlElement(name="serviceUsage")
	private String serviceUsage;
	@XmlElement(name="promoId")
	private String promoId;
	@XmlElement(name="subscriptionFlag")
	private String subscriptionFlag;
	@XmlElement(name="optionalParameter1")
	private String optionalParameter1;
	@XmlElement(name="optionalParameter2")
	private String optionalParameter2;
	@XmlElement(name="optionalParameter3")
	private String optionalParameter3;
	@XmlElement(name="optionalParameter4")
	private String optionalParameter4;
	@XmlElement(name="optionalParameter5")
	private String optionalParameter5;
	@XmlElement(name="optionalParameter6")
	private String optionalParameter6;
	@XmlElement(name="optionalParameter7")
	private String optionalParameter7;
	@XmlElement(name="optionalParameter8")
	private String optionalParameter8;
	@XmlElement(name="optionalParameter9")
	private String optionalParameter9;
	@XmlElement(name="optionalParameter10")
	private String optionalParameter10;
	@XmlElement(name="optionalParameter11")
	private String optionalParameter11;
	
	@XmlElement(name="optionalParameter12")
	private String optionalParameter12;
	
	
	public OCSRequest(){
		chargeAmount=-1;
		asyncFlag="Y";
		renewalFlag="-1";
		bundleType="N";
		serviceUsage="-1";
		promoId="-1";
		subscriptionFlag="S";
		optionalParameter1="circleName#-1";
		optionalParameter2="serviceProviderId#-1";
		optionalParameter3="subService#-1";
		optionalParameter4="languageId#en";
		optionalParameter5="channelCode#-1";
		optionalParameter6="genereID#-1";
		optionalParameter7="categoryId#-1";
		optionalParameter8="toneCategory#-1";
		optionalParameter9="rbtFeature#-1";
		optionalParameter10="contentId#-1";
		optionalParameter11="msgText#-1";
		optionalParameter12="categoryId#-1";
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

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getServiceNode() {
		return serviceNode;
	}

	public void setServiceNode(String serviceNode) {
		this.serviceNode = serviceNode;
	}

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
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

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getBearerId() {
		return bearerId;
	}

	public void setBearerId(String bearerId) {
		this.bearerId = bearerId;
	}

	public Integer getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Integer chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getAsyncFlag() {
		return asyncFlag;
	}

	public void setAsyncFlag(String asyncFlag) {
		this.asyncFlag = asyncFlag;
	}

	public String getRenewalFlag() {
		return renewalFlag;
	}

	public void setRenewalFlag(String renewalFlag) {
		this.renewalFlag = renewalFlag;
	}

	public String getBundleType() {
		return bundleType;
	}

	public void setBundleType(String bundleType) {
		this.bundleType = bundleType;
	}

	public String getServiceUsage() {
		return serviceUsage;
	}

	public void setServiceUsage(String serviceUsage) {
		this.serviceUsage = serviceUsage;
	}

	public String getPromoId() {
		return promoId;
	}

	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	public String getSubscriptionFlag() {
		return subscriptionFlag;
	}

	public void setSubscriptionFlag(String subscriptionFlag) {
		this.subscriptionFlag = subscriptionFlag;
	}

	public String getOptionalParameter1() {
		return optionalParameter1;
	}

	public void setOptionalParameter1(String optionalParameter1) {
		this.optionalParameter1 = optionalParameter1;
	}

	public String getOptionalParameter2() {
		return optionalParameter2;
	}

	public void setOptionalParameter2(String optionalParameter2) {
		this.optionalParameter2 = optionalParameter2;
	}

	public String getOptionalParameter3() {
		return optionalParameter3;
	}

	public void setOptionalParameter3(String optionalParameter3) {
		this.optionalParameter3 = optionalParameter3;
	}

	public String getOptionalParameter4() {
		return optionalParameter4;
	}

	public void setOptionalParameter4(String optionalParameter4) {
		this.optionalParameter4 = optionalParameter4;
	}

	public String getOptionalParameter5() {
		return optionalParameter5;
	}

	public void setOptionalParameter5(String optionalParameter5) {
		this.optionalParameter5 = optionalParameter5;
	}

	public String getOptionalParameter6() {
		return optionalParameter6;
	}

	public void setOptionalParameter6(String optionalParameter6) {
		this.optionalParameter6 = optionalParameter6;
	}

	public String getOptionalParameter7() {
		return optionalParameter7;
	}

	public void setOptionalParameter7(String optionalParameter7) {
		this.optionalParameter7 = optionalParameter7;
	}

	public String getOptionalParameter8() {
		return optionalParameter8;
	}

	public void setOptionalParameter8(String optionalParameter8) {
		this.optionalParameter8 = optionalParameter8;
	}

	public String getOptionalParameter9() {
		return optionalParameter9;
	}

	public void setOptionalParameter9(String optionalParameter9) {
		this.optionalParameter9 = optionalParameter9;
	}

	public String getOptionalParameter10() {
		return optionalParameter10;
	}

	public void setOptionalParameter10(String optionalParameter10) {
		this.optionalParameter10 = optionalParameter10;
	}

	public String getOptionalParameter11() {
		return optionalParameter11;
	}

	public void setOptionalParameter11(String optionalParameter11) {
		this.optionalParameter11 = optionalParameter11;
	}

	public String getOptionalParameter12() {
		return optionalParameter12;
	}

	public void setOptionalParameter12(String optionalParameter12) {
		this.optionalParameter12 = optionalParameter12;
	}

	

}
