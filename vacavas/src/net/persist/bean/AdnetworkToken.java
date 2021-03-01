package net.persist.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "adnetwork_tokens")
@NamedQueries({ @NamedQuery(name = "AdnetworkTokens.findAdnetworkTokensByToken", 
query = "SELECT b FROM AdnetworkToken b where b.token=:token order by b.reqTime desc"),
	@NamedQuery(name = "AdnetworkTokens.findAdnetworkTokensById", 
	query = "SELECT b FROM AdnetworkToken b where b.tokenId=:tokenId")})

public class AdnetworkToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "token_id", unique = true, nullable = false)
	
	private Integer tokenId;
	private String source;
	@Column(name = "action")
	private String action;
	@Column(name="redirect_to_url")
	private String redirectToUrl;
	@Column(name = "adnetwork_id")
	private Integer adnetworkId;	
	@Column(name = "campaign_id")
	private Integer campaignId;	
	@Column(name="pub_id")
	private String pubId;	
	private String token;
	@Column(name="token_to_cg")
	private String tokenToCg;
	@Column(name = "req_time")
	private Timestamp reqTime;
	@Column(name = "req_time_long")
	private Long reqTimeLong;
	
	private Integer status;
	@Column(name = "status_descp")
	private String status_desc;
	@Column(name = "headers")
	private String headers;
	@Column(name = "query_str")
	private String queryStr;	
	@Column(name = "info")
	private String info;
	@Column(name="msisdn")
    private String msisdn;
		
	@Column(name="circle_id")
    private Integer circleId=0;
	
	@Column(name="conversion_received")
	private Boolean conversionReceived=Boolean.FALSE;
	@Column(name="conversion_received_time")
	private Timestamp conversionReceivedTime;
	@Column(name="conversion_send_to_adntework")
	private Boolean conversionSendToAdntework=Boolean.FALSE;;
	@Column(name="conversion_send_to_adntework_time")
	private Timestamp conversionSendToAdnteworkTime;
	@Column(name="adnetwork_callback_url")
	private String adnetworkCallbackUrl;
	@Column(name="adnetwork_callback_response")
	private String adnetworkCallbackResponse;	
	
	@Column(name="dct_adnetwork_callback_url")
	private String dctAdnetworkCallbackUrl;	
	@Column(name="dct_adnetwork_callback_response")
	private String dctAdnetworkCallbackResponse;	
	@Column(name="dct_adnetwork_callback_time")
	private Timestamp dctAdnetworkCallbackTime;
	
	
	@Column(name="renewal_adnetwork_callback_url")
	private String renewalAdnetworkCallbackUrl;	
	@Column(name="renewal_adnetwork_callback_response")
	private String renewalAdnetworkCallbackResponse;	
	@Column(name="renewal_adnetwork_callback_time")
	private Timestamp renewalAdnetworkCallbackTime;
	@Column(name="ren_counter")
	private Integer renewalCounter=0;
	@Column(name="referer")
	private String referer;
	
	@Column(name="duplicate_click")
	private Boolean duplicateClick=false;
	
	@Transient
	//@Column(name="param1")
	private String param1;
	@Transient
	//@Column(name="param2")
	private String param2;
	
	
	@Transient
	private boolean duplicateConversion;
	
	@Transient
	private String type;

	@Transient
	private Integer opId;
	

	public String getRenewalAdnetworkCallbackUrl() {
	return renewalAdnetworkCallbackUrl;
}


public void setRenewalAdnetworkCallbackUrl(String renewalAdnetworkCallbackUrl) {
	this.renewalAdnetworkCallbackUrl = renewalAdnetworkCallbackUrl;
}


public Integer getRenewalCounter() {
	return renewalCounter;
}


public void setRenewalCounter(Integer renewalCounter) {
	this.renewalCounter = renewalCounter;
}

public void addRenewalCounter(Integer renewalCounter) {
	this.renewalCounter = this.renewalCounter+renewalCounter;
}


public String getRenewalAdnetworkCallbackResponse() {
	return renewalAdnetworkCallbackResponse;
}


public void setRenewalAdnetworkCallbackResponse(String renewalAdnetworkCallbackResponse) {
	this.renewalAdnetworkCallbackResponse = renewalAdnetworkCallbackResponse;
}


public Timestamp getRenewalAdnetworkCallbackTime() {
	return renewalAdnetworkCallbackTime;
}


public void setRenewalAdnetworkCallbackTime(Timestamp renewalAdnetworkCallbackTime) {
	this.renewalAdnetworkCallbackTime = renewalAdnetworkCallbackTime;
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


	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getReqTime() {
		return reqTime;
	}

	public void setReqTime(Timestamp reqTime) {
		this.reqTime = reqTime;
	}

	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatus_desc() {
		return status_desc;
	}

	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public void addInfo(String infoStr) {
		if(info==null){
		this.info = infoStr;
		}else {
		this.info+=infoStr;
		}
	}


	public String getHeaders() {
		return headers;
	}


	public void setHeaders(String headers) {
		this.headers = headers;
	}


	public String getQueryStr() {
		return queryStr;
	}


	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	
	public Boolean getConversionReceived() {
		return conversionReceived;
	}

	public void setConversionReceived(Boolean conversionReceived) {
		this.conversionReceived = conversionReceived;
	}


	public Timestamp getConversionReceivedTime() {
		return conversionReceivedTime;
	}


	public void setConversionReceivedTime(Timestamp conversionReceivedTime) {
		this.conversionReceivedTime = conversionReceivedTime;
	}


	public Boolean getConversionSendToAdntework() {
		return conversionSendToAdntework;
	}


	public void setConversionSendToAdntework(Boolean conversionSendToAdntework) {
		this.conversionSendToAdntework = conversionSendToAdntework;
	}



	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public Long getReqTimeLong() {
		return reqTimeLong;
	}


	public void setReqTimeLong(Long reqTimeLong) {
		this.reqTimeLong = reqTimeLong;
	}


	public String getRedirectToUrl() {
		return redirectToUrl;
	}


	public void setRedirectToUrl(String redirectToUrl) {
		this.redirectToUrl = redirectToUrl;
	}


	public String getAdnetworkCallbackUrl() {
		return adnetworkCallbackUrl;
	}


	public void setAdnetworkCallbackUrl(String adnetworkCallbackUrl) {
		this.adnetworkCallbackUrl = adnetworkCallbackUrl;
	}


	public String getAdnetworkCallbackResponse() {
		return adnetworkCallbackResponse;
	}


	public void setAdnetworkCallbackResponse(String adnetworkCallbackResponse) {
		this.adnetworkCallbackResponse = adnetworkCallbackResponse;
	}


	public Timestamp getConversionSendToAdnteworkTime() {
		return conversionSendToAdnteworkTime;
	}


	public void setConversionSendToAdnteworkTime(Timestamp conversionSendToAdnteworkTime) {
		this.conversionSendToAdnteworkTime = conversionSendToAdnteworkTime;
	}


	public String getTokenToCg() {
		return tokenToCg;
	}


	public void setTokenToCg(String tokenToCg) {
		this.tokenToCg = tokenToCg;
	}


	public Integer getCircleId() {
		return circleId;
	}


	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}


	public boolean isDuplicateConversion() {
		return duplicateConversion;
	}


	public void setDuplicateConversion2(boolean duplicateConversion) {
		this.duplicateConversion = duplicateConversion;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public String getDctAdnetworkCallbackResponse() {
		return dctAdnetworkCallbackResponse;
	}


	public void setDctAdnetworkCallbackResponse(String dctAdnetworkCallbackResponse) {
		this.dctAdnetworkCallbackResponse = dctAdnetworkCallbackResponse;
	}


	public Timestamp getDctAdnetworkCallbackTime() {
		return dctAdnetworkCallbackTime;
	}


	public void setDctAdnetworkCallbackTime(Timestamp dctAdnetworkCallbackTime) {
		this.dctAdnetworkCallbackTime = dctAdnetworkCallbackTime;
	}


	public void setDuplicateConversion(boolean duplicateConversion) {
		this.duplicateConversion = duplicateConversion;
	}


	public String getDctAdnetworkCallbackUrl() {
		return dctAdnetworkCallbackUrl;
	}


	public void setDctAdnetworkCallbackUrl(String dctAdnetworkCallbackUrl) {
		this.dctAdnetworkCallbackUrl = dctAdnetworkCallbackUrl;
	}


	public String getPubId() {
		return pubId;
	}


	public void setPubId(String pubId) {
		this.pubId = pubId;
	}


	public String getReferer() {
		return referer;
	}


	public void setReferer(String referer) {
		this.referer = referer;
	}


	public Integer getOpId() {
		return opId;
	}


	public void setOpId(Integer opId) {
		this.opId = opId;
	}


	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}


	public Integer getAdnetworkId() {
		return adnetworkId;
	}


	public void setAdnetworkId(Integer adnetworkId) {
		this.adnetworkId = adnetworkId;
	}


	public String getParam1() {
		return param1;
	}


	public void setParam1(String param1) {
		this.param1 = param1;
	}


	public String getParam2() {
		return param2;
	}


	public void setParam2(String param2) {
		this.param2 = param2;
	}
	
}
