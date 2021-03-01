package net.persist.bean;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.net.util.SubnetUtils;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;

@Entity
@Table(name = "tb_ippool")
@NamedQueries({ @NamedQuery(name = "IPPool.findAllEnable",
query = "SELECT b FROM IPPool b where b.status=:status and b.ipLongLowValue is null and "
		+ " b.ipLongHighValue is null"),
		 @NamedQuery(name = "IPPool.findIpPoolByLongValue",
		query = "SELECT b FROM IPPool b where b.status=true  and b.ipLongLowValue<=:ipValue1 "
				+ " and b.ipLongHighValue>=:ipValue2")	
		})
public class IPPool {
	
	@Id
	@GeneratedValue
	private Integer id;                 
	@Column(name = "subnet_ip_address")
	private String subnetIpAddress;        
//	@Column(name = "network_ip")
//	private String networkIp;       
//	@Column(name = "network_ip_long")
//	private Long networkIpLong;    
//	@Column(name = "brodacast_ip")
//	private String brodacastIp;      
//	@Column(name = "brodacast_ip_long")
//	private Long brodacastIpLong;  
	@Column(name = "ip_long_low_value")
	private Long ipLongLowValue;
	@Column(name = "ip_long_high_value")
	private Long ipLongHighValue;
	
	@Column(name = "op_id")
	private Integer opId;              
	@Column(name = "circle_id")
	private Integer circleId; 
	@Column(name = "circle_name")
	private String circleName;
	@Column(name = "state_name")
	private String stateName="";
	@Column(name = "created_date")
	private Timestamp createdDate;   
	@Column(name = "last_update")
	private Timestamp lastUpdate;        
	private Boolean status;
	@Column(name="op_name")
	private String opName;
	@Column(name="geoip_response")
	private String geoipResponse;
	@Transient
	public SubnetUtils subnetUtils;
	@Transient
	private String info="pool";
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
	public Integer getCircleId() {
		return circleId;
	}
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getSubnetIpAddress() {
		return subnetIpAddress;
	}
	public void setSubnetIpAddress(String subnetIpAddress) {
		this.subnetIpAddress = subnetIpAddress;
	}
	public SubnetUtils getSubnetUtils() {
		return subnetUtils;
	}
	public void setSubnetUtils(SubnetUtils subnetUtils) {
		this.subnetUtils = subnetUtils;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getGeoipResponse() {
		return geoipResponse;
	}
	public void setGeoipResponse(String geoipResponse) {
		this.geoipResponse = geoipResponse;
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
	public Long getIpLongLowValue() {
		return ipLongLowValue;
	}
	public void setIpLongLowValue(Long ipLongLowValue) {
		this.ipLongLowValue = ipLongLowValue;
	}
	public Long getIpLongHighValue() {
		return ipLongHighValue;
	}
	public void setIpLongHighValue(Long ipLongHighValue) {
		this.ipLongHighValue = ipLongHighValue;
	}

}
