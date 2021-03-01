package net.mycomp.intarget;

public enum IntargetFlag {

	HIGATE_FLAG_SMS_POPUP("HIGATE_FLAG_SMS_POPUP","0x00000001","Send SMS as a popup message if supported by operator"),
	HIGATE_FLAG_SMS_FLASH("HIGATE_FLAG_SMS_FLASH","0x00000002","Send SMS as a flashing popup message if supported by operator"),
	HIGATE_FLAG_SMS_8BIT("HIGATE_FLAG_SMS_8BIT","0x00000004","SMS contains 8 bit (binary) data note1"),
	HIGATE_FLAG_SMS_UDH("HIGATE_FLAG_SMS_UDH","0x00000008","SMS data includes a User Data Header (UDH)"),
	HIGATE_FLAG_SMS_SEGMENT("HIGATE_FLAG_SMS_SEGMENT","0x00000010","SMS is a segment of a larger message"),
	HIGATE_FLAG_SMS_DN_MASK("HIGATE_FLAG_SMS_DN_MASK","0x001F0000","SMS Delivery Receipt Mask"),
	HIGATE_FLAG_SMS_DN_FINAL("HIGATE_FLAG_SMS_DN_FINAL","0x00010000","SMS Delivery Receipt requested where final delivery outcome is success or failure"),
	HIGATE_FLAG_SMS_DN_FAILED("HIGATE_FLAG_SMS_DN_FAILED","0x00020000","SMS Delivery Receipt requested where final delivery outcome is failure"),
	HIGATE_FLAG_SMS_DN_INTERM("HIGATE_FLAG_SMS_DN_INTERM","0x00100000","SMS Delivery Receipt requested for Intermediate notification"),
	HIGATE_FLAG_OBS_LINKED("HIGATE_FLAG_OBS_LINKED","0x00000020","SMS pending OBS authentication"),
	HIGATE_FLAG_OBS_TKT_ADDR("HIGATE_FLAG_OBS_TKT_ADDR","0x00000040","Charge the sms to the ticket charge address (OBS-linked only)"),
	HIGATE_FLAG_OBS_AUTO_CONFIRM("HIGATE_FLAG_OBS_AUTO_CONFIRM","0x00000200","Auto-confirm once authorized"),
	HIGATE_FLAG_OBS_SUBSCRIBE_ONLY("HIGATE_FLAG_OBS_SUBSCRIBE_ONLY","0x00000400","OBS transaction is a subscription only transaction"),
	HIGATE_FLAG_HEX_ENCODED("HIGATE_FLAG_HEX_ENCODED","0x00000080","The source string is hex encoded text (eg \"060BFC')"),
	HIGATE_FLAG_MT_BILLED("HIGATE_FLAG_MT_BILLED","0x00000100","MT Billed content"),
	HIGATE_FLAG_USS_EXIT("HIGATE_FLAG_USS_EXIT","0x00000001","Terminate this USSD Session")
	;
	
	private String flageName;
	private String hexValue;
	private String desc;
	
	IntargetFlag(String flageName,String hexValue,String desc){
		this.flageName=flageName;
		this.hexValue=hexValue;
		this.desc=desc;
	}

	public String getFlageName() {
		return flageName;
	}

	public void setFlageName(String flageName) {
		this.flageName = flageName;
	}

	public String getHexValue() {
		return hexValue;
	}

	public void setHexValue(String hexValue) {
		this.hexValue = hexValue;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
