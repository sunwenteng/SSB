package com.bingo.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class HttpUtil {

	private static String getParam(Map<String, Object> paramMap)
			throws UnsupportedEncodingException {

		StringBuilder builder = new StringBuilder();
		Set<Entry<String, Object>> entrySet = paramMap.entrySet();
		int index = 0;
		for (Entry<String, Object> entry : entrySet) {
			if (index != 0) {
				builder.append("&");
			}
			// String s = String.valueOf(((String[]) entry.getValue())[0]);
			builder.append(entry.getKey())
					.append("=")
					.append(URLEncoder.encode(entry.getValue().toString(),
							"UTF8"));
			index++;
		}
		return builder.toString();
	}

	static public void DecodeWithGZip(String src) throws Exception {
		ByteArrayInputStream stringInputStream = new ByteArrayInputStream(
				src.getBytes());
		GZIPInputStream gzi = new GZIPInputStream(stringInputStream);
		ObjectInputStream ois = new ObjectInputStream(gzi);
		ois.readObject();
		ois.close();
		gzi.close();
	}

	public static String sendRequestWithResult(String requestURL,
			Map<String, Object> paramMap) throws ClassNotFoundException {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(requestURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(120000);
			connection.setReadTimeout(120000);
			connection.setDoOutput(true);

			// 发送POST数据
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF8");
			String param = getParam(paramMap);
			out.write(param);
			out.flush();
			out.close();

			int code = connection.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedInputStream bis = new BufferedInputStream(
						connection.getInputStream());
				
				int length = -1;
				byte[] buff = new byte[1024];
				StringBuilder builder = new StringBuilder("");
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

}
