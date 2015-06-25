package com.bingo.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XmlUtil {

	public static final String path = XmlUtil.class.getResource("/").getPath()
			+ "server.xml";;

	public static int getNodeCnt() {
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(path);
			return document.getRootElement().elements().size();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static List<String> getAllNodeId() {
		List<String> result = new ArrayList<String>();
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(path);
			Element e = document.getRootElement();
			for (Object instance : e.elements()) {
				Element element = (Element) instance;
				result.add(element.attribute("id").getData().toString());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getVal(String serverId, String attribute) {
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(path);
			Node root = document.selectSingleNode("/servers");
			return root.selectSingleNode("server[@id='" + serverId + "']")
					.valueOf(attribute);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) throws Exception {
		System.out.println(XmlUtil.getVal("Int-1", "host"));
	}
}
