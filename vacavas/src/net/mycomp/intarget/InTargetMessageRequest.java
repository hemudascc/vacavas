package net.mycomp.intarget;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlPath;


@XmlRootElement(name="Message")
//@XmlType(propOrder = { "version", "response"})
public  class InTargetMessageRequest {

	@XmlPath("Version/@Version")
	
	private String version="1.0";
	
	@XmlElement(name="Request")
	private InTargetRequest higateRequest;
	
	public InTargetMessageRequest(){}
	
	public InTargetMessageRequest(String requestType,String refNo,String userId,String password,String toAddr,
			String validity,String flags,String ticketService,String ticketValue,String content,String tag
			){
		this.higateRequest=new InTargetRequest( requestType, refNo, userId, password, toAddr,
				 validity, flags, ticketService, ticketValue, content, tag);
	}
	
	public InTargetMessageRequest(String requestType,String refNo,String userId,String password,String toAddr,
			String validity,String flags,String ticketService,String ticketValue,String content,String tag
			,String subService,String chargeAddr,String description, String adultRating){
		this.higateRequest=new InTargetRequest( requestType, refNo, userId, password, toAddr,
				 validity, flags, ticketService, ticketValue, content, tag, subService
				 , chargeAddr, description,  adultRating);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	

	public InTargetRequest getHigateRequest() {
		return higateRequest;
	}

	public void setHigateRequest(InTargetRequest higateRequest) {
		this.higateRequest = higateRequest;
	}
	
}
