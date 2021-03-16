package net.mycomp.comviva;

public enum ComvivaDBillRequestType {
	
	UNSUBSCRIPTION("1007"),
	SUBSCRIPTION("1006"),
	SUB_CHECK("1001"),
	MT_SMS("2015");
	
	public String requestType;
	
	ComvivaDBillRequestType(String requestType){
		this.requestType = requestType;
	}
	
}
