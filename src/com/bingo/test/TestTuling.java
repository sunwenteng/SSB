package com.bingo.test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.bingo.utils.HttpUtil;
import com.bingo.utils.JsonUtil;

public class TestTuling {
	public static void main(String[] args) throws Exception {
		String APIKEY = "e5037622f2f7102636646bdd8c654bb4";
		String INFO = URLEncoder.encode("今天星期几", "utf-8");
		String requesturl = "http://www.tuling123.com/openapi/api?key="
				+ APIKEY + "&info=" + INFO;
		String str = HttpUtil.sendRequestWithResult(requesturl, new HashMap<String, Object>()).toString();
		
		System.out.println(str);
		Map<String, Object> map = JsonUtil.readJson2Map(str);
		System.out.println("");
	}
}
