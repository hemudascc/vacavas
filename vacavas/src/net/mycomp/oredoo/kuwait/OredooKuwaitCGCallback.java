package net.mycomp.oredoo.kuwait;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "tb_oredoo_kuwait_cg_callback")
public class OredooKuwaitCGCallback implements Serializable{

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	private String msisdn;
	private String result;
	private String reason;
	@Column(name = "product_id")
	private String productId;
	@Column(name = "trans_id")
	private String transId;
	@Column(name = "tpcg_id")
	private String tpcgId;
	@Column(name = "song_name")
	private String songName;
	@Column(name = "query_str")
	private String queryStr;
	
	@Column(name = "subcription_api_url")
	private String subscriptionApiUrl;
	
	@Column(name = "subscripton_api_response")
	private String subscriptonApiResponse;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name = "used")
	private boolean used;
	@Column(name = "redirect_to")
	private String redirectTo;	
	@Column(name = "duplicate")
	private boolean duplicate;
	private boolean status;
	
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


	
	public OredooKuwaitCGCallback(){
		createTime=new Timestamp(System.currentTimeMillis());
		status=true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTpcgId() {
		return tpcgId;
	}
	public void setTpcgId(String tpcgId) {
		this.tpcgId = tpcgId;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	public String getSubscriptionApiUrl() {
		return subscriptionApiUrl;
	}
	public void setSubscriptionApiUrl(String subscriptionApiUrl) {
		this.subscriptionApiUrl = subscriptionApiUrl;
	}
	public String getSubscriptonApiResponse() {
		return subscriptonApiResponse;
	}
	public void setSubscriptonApiResponse(String subscriptonApiResponse) {
		this.subscriptonApiResponse = subscriptonApiResponse;
	}
	public boolean isDuplicate() {
		return duplicate;
	}
	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}



	public boolean isUsed() {
		return used;
	}



	public void setUsed(boolean used) {
		this.used = used;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}



	public String getRedirectTo() {
		return redirectTo;
	}



	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}
	
	
}
