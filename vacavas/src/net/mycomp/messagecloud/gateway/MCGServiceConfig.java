package net.mycomp.messagecloud.gateway;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

import net.util.JpaConverterJson;

@Entity
@Table(name = "tb_mcg_service_config")
public class MCGServiceConfig {

	@Id
	private Integer id;
	@Column(name = "service_id")
	private Integer serviceId;
	@Column(name="config_service_name")
	private String configServiceName;
	@Column(name = "service_name")
	private String serviceName;
	
	@Column(name="operator_id")
	private Integer operatorId;
	
	@Column(name = "short_code")
	private String shortCode;
	private String keyword;
	@Column(name = "company_code")
	private String companyCode;
	private String ekey;
	@Column(name="network")
	private String network;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name = "opt_in_sms")
	private String optInSms;
	@Column(name = "billed_sms")
	private String billedSms;
	
	@Column(name = "welcome_sms")
	private String welcomeSms;
	
	@Column(name = "first_mt")
	private String firstMt;
	
	@Column(name = "second_mt")
	private String secondMt;
	@Column(name = "third_mt")
	private String thirdMt;
	
	@Column(name="already_subscribed_sms")
	private String alreadySubscribedSms;
	@Column(name="unsub_sms")
	private String unsubSms;
	
	@Column(name="invalid_message")
	private String invalidmessage;
	
	@Column(name = "lp_images")
	@Convert(converter=JpaConverterJson.class)
	private List<String> lpImages;	
	
	@Column(name="lp_pages")
	private String lpPages;
	
	@Column(name="lp_pages2")
	private String lpPages2;
	
	@Column(name="portal_url")
	private String portalurl;
	
	@Column(name="term_condition_page")
	private String termConditionPage;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumns({ 
		@JoinColumn(name = "mcg_service_config_id"), 
	})
	@OrderBy("sequence")
	private List<MCGFallbackPricePointConfig> listMCGFallbackPricePointConfig;
	
	
	private Boolean status;


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


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public Integer getOperatorId() {
		return operatorId;
	}


	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}


	public String getShortCode() {
		return shortCode;
	}


	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String getCompanyCode() {
		return companyCode;
	}


	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


	public String getEkey() {
		return ekey;
	}


	public void setEkey(String ekey) {
		this.ekey = ekey;
	}


	public String getNetwork() {
		return network;
	}


	public void setNetwork(String network) {
		this.network = network;
	}


	public String getConfigServiceName() {
		return configServiceName;
	}


	public void setConfigServiceName(String configServiceName) {
		this.configServiceName = configServiceName;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getOptInSms() {
		return optInSms;
	}


	public void setOptInSms(String optInSms) {
		this.optInSms = optInSms;
	}

	public String getFirstMt() {
		return firstMt;
	}


	public void setFirstMt(String firstMt) {
		this.firstMt = firstMt;
	}


	public String getSecondMt() {
		return secondMt;
	}


	public void setSecondMt(String secondMt) {
		this.secondMt = secondMt;
	}


	public String getThirdMt() {
		return thirdMt;
	}


	public void setThirdMt(String thirdMt) {
		this.thirdMt = thirdMt;
	}


	public String getAlreadySubscribedSms() {
		return alreadySubscribedSms;
	}


	public void setAlreadySubscribedSms(String alreadySubscribedSms) {
		this.alreadySubscribedSms = alreadySubscribedSms;
	}


	public String getUnsubSms() {
		return unsubSms;
	}


	public void setUnsubSms(String unsubSms) {
		this.unsubSms = unsubSms;
	}


	public String getInvalidmessage() {
		return invalidmessage;
	}


	public void setInvalidmessage(String invalidmessage) {
		this.invalidmessage = invalidmessage;
	}


	public List<String> getLpImages() {
		return lpImages;
	}


	public void setLpImages(List<String> lpImages) {
		this.lpImages = lpImages;
	}


	public String getLpPages() {
		return lpPages;
	}


	public void setLpPages(String lpPages) {
		this.lpPages = lpPages;
	}


	public String getLpPages2() {
		return lpPages2;
	}


	public void setLpPages2(String lpPages2) {
		this.lpPages2 = lpPages2;
	}


	public String getPortalurl() {
		return portalurl;
	}


	public void setPortalurl(String portalurl) {
		this.portalurl = portalurl;
	}


	public List<MCGFallbackPricePointConfig> getListMCGFallbackPricePointConfig() {
		return listMCGFallbackPricePointConfig;
	}


	public void setListMCGFallbackPricePointConfig(
			List<MCGFallbackPricePointConfig> listMCGFallbackPricePointConfig) {
		this.listMCGFallbackPricePointConfig = listMCGFallbackPricePointConfig;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public String getWelcomeSms() {
		return welcomeSms;
	}


	public void setWelcomeSms(String welcomeSms) {
		this.welcomeSms = welcomeSms;
	}


	public String getTermConditionPage() {
		return termConditionPage;
	}


	public void setTermConditionPage(String termConditionPage) {
		this.termConditionPage = termConditionPage;
	}


	public String getBilledSms() {
		return billedSms;
	}


	public void setBilledSms(String billedSms) {
		this.billedSms = billedSms;
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
