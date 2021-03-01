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
@Table(name = "tb_mcg_mt_message")
public class MCGMTMessage implements Serializable{

	@Id
	//@GeneratedValue
	private Integer id;
	private String action;
	@Column(name="type")
	private String type;
	@Column(name="service_config_id")
	private Integer serviceConfigId;
	@Column(name="fallback_service_config_id")	
	private Integer fallbackServiceConfigId;
	private String number;
	private String message;
	private String title;
	private String cc;
	private String ekey;
	private String reply;
	@Column(name = "message_id")
	private String messageId;
	private String network;
	private String currency;
	private String value;
	private String smscat;
	private String encoding;
	@Column(name = "request_url")
	private String requestUrl;
	@Column(name = "response_code")
	private String responseCode;;
	private String response;
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name = "dlr_received_status")
	private String dlrReceivedStatus;
	
	@Column(name = "dlr_received_time")
	private String dlrReceivedTime;
	
	@Column(name = "token")
	private String token;
	
	private Boolean status;
	
	public MCGMTMessage(){}
	public MCGMTMessage(boolean status){
		
		id=MCGConstant.inMCGMTMessageIdAtomicInteger.incrementAndGet();
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getEkey() {
		return ekey;
	}
	public void setEkey(String ekey) {
		this.ekey = ekey;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSmscat() {
		return smscat;
	}
	public void setSmscat(String smscat) {
		this.smscat = smscat;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getServiceConfigId() {
		return serviceConfigId;
	}
	public void setServiceConfigId(Integer serviceConfigId) {
		this.serviceConfigId = serviceConfigId;
	}
	public Integer getFallbackServiceConfigId() {
		return fallbackServiceConfigId;
	}
	public void setFallbackServiceConfigId(Integer fallbackServiceConfigId) {
		this.fallbackServiceConfigId = fallbackServiceConfigId;
	}
	public String getDlrReceivedStatus() {
		return dlrReceivedStatus;
	}
	public void setDlrReceivedStatus(String dlrReceivedStatus) {
		this.dlrReceivedStatus = dlrReceivedStatus;
	}
	public String getDlrReceivedTime() {
		return dlrReceivedTime;
	}
	public void setDlrReceivedTime(String dlrReceivedTime) {
		this.dlrReceivedTime = dlrReceivedTime;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
