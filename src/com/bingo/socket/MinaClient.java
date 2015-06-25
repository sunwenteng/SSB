package com.bingo.socket;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.bingo.core.Constants;

public class MinaClient {

	private IoConnector connector;
	private static IoSession session;

	private String serverIp;
	private Integer port;

	public static MinaClient instance;

	public static MinaClient getInstance() {
		if (instance == null) {
			instance = new MinaClient();
		}
		return instance;
	}

	// 包最大长度
	private int CHUNK_SIZE = 10000;

	public void connect(String serverIp, Integer port) {
		setServerIp(serverIp);
		setPort(port);

		connector = new NioSocketConnector();

		connector.getSessionConfig().setReadBufferSize(2048);
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		/*
		 * TextLineCodecFactory tlcf = new TextLineCodecFactory(
		 * Charset.forName("UTF-8")); tlcf.setDecoderMaxLineLength(10240000);
		 * connector.getFilterChain().addLast("codec", new
		 * ProtocolCodecFilter(tlcf));
		 */

		connector.setHandler(new MinaClientHandler());
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress(
				serverIp, port));
		connectFuture.awaitUninterruptibly(5000);
		if (connectFuture.isConnected()) {
			session = connectFuture.getSession();
		} else
			reconnect();
	}

	public void reconnect() {
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress(
				serverIp, port));
		connectFuture.awaitUninterruptibly(5000);
		
		if (connectFuture.isConnected()) {
			session = connectFuture.getSession();
		} else
		{
			reconnect();
			System.out.println("reconnecting to serverIp: " + serverIp
					+ " port: " + port);
		}
	}

	public void disconnet() {
		connector.dispose();
	}

	public void sendPacket(WorldPacket pck) {
		if (session.isConnected())
			session.write(pck.SerializePacket());
		else
			reconnect();
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		SocketService service = new SocketService();
		service.start();
		WorldPacket pck = new WorldPacket(Constants.CMD_CS_TEST);
		pck.getData().putShort((short) 1);
		MinaClient.getInstance().sendPacket(pck);
	}
}
