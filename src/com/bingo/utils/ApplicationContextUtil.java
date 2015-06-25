package com.bingo.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bingo.persistence.login.dao.PlayerInfoMapper;
import com.bingo.persistence.login.domain.PlayerInfo;

public class ApplicationContextUtil {

	private static final String CONFIG = "config";
	private static final String CONTEXT = "context";
	private static final ResourceBundle src = ResourceBundle.getBundle(CONFIG);

	public static ApplicationContext context;

	static {
		context = new FileSystemXmlApplicationContext(src.getString(CONTEXT)
				+ "applicationContext.xml");
	}

	public static void main(String[] args) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", "摘星者阿兰");
		paramMap.put("serverid", "2");
		List<PlayerInfo> resultList = ApplicationContextUtil.context.getBean(
				PlayerInfoMapper.class).getByNameAndServerId(paramMap);
		System.out.println(resultList.size());
	}
}
