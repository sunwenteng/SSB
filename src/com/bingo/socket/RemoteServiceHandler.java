package com.bingo.socket;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class RemoteServiceHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("出现异常信息");
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("收到服务端的消息: " + message.toString());
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("发送消息给服务端: " + message.toString());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("会话关闭");
		// 由于Python端数据错误导致的会话关闭，进行立即重连
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("会话创建");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("该会话空闲了");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("会话打开");
	}

}
