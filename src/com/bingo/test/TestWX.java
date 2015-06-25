package com.bingo.test;

import java.util.HashMap;

import com.bingo.utils.HttpUtil;
import com.bingo.utils.JsonUtil;

public class TestWX {
	public static void main(String[] args) throws Exception {
		String APPID = "wxb5ff0d62858e3101";
		String APPSECRET = "36a4bbbf4cdb2a7a882102630dea1cca";
		String requesturl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ APPID + "&secret=" + APPSECRET;
		String accessToken = JsonUtil.readJson2Map(HttpUtil.sendRequestWithResult(requesturl,
				new HashMap<String, Object>()).toString()).get("access_token").toString();
		String openid = "o3rwgs6LQV185DmT0vqnsPy980Ik";
		requesturl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				+ accessToken + "&openid=" + openid + "&lang=zh_CN";
		String ret = HttpUtil.sendRequestWithResult(requesturl,
				new HashMap<String, Object>()).toString();
		System.out.println(ret);
	}
}
