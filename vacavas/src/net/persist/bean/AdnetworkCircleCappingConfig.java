package net.persist.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

import java.lang.reflect.Field;

import javax.persistence.Column;

@Entity
@Table(name = "tb_adnetwork_circle_capping_config")
public class AdnetworkCircleCappingConfig {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "adnetwork_type_config_id")
	private Integer adnetworkTypeConfigId;
	@Column(name = "circle_id")
	private Integer circleId;
	@Column(name = "conversion_capping")
	private Integer conversionCapping;
	private Boolean status;
	
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

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCircleId() {
		return circleId;
	}
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	public Integer getConversionCapping() {
		return conversionCapping;
	}
	public void setConversionCapping(Integer conversionCapping) {
		this.conversionCapping = conversionCapping;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getAdnetworkTypeConfigId() {
		return adnetworkTypeConfigId;
	}
	public void setAdnetworkTypeConfigId(Integer adnetworkTypeConfigId) {
		this.adnetworkTypeConfigId = adnetworkTypeConfigId;
	}
	
}
