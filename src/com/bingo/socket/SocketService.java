package com.bingo.socket;

import com.bingo.utils.XmlUtil;

public class SocketService extends Thread {

	@Override
	public void run() {
		MinaClient instance = new MinaClient();
		instance.connect(XmlUtil.getVal("1", "host"),
				Integer.valueOf(XmlUtil.getVal("1", "gameserverport")));
	}

}
