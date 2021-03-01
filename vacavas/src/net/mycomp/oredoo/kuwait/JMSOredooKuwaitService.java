package net.mycomp.oredoo.kuwait;

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

@Service("jmsOredooKuwaitService")
public class JMSOredooKuwaitService {


	private static final Logger logger = Logger.getLogger(JMSOredooKuwaitService.class);

	
	@Autowired
	@Qualifier("oredooKuwaitCGCallbackJMSTemplate")
	private JmsTemplate oredooKuwaitCGCallbackJMSTemplate;
	
	@Autowired
	@Qualifier("oredooKuwaitCGNotificationJMSTemplate")
	private JmsTemplate oredooKuwaitCGNotificationJMSTemplate;
	
	@Autowired
	private IDaoService daoService;
	
	public boolean saveOredooKuwaitCGCallback(final OredooKuwaitCGCallback oredooKuwaitCGCallback) {
	
		try{
			oredooKuwaitCGCallbackJMSTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message=session.createObjectMessage(oredooKuwaitCGCallback);
				//message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 1*60*1000);
				return message;
			}
		});
		}catch(Exception ex){
			logger.error("saveOredooKuwaitCGCallback::::::::::::: "+ex);
			boolean save=daoService.saveObject(oredooKuwaitCGCallback);			
			return save;
		}
		return true;
	}
	
	public boolean saveOredooKuwaitCGNotification(final OredooKuwaitCGNotification oredooKuwaitCGNotification) {
		
		try{
			oredooKuwaitCGNotificationJMSTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message=session.createObjectMessage(oredooKuwaitCGNotification);
				message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5*60*1000);
				return message;
			}
		});
		}catch(Exception ex){
			logger.error("saveOredooKuwaitCGNotification::::::::::::: "+ex);
			boolean save=daoService.saveObject(oredooKuwaitCGNotification);			
			return save;
		}
		return true;
	}
}
