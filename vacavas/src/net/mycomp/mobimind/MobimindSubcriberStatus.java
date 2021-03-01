package net.mycomp.mobimind;

import net.util.MConstants;

public enum MobimindSubcriberStatus {

	ACT_SB("ACT-SB",MobimindConstant.RECYCLED_SUBCRIBER),
	BLD_SB("BLD-SB",MConstants.DCT),
	RCL_SB("RCL-SB",MobimindConstant.RECYCLED_SUBCRIBER),
	FSC_BL("FSC-BL",MConstants.ACT),
	FFL_BL("FFL-BL",""),
	RSC_BL("RSC-BL",MConstants.RENEW),
	RFL_BL("RFL-BL",""),
	DSC_BL("DSC-BL",MConstants.ACT),
	DFL_BL("DFL-BL","");
	
	MobimindSubcriberStatus( String status,String action){
		this.status=status;
		this.action=action;
	}
	 String status;
	 String action;
	 
	 public static String findAction(String status){
		 if(status==null){
			 return "";
		 }
		 for(MobimindSubcriberStatus mobimindSubcriberStatus:values()){
			 if(mobimindSubcriberStatus.status.equalsIgnoreCase(status)){
				 return mobimindSubcriberStatus.action;
			 }
		 }
		 return "";
	 }
	 
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
