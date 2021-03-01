package net.mycomp.oredoo.kuwait;

import net.util.SubscriptionMode;

public enum OredooKuwaitSubcriptionMode {
    
	SMS("SMS",SubscriptionMode.SMS.getMode()),
	WAP("WAP",SubscriptionMode.WAP.getMode()),
	WEB("WEBPORTAL",SubscriptionMode.WEB.getMode()),
	MOBAPP("MOBAPP",SubscriptionMode.INAPP.getMode()),
	CC("CC",SubscriptionMode.CC.getMode());
	
	private String bearerId;
	private String mode;
	
	OredooKuwaitSubcriptionMode(String bearerId,String mode){
		this.bearerId=bearerId;
		this.mode=mode;
	}
	
	public static String getMode(String bearerId){
		for(OredooKuwaitSubcriptionMode oredooKuwaitSubcriptionMode:values()){
			if(oredooKuwaitSubcriptionMode.getBearerId().equalsIgnoreCase(bearerId)){
				return oredooKuwaitSubcriptionMode.mode;
			}
		}
		return null;
	}

	public String getBearerId() {
		return bearerId;
	}

	public void setBearerId(String bearerId) {
		this.bearerId = bearerId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
