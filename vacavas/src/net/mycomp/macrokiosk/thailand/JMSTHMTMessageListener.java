package net.mycomp.macrokiosk.thailand;

import javax.jms.Message;
import javax.jms.MessageListener;
import net.common.service.IDaoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class JMSTHMTMessageListener  implements MessageListener {
	private static final Logger logger = Logger.getLogger(JMSTHMTMessageListener.class);

	@Autowired
	private MacroKioskFactoryService macroKioskFactoryService;
	
	@Autowired
	private IDaoService daoService;
	
	@Override
	public void onMessage(Message m) {
		
		long time = System.currentTimeMillis();
		logger.info("onMessage::::::::::::::::: " + m);
//		ObjectMessage objectMessage = (ObjectMessage) m;
//		boolean update = false;		
//		MTMessage mtMessage=null;
//		try {			
//			 mtMessage=(MTMessage)objectMessage;
//			 logger.info("onMessage:::::::: "+mtMessage);
//			macroKioskFactoryService.handleSubscriptionMTMessage(mtMessage);
//			logger.info("update:: "+objectMessage+", update:: "+update);
//		} catch (Exception e) {
//			logger.error("onMessage:::::::::::::::::"  + " , Exception  " , e);
//		}finally{
//			daoService.saveObject(mtMessage);
//		}	
		
		logger.info("onMessage::::::::::::::::: :: update:: " + ", total time:: " + (System.currentTimeMillis() - time));
	}	
}


