package net.common.jms;

import java.io.Serializable;

import javax.jms.DeliveryMode;
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
import net.persist.bean.AdnetworkToken;



@Service("jmsService")
public class JMSService {

	@Autowired
	@Qualifier("adnetworkTokenJMSTemplate")
	private JmsTemplate adnetworkTokenJMSTemplate;
	
	@Autowired
	@Qualifier("objectSaveJMSTemplate")
	private JmsTemplate objectSaveJMSTemplate;
	
	@Autowired
	private IDaoService daoService;
	
	

	private static final Logger logger = Logger.getLogger(JMSService.class);

	public boolean saveAdnetworkToken(final AdnetworkToken adnetworkToken) {
	  long time=System.currentTimeMillis();
		try{
        logger.debug("saveAdnetworkToken:: adnetworkToken "+adnetworkToken);
        adnetworkTokenJMSTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message=session.createObjectMessage(adnetworkToken);
				//message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
				return message;
			}
		});
		}catch(Exception ex){
			logger.error("saveAdnetworkToken::::::::::::: "+ex);
			boolean save=daoService.saveObject(adnetworkToken);			
			return save;
		}
		 logger.debug("saveAdnetworkToken:: total time "+(System.currentTimeMillis()-time));
		return true;
	}
	

	
	public boolean saveObject(final Serializable object) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("saveObject:: adnetworkToken "+object);
	        objectSaveJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(object);
				}
			});
			}catch(Exception ex){
				logger.error("saveObject::::::::::::: "+ex);
				boolean save=daoService.saveObject(object);				
				return save;
			}
			 logger.debug("saveObject:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
	

}
