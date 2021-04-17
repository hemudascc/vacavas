package net.mycomp.macrokiosk.thailand;

import net.util.MConstants;

public enum ThialandOperatorTelcoidMap {

	//DTAC(MConstants.DTAC_OPERATOR_ID,1,"DTAC"),
	AIS(MConstants.MICROKIOSK_AIS_OPERATOR_ID,3,"AIS");
//	TRUEMOVE_HUTCHISON(MConstants.MICROKIOSK_TRUEMOVE_OPERATOR_ID,4,"TRUEMOVE_HUTCHISON");
	
	private int opId;
	private int telcoId;
	private String descp;
	
	ThialandOperatorTelcoidMap(int opId,int telcoId,String descp){
		this.opId=opId;
		this.telcoId=telcoId;
		this.descp=descp;
	}
	
	
	public static int getOperatorId(int telcoId){
		
		for(ThialandOperatorTelcoidMap thiaOperatorTelcoMap:values()){
			if(thiaOperatorTelcoMap.getTelcoId()==telcoId){
				return thiaOperatorTelcoMap.getOpId();
			}
		}
		return 0;		
	}
	
	public static int getTelcoId(int operatorId){
		for(ThialandOperatorTelcoidMap thiaOperatorTelcoMap:values()){
			if(thiaOperatorTelcoMap.getOpId()==operatorId){
				return thiaOperatorTelcoMap.getTelcoId();
			}
		}
		return 0;		
	}
	
	public int getOpId() {
		return opId;
	}
	public void setOpId(int opId) {
		this.opId = opId;
	}
	public int getTelcoId() {
		return telcoId;
	}
	public void setTelcoId(int telcoId) {
		this.telcoId = telcoId;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
}
