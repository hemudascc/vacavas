package net.mycomp.macrokiosk.thailand;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "tb_th_mt_message")
public class THMTMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue
	private Integer id;

//	@Column(name="action")
//	private String action;
	@Column(name="service_id")
	private Integer serviceId;
	
	@Column(name = "msg_type")
	private String messageType;
	
	@Column(name = "op_id")
	private Integer opId;
	@Column(name = "user")
	private String user;
	@Column(name = "pass")
	private String pass;
	private String type;
	@Column(name = "msisdn")
	private String msisdn;
	@Column(name = "from_str")
	private String fromStr;
	@Column(name = "text_msg")
	private String textMsg;
	private Double price;
	private Integer telcoId;
	private Integer cat;
	private String keyword;
	private String senderid;
	@Column(name = "request_url")
	private String requestUrl;
	private String response;
	@Column(name="mo_message_id")
	private Integer moMessageId;
	
	@Column(name = "msg_id")
	private String msgId;
	
	@Column(name = "linkid")
	private String linkId;	
	
	@Column(name = "mt_action_type")
	private String mtActionType;	
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "token_id")
	private Integer  tokenId;
	@Column(name = "token")
	private String token;
	
	@Column(name = "campaign_id")
	private Integer  campaignId;
	
	private Boolean status;
	
	public THMTMessage(){
		
	}
	
	public THMTMessage(boolean status){
	 id= ThiaConstant.mtMessageIdAtomicInteger.incrementAndGet();
	 this.createTime=new Timestamp(System.currentTimeMillis());
	 this.status=status;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	public Integer getTelcoId() {
		return telcoId;
	}
	public void setTelcoId(Integer telcoId) {
		this.telcoId = telcoId;
	}
	public Integer getCat() {
		return cat;
	}
	public void setCat(Integer cat) {
		this.cat = cat;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSenderid() {
		return senderid;
	}
	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
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
	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}


	public String getFromStr() {
		return fromStr;
	}


	public void setFromStr(String fromStr) {
		this.fromStr = fromStr;
	}


	public String getTextMsg() {
		return textMsg;
	}


	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}


	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}





	public String getMessageType() {
		return messageType;
	}


	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMtActionType() {
		return mtActionType;
	}
	public void setMtActionType(String mtActionType) {
		this.mtActionType = mtActionType;
	}
	public Integer getTokenId() {
		return tokenId;
	}
	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getMoMessageId() {
		return moMessageId;
	}
	public void setMoMessageId(Integer moMessageId) {
		this.moMessageId = moMessageId;
	}

	

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
