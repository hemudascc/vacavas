package net.mycomp.actel;

import net.util.SubscriptionMode;

public enum ActelMode {

	MO("1","MO",SubscriptionMode.SMS.getMode()),
	WIFI("2","WIFI",SubscriptionMode.WIFI.getMode()),
	HE("3","HE",SubscriptionMode.WAP.getMode()),
	USSD("4","USSD",SubscriptionMode.USSD.getMode()),
	Unkown("0","Unkown",SubscriptionMode.UNKNOWN.getMode());
	
	private String flow;
	private String flowDesc;
	private String mode;
	
	ActelMode(String flow,String flowDesc,String mode){
		this.flow=flow;
		this.flowDesc=flowDesc;
		this.mode=mode;
		
	}	
	
	public static String getFlowDesc(String flow){
		try{
		for(ActelMode actelMode:values()){
			if(actelMode.flow.equalsIgnoreCase(flow)){
				return actelMode.flowDesc;
			}
		}
		}catch(Exception ex){}
		return null;
	}
	
public static String getMode(String flow){
		try{
		for(ActelMode actelMode:values()){
			if(actelMode.flow.equalsIgnoreCase(flow)){
				return actelMode.mode;
			}
		}
		}catch(Exception ex){
			
		}
		return null;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
}
