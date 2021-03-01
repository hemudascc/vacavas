package net.mycomp.oredoo.kuwait;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_oredoo_kuwait_notification")
public class OredooKuwaitCGNotification implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	private Integer id;
	private String msisdn;
	
	@Column(name = "comviva_service_id")
	private String comvivaServiceId;
	
	@Column(name = "request_plan")
	private String requestPlan;
	@Column(name = "sequence_no")
	private String sequenceNo;
	@Column(name = "charge_amount")
	private Double chargeAmount;
	@Column(name = "validity_days")
	private Integer validityDays;
	@Column(name = "operation_id")
	private String operationId;
	@Column(name="action_key")
	private String actionKey;
	@Column(name = "bearer_id")
	private String bearerId;
	private String shortcode;
	private String createddate;
	@Column(name="sms_key")
	private String smsKey;	
	@Column(name="error_code")
	private String errorCode;
	@Column(name="result")
	private String result;
	
	
	@Column(name = "request_time")
	private Timestamp requestTime;
	@Column(name = "token_id")
	private Integer tokenId;
	@Column(name = "token_to_cg")
	private String tokenToCG;
	@Column(name="query_str")
	private String queryStr;
	@Column(name = "sms_push_url")
	private String smsPushUrl;
	@Column(name = "sms_push_response")
	private String smsPushResponse;
	private boolean duplicate;
	private boolean status;
	@Column(name = "send_to_adnetwork")
	private boolean sendToAdnetwork;
	@Column(name="campaign_id")
	private Integer campaignId;
	@Column(name="cc_mode")
	private String ccMode;
	
	public OredooKuwaitCGNotification(){
		requestTime=new Timestamp(System.currentTimeMillis());
		status=true;		
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
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getRequestPlan() {
		return requestPlan;
	}
	public void setRequestPlan(String requestPlan) {
		this.requestPlan = requestPlan;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public Double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public Integer getValidityDays() {
		return validityDays;
	}
	public void setValidityDays(Integer validityDays) {
		this.validityDays = validityDays;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getBearerId() {
		return bearerId;
	}
	public void setBearerId(String bearerId) {
		this.bearerId = bearerId;
	}
	public String getShortcode() {
		return shortcode;
	}
	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public Integer getTokenId() {
		return tokenId;
	}
	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}
	public String getTokenToCG() {
		return tokenToCG;
	}
	public void setTokenToCG(String tokenToCG) {
		this.tokenToCG = tokenToCG;
	}
	

	public String getSmsPushUrl() {
		return smsPushUrl;
	}

	public void setSmsPushUrl(String smsPushUrl) {
		this.smsPushUrl = smsPushUrl;
	}

	public String getSmsPushResponse() {
		return smsPushResponse;
	}

	public void setSmsPushResponse(String smsPushResponse) {
		this.smsPushResponse = smsPushResponse;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public boolean isDuplicate() {
		return duplicate;
	}

	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getSmsKey() {
		return smsKey;
	}

	public void setSmsKey(String smsKey) {
		this.smsKey = smsKey;
	}

	public String getActionKey() {
		return actionKey;
	}

	public void setActionKey(String actionKey) {
		this.actionKey = actionKey;
	}

	public boolean isSendToAdnetwork() {
		return sendToAdnetwork;
	}

	public void setSendToAdnetwork(boolean sendToAdnetwork) {
		this.sendToAdnetwork = sendToAdnetwork;
	}

	public Integer getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getCcMode() {
		return ccMode;
	}


	public void setCcMode(String ccMode) {
		this.ccMode = ccMode;
	}


	public String getComvivaServiceId() {
		return comvivaServiceId;
	}


	public void setComvivaServiceId(String comvivaServiceId) {
		this.comvivaServiceId = comvivaServiceId;
	}


		
}
