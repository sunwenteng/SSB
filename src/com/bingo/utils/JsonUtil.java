package com.bingo.utils;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JsonUtil {

	private static ObjectMapper om = new ObjectMapper();

	static {
		// 允许jackson序列化属性为空的对象
		om.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
	}

	/**
	 * 转换Object到字符串
	 * 
	 * @param o
	 *            要转换的源对象
	 * @return 转换后的字符串
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static String toString(Object o) throws JsonGenerationException,
			JsonMappingException, IOException {
		return om.writeValueAsString(o);
	}

	/**
	 * 转换Object到格式化的字符串[效率较低]
	 * 
	 * @param o
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String toFormatString(Object o)
			throws JsonGenerationException, JsonMappingException, IOException {
		return om.defaultPrettyPrintingWriter().writeValueAsString(o);
	}

	/**
	 * 转换Object到字节流
	 * 
	 * @param o
	 *            要转换的源对象
	 * @return 转换后的字符串
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static byte[] writeValueAsBytes(Object o)
			throws JsonGenerationException, JsonMappingException, IOException {
		return om.writeValueAsBytes(o);
	}

	/**
	 * 
	 * @Title: JsonUtil
	 * @Description: 反序列化指定类型的json串
	 * @param content
	 * @param valueType
	 * @return
	 * @return
	 */
	public static <T> T readValue(String content, Class<T> valueType) {
		try {
			return om.readValue(content, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> readJson2Map(String json) {
		try {
			Map<String, Object> maps = om.readValue(json,
					Map.class);
			return maps;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
