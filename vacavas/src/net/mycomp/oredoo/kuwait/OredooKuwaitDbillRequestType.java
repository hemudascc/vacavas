package net.mycomp.oredoo.kuwait;

public enum OredooKuwaitDbillRequestType {

	UNSUBSCRIPTION("1007"),
	SUBSCRIPTION("1006"),
	SUB_CHECK("1001"),
	MT_SMS("2015");
	
	public String requestType;
	
	OredooKuwaitDbillRequestType(String requestType){
		this.requestType=requestType;
	}
}
