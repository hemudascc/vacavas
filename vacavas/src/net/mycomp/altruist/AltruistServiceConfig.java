package net.mycomp.altruist;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_altruist_service_config")
public class AltruistServiceConfig {
	@Id
	@Column(name="id")
	private Integer id;
	@Column(name="service_id")
	private Integer serviceId;
	@Column(name="service_name")
	private String serviceName;
	@Column(name="mt_push_login")
	private String mtPushLogin;
	@Column(name="mt_push_password")
	private String mtPushPassword;
	@Column(name="package_id")
	private Integer packageId;
	@Column(name="package_name")
	private String packageName;
	@Column(name="price")
	private Double price;
	@Column(name="duration")
	private Integer duration;
	@Column(name="duration_description")
	private String durationDescription;
	@Column(name="unsub_key")
	private String unsubKey;
	@Column(name="short_code")
	private String shortCode;
	@Column(name="user_name")
	private String userName;
	@Column(name="password")
	private String password;
	@Column(name="portal_url")
	private String portalURL;
	@Column(name="welcome_message")
	private String welcomeMessage;
	@Column(name="unsubscribe_message")
	private String unsubscribeMessage;
	@Column(name="already_subscribe_message")
	private String alreadySubscribeMessage;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="status")
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
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getDurationDescription() {
		return durationDescription;
	}
	public void setDurationDescription(String durationDescription) {
		this.durationDescription = durationDescription;
	}
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	public String getUnsubscribeMessage() {
		return unsubscribeMessage;
	}
	public void setUnsubscribeMessage(String unsubscribeMessage) {
		this.unsubscribeMessage = unsubscribeMessage;
	}
	public String getAlreadySubscribeMessage() {
		return alreadySubscribeMessage;
	}
	public void setAlreadySubscribeMessage(String alreadySubscribeMessage) {
		this.alreadySubscribeMessage = alreadySubscribeMessage;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getUnsubKey() {
		return unsubKey;
	}
	public void setUnsubKey(String unsubKey) {
		this.unsubKey = unsubKey;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public String getPortalURL() {
		return portalURL;
	}
	public void setPortalURL(String portalURL) {
		this.portalURL = portalURL;
	}
	public String getMtPushLogin() {
		return mtPushLogin;
	}
	public void setMtPushLogin(String mtPushLogin) {
		this.mtPushLogin = mtPushLogin;
	}
	public String getMtPushPassword() {
		return mtPushPassword;
	}
	public void setMtPushPassword(String mtPushPassword) {
		this.mtPushPassword = mtPushPassword;
	}
	
	@Override
	public String toString() {
		return "AltruistServiceConfig [id=" + id + ", serviceId=" + serviceId + ", serviceName=" + serviceName
				+ ", mtPushLogin=" + mtPushLogin + ", mtPushPassword=" + mtPushPassword + ", packageId=" + packageId
				+ ", packageName=" + packageName + ", price=" + price + ", duration=" + duration
				+ ", durationDescription=" + durationDescription + ", unsubKey=" + unsubKey + ", shortCode=" + shortCode
				+ ", userName=" + userName + ", password=" + password + ", portalURL=" + portalURL + ", welcomeMessage="
				+ welcomeMessage + ", unsubscribeMessage=" + unsubscribeMessage + ", alreadySubscribeMessage="
				+ alreadySubscribeMessage + ", createTime=" + createTime + ", status=" + status + "]";
	}
}
