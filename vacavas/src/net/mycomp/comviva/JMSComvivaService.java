package net.mycomp.comviva;

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

@Service
public class JMSComvivaService {
	
	private static final Logger logger = Logger.getLogger(JMSComvivaService.class);
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	@Qualifier("comvivaCGCallbackJMSTemplate")
	private JmsTemplate comvivaCGCallbackJMSTemplate;
		
	public boolean saveComvivaCallback(final ComvivaCGCallback comvivaCGCallback){
		long time=System.currentTimeMillis();
		logger.debug("saving::::: comvivaCGCallback::: "+comvivaCGCallback);
		try {
		comvivaCGCallbackJMSTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createObjectMessage(comvivaCGCallback);
				return message;
			}
		});
		}catch (Exception e) {
			logger.error("error while saving comvivaCGCallback:::"+comvivaCGCallback);
			boolean save=daoService.saveObject(comvivaCGCallback);
			return save;
		}
		logger.debug("saved::::: comvivaCGCallback:::::"+comvivaCGCallback+" total time "+(System.currentTimeMillis()-time));
		return true;
	}
	
}
