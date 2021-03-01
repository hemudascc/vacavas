
package net.process.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

public class AggReport implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Integer adnetworkId;
	private Integer opid;
	private Integer aggregatorId;
	private Integer productId;
	
	private Timestamp fromTime;
	private Timestamp toTime;
    private String reportType;
    private String msisdn;
    
    
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

	public Integer getOpid() {
		return opid;
	}

	public void setOpid(Integer opid) {
		this.opid = opid;
	}

	

	public Timestamp getFromTime() {
		return fromTime;
	}

	public void setFromTime(Timestamp fromTime) {
		this.fromTime = fromTime;
	}

	public Timestamp getToTime() {
		return toTime;
	}

	public void setToTime(Timestamp toTime) {
		this.toTime = toTime;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Integer getAdnetworkId() {
		return adnetworkId;
	}

	public void setAdnetworkId(Integer adnetworkId) {
		this.adnetworkId = adnetworkId;
	}

	public Integer getAggregatorId() {
		return aggregatorId;
	}

	public void setAggregatorId(Integer aggregatorId) {
		this.aggregatorId = aggregatorId;
	}

	

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
}
