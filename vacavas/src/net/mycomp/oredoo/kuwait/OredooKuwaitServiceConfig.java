package net.mycomp.oredoo.kuwait;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

import net.util.JpaConverterJson;

@Entity
@Table(name = "tb_oredoo_kuwait_service_config")
public class OredooKuwaitServiceConfig {

	@Id
	private Integer id;
	@Column(name = "service_name")
	private String serviceName;
	@Column(name = "short_code")
	private String shortCode;
	@Column(name = "service_id")
	private Integer serviceId;
	
	
	
	@Column(name = "cc_product_id")
	private Integer ccProductId;
	
	@Column(name = "cp_name")
	private String cpName;
	@Column(name="service_node")
	private String serviceNode;
	
	@Column(name="plan_id")
	private String planId;
	
	@Column(name="service_type")
	private String serviceType;
	
	@Column(name="comviva_service_id")
	private String comvivaServiceId;
	
	@Column(name = "product_id")
	private String productId;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "cp_id")
	private String cpId;
	@Column(name = "cp_pwd")
	private String cpPwd;
	@Column(name = "req_mode")
	private String reqMode;
	@Column(name = "req_type")
	private String reqType;
	@Column(name = "ism_id")
	private String ismId;
	
	@Column(name = "price_point")
	private Double pricePoint;
	
	@Column(name = "validity")
	private Integer validity;
	
	@Column(name = "renewal_price")
	private Double renewalPrice;
	
	@Column(name = "renewal_validity")
	private Integer renewalValidity;
	@Column(name="lp_images")
	@Convert(converter=JpaConverterJson.class)
	private List<String> lpImages;
	
	@Column(name = "sub_keyword")
	private String subKeyword;
	@Column(name = "unsub_keyword")
	private String unsubKeyword;
	
	@Column(name = "portal_url")
	private String portalUrl;
	
	
	@Column(name = "not_he_view")
	private String notHeView;
	@Column(name = "not_he_auto_wifi_view")
	private String notHeAutoWifiView;	
	@Column(name = "he_view")
	private String heView;
	@Column(name = "he_auto_view")
	private String heAutoView;
	
	
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
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getCpPwd() {
		return cpPwd;
	}
	public void setCpPwd(String cpPwd) {
		this.cpPwd = cpPwd;
	}
	public String getReqMode() {
		return reqMode;
	}
	public void setReqMode(String reqMode) {
		this.reqMode = reqMode;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getIsmId() {
		return ismId;
	}
	public void setIsmId(String ismId) {
		this.ismId = ismId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Double getPricePoint() {
		return pricePoint;
	}
	public void setPricePoint(Double pricePoint) {
		this.pricePoint = pricePoint;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public Double getRenewalPrice() {
		return renewalPrice;
	}
	public void setRenewalPrice(Double renewalPrice) {
		this.renewalPrice = renewalPrice;
	}
	public Integer getRenewalValidity() {
		return renewalValidity;
	}
	public void setRenewalValidity(Integer renewalValidity) {
		this.renewalValidity = renewalValidity;
	}

	public List<String> getLpImages() {
		return lpImages;
	}

	public void setLpImages(List<String> lpImages) {
		this.lpImages = lpImages;
	}

	public Integer getCcProductId() {
		return ccProductId;
	}

	public void setCcProductId(Integer ccProductId) {
		this.ccProductId = ccProductId;
	}

	public String getServiceNode() {
		return serviceNode;
	}

	public void setServiceNode(String serviceNode) {
		this.serviceNode = serviceNode;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getComvivaServiceId() {
		return comvivaServiceId;
	}

	public void setComvivaServiceId(String comvivaServiceId) {
		this.comvivaServiceId = comvivaServiceId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getSubKeyword() {
		return subKeyword;
	}

	public void setSubKeyword(String subKeyword) {
		this.subKeyword = subKeyword;
	}

	public String getUnsubKeyword() {
		return unsubKeyword;
	}

	public void setUnsubKeyword(String unsubKeyword) {
		this.unsubKeyword = unsubKeyword;
	}

	public String getPortalUrl() {
		return portalUrl;
	}

	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}

	public String getNotHeView() {
		return notHeView;
	}

	public void setNotHeView(String notHeView) {
		this.notHeView = notHeView;
	}

	public String getNotHeAutoWifiView() {
		return notHeAutoWifiView;
	}

	public void setNotHeAutoWifiView(String notHeAutoWifiView) {
		this.notHeAutoWifiView = notHeAutoWifiView;
	}

	public String getHeView() {
		return heView;
	}

	public void setHeView(String heView) {
		this.heView = heView;
	}

	public String getHeAutoView() {
		return heAutoView;
	}

	public void setHeAutoView(String heAutoView) {
		this.heAutoView = heAutoView;
	}
	
}
