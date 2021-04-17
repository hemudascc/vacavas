package net.mycomp.macrokiosk.thailand;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;


@Entity
@Table(name = "tb_delivery_notification")
public class DeliveryNotification implements Serializable{
	// http://www.yourdomainDNurl/receive.aspx?mtid=123296707&moid=1234567&
	// msisdn=66874111222&shortcode=4541889&telcoid=1&countryid=3&datetime=2010-06-15
	// 10:10:10&status=OK
	 
	@Id
	@GeneratedValue
	private Integer id;
	private String mtid;
	@Column(name="action")
	private String action;
	
	private String moid;
	private String msisdn;
	@Column(name = "short_code")
	private String shortcode;
	@Column(name = "keyword")
	private String keyword;
	@Column(name = "telco_id")
	private Integer telcoId;
	
	@Column(name = "op_id")
	private Integer opId;
	
	@Column(name = "country_id")
	private Integer countryId;
	private Timestamp datetime;
	private String status;
	@Column(name = "query_str")
	private String queryStr;	
	
	@Column(name = "notification_type")
	private String notificationType;
	
	@Column(name = "charged")
	private boolean charged;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "token_id")
	private Integer tokenId;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "retry")
	private Boolean retry;
	
	@Column(name = "current_retry_counter")
	private Integer currentRetryCounter;
	
	@Column(name = "process_status")
	private Boolean processStatus=false;
	
	
	@Column(name = "amount")
	private Double amount;
	@Column(name="cc_mode")
	private String ccMode;
	
	public DeliveryNotification(){}
	public DeliveryNotification(boolean status){
		this.retry=false;
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
	public String getMtid() {
		return mtid;
	}
	public void setMtid(String mtid) {
		this.mtid = mtid;
	}
	public String getMoid() {
		return moid;
	}
	public void setMoid(String moid) {
		this.moid = moid;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getShortcode() {
		return shortcode;
	}
	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
	public Integer getTelcoId() {
		return telcoId;
	}
	public void setTelcoId(Integer telcoId) {
		this.telcoId = telcoId;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public Timestamp getDatetime() {
		return datetime;
	}
	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public boolean isCharged() {
		return charged;
	}
	public void setCharged(boolean charged) {
		this.charged = charged;
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

	public Boolean getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Boolean processStatus) {
		this.processStatus = processStatus;
	}

	public Boolean getRetry() {
		return retry;
	}

	public void setRetry(Boolean retry) {
		this.retry = retry;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getCurrentRetryCounter() {
		return currentRetryCounter;
	}
	public void setCurrentRetryCounter(Integer currentRetryCounter) {
		this.currentRetryCounter = currentRetryCounter;
	}
	public String getCcMode() {
		return ccMode;
	}
	public void setCcMode(String ccMode) {
		this.ccMode = ccMode;
	}

}
