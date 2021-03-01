package net.util;

public enum SubscriptionType {

	NULL(null), ZERO_PRICE_ACTIVATION("ZERO_PRICE_ACTIVATION")
	        , GREATOR_THAN_ZERO_PRICE_ACTIVATION(
			"GREATOR_THAN_ZERO_PRICE_ACTIVATION"), ZERO_PRICE_RENEWAL(
			"ZERO_PRICE_RENEWAL"), GREATOR_THAN_ZERO_PRICE_RENEWAL(
			"GREATOR_THAN_ZERO_PRICE"), DEACTIVATION("DEACTIVATION"),
			TEMPORARY_ACT("TEMPORARY_ACT");

	public final String description;

	private SubscriptionType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static String getSubscriptionType(Double amount, String action) {
		String subscriptionType=null;
		switch (action) {
		case MConstants.ACT: {
			if(amount<=0){
				subscriptionType=ZERO_PRICE_ACTIVATION.description;
			}else{
				subscriptionType=GREATOR_THAN_ZERO_PRICE_ACTIVATION.description;
			}	
		}
		
		break;
		case MConstants.RENEW: {
			if(amount<=0){
				subscriptionType=ZERO_PRICE_RENEWAL.description;
			}else{
				subscriptionType=GREATOR_THAN_ZERO_PRICE_RENEWAL.description;
			}	
		}
		break;
		case MConstants.DCT: {
			subscriptionType=DEACTIVATION.description;
		}
		case MConstants.TEMPORARY_ACT: {
			
				subscriptionType=TEMPORARY_ACT.description;
			
		}
		}
		return subscriptionType;
	}
}
