package net.process.bean;

import java.lang.reflect.Field;

public class DeactivationResponse {

	private boolean status;
	private String messgae;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessgae() {
		return messgae;
	}
	public void setMessgae(String messgae) {
		this.messgae = messgae;
	}
	
public String toString() {
		
	    Field[] fields = this.getClass().getDeclaredFields();
	    String str = this.getClass().getName();
	    try {
	        for (Field field : fields) {
	            str += field.getName() + "=" + field.get(this) + ",";
	        }
	    } catch (IllegalArgumentException ex) {
	        System.out.println(ex);
	    } catch (IllegalAccessException ex) {
	        System.out.println(ex);
	    }
	    return str;
	}

}
