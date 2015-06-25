package com.bingo.persistence.login.domain;


public class GameserverInfo {
	private int server_id;
	private String server_name;
	private String ip;
	private String local_ip;
	private int port;
	private String version;
	private String res_version;
	private String res_server_ip;
	private String online_num;
	private boolean can_login;
	private int status;
	private int update_time;
	private int login_strategy_id;
	public int getServer_id() {
		return server_id;
	}
	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLocal_ip() {
		return local_ip;
	}
	public void setLocal_ip(String local_ip) {
		this.local_ip = local_ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getRes_version() {
		return res_version;
	}
	public void setRes_version(String res_version) {
		this.res_version = res_version;
	}
	public String getRes_server_ip() {
		return res_server_ip;
	}
	public void setRes_server_ip(String res_server_ip) {
		this.res_server_ip = res_server_ip;
	}
	public String getOnline_num() {
		return online_num;
	}
	public void setOnline_num(String online_num) {
		this.online_num = online_num;
	}
	public boolean isCan_login() {
		return can_login;
	}
	public void setCan_login(boolean can_login) {
		this.can_login = can_login;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(int update_time) {
		this.update_time = update_time;
	}
	public int getLogin_strategy_id() {
		return login_strategy_id;
	}
	public void setLogin_strategy_id(int login_strategy_id) {
		this.login_strategy_id = login_strategy_id;
	}
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
