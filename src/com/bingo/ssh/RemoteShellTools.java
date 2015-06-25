package com.bingo.ssh;

import com.bingo.utils.PropertyUtil;

// http://pirate2089.github.io/blog/2013/07/31/javacheng-xu-yun-xing-slash-ting-zhi-shelljiao-ben/

public class RemoteShellTools {
	// single ShellCmd exe without result handler
	public static void exeShell(String shellStr) {
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec(shellStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// single ShellCmd exe with result handler
	public static void runShell(String shStr) throws Exception {
		Process process;
		process = Runtime.getRuntime().exec(
				new String[] { "/bin/sh", "-c", shStr }, null, null);
		StreamGobbler errorMsg = new StreamGobbler(process.getErrorStream(),
				"ERROR", 0);
		StreamGobbler resultMsg = new StreamGobbler(process.getInputStream(),
				"OUTPUT", 0);
		errorMsg.start();
		resultMsg.start();
		process.waitFor();

	}

	public static void main(String[] args) throws Exception {
		RemoteShellTools.runShell(PropertyUtil.readValue(PropertyUtil.propPath,
				"svn_up"));
		RemoteShellTools.runShell(PropertyUtil.readValue(PropertyUtil.propPath,
				"restart_server"));
	}
}
