package com.bingo.timer;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/*
 * document
 * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
*/
@Service
public class Timer {

	public static Logger logger = Logger.getLogger(Timer.class);
	
	// 从开服算，每隔五秒
	@Scheduled(cron = "0/5 * *  * * ? ")
	public void onTime5s() {
		//logger.info("5s timer");
	}

}
