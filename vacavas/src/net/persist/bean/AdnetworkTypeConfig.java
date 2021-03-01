package net.persist.bean;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.util.JpaStringListConverter;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Convert;

@Entity
@Table(name = "tb_adnetwork_type_config")
@NamedQueries({ @NamedQuery(name = "AdnetworkTypeConfig.findAdnetworkTypeConfig", 
query = "SELECT b FROM AdnetworkTypeConfig b where b.status=:status")
	})
public class AdnetworkTypeConfig {

	@Id
	private Integer id;
	@Column(name = "type")
	private String type;
	@Column(name = "adnetwork_id")
	private Integer adnetwokId;
	@Column(name = "operator_id")
	private Integer operatorId;	
	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "adnetwork_type_config_id")
	private List<AdnetworkCircleCappingConfig> listAdnetworkCircleCappingConfig;	
	@Column(name = "block_circle")
	@Convert(converter=JpaStringListConverter.class)
	private List<Integer> blockCircleList;
	@Column(name = "prepaid_block_circles")
	@Convert(converter=JpaStringListConverter.class)
	private List<Integer> prepaidBlockCircles;
	@Column(name = "postpaid_block_circles")
	@Convert(converter=JpaStringListConverter.class)
	private List<Integer> postpaidBlockCircles;
	@Column(name = "ip_check")
	private Boolean ipCheck=Boolean.FALSE;
	@Column(name = "block_series_check")
	private Boolean blockSeriesCheck;
	@Column(name = "info")
	private String info;
	@Column(name = "status")
	private Boolean status;
	
	@Transient
	private Map<Integer,OperatorCircles> mapBlockCircle=new HashMap<Integer,OperatorCircles>();
	
	@Transient
	private Map<Integer,OperatorCircles> mapPrepaidBlockCircle=new HashMap<Integer,OperatorCircles>();
	
	@Transient
	private Map<Integer,OperatorCircles> mapPostpaidBlockCircle=new HashMap<Integer,OperatorCircles>();

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

	public AdnetworkCircleCappingConfig findAdnetworkCircleCappingConfig(Integer circleId){
		
		if(listAdnetworkCircleCappingConfig==null){
			return null;
		}
		
		for(AdnetworkCircleCappingConfig adnetworkCircleCappingConfig:listAdnetworkCircleCappingConfig){
			if(adnetworkCircleCappingConfig.getCircleId()==circleId.intValue()){
				return adnetworkCircleCappingConfig;
			}
		}
		return null;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getAdnetwokId() {
		return adnetwokId;
	}
	public void setAdnetwokId(Integer adnetwokId) {
		this.adnetwokId = adnetwokId;
	}
	public List<Integer> getBlockCircleList() {
		return blockCircleList;
	}
	public void setBlockCircleList(List<Integer> blockCircleList) {
		this.blockCircleList = blockCircleList;
	}
	public Boolean getIpCheck() {
		return ipCheck;
	}
	public void setIpCheck(Boolean ipCheck) {
		this.ipCheck = ipCheck;
	}
	public Boolean getBlockSeriesCheck() {
		return blockSeriesCheck;
	}
	public void setBlockSeriesCheck(Boolean blockSeriesCheck) {
		this.blockSeriesCheck = blockSeriesCheck;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public List<Integer> getPrepaidBlockCircles() {
		return prepaidBlockCircles;
	}
	public void setPrepaidBlockCircles(List<Integer> prepaidBlockCircles) {
		this.prepaidBlockCircles = prepaidBlockCircles;
	}
	public List<Integer> getPostpaidBlockCircles() {
		return postpaidBlockCircles;
	}
	public void setPostpaidBlockCircles(List<Integer> postpaidBlockCircles) {
		this.postpaidBlockCircles = postpaidBlockCircles;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	
	public List<AdnetworkCircleCappingConfig> getListAdnetworkCircleCappingConfig() {
		return listAdnetworkCircleCappingConfig;
	}
	public void setListAdnetworkCircleCappingConfig(List<AdnetworkCircleCappingConfig> listAdnetworkCircleCappingConfig) {
		this.listAdnetworkCircleCappingConfig = listAdnetworkCircleCappingConfig;
	}
	public Map<Integer, OperatorCircles> getMapBlockCircle() {
		return mapBlockCircle;
	}
	public void setMapBlockCircle(Map<Integer, OperatorCircles> mapBlockCircle) {
		this.mapBlockCircle = mapBlockCircle;
	}
	public Map<Integer, OperatorCircles> getMapPrepaidBlockCircle() {
		return mapPrepaidBlockCircle;
	}
	public void setMapPrepaidBlockCircle(Map<Integer, OperatorCircles> mapPrepaidBlockCircle) {
		this.mapPrepaidBlockCircle = mapPrepaidBlockCircle;
	}
	public Map<Integer, OperatorCircles> getMapPostpaidBlockCircle() {
		return mapPostpaidBlockCircle;
	}
	public void setMapPostpaidBlockCircle(Map<Integer, OperatorCircles> mapPostpaidBlockCircle) {
		this.mapPostpaidBlockCircle = mapPostpaidBlockCircle;
	}
	
}
