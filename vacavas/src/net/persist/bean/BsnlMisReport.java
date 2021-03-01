package net.persist.bean;

import java.util.HashMap;
import java.util.Map;

import net.util.MConstants;

public class BsnlMisReport {

	private String reportDate;
	private Map<String,Integer> mapAct;
	private int totalAct;
	private int totalActRevenue;	
	private Map<String,Integer> mapRen;
	private int totalRen;
	private int totalRenRevenue;	
	private int totalRevenue;
	
	public void addReportData(String action,int amount,int count){
		
		if(action.equalsIgnoreCase(MConstants.ACT)){
			mapAct.put(String.valueOf(amount), count);
			totalAct+=count;
			totalActRevenue+=amount*count;
			totalRevenue+=amount*count;
		}else if(action.equalsIgnoreCase(MConstants.RENEW)){
			mapRen.put(String.valueOf(amount), count);
			totalRen+=count;
			totalRenRevenue+=amount*count;
			totalRevenue+=amount*count;
		}
	}
	
	public BsnlMisReport(String reportDate){
		this.reportDate=reportDate;
		mapAct=new HashMap<String,Integer>();
		mapRen=new HashMap<String,Integer>();
	}
	
	
	public Map<String, Integer> getMapAct() {
		return mapAct;
	}
	public void setMapAct(Map<String, Integer> mapAct) {
		this.mapAct = mapAct;
	}
	public int getTotalAct() {
		return totalAct;
	}
	public void setTotalAct(int totalAct) {
		this.totalAct = totalAct;
	}
	public int getTotalActRevenue() {
		return totalActRevenue;
	}
	public void setTotalActRevenue(int totalActRevenue) {
		this.totalActRevenue = totalActRevenue;
	}
	public Map<String, Integer> getMapRen() {
		return mapRen;
	}
	public void setMapRen(Map<String, Integer> mapRen) {
		this.mapRen = mapRen;
	}
	public int getTotalRen() {
		return totalRen;
	}
	public void setTotalRen(int totalRen) {
		this.totalRen = totalRen;
	}
	public int getTotalRenRevenue() {
		return totalRenRevenue;
	}
	public void setTotalRenRevenue(int totalRenRevenue) {
		this.totalRenRevenue = totalRenRevenue;
	}
	public int getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(int totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	
}
