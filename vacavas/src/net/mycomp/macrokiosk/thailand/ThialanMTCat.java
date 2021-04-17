package net.mycomp.macrokiosk.thailand;

public enum ThialanMTCat {
	
	IOD(0,"Info on Demand (IOD) service, i.e. not subscription service."),
	SUBCRIBE_TYPE(1,"Welcome message when user subscribes. (Must contain Service Name, Price & instruction to confirm.)"),
	FIRST_PREMIUM_CONTENT(2,"First Premium content must be sent to the subscriber immediately after subscription."),
	ERROR(3,"Error Message."),
	WEEKLY_REMINDER(4,"Weekly reminder message to keep active subscriber status alive."),
	RENEWAL_SUBSCRIPTION(5,"Renewal of subscription (only applicable for monthly/weekly subscriptions)."),
	CONTENT_BRODCAST(6,"Content Broadcast"),
	CACELLATION_CONFIRMATION(7,"Cancellation confirmation"),
	UNREGISTER_SUBSCRIBER(7,"Unregister subscriber");
	
	
	ThialanMTCat(int catId,String description){
		this.catId=catId;
		this.description=description;
	}
	
	private int catId;
	private String description;
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
