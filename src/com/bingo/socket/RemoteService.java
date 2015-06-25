package com.bingo.socket;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.bingo.utils.JsonUtil;

public class RemoteService {

	private static final int SERVER_PORT = 5555;
	private static final String SERVER_IP = "10.0.0.175";

	private static IoSession session;
	private static IoConnector connector;
	private static ConnectFuture future;

	private static int CHUNK_SIZE = 10000;

	public RemoteService(){
		connector = new NioSocketConnector();
		connector.getSessionConfig().setReadBufferSize(2048);
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		TextLineCodecFactory tlcf = new TextLineCodecFactory(Charset
				.forName("UTF-8"));
		tlcf.setDecoderMaxLineLength(10240000);
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(tlcf));

		connector.setHandler(new RemoteServiceHandler());
		future = connector
				.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
		future.awaitUninterruptibly();

		if (!future.isConnected()) {
			return;
		}
			
		session = future.getSession();
		session.getConfig().setUseReadOperation(true);
		session.getCloseFuture().awaitUninterruptibly();

		System.out.println("After Writing");
		connector.dispose();
	}

	// 阻塞式通信，同步通信
	public static String sendMsg(String msg) throws Exception {
		if (future.isConnected()) {
			sendMsgBase(msg);
		}
		return "error";
	}

	private static String sendMsgBase(String msg) {
		WriteFuture wf = RemoteService.session.write(msg);
		wf.awaitUninterruptibly();
		if (wf.isWritten()) {
			ReadFuture rf = session.read();
			rf.awaitUninterruptibly();
			if (rf.isRead())
				return rf.getMessage().toString();
		}
		return "error";
	}
	
	public static void main(String[] args) throws Exception{
		RemoteThread rt = new RemoteThread();
		rt.start();
		
	}
}
