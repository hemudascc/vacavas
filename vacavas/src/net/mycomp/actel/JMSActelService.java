package net.mycomp.actel;

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

@Service("jmsActelService")
public class JMSActelService {

	private static final Logger logger = Logger.getLogger(JMSActelService.class);

	@Autowired
	private IDaoService daoService;


	@Autowired
	@Qualifier("actelDlrJMSTemplate")
	private JmsTemplate actelDlrJMSTemplate;

	public boolean saveActelDlr(final ActelDlr actelDlr) {
		long time=System.currentTimeMillis();
		try{
			logger.debug("saveDeliveryReceipt:: ActelDlr:: "+actelDlr);
			actelDlrJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(actelDlr);
					return message;
				}
			});
		}catch(Exception ex){
			logger.error("saveDeliveryReceipt::::::::::::: ",ex);
			boolean save=daoService.saveObject(actelDlr);			
			return save;
		}
		logger.debug("saveDeliveryReceipt:: total time "+(System.currentTimeMillis()-time));
		return true;
	}
}
