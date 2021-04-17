package net.mycomp.timwe;

import java.util.Map;
import java.util.Objects;

public class TimweResponse {

	private String requestId;
	private String code;
	private Boolean inError;
	private String message;
	private Map<String,String> responseData;
	
	TimweResponse(){
		this.requestId=Objects.toString(System.currentTimeMillis());
		this.code="SUCCESS";
		this.inError=false;
		this.message="Notification has been received successfully";
	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getInError() {
		return inError;
	}
	public void setInError(Boolean inError) {
		this.inError = inError;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getResponseData() {
		return responseData;
	}
	public void setResponseData(Map<String, String> responseData) {
		this.responseData = responseData;
	}
	
	@Override
	public String toString() {
		return "TimweResponse [requestId=" + requestId + ", code=" + code + ", inError=" + inError + ", message="
				+ message + ", responseData=" + responseData + "]";
	}
}
