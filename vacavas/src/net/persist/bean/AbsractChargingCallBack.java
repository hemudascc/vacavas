package net.persist.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlTransient;


@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbsractChargingCallBack implements IChargingCallBack, Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@XmlTransient
	@GeneratedValue
	private Integer id;	
	@Column(name = "action")
	@XmlTransient
	private String action;
	@Column(name = "campaign_id")
	@XmlTransient
	private Integer campaignId;
	@Column(name = "send_to_adnetwork")
	@XmlTransient
	private Boolean sendToAdnetwork = Boolean.FALSE;
	@Column(name = "send_to_adnetwork_by")
	@XmlTransient
	private String sendToAdnetworkBY;
	@Column(name = "churn")
	@XmlTransient
	private Boolean churn;
	@Column(name = "token")
	@XmlTransient
	private String token;	
	@Column(name = "token_id")
	@XmlTransient
	private Integer tokenId;	
	@Column(name = "query_str")
	@XmlTransient
	private String queryStr;
	@Column(name = "duplicate")
	@XmlTransient
	private Boolean duplicate = Boolean.FALSE;
	@Column(name = "create_time")
	@XmlTransient
	private Timestamp createTime;	
	@Column(name = "act_key")
	@XmlTransient
	private String actKey;	
	
	@Transient
	@XmlTransient
	private int graceConversionCount;
	@Transient
	@XmlTransient
	private int conversionCount;
	@Transient
	@XmlTransient
	private int blockCount;
	
	public final Boolean getSendToAdnetwork() {
		return sendToAdnetwork;
	}

	public final void setSendToAdnetwork(Boolean sendToAdnetwork) {
		this.sendToAdnetwork = sendToAdnetwork;
	}

	public final Boolean getChurn() {
		return churn;
	}

	public final void setChurn(Boolean churn) {
		this.churn = churn;
	}

	public final Integer getCampaignId() {
		return campaignId;
	}

	public final void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	public final String getToken() {
		return token;
	}

	public final void setToken(String token) {
		this.token = token;
	}

	public final String getAction() {
		return action;
	}

	public final void setAction(String action) {
		this.action = action;
	}

	public final String getQueryStr() {
		return queryStr;
	}

	public final void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public final Boolean getDuplicate() {
		return duplicate;
	}

	public final void setDuplicate(Boolean duplicate) {
		this.duplicate = duplicate;
	}

	public final String getActKey() {
		return actKey;
	}

	public final void setActKey(String actKey) {
		this.actKey = actKey;
	}
	
	public final Timestamp getCreateTime() {
		return createTime;
	}

    
	public final void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}

	public final int getGraceConversionCount() {
		return graceConversionCount;
	}

	public final void setGraceConversionCount(int graceConversionCount) {
		this.graceConversionCount = graceConversionCount;
	}

	public final int getConversionCount() {
		return conversionCount;
	}

	public final void setConversionCount(int conversionCount) {
		this.conversionCount = conversionCount;
	}

	public final int getBlockCount() {
		return blockCount;
	}

	public final void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	public final Integer getTokenId() {
		return tokenId;
	}

	public final void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public final String getSendToAdnetworkBY() {
		return sendToAdnetworkBY;
	}

	public final void setSendToAdnetworkBY(String sendToAdnetworkBY) {
		this.sendToAdnetworkBY = sendToAdnetworkBY;
	}
}
