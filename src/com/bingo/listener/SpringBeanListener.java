package com.bingo.listener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bingo.persistence.tools.dao.GMMapper;

// springbean初始化完毕回调事件
@Component
public class SpringBeanListener implements
		ApplicationListener<ContextRefreshedEvent> {
	
	public static Logger logger = Logger.getLogger(SpringBeanListener.class);

	@Autowired
	private GMMapper gmMapper;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		logger.info("Start Init DB");
		gmMapper.createTable();
	}
}
