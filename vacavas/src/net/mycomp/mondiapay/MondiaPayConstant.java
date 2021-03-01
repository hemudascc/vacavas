package net.mycomp.mondiapay;

import net.util.MConstants;

public interface MondiaPayConstant {
	
	String CLIENT_ID = "0d663227-e258-417f-8510-b9c423996826";
	String CLIENT_SECRET = "b2a8dd2b-98c0-489b-87d4-6cc2056de069";
	String SUBSCRIPTION_TYPE_ID = "66730001";
	Integer MONDIA_PAY_OPERATOR_ID=12;
	Integer MONDIA_PAY_SERVICE_ID=12;
	Integer MONDIA_PAY_PRODUCT_ID=1;
	String MONDIA_PAY_TOKEN_SUBSCRIPTION_ID_CACHE_PREFIX="MONDIA_PAY_TOKEN_SUBSCRIPTION_ID_CACHE_PREFIX";
	/* REQUEST TYPES */
	String CLIENT_TOKEN="CLIENT_TOKEN";
	String PURCHASE_SUBSCRIPTION="PURCHASE_SUBSCRIPTION";
	String ACCESS_TOKEN="ACCESS_TOKEN";
	String SUBSCRIPTION_DETAIL="SUBSCRIPTION_DETAIL";
	String CANCEL_SUBSCRIPTION="CANCEL_SUBSCRIPTION";
	static String findAction(MondiaPayNotification mondiaPayNotification) {
		if(mondiaPayNotification.getEvent().equalsIgnoreCase("CREATE") && mondiaPayNotification.getAmount()>0) {
			return MConstants.ACT;
		}else if(mondiaPayNotification.getEvent().equalsIgnoreCase("CREATE") && mondiaPayNotification.getAmount()<=0){
			return MConstants.GRACE;
		}else if(mondiaPayNotification.getEvent().equalsIgnoreCase("RENEWAL")) {
			return MConstants.RENEW;
		}else if(mondiaPayNotification.getEvent().equalsIgnoreCase("CANCEL")) {
			return MConstants.DCT;
		}
		return null;
	}

}
