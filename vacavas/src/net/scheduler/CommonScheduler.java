package net.scheduler;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.util.MConstants;

public class CommonScheduler {

	private static final Logger logger = Logger.getLogger(CommonScheduler.class);

	@Autowired
	private IDaoService daoService;	
	
	@Scheduled(cron="0 0 * ? * *")	
	public void resetDailyValues(){
		try{
		RedisCacheService.dateddMMYYYY=MConstants.sdfDDMMyyyy.format(
				new Timestamp(System.currentTimeMillis()).toLocalDateTime());
		RedisCacheService.dateddMMYYYYHH=MConstants.sdfDDMMyyyyHH.format(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
		}catch(Exception ex){
			logger.error("resetDailyValues ",ex);
		}
	}
}
