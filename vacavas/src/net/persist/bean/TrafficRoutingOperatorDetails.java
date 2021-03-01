package net.persist.bean;

import javax.persistence.Entity;

public class TrafficRoutingOperatorDetails {
private String msisdn;
private Integer operatorId;
private String operatorName;
private String sourceIp;
private Integer cmpId;
private Integer trafficRoutingAdnetworkId;



public Integer getOperatorId() {
	return operatorId;
}
public void setOperatorId(Integer operatorId) {
	this.operatorId = operatorId;
}
public String getOperatorName() {
	return operatorName;
}
public void setOperatorName(String operatorName) {
	this.operatorName = operatorName;
}
public String getSourceIp() {
	return sourceIp;
}
public void setSourceIp(String sourceIp) {
	this.sourceIp = sourceIp;
}
public String getMsisdn() {
	return msisdn;
}
public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
}
public Integer getCmpId() {
	return cmpId;
}
public void setCmpId(Integer cmpId) {
	this.cmpId = cmpId;
}
public Integer getTrafficRoutingAdnetworkId() {
	return trafficRoutingAdnetworkId;
}
public void setTrafficRoutingAdnetworkId(Integer trafficRoutingAdnetworkId) {
	this.trafficRoutingAdnetworkId = trafficRoutingAdnetworkId;
}

}
