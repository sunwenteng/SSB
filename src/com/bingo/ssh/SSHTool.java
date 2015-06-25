package com.bingo.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 使用这个工具的时候必须保证多台linux服务器相互都信任自己和对方的密钥
 * */
public class SSHTool {

	public static Logger logger = Logger.getLogger(SSHTool.class);
	
	public SSHTool() {
		shellOutPutLineIndex = 0;
		mapShellOutPut = new ConcurrentHashMap<Integer, String>();
		outPutListener = new StreamGobbler();
		bCanExec = true;
	}

	private static int maxTimeOut = 30000;

	public static String charset = "UTF-8";

	// instance
	public static SSHTool instance;

	// ssh session member
	public Session session;
	public ChannelShell channel;
	public OutputStream out;
	public StreamGobbler outPutListener;

	// ssh server info
	public String curSessionOwner;
	public boolean bCanExec;
	// 最大容量超过200w条就会内存溢出了，考虑用外部存储，当查询返回结果过于庞大的时候，而且内存占用也偏大
	public Map<Integer, String> mapShellOutPut;
	public int shellOutPutLineIndex;

	public static SSHTool getInstance() {
		if (instance == null) {
			instance = new SSHTool();
		}
		return instance;
	}

	public void createSession(String host, String user, String passwd)
			throws Exception {
		if (session != null && session.isConnected())
			return;

		JSch jsch = new JSch();
		session = jsch.getSession(user, host, 22);
		session.setPassword(passwd);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setTimeout(maxTimeOut);
		session.connect();

		logger.info("Session Created! Host = " + session.getHost()
				+ " User = " + session.getUserName());
	}

	public void createShellChannel() throws Exception {
		channel = (ChannelShell) session.openChannel("shell");

		out = channel.getOutputStream();
		channel.connect();
		logger.info("Channel Created! Channel Id = " + channel.getId());
	}

	public void clearShellOutput() {
		shellOutPutLineIndex = 1;
		mapShellOutPut.clear();
	}

	public void connect(String host, String user, String passwd)
			throws Exception {
		createSession(host, user, passwd);
		createShellChannel();
		outPutListener.setChannelId(channel.getId());
		outPutListener.setIs(channel.getInputStream());
		outPutListener.setType("shell");
		outPutListener.start();
	}

	public String exec(String command) throws Exception {
		BufferedReader reader = null;
		ChannelExec channel = null;
		String result = "";
		try {
			if (command != null) {
				channel = (ChannelExec) session.openChannel("exec");
				channel.setCommand(command);
				channel.connect();
				InputStream in = channel.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in));
				String buf = null;
				while ((buf = reader.readLine()) != null ) {
					result += ( buf + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			channel.disconnect();
		}
		return result;
	}
	
	public boolean exec(String cmd, String sessionId) throws Exception {
		if (!bCanExec)
			return false;

		if (channel == null || session == null || channel.isClosed()
				|| !session.isConnected()) {
			return false;
		}

		cmd += "\ncd ~\n";
		out.write(cmd.getBytes());
		out.flush();

		curSessionOwner = sessionId;
		bCanExec = false;
		return true;
	}

	public void sendSignal() throws Exception {
		out.write(3);
		out.flush();

		curSessionOwner = "";
		bCanExec = true;
	}

	public static void main(String[] args) throws Exception {
		SSHTool instance  = new SSHTool();
		List<String> strList = new ArrayList<String>();
		
		strList.clear();
		instance.createSession("172.16.0.97", "root", "ime@server");
		strList.addAll(Arrays.asList(instance.exec("cd /data/workspace/bh/client/branches && ls").split("\n")));
		for( String a : strList )
		{
			System.out.println(a);
		}
		
		strList.clear();
		strList.addAll(Arrays.asList(instance.exec("du -sh /data/workspace/bh/client/branches/bh_1.6.0/client/*").split("\n")));
		for( String a : strList )
		{
			System.out.println(a);
		}
		
		strList.clear();
		strList.addAll(Arrays.asList(instance.exec("cd /data/workspace/bh/client/branches && pwd").split("\n")));
		for( String a : strList )
		{
			System.out.println(a);
		}
	}
}
