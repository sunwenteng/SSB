package com.bingo.core;

import com.bingo.utils.PropertyUtil;

public class Constants {
	public static int WORLD_PACKET_MAX_LENGTH = 1024;
	
	public static Short CMD_CS_TEST = 3;
	public static Short CMD_SC_TEST = 2;
	
	public static String PRIVATE_SERVER_PREFIX = "Int-";
	public static String PUBLIC_SERVER_PREFIX = "Ext-";
	
	public static String WORKSPACE_PATH = PropertyUtil.readValue(PropertyUtil.propPath, "workspacePath");
	public static String WORKSPACE_IP = PropertyUtil.readValue(PropertyUtil.propPath, "workspaceIp");
	public static String WORKSPACE_USER = PropertyUtil.readValue(PropertyUtil.propPath, "workspaceUser");
	public static String WORKSPACE_PWD = PropertyUtil.readValue(PropertyUtil.propPath, "workspacePwd");
	public static String SSHENABLE = PropertyUtil.readValue(PropertyUtil.propPath, "sshenable");
	
	public static String WX_TOKEN = "bingo"; 
}
