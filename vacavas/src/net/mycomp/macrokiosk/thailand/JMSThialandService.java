package net.mycomp.macrokiosk.thailand;

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
import net.process.bean.SuscriberIdMsg;

@Service("jmsThialandService")
public class JMSThialandService {

	private static final Logger logger = Logger.getLogger(JMSThialandService.class);


	@Autowired
	private IDaoService daoService;
	
	@Autowired
	@Qualifier(value="thMoMessageJMSTemplate")
	private JmsTemplate thMoMessageJMSTemplate;

	@Autowired
	@Qualifier(value="thMtMessageJMSTemplate")
	private JmsTemplate thMtMessageJMSTemplate;
	
	@Autowired
	@Qualifier(value="thDlnMessageJMSTemplate")
	private JmsTemplate thDlnMessageJMSTemplate;
	
	@Autowired
	@Qualifier(value="thialandRenewalMessageJMSTemplate")
	private JmsTemplate thialandRenewalMessageJMSTemplate;
	
	
	
	public boolean saveMOMessage(final THMOMessage moMessage) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("saveMOMessage:: moMessage:: "+moMessage);
	        thMoMessageJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(moMessage);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("saveMOMessage::::::::::::: ",ex);
				boolean save=daoService.saveObject(moMessage);			
				return save;
			}
			 logger.debug("saveMOMessage:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
	
	
	
	public boolean saveMTMessage(final THMTMessage mtMessage) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("saveMTMessage:: mtMessage:: "+mtMessage);
	        thMtMessageJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(mtMessage);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("saveMTMessage::::::::::::: ",ex);
				boolean save=daoService.saveObject(mtMessage);			
				return save;
			}
			 logger.debug("saveMTMessage:: total time "+(System.currentTimeMillis()-time));
			return true;
		}

	
	public boolean saveDeliveryNotification(final DeliveryNotification deliveryNotification) {
		
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("saveDeliveryNotification:: deliveryNotification:: "+deliveryNotification);
	        thDlnMessageJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(deliveryNotification);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("saveDeliveryNotification::::::::::::: ",ex);
				boolean save=daoService.saveObject(deliveryNotification);			
				return save;
			}
			 logger.debug("saveDeliveryNotification:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
		
	public boolean sendRenewal(final SuscriberIdMsg suscriberIdMsg) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("sendRenewal:: suscriberIdMsg:: "+suscriberIdMsg);
	        thialandRenewalMessageJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(suscriberIdMsg);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("sendRenewal::::::::::::: ",ex);
				
			}
			 logger.debug("sendRenewal:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
	
	
	public boolean sendRenewalRetry(final SuscriberIdMsg suscriberIdMsg) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("sendRenewal:: suscriberIdMsg:: "+suscriberIdMsg);
	        thialandRenewalMessageJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(suscriberIdMsg);
					message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 2*60*60*1000);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("sendRenewal::::::::::::: ",ex);
				
			}
			 logger.debug("sendRenewal:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
	
}
