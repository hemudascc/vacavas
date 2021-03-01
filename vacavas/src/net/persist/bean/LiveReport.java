package net.persist.bean;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;

import net.util.MConstants;
import net.util.SubscriptionMode;

public class LiveReport {

	private int id;
	private Timestamp reportDate;
	private Timestamp lastActivationTime;
	private Timestamp lastClickTime;
	private int operatorId;
	private int adnetworkCampaignId;
	private String pubId = MConstants.DEFAULT_PUB_ID;
	private String type=MConstants.DEFAULT_CLICK_TYPE;
	private int clickCount;
	private int validClickCount;
	private int invalidClickCount;
	private int reverseClickCount;
	private int duplicateClickCount;
	private int blockClickCount;
	private int alreadySubscribedCount;
	private Double amount = 0d;
	private int conversionCount=0;
	private int duplicateConversionCount;
	private int sendConversionCount;
	private int sendManualConversionCount;
	private int sendAutoConversionCount;
	private int graceConversionCount;
	private int  subscriptionFailed;
	
	private int duplcateGraceConversionCount;
	private int graceSendConversionCount;
	private int mismatchOperatorConversionCount;
	private boolean status;
	private int dctCount;
	private int duplcateDctCount;
	private int dctSendCount;
	private int churnDctCount;
	private int renewalSentCount;
	private Double renewalAmount = 0d;
    private int renewalCount;
    private int circleId;
    private int serviceId;
    private int noOfDays=0;
    
    private int parkingCount;
    private int parkingToActivationCount;
    private  double parkToActivationAmount=0d;
    
//    private int renewalCountOfZeroPriceActivationAfter1Days=0;
//    private double renewalAmountOfZeroPriceActivationAfter1Days=0;
//    private int renewalCountOfZeroPriceActivationAfter2Days=0;
//    private double renewalAmountOfZeroPriceActivationAfter2Days=0; 
//    private int renewalCountOfZeroPriceActivationEqualOrMore3Days=0;
//    private double renewalAmountOfZeroPriceActivationEqualOrMore3Days=0;
    
	private int blockSeriesCount;
	private int actionHours;
	   // Transient
		private String action;
		private Integer tokenId;
		private String token;
		private String msisdn;
		
		private boolean duplicateRequest;
		private String operatorName;
		private String networkName;
		private Double spend = 0d;
		private String lcId;
		private String lastActivationColoumnColor="normal";
		private String lastClickColoumnColor="normal";
		private String  adnetworkCampaignName;
		private String circleName;
		private String reportDateStr;
		private String param1;
		private String param2;
		private String param3;
		
		private Integer productId;
		private Integer adnetworkId;
		private Integer aggregatorId;
		private int intAmount;
		private Map<Double,Integer> mapAmountCount;
		private int subId;
		private String mode=SubscriptionMode.WAP.getMode();
		private int pinSendCount;
		private int pinValidationCount;
		
		private int smsConversionCount;
		private double smsConversionAmount;
		private int smsRenwalCount;
		private double smsRenewalAmount;
		private int smsGraceCount;
		
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

public LiveReport(){		
	}

	public LiveReport(int operatorId, Timestamp timestamp,
			Integer adnetworkCampaignId,int serviceId,int productId) {

		this.operatorId = operatorId;
		this.reportDate = timestamp;
		
		if (adnetworkCampaignId == null) {
			adnetworkCampaignId = MConstants.DEFAULT_ADNETWORK_CAMPAIGN_ID;		
		}
		this.serviceId=serviceId;
		this.adnetworkCampaignId = adnetworkCampaignId;
		this.productId=productId;
	}

	
	public int getRenewallSentCount() {
		return renewalSentCount;
	}

	public void setRenewalSentCount(int renewalSentCount) {
		this.renewalSentCount = renewalSentCount;
	}

	public Double getRenewalAmount() {
		return renewalAmount;
	}

	public void setRenewalAmount(Double renewalAmount) {
		this.renewalAmount = renewalAmount;
	}

	public int getRenewalCount() {
		return renewalCount;
	}
	
	public void addRenewalCount(int renewalCount){
		this.renewalCount=this.renewalCount+renewalCount;
	}
	
	public void addDeactivation(int dctCount){
		this.dctCount=this.dctCount+dctCount;
	}
	
	public void addRenewalAmount(double renewalAmount){
		this.renewalAmount=this.renewalAmount+renewalAmount;
	}

	public void setRenewalCount(int renewalCount) {
		this.renewalCount = renewalCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getReportDate() {
		return reportDate;
	}

	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public int getAdnetworkCampaignId() {
		return adnetworkCampaignId;
	}

	public void setAdnetworkCampaignId(int adnetworkCampaignId) {
		this.adnetworkCampaignId = adnetworkCampaignId;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public int getValidClickCount() {
		return validClickCount;
	}

	public void setValidClickCount(int validClickCount) {
		this.validClickCount = validClickCount;
	}

	public int getInvalidClickCount() {
		return invalidClickCount;
	}

	public void setInvalidClickCount(int invalidClickCount) {
		this.invalidClickCount = invalidClickCount;
	}

	public int getReverseClickCount() {
		return reverseClickCount;
	}

	public void setReverseClickCount(int reverseClickCount) {
		this.reverseClickCount = reverseClickCount;
	}

	public int getBlockClickCount() {
		return blockClickCount;
	}

	public void setBlockClickCount(int blockClickCount) {
		this.blockClickCount = blockClickCount;
	}

	public int getAlreadySubscribedCount() {
		return alreadySubscribedCount;
	}

	public void setAlreadySubscribedCount(int alreadySubscribedCount) {
		this.alreadySubscribedCount = alreadySubscribedCount;
	}

	public int getConversionCount() {
		return conversionCount;
	}

	public void setConversionCount(int conversionCount) {
		if (duplicateRequest) {
			this.duplicateConversionCount = conversionCount;
		} else {
			this.conversionCount = conversionCount;
		}
	}

	public int getSendConversionCount() {
		return sendConversionCount;
	}

	public void setSendConversionCount(int sendConversionCount) {
		this.sendConversionCount = sendConversionCount;
	}

	public int getGraceConversionCount() {
		return graceConversionCount;
	}

	public void setGraceConversionCount(int graceConversionCount) {
		if (duplicateRequest) {
			this.duplcateGraceConversionCount=graceConversionCount;
		} else {
			this.graceConversionCount = graceConversionCount;
		}
	}

	public int getGraceSendConversionCount() {
		return graceSendConversionCount;
	}

	public void setGraceSendConversionCount(int graceSendConversionCount) {
		this.graceSendConversionCount = graceSendConversionCount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		if(amount!=null){
		 this.amount = amount;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		
		if(type!=null){
		this.type = type;
		}
	}

	public int getDuplicateConversionCount() {
		return duplicateConversionCount;
	}

	public int getDuplicateClickCount() {
		return duplicateClickCount;
	}

	public void setDuplicateClickCount(int duplicateClickCount) {
		this.duplicateClickCount = duplicateClickCount;
	}

	public int getMismatchOperatorConversionCount() {
		return mismatchOperatorConversionCount;
	}

	public void setMismatchOperatorConversionCount(int mismatchOperatorConversionCount) {
		this.mismatchOperatorConversionCount = mismatchOperatorConversionCount;
	}

	public int getDctCount() {
		return dctCount;
	}

	public void setDctCount(int dctCount) {
		
		if (duplicateRequest) {
			this.duplcateDctCount = dctCount;
		} else {
			this.dctCount = dctCount;
		}
	}

	public int getChurnDctCount() {
		return churnDctCount;
	}

	public void setChurnDctCount(int churnDctCount) {
		this.churnDctCount = churnDctCount;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public int getBlockSeriesCount() {
		return blockSeriesCount;
	}

	public void setBlockSeriesCount(int blockSeriesCount) {
		this.blockSeriesCount = blockSeriesCount;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public String getPubId() {
		return pubId;
	}


	public boolean isDuplicateRequest() {
		return duplicateRequest;
	}

	public void setDuplicateRequest(boolean duplicateRequest) {
		this.duplicateRequest = duplicateRequest;
	}
	
	public void setDuplicateValues(){
		if (duplicateRequest) {
			amount=0d;
			conversionCount=0;
			sendConversionCount=0;
			dctCount=0;
			churnDctCount=0;
			graceConversionCount=0;
			graceSendConversionCount=0;		
			renewalCount=0;
			renewalAmount=0d;
			parkingToActivationCount=0;
			parkToActivationAmount=0d;			
			
		}
	}

	public int getDuplcateDctCount() {
		return duplcateDctCount;
	}

	public void setDuplcateDctCount(int duplcateDctCount) {
		this.duplcateDctCount = duplcateDctCount;
	}

	public int getDuplcateGraceConversionCount() {
		return duplcateGraceConversionCount;
	}

	public int getDctSendCount() {
		return dctSendCount;
	}

	public void setDctSendCount(int dctSendCount) {
		this.dctSendCount = dctSendCount;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public Double getSpend() {
		return spend;
	}

	public void setSpend(Double spend) {
		this.spend = spend;
	}

	

	public int getRenewalSentCount() {
		return renewalSentCount;
	}

	public void setDuplicateConversionCount(int duplicateConversionCount) {
		this.duplicateConversionCount = duplicateConversionCount;
	}

	public void setDuplcateGraceConversionCount(int duplcateGraceConversionCount) {
		this.duplcateGraceConversionCount = duplcateGraceConversionCount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLcId() {
		return lcId;
	}

	public void setLcId(String lcId) {
		this.lcId = lcId;
	}

	public Timestamp getLastActivationTime() {
		return lastActivationTime;
	}

	public void setLastActivationTime(Timestamp lastActivationTime) {
		this.lastActivationTime = lastActivationTime;
	}

	public Timestamp getLastClickTime() {
		return lastClickTime;
	}

	public void setLastClickTime(Timestamp lastClickTime) {
		this.lastClickTime = lastClickTime;
	}

	public String getLastActivationColoumnColor() {
		return lastActivationColoumnColor;
	}

	public void setLastActivationColoumnColor(String lastActivationColoumnColor) {
		this.lastActivationColoumnColor = lastActivationColoumnColor;
	}

	public String getLastClickColoumnColor() {
		return lastClickColoumnColor;
	}

	public void setLastClickColoumnColor(String lastClickColoumnColor) {
		this.lastClickColoumnColor = lastClickColoumnColor;
	}

	public int getSendManualConversionCount() {
		return sendManualConversionCount;
	}

	public void setSendManualConversionCount(int sendManualConversionCount) {
		this.sendManualConversionCount = sendManualConversionCount;
	}

	public int getSendAutoConversionCount() {
		return sendAutoConversionCount;
	}

	public void setSendAutoConversionCount(int sendAutoConversionCount) {
		this.sendAutoConversionCount = sendAutoConversionCount;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	

	public int getActionHours() {
		return actionHours;
	}

	public void setActionHours(int actionHours) {
		this.actionHours = actionHours;
	}

	public String getAdnetworkCampaignName() {
		return adnetworkCampaignName;
	}

	public void setAdnetworkCampaignName(String adnetworkCampaignName) {
		this.adnetworkCampaignName = adnetworkCampaignName;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getReportDateStr() {
		return reportDateStr;
	}

	public void setReportDateStr(String reportDateStr) {
		this.reportDateStr = reportDateStr;
	}

	public int getIntAmount() {
		return intAmount;
	}

	public void setIntAmount(int intAmount) {
		this.intAmount = intAmount;
	}

	

	public double getParkToActivationAmount() {
		return parkToActivationAmount;
	}

	public void setParkToActivationAmount(double parkToActivationAmount) {
		this.parkToActivationAmount = parkToActivationAmount;
	}

	public int getParkingCount() {
		return parkingCount;
	}

	public void setParkingCount(int parkingCount) {
		this.parkingCount = parkingCount;
	}

	public int getParkingToActivationCount() {
		return parkingToActivationCount;
	}

	public void setParkingToActivationCount(int parkingToActivationCount) {
		this.parkingToActivationCount = parkingToActivationCount;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public Map<Double, Integer> getMapAmountCount() {
		return mapAmountCount;
	}

	public void setMapAmountCount(Map<Double, Integer> mapAmountCount) {
		this.mapAmountCount = mapAmountCount;
	}

	public int getSubscriptionFailed() {
		return subscriptionFailed;
	}

	public void setSubscriptionFailed(int subscriptionFailed) {
		this.subscriptionFailed = subscriptionFailed;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getAdnetworkId() {
		return adnetworkId;
	}

	public void setAdnetworkId(Integer adnetworkId) {
		this.adnetworkId = adnetworkId;
	}

	public Integer getAggregatorId() {
		return aggregatorId;
	}

	public void setAggregatorId(Integer aggregatorId) {
		this.aggregatorId = aggregatorId;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		if(mode!=null){
		this.mode = mode;
		}
	}

	public int getPinSendCount() {
		return pinSendCount;
	}

	public void setPinSendCount(int pinSendCount) {
		this.pinSendCount = pinSendCount;
	}

	public int getPinValidationCount() {
		return pinValidationCount;
	}

	public void setPinValidationCount(int pinValidationCount) {
		this.pinValidationCount = pinValidationCount;
	}

	public int getSmsConversionCount() {
		return smsConversionCount;
	}

	public void setSmsConversionCount(int smsConversionCount) {
		this.smsConversionCount = smsConversionCount;
	}

	public double getSmsConversionAmount() {
		return smsConversionAmount;
	}

	public void setSmsConversionAmount(double smsConversionAmount) {
		this.smsConversionAmount = smsConversionAmount;
	}

	public int getSmsRenwalCount() {
		return smsRenwalCount;
	}

	public void setSmsRenwalCount(int smsRenwalCount) {
		this.smsRenwalCount = smsRenwalCount;
	}

	public double getSmsRenewalAmount() {
		return smsRenewalAmount;
	}

	public void setSmsRenewalAmount(double smsRenewalAmount) {
		this.smsRenewalAmount = smsRenewalAmount;
	}

	public int getSmsGraceCount() {
		return smsGraceCount;
	}

	public void setSmsGraceCount(int smsGraceCount) {
		this.smsGraceCount = smsGraceCount;
	}

}
	
	


