package net.mycomp.mobimind;

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

@Service("jmsMobimindService")
public class JMSMobimindService {

	private static final Logger logger = Logger.getLogger(JMSMobimindService.class);
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	@Qualifier("mobimindNotificationJMSTemplate")
	private JmsTemplate mobimindNotificationJMSTemplate;

	
	
	public boolean saveMobimindNotification(final MobimindNotification mobimindNotification) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("saveMobimindNotification:: mobimindNotification:: "+mobimindNotification);
	        mobimindNotificationJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(mobimindNotification);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("saveMobimindNotification::::::::::::: ",ex);
				boolean save=daoService.saveObject(mobimindNotification);			
				return save;
			}
			 logger.debug("saveMobimindNotification:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
	
	
	
	
}
