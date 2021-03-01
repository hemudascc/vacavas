package net.persist.bean;


import java.lang.reflect.Field;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;

@Entity
@Table(name ="vw_circle_info")
@NamedQueries({
		@NamedQuery(name = "VWCircleInfo.findCircleInfoByMsisdnSeries",
            query = "SELECT b FROM VWCircleInfo b WHERE b.msisdnPrefix=:msisdnPrefix"),
		@NamedQuery(name = "VWCircleInfo.findDefaultCircleInfo", 
		query = "SELECT b FROM VWCircleInfo b WHERE b.circleName=:circle"),	
		
		@NamedQuery(name = "VWCircleInfo.findAllCircleInfo", 
		query = "SELECT b FROM VWCircleInfo b ")})

public class VWCircleInfo {

	
	@Id
	@Column(name = "msisdn_series_id")
	private Integer msisdnSeriesId;
	@Column(name = "circle_id", nullable = false)
	private Integer circleId;
	@Column(name = "operator_id", nullable = false)
	private Integer operatorId;
	@Column(name = "circle_name", nullable = false)
	private String circleName;
	@Column(name = "zone_name")
	private String zoneName;
	@Column(name = "operator_circles_status")
	private Boolean operatorCirclesStatus;
	private Integer lrn;
	
	@Column(name = "op_circle_code")
	private String opCircleCode;
	@Column(name = "op_circle_code_descp")
	private String opCircleCodeDescp;
	@Column(name = "msisdn_prefix")
	private String msisdnPrefix;
	@Column(name = "date_of_creation")
	private Timestamp dateOfCreation;
	@Column(name = "last_update")
	private Timestamp lastUpdate;
	@Column(name = "msisdn_series_status")
	private Boolean msisdnSeriesStatus;
	
	

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



public Integer getMsisdnSeriesId() {
	return msisdnSeriesId;
}



public void setMsisdnSeriesId(Integer msisdnSeriesId) {
	this.msisdnSeriesId = msisdnSeriesId;
}



public Integer getCircleId() {
	return circleId;
}



public void setCircleId(Integer circleId) {
	this.circleId = circleId;
}



public Integer getOperatorId() {
	return operatorId;
}



public void setOperatorId(Integer operatorId) {
	this.operatorId = operatorId;
}



public String getCircleName() {
	return circleName;
}



public void setCircleName(String circleName) {
	this.circleName = circleName;
}



public String getZoneName() {
	return zoneName;
}



public void setZoneName(String zoneName) {
	this.zoneName = zoneName;
}



public Boolean getOperatorCirclesStatus() {
	return operatorCirclesStatus;
}



public void setOperatorCirclesStatus(Boolean operatorCirclesStatus) {
	this.operatorCirclesStatus = operatorCirclesStatus;
}



public Integer getLrn() {
	return lrn;
}



public void setLrn(Integer lrn) {
	this.lrn = lrn;
}



public String getOpCircleCode() {
	return opCircleCode;
}



public void setOpCircleCode(String opCircleCode) {
	this.opCircleCode = opCircleCode;
}



public String getOpCircleCodeDescp() {
	return opCircleCodeDescp;
}



public void setOpCircleCodeDescp(String opCircleCodeDescp) {
	this.opCircleCodeDescp = opCircleCodeDescp;
}



public String getMsisdnPrefix() {
	return msisdnPrefix;
}



public void setMsisdnPrefix(String msisdnPrefix) {
	this.msisdnPrefix = msisdnPrefix;
}



public Timestamp getDateOfCreation() {
	return dateOfCreation;
}



public void setDateOfCreation(Timestamp dateOfCreation) {
	this.dateOfCreation = dateOfCreation;
}



public Timestamp getLastUpdate() {
	return lastUpdate;
}



public void setLastUpdate(Timestamp lastUpdate) {
	this.lastUpdate = lastUpdate;
}
public Boolean getMsisdnSeriesStatus() {
	return msisdnSeriesStatus;
}
public void setMsisdnSeriesStatus(Boolean msisdnSeriesStatus) {
	this.msisdnSeriesStatus = msisdnSeriesStatus;
}

}
