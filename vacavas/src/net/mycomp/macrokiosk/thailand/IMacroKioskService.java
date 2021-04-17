package net.mycomp.macrokiosk.thailand;

import net.persist.bean.SubscriberReg;

public interface IMacroKioskService {

	public boolean handleSubscriptionMOMessage(THMOMessage moMessage);
	//public boolean handleWelcomeSubscriptionMTMessage(DeliveryNotification deliveryNotification);
	//public boolean handleSubscriptionMTMessage(MTMessage MTMessage);	
	public boolean sendSubscriptionRenewalRequest(SubscriberReg subscriberReg);
	public void processDeliveryNotification(DeliveryNotification deliveryNotification);
}
