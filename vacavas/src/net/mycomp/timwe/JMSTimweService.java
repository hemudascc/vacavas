package net.mycomp.timwe;

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

@Service("jmsTimweService")
public class JMSTimweService {

	private static final Logger logger = Logger.getLogger(JMSTimweService.class);
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	@Qualifier("timweNotificationJMSTemplate")
	private JmsTemplate timweNotificationJMSTemplate;
	
	public boolean saveTimweNotification(TimweNotification timweOptInNotification, String notificationType) {
		long time=System.currentTimeMillis();
		timweOptInNotification.setNotificationType(notificationType);
		logger.debug("saving:::::TimweNotification:: "+timweOptInNotification);
		try {
			timweNotificationJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = session.createObjectMessage(timweOptInNotification);
					return message;
				}
			});
		} catch (Exception e) {
			logger.error("ERROR Saving TimweNotification::::::::::::: ",e);
			boolean save=daoService.saveObject(timweOptInNotification);
			return save;
		}
		logger.debug("saved:::::TimweNotification:: "+timweOptInNotification+" total time "+(System.currentTimeMillis()-time));
		return true;
	}

}
