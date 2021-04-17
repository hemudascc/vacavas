package net.mycomp.macrokiosk.thailand;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_th_mo_message")
public class THMOMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue
	private Integer id;
	@Column(name="action")
	private String action;
	
	@Column(name = "op_id")
	private Integer opId;
	@Column(name="msisdn")
	private String msisdn;
	@Column(name="text_msg")
	private String text;
	@Column(name="time_str")
	private String time;
	@Column(name = "req_time")
	private Timestamp reqTime;
	@Column(name = "short_code")
	private String shortcode;	
	@Column(name = "keyword")
	private String keyword;	
	private String moid;
	private String msgid;
	private Integer telcoid;
	private String channel;
	private String refId;
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name="query_str")
	private String queryStr;
	@Column(name = "status")
	private Boolean status=true;
	
	@Column(name = "token_id")
	private Integer tokenId;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "campaign_id")
	private Integer campaignId;
	
	@Column(name = "activation_processed")
	private Boolean activationProcessed=false;
	
	@Column(name = "duplicate")
	private Boolean duplicate=false;
		
	public THMOMessage(){}
	
	public THMOMessage(boolean status){
		id= ThiaConstant.moMessageIdAtomicInteger.incrementAndGet();
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

	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getMoid() {
		return moid;
	}

	public void setMoid(String moid) {
		this.moid = moid;
	}

	public Integer getTelcoid() {
		return telcoid;
	}

	public void setTelcoid(Integer telcoid) {
		this.telcoid = telcoid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
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

	public Timestamp getReqTime() {
		return reqTime;
	}

	public void setReqTime(Timestamp reqTime) {
		this.reqTime = reqTime;
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



	public String getQueryStr() {
		return queryStr;
	}



	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}



	public String getMsgid() {
		return msgid;
	}



	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}



	public String getMsisdn() {
		return msisdn;
	}



	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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



	public Boolean getActivationProcessed() {
		return activationProcessed;
	}



	public void setActivationProcessed(Boolean activationProcessed) {
		this.activationProcessed = activationProcessed;
	}



	public Boolean getDuplicate() {
		return duplicate;
	}



	public void setDuplicate(Boolean duplicate) {
		this.duplicate = duplicate;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


}
