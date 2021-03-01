package net.util;

import net.persist.bean.LiveReport;

public class ReportGenerateException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LiveReport liveReport;
	private String info;
	
	public ReportGenerateException(LiveReport liveReport,String info){
		this.liveReport=liveReport;
		this.info=info;
	}
	public LiveReport getLiveReport() {
		return liveReport;
	}
	public void setLiveReport(LiveReport liveReport) {
		this.liveReport = liveReport;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
