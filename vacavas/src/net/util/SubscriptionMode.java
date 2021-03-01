package net.util;

public enum SubscriptionMode {

	WAP("WAP"),
	WEB("WEB"),
	SMS("SMS"),
	INAPP("INAPP"),
	WIFI("WIFI"),
	USSD("USSD"),
	CC("CC"),
	IVR("IVR"),
	UNKNOWN("UNKNOWN");
	
	private String mode;
	
	SubscriptionMode(String mode){
		this.mode=mode;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
