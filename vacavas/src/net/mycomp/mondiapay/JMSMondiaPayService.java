package net.mycomp.mondiapay;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import net.common.service.IDaoService;

@Service("jmsMondiaPayService")
public class JMSMondiaPayService {
	
	private static final Logger logger = Logger.getLogger(JMSMondiaPayService.class);
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	@Qualifier("mondiaPayNotificationJMSTemplate")
	private JmsTemplate mondiaPayNotificationJMSTemplate;
	
	public boolean saveMondiaPayNotification(final MondiaPayNotification mondiaPayNotification) {
		long time=System.currentTimeMillis();
		logger.debug("saving:::::MondiaPayNotification:: "+mondiaPayNotification);
		try {
			mondiaPayNotificationJMSTemplate.send(new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = session.createObjectMessage(mondiaPayNotification);
					message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 12*1000L);
					return message;
				}
			});
		} catch (Exception e) {
			logger.error("ERROR Saving MondiaPayNotification::::::::::::: ",e);
			boolean save=daoService.saveObject(mondiaPayNotification);
			return save;
		}
		logger.debug("saved:::::MondiaPayNotification:: "+mondiaPayNotification+" total time "+(System.currentTimeMillis()-time));
		return true;
	}

}
