package net.mycomp.altruist;

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

@Service("jmsAltruistService")
public class JMSAltruistService {
	
	private static final Logger logger = Logger.getLogger(JMSAltruistService.class);
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	@Qualifier("altruistCallbackJMSTemplate")
	private JmsTemplate altruistCallbackJMSTemplate;
	
	public boolean saveAltruistCallback(AltruistCallback altruistCallback) {
		long time=System.currentTimeMillis();
		logger.debug("saving:::::AltruistCallback:: "+altruistCallback);
		try {
			altruistCallbackJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = session.createObjectMessage(altruistCallback);
					return message;
				}
			});
		} catch (Exception e) {
			logger.error("error while saving AltruistCallback: "+altruistCallback, e);
			boolean save=daoService.saveObject(altruistCallback);
			return save;
		}
		logger.debug("saved:::::AltruistCallback:: "+altruistCallback+" total time "+(System.currentTimeMillis()-time));
		return true;
	}
	

}
