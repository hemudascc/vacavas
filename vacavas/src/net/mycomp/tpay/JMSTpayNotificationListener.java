package net.mycomp.tpay;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import net.persist.bean.LiveReport;
import net.process.bean.CGToken;

public class JMSTpayNotificationListener implements MessageListener{
	
	private static final Logger LOGGER = Logger.getLogger(JMSTpayNotificationListener.class);

	TpayNotification tpayNotification = null;
	LiveReport liveReport= null;
	long time = System.currentTimeMillis();
	CGToken cgToken = new CGToken("");
	
	@Override
	public void onMessage(Message message) {
		try {
			ObjectMessage objectMessage = (ObjectMessage)message;
			tpayNotification = (TpayNotification)objectMessage.getObject();
			
			
		} catch (Exception e) {
			LOGGER.error("error while processing tpay notification"+tpayNotification, e);
		}finally {
			
		}
	}
	
}