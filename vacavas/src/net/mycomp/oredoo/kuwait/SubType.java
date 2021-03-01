package net.mycomp.oredoo.kuwait;

import java.io.Serializable;
import java.lang.reflect.Field;

public class SubType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean subscribed;
	private boolean chargeable;
	private String type;
	private String transId;
	private String msisdn;
	private OredooKuwaitServiceConfig oredooKuwaitServiceConfig;
	
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

	public boolean isSubscribed() {
		return subscribed;
	}
	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}
	public boolean isChargeable() {
		return chargeable;
	}
	public void setChargeable(boolean chargeable) {
		this.chargeable = chargeable;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public OredooKuwaitServiceConfig getOredooKuwaitServiceConfig() {
		return oredooKuwaitServiceConfig;
	}
	public void setOredooKuwaitServiceConfig(
			OredooKuwaitServiceConfig oredooKuwaitServiceConfig) {
		this.oredooKuwaitServiceConfig = oredooKuwaitServiceConfig;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	
}

