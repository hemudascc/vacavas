package net.mycomp.intarget;

import javax.xml.bind.annotation.XmlElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

public class InTargetRequest {

	@XmlPath("@Type")
	private String requestType;
	@XmlPath("@RefNo")
	private String refNo;
	@XmlElement(name="UserID")
	private String userId;
	@XmlElement(name="Password")
	private String password;
	
	@XmlPath("SendSMS/@ToAddr")
	private String toAddr;
	@XmlPath("SendSMS/@Validity")
	private String validity;
	@XmlPath("SendSMS/@Flags")
	private String flags;
	@XmlPath("SendSMS/Reply/@Tag")
	private String tag="";
	@XmlPath("SendSMS/Ticket/@Type")
	private String ticketType="Mobile";
	
	@XmlPath("SendSMS/Ticket/@Service")
	private String ticketService;
	
	@XmlPath("SendSMS/Ticket/@Value")
	private String ticketValue;
	
	///
	
	@XmlPath("SendSMS/AdultRating/text()")
	private String adultRating;
	
	@XmlPath("SendSMS/Ticket/@OBSService")
	private String obsService;
	
	@XmlPath("SendSMS/Ticket/@SubService")
	private String subService;
	
	@XmlPath("SendSMS/Ticket/@ChargeAddr")
	private String chargeAddr;
	
	@XmlPath("SendSMS/Ticket/@Description")
	private String description;
	//
	
	@XmlPath("SendSMS/Content/text()")
	private String content;
	
	@XmlPath("SendSMS/Content/@Type")
	private String contentType="TEXT";
	
	
	
	public InTargetRequest(){}
	public InTargetRequest(String requestType,String refNo,String userId,String password,String toAddr,
			String validity,String flags,String ticketService,String ticketValue,String content,
			String tag){
		
		this.requestType=requestType;
		this.refNo=refNo;
		this.userId=userId;
		this.password=password;
		this.toAddr=toAddr;
		this.validity=validity;
		this.flags=flags;
		this.ticketService=ticketService;
		this.ticketValue=ticketValue;
		this.content=content;
		this.tag=tag;
		
	}

	public InTargetRequest(String requestType,String refNo,String userId,String password,String toAddr,
			String validity,String flags,String ticketService,String ticketValue,String content,
			String tag,String subService,String chargeAddr,String description, String adultRating){
		
		this.requestType=requestType;
		this.refNo=refNo;
		this.userId=userId;
		this.password=password;
		this.toAddr=toAddr;
		this.validity=validity;
		this.flags=flags;
		this.ticketService=ticketService;
		this.ticketValue=ticketValue;
		this.content=content;
		this.tag=tag;
		this.subService=subService;
		this.chargeAddr=chargeAddr;
		this.description=description;
		this.adultRating=adultRating;
		this.obsService="";
	}
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getTicketService() {
		return ticketService;
	}

	public void setTicketService(String ticketService) {
		this.ticketService = ticketService;
	}

	public String getTicketValue() {
		return ticketValue;
	}

	public void setTicketValue(String ticketValue) {
		this.ticketValue = ticketValue;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getSubService() {
		return subService;
	}
	public void setSubService(String subService) {
		this.subService = subService;
	}
	public String getChargeAddr() {
		return chargeAddr;
	}
	public void setChargeAddr(String chargeAddr) {
		this.chargeAddr = chargeAddr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdultRating() {
		return adultRating;
	}
	public void setAdultRating(String adultRating) {
		this.adultRating = adultRating;
	}
	public String getObsService() {
		return obsService;
	}
	public void setObsService(String obsService) {
		this.obsService = obsService;
	}
	
	
	
}
