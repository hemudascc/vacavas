package net.persist.bean;

import java.sql.Timestamp;

public interface IOtp {
	public Integer getId();
	public void setId(Integer id);
	public String getMsisdn();
	public void setMsisdn(String msisdn);
	public String getOtp();
	public void setOtp(String otp);
	
	public Boolean getStatus();
	public void setStatus(Boolean status);
	public Timestamp getCreateTime();
	public void setCreateTime(Timestamp createTime);
	public Boolean getSend();
	public void setSend(Boolean send);
	public Boolean getUsed() ;
	public void setUsed(Boolean used);
	public String getMsg();
	public void setMsg(String msg);

}
