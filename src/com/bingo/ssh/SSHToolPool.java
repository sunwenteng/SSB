package com.bingo.ssh;

public class SSHToolPool {

	public static SSHToolPool instance;

	public static SSHToolPool getInstance() {
		if (instance == null) {
			instance = new SSHToolPool();
		}
		return instance;
	}

}
