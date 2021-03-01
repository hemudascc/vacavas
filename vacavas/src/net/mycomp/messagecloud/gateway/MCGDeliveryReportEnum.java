package net.mycomp.messagecloud.gateway;

public enum MCGDeliveryReportEnum {

	DELIVERED("DELIVERED",false),
	NO_CREDIT("NO_CREDIT",true),
	FAILED("FAILED",true),
	VALIDITY_EXPIRED("VALIDITY_EXPIRED",true),
	REJECTED("REJECTED",false),
	INVALID_MSISDN("INVALID_MSISDN",false),
	ACKNOWLEDGED("ACKNOWLEDGED",false),
	UNKNOWN("UNKNOWN",true),
	OPERATOR_ERROR("OPERATOR_ERROR",true);
	public String report;
	
	//public boolean finalStatus;
	public boolean retry;
	
	 MCGDeliveryReportEnum(String report,boolean retry){
		this.report=report;
		this.retry=retry;
	}
	 
	 public static MCGDeliveryReportEnum getMCGDeliveryStatusEnum(String status){
		 for(MCGDeliveryReportEnum mcgDeliveryStatusEnum:values()){
			 if(mcgDeliveryStatusEnum.report.equalsIgnoreCase(status)){
				 return mcgDeliveryStatusEnum;
			 }
		 }
		 return UNKNOWN;
	 }
}
