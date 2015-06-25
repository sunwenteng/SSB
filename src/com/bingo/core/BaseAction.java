package com.bingo.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8403487630168628695L;
	
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected Map<String, Object> session = new HashMap<String, Object>();
	protected String serverip;
	protected String rawserverip;
	protected String serverid;
	protected Map<String, String[]> paramMap;

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setSession(Map arg0) {
		this.session = arg0;
		this.paramMap = this.request.getParameterMap();
	}
	
	/**
	 * 发送请求到指定URL，POST方式
	 * 
	 * @param requestURL
	 * @param paramMap
	 * @return 2010-6-11 下午05:36:54
	 */
	public String sendRequestWithResult(String requestURL, Map<String, Object> paramMap) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(requestURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(120000);
			connection.setReadTimeout(120000);
			connection.setDoOutput(true);

			// 发送POST数据
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF8");
			String param = getParam(paramMap);
			out.write(param);
			out.flush();
			out.close();

			int code = connection.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
				int length = -1;
				byte[] buff = new byte[1024];
				StringBuilder builder =	 new StringBuilder("");
				while ((length = bis.read(buff)) != -1) {
					builder.append(new String(buff, 0, length, "UTF8"));
				}
				return builder.toString();
			} else {
				return "";
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private String getParam(Map<String, Object> paramMap) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		Set<Entry<String, Object>> entrySet = paramMap.entrySet();
		int index = 0;
		for (Entry<String, Object> entry : entrySet) {
			if (index != 0) {
				builder.append("&");
			}
			builder.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(((String [])entry.getValue())[0]), "UTF8"));
			index++;
		}
		return builder.toString();
	}

	public String getServerip() {
		return serverip;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	public String getRawserverip() {
		return rawserverip;
	}

	public void setRawserverip(String rawserverip) {
		this.rawserverip = rawserverip;
	}

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}


}
