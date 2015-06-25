package com.bingo.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;


public class StreamGobbler extends Thread {

	private InputStream is;
	private String type;
	private int channelId;

	public StreamGobbler() {

	}

	StreamGobbler(InputStream is, String type, int channelId) {
		this.is = is;
		this.type = type;
		this.channelId = channelId;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				line = type
						+ " "
						+ channelId
						+ " "
						+ new Timestamp(System.currentTimeMillis()).toString()
								.substring(0, 19) + " > " + line;
				synchronized (SSHTool.getInstance().mapShellOutPut) {
					SSHTool.getInstance().mapShellOutPut.put(
							SSHTool.getInstance().mapShellOutPut.size() + 1,
							line);
					System.out.println(line);
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
}