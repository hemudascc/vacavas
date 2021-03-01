package net.common.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.common.service.IDaoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class JMSObjectSaveListener implements MessageListener{

	private static final Logger logger = Logger.getLogger(JMSObjectSaveListener.class);

	
	@Autowired
	private IDaoService daoService;
	
	@Override
	public void onMessage(Message m) {
		try{
		ObjectMessage objectMessage = (ObjectMessage) m;
		daoService.saveObject(objectMessage.getObject());
		// TODO Auto-generated method stub
		}catch(Exception ex){
			logger.error("onMessage::::: ",ex);
		}
	}

}
