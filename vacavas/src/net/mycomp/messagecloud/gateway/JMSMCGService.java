package net.mycomp.messagecloud.gateway;

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

@Service("jmsMCGService")
public class JMSMCGService {

	private static final Logger logger = Logger.getLogger(JMSMCGService.class);
	
	@Autowired
	private IDaoService daoService;
	
	@Autowired
	@Qualifier("mcgMOMessageJMSTemplate")
	private JmsTemplate mcgMOMessageJMSTemplate;
	
	@Autowired
	@Qualifier("mcgDeliveryReportJMSTemplate")
	private JmsTemplate mcgDeliveryReportJMSTemplate;
	
	@Autowired
	@Qualifier("mcgRenewalMessageJMSTemplate")
	private JmsTemplate mcgRenewalMessageJMSTemplate;
		
	public boolean savemoMessage(final MCGMoMessage mcgMoMessage) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("savemoMessage:: mcgMoMessage:: "+mcgMoMessage);
	        mcgMOMessageJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(mcgMoMessage);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("savemoMessage::::::::::::: ",ex);
				boolean save=daoService.saveObject(mcgMoMessage);			
				return save;
			}
			 logger.debug("savemoMessage:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
	
	
	
	public boolean saveDeliveryReport(final MCGDeliveryReport mcgDeliveryReport) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("saveDeliveryReport:: mcgMoMessage:: "+mcgDeliveryReport);
	        mcgDeliveryReportJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(mcgDeliveryReport);
					message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5*60*1000);
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("saveDeliveryReport::::::::::::: ",ex);
				boolean save=daoService.saveObject(mcgDeliveryReport);			
				return save;
			}
			 logger.debug("saveDeliveryReport:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
		
	public boolean sendRenewal(final SuscriberIdMsg suscriberIdMsg) {
		  long time=System.currentTimeMillis();
			try{
	        logger.debug("sendRenewal:: suscriberIdMsg:: "+suscriberIdMsg);
	        mcgRenewalMessageJMSTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message=session.createObjectMessage(suscriberIdMsg);
					
					return message;
				}
			});
	        
			}catch(Exception ex){
				logger.error("sendRenewal::::::::::::: ",ex);
				//boolean save=daoService.saveObject(mcgDeliveryReport);			
				return false;
			}
			 logger.debug("sendRenewal:: total time "+(System.currentTimeMillis()-time));
			return true;
		}
	
}
