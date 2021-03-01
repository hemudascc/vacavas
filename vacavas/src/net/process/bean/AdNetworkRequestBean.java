package net.process.bean;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Map;

import net.persist.bean.AdnetworkOperatorConfig;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.AdnetworkTypeConfig;
import net.persist.bean.CampaignDetails;
import net.persist.bean.VWServiceCampaignDetail;

public class AdNetworkRequestBean {

	private String msisdn;
	private int adNetworkId;	
	private int cmpid;
	private String token;
	private String userAgent;
	private String sourceIp;
	private String httpRefrer;
	private String queryString;
	private String headersStr;
	private String requestBody;
	private Timestamp reqTime;
	private String pubId;
	private String referer;
	private int opId;
	private int smartCampaignGroupId;
	private Map<String,String> headerMap;
	public VWServiceCampaignDetail vwserviceCampaignDetail;
	public AdnetworkToken adnetworkToken;

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

public String getMsisdn() {
	return msisdn;
}

public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
}

public int getAdNetworkId() {
	return adNetworkId;
}

public void setAdNetworkId(int adNetworkId) {
	this.adNetworkId = adNetworkId;
}

public int getCmpid() {
	return cmpid;
}

public void setCmpid(int cmpid) {
	this.cmpid = cmpid;
}

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getUserAgent() {
	return userAgent;
}

public void setUserAgent(String userAgent) {
	this.userAgent = userAgent;
}

public String getSourceIp() {
	return sourceIp;
}

public void setSourceIp(String sourceIp) {
	this.sourceIp = sourceIp;
}

public String getHttpRefrer() {
	return httpRefrer;
}

public void setHttpRefrer(String httpRefrer) {
	this.httpRefrer = httpRefrer;
}

public String getQueryString() {
	return queryString;
}

public void setQueryString(String queryString) {
	this.queryString = queryString;
}

public String getHeadersStr() {
	return headersStr;
}

public void setHeadersStr(String headersStr) {
	this.headersStr = headersStr;
}

public String getRequestBody() {
	return requestBody;
}

public void setRequestBody(String requestBody) {
	this.requestBody = requestBody;
}

public Timestamp getReqTime() {
	return reqTime;
}

public void setReqTime(Timestamp reqTime) {
	this.reqTime = reqTime;
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

public int getOpId() {
	return opId;
}

public void setOpId(int opId) {
	this.opId = opId;
}

public int getSmartCampaignGroupId() {
	return smartCampaignGroupId;
}

public void setSmartCampaignGroupId(int smartCampaignGroupId) {
	this.smartCampaignGroupId = smartCampaignGroupId;
}

public Map<String, String> getHeaderMap() {
	return headerMap;
}

public void setHeaderMap(Map<String, String> headerMap) {
	this.headerMap = headerMap;
}
	}
