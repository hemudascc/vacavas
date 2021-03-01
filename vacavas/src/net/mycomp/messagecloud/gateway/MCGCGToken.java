package net.mycomp.messagecloud.gateway;

import net.util.MConstants;
import net.util.MUtility;

public class MCGCGToken{ 

private String type;
private int mtId;
private int serviceConfigId;
private int fallbackServiceConfigId;

public MCGCGToken(String type,int mtId,int serviceConfigId,int fallbackServiceConfigId){
	
	this.type=type;
	this.mtId=mtId;	
	this.serviceConfigId=serviceConfigId;
	this.fallbackServiceConfigId=fallbackServiceConfigId;
}

public String getCGToken(){
	 return type + MConstants.TOKEN_SEPERATOR
			+ mtId +MConstants.TOKEN_SEPERATOR+serviceConfigId
			+MConstants.TOKEN_SEPERATOR+fallbackServiceConfigId;
}

public MCGCGToken(String str,String messageId){

	try{
		String token[]=str.split("\\|");
		//this.type=token[0];
		this.mtId=Integer.parseInt(token[1]);
		//this.serviceConfigId=Integer.parseInt(token[2]);
		//this.fallbackServiceConfigId=Integer.parseInt(token[3]);
	}catch(Exception ex){
		type=null;
		
		serviceConfigId=0;
		serviceConfigId=0;
		fallbackServiceConfigId=0;
		this.mtId=MUtility.toInt(messageId,0);
	}
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public int getMtId() {
	return mtId;
}

public void setMtId(int mtId) {
	this.mtId = mtId;
}

public int getServiceConfigId() {
	return serviceConfigId;
}

public void setServiceConfigId(int serviceConfigId) {
	this.serviceConfigId = serviceConfigId;
}

public int getFallbackServiceConfigId() {
	return fallbackServiceConfigId;
}

public void setFallbackServiceConfigId(int fallbackServiceConfigId) {
	this.fallbackServiceConfigId = fallbackServiceConfigId;
}

}
