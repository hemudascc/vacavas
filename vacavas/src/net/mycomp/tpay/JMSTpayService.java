package net.mycomp.tpay;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import net.common.service.IDaoService;

@Service("jmsTpayService")
public class JMSTpayService {

	private static final Logger logger = Logger.getLogger(JMSTpayService.class);

	@Autowired
	private IDaoService daoService;

	@Autowired
	@Qualifier("tpayNotificationJMSTemplate")
	private JmsTemplate tpayNotificationJMSTemplate;

	public boolean saveTpayNotification(final TpayNotification tpayNotification) {
		long time=System.currentTimeMillis();
		logger.debug("saveTpayNotification:: TpayNotification:: "+tpayNotification);
		try {
			tpayNotificationJMSTemplate.send(new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = session.createObjectMessage(tpayNotification);
					return message;
				}
			});

		} catch (Exception e) {
			logger.error("saveTpayNotification::::::::::::: ",e);
			boolean save=daoService.saveObject(tpayNotification);
			return save;
		}
		logger.debug("saveTpayNotification:: total time "+(System.currentTimeMillis()-time));
		return true;
	}
 
}
