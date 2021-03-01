package net.mycomp.intarget;

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

@Service("jmsIntargetService")
public class JMSIntargetService {

	private static final Logger logger = Logger.getLogger(JMSIntargetService.class);
	
	
	@Autowired
    @Qualifier("intargetOnReceiveSMSJMSTemplate")
	private JmsTemplate intargetOnReceiveSMSJMSTemplate;
	
	@Autowired
    @Qualifier("intargetOnResultJMSTemplate")
	private JmsTemplate intargetOnResultJMSTemplate;
	
	
	@Autowired
	private IDaoService daoService;
	
	

	
	public boolean saveIntargetOnResultJMSTemplate(final IntargetOnResultNotification intargetOnResultNotification) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("saveIntargetOnResultJMSTemplate:: intargetOnResultNotification:: "+intargetOnResultNotification);
	        intargetOnResultJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(intargetOnResultNotification);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("saveIntargetOnResultJMSTemplate::::::::::::: ",ex);
				boolean save=daoService.saveObject(intargetOnResultNotification);			
				return save;
			}
			 logger.debug("saveIntargetOnResultJMSTemplate:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
	
	
	public boolean saveIntargetOnReceiveSMSJMSTemplate(final IntargetOnReceiveSMSNotification intargetOnReceiveSMSNotification) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("saveIntargetOnReceiveSMSJMSTemplate:: intargetOnResultNotification:: "+intargetOnReceiveSMSNotification);
	        intargetOnReceiveSMSJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(intargetOnReceiveSMSNotification);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("saveIntargetOnReceiveSMSJMSTemplate::::::::::::: ",ex);
				boolean save=daoService.saveObject(intargetOnReceiveSMSNotification);			
				return save;
			}
			 logger.debug("saveIntargetOnReceiveSMSJMSTemplate:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
	

	
}
