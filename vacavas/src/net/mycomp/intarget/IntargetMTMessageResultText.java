package net.mycomp.intarget;

public enum IntargetMTMessageResultText {

	QUEUED("QUEUED","Received from the client"),
	SUBMITTED("SUBMITTED","Submitted to gateway (network operator)"),
	ACKNOWLEDGED("ACKNOWLEDGED","Acknowledged by gateway or authorized (OBS)"),
	RECEIPTED("RECEIPTED","Successfully delivered or Confirmed (OBS)"),
	CANCELLED("CANCELLED","Cancelled (OBS only)"),
	EXPIRED("EXPIRED","Validity period has expired"),
	PENDING("PENDING","Pending authentication (e.g. LBS)"),
	DENIED("DENIED","Authorization denied"),
	FAILED("FAILED","Generic Failure"),
	ERROR("ERROR","ERROR");
	
	private String status;
	private String description;
	
	IntargetMTMessageResultText(String status,String description){
		this.status=status;
		this.description=description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
