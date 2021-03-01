package net.mycomp.intarget;

public enum IntargetMessageType {
	
	USSD_MSG("2304",null,"",IntargetConstant.USSD_MSG),
	BILLED_MSG("256",null,"",IntargetConstant.BILLLED_MSG),
	CONTENT_MSG("0",null,"",IntargetConstant.CONTENT_MSG)
	;
	
	 String flag;
	 String dataCoding;
	 String replyTag;
	 String type;
	IntargetMessageType(String flag,String dataCoding,String replyTag,String type){
		this.flag=flag;
		this.dataCoding=replyTag;
		this.replyTag=replyTag;
		this.type=type;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getDataCoding() {
		return dataCoding;
	}
	public void setDataCoding(String dataCoding) {
		this.dataCoding = dataCoding;
	}
	public String getReplyTag() {
		return replyTag;
	}
	public void setReplyTag(String replyTag) {
		this.replyTag = replyTag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
