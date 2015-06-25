package com.bingo.socket;

import com.bingo.ssh.RemoteShellTools;

public class RemoteThread extends Thread {

	public static RemoteThread instance = null;
	public static boolean bInited = false;
	public static RemoteThread getInstance()
	{
		if( instance == null ){
			instance = new RemoteThread();
			instance.start();
		}
		return instance;
	}
	
	@Override
	public void run() {
		try {
			if(bInited){
				RemoteShellTools
					.runShell("svn up /home/zmj/battle_workspace/data/config/");
				RemoteShellTools
					.runShell("cd /home/zmj/battle_workspace/shell/ \n sudo ./start_server.sh");
			}
			else{
				bInited = true;
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
