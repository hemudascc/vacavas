package net.util;

import java.lang.reflect.Field;

public class HTTPResponse {

	private int responseCode;
	private String responseStr;
	private StringBuffer headerStr;
	private String error;
	private boolean isSuccess;
	private String errorMessage;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseStr() {
		return responseStr;
	}
	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public StringBuffer getHeaderStr() {
		return headerStr;
	}
	public void setHeaderStr(StringBuffer headerStr) {
		this.headerStr = headerStr;
	}
	
	public  String toString(){
		StringBuilder sb=new StringBuilder("");
		sb.append("["+this.getClass().getName());
		Field[] allFields = this.getClass().getDeclaredFields();
	    for (Field field : allFields) {
	       try {
			sb.append(field.getName()+"="+field.get(this)+", ");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    sb.append("]");
	    return sb.toString();
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	}
