package com.bingo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.bingo.core.Constants;
import com.bingo.ssh.SSHTool;

// 监听web容器生命周期
public class ApplicationContextListener implements ServletContextListener {

	public static Logger logger = Logger.getLogger(ApplicationContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("ApplicationContextListener destroyed...");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("ApplicationContextListener start...");

		if( Constants.SSHENABLE.equals("true") )
		{
			try {
				SSHTool.getInstance().connect(Constants.WORKSPACE_IP,
						Constants.WORKSPACE_USER, Constants.WORKSPACE_PWD);
				logger.info("SSH connect sucess");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}