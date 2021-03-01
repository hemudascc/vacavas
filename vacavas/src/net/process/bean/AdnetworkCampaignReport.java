package net.process.bean;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.persist.bean.LiveReport;

public class AdnetworkCampaignReport {

	private Timestamp date;	
	private Map<Integer,List<LiveReport>> mapReport;
	private String campaignName;
	private Integer totalActivationCount;
	private Integer totalParkingCount;
	private Double totalActivationAmount;
	
	
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
	
	public Integer getTotalActivationCount() {
		return totalActivationCount;
	}
	public void setTotalActivationCount(Integer totalActivationCount) {
		this.totalActivationCount = totalActivationCount;
	}
	public Integer getTotalParkingCount() {
		return totalParkingCount;
	}
	public void setTotalParkingCount(Integer totalParkingCount) {
		this.totalParkingCount = totalParkingCount;
	}
	
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public Map<Integer, List<LiveReport>> getMapReport() {
		return mapReport;
	}
	public void setMapReport(Map<Integer, List<LiveReport>> mapReport) {
		this.mapReport = mapReport;
	}
	public Double getTotalActivationAmount() {
		return totalActivationAmount;
	}
	public void setTotalActivationAmount(Double totalActivationAmount) {
		this.totalActivationAmount = totalActivationAmount;
	}
	
	
	
	
}
