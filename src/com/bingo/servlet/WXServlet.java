package com.bingo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bingo.utils.SignUtil;

public class WXServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;
	public static Logger logger = Logger.getLogger(WXServlet.class);
	/**
	 * 确认请求来自微信服务器
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doGet");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		if (true || SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}

		out.close();
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doPost");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String respMessage = CoreService.processRequest(request);
		System.out.println(respMessage);
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

}