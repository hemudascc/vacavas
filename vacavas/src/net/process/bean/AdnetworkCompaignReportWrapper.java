package net.process.bean;

import java.io.Serializable;
import java.util.Map;

import net.persist.bean.LiveReport;

public class AdnetworkCompaignReportWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int[] reportHour;
	private int totalRenewalCount=0;
	private double totalRenewalAmount=0;
	private Map<Integer,LiveReport> mapRenewal;
	private Map<Integer,LiveReport> mapDeactivation;
	private int totalDeactivationCount=0;
	private Map<Integer,AdnetworkCampaignReport> mapAdnetworkCampaignReport;
	private int noOfPrevDay;
	
	public Map<Integer, LiveReport> getMapRenewal() {
		return mapRenewal;
	}
	public void setMapRenewal(Map<Integer, LiveReport> mapRenewal) {
		this.mapRenewal = mapRenewal;
	}
	public Map<Integer, LiveReport> getMapDeactivation() {
		return mapDeactivation;
	}
	public void setMapDeactivation(Map<Integer, LiveReport> mapDeactivation) {
		this.mapDeactivation = mapDeactivation;
	}
	public Map<Integer, AdnetworkCampaignReport> getMapAdnetworkCampaignReport() {
		return mapAdnetworkCampaignReport;
	}
	public void setMapAdnetworkCampaignReport(
			Map<Integer, AdnetworkCampaignReport> mapAdnetworkCampaignReport) {
		this.mapAdnetworkCampaignReport = mapAdnetworkCampaignReport;
	}
	public int[] getReportHour() {
		return reportHour;
	}
	public void setReportHour(int[] reportHour) {
		this.reportHour = reportHour;
	}
	public int getTotalRenewalCount() {
		return totalRenewalCount;
	}
	public void setTotalRenewalCount(int totalRenewalCount) {
		this.totalRenewalCount = totalRenewalCount;
	}
	public void addTotalRenewalCount(int totalRenewalCount) {
		this.totalRenewalCount =this.totalRenewalCount+ totalRenewalCount;
	}
	public double getTotalamount() {
		return this.totalRenewalAmount;
	}

	public void addTotalRenewalAmount(double totalRenewalAmount) {
		this.totalRenewalAmount =this.totalRenewalAmount+ totalRenewalAmount;
	}
	public double getTotalRenewalAmount() {
		return totalRenewalAmount;
	}
	public void setTotalRenewalAmount(double totalRenewalAmount) {
		this.totalRenewalAmount = totalRenewalAmount;
	}
	public int getTotalDeactivationCount() {
		return totalDeactivationCount;
	}
	public void setTotalDeactivationCount(int totalDeactivationCount) {
		this.totalDeactivationCount = totalDeactivationCount;
	}
	
	public void addTotalDeactivationCount(int  totalDeactivationCount) {
		this.totalDeactivationCount =this.totalDeactivationCount+ totalDeactivationCount;
	}
	public int getNoOfPrevDay() {
		return noOfPrevDay;
	}
	public void setNoOfPrevDay(int noOfPrevDay) {
		this.noOfPrevDay = noOfPrevDay;
	}
}
