package com.bingo.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.bingo.core.BaseAction;
import com.bingo.core.Constants;
import com.bingo.core._Result;
import com.bingo.persistence.login.dao.GameserverInfoMapper;
import com.bingo.persistence.login.domain.GameserverInfo;
import com.bingo.persistence.tools.dao.GMMapper;
import com.bingo.persistence.tools.domain.GM;
import com.bingo.ssh.SSHTool;
import com.bingo.utils.JsonUtil;

/**
 * @author Elvis Sun
 * 
 */
@ParentPackage("MyPackage")
@Namespace("/portal")
public class PortalAction extends BaseAction {

	public static Logger logger = Logger.getLogger(PortalAction.class);

	private static final long serialVersionUID = -4371642493340401521L;

	private _Result result;

	@Autowired
	private GameserverInfoMapper gameserverInfoMapper;
	@Autowired
	private GMMapper gmMapper;

	/**
	 * test
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "test", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String test() throws Exception {
		result = new _Result();
		result.setData("test");
		return SUCCESS;
	}

	/**
	 * restartServer
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "restartServer", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String restartServer() throws Exception {
		result = new _Result();
		String str = "success!";
		if (!SSHTool.getInstance().bCanExec) {
			result.setStatus(-1);
			str = "Execute Failure, Remote Addr = "
					+ SSHTool.getInstance().curSessionOwner + " Is Occupying";
			result.setData(JsonUtil.toFormatString(str));
			return SUCCESS;
		}

		String serverId = request.getParameter("serverId");
		if (serverId.equals("-1")) {
			result.setStatus(-1);
			str = "Execute Failure, pls select server";
			result.setData(JsonUtil.toFormatString(str));
			return SUCCESS;
		}
		serverId = serverId.substring(4, serverId.length());
		SSHTool.getInstance().clearShellOutput();

		SSHTool.getInstance().exec(
				"cd /data/deploy/bh" + serverId
						+ "/shell && sudo ./start_server.sh -grd",
				request.getRemoteAddr());
		result.setData(JsonUtil.toFormatString(str));
		return SUCCESS;
	}

	/**
	 * releaseServer
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "releaseServer", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String releaseServer() throws Exception {
		result = new _Result();
		String str = "success!";
		if (!SSHTool.getInstance().bCanExec) {
			result.setStatus(-1);
			str = "Execute Failure, Remote Addr = "
					+ SSHTool.getInstance().curSessionOwner + " Is Occupying";
			result.setData(JsonUtil.toFormatString(str));
			return SUCCESS;
		}

		String serverId = request.getParameter("serverId");
		if (serverId.equals("-1")) {
			result.setStatus(-1);
			str = "Execute Failure, pls select server";
			result.setData(JsonUtil.toFormatString(str));
			return SUCCESS;
		}
		serverId = serverId.substring(4, serverId.length());
		SSHTool.getInstance().clearShellOutput();
		SSHTool.getInstance().exec(
				"svn up " + Constants.WORKSPACE_PATH + "data/config && cp -rf "
						+ Constants.WORKSPACE_PATH
						+ "build/gameserver/Gameserver /data/deploy/bh"
						+ serverId + "/build/gameserver && cp -rf "
						+ Constants.WORKSPACE_PATH
						+ "data/config/* /data/deploy/bh" + serverId
						+ "/data/config/ && cd /data/deploy/bh" + serverId
						+ "/shell && ./start_server.sh -grd",
				request.getRemoteAddr());

		result.setData(JsonUtil.toFormatString(str));
		return SUCCESS;
	}

	/**
	 * releaseResource
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "releaseResource", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String releaseResource() throws Exception {
		result = new _Result();
		String str = "success!";
		if (!SSHTool.getInstance().bCanExec) {
			result.setStatus(-1);
			str = "Execute Failure, Remote Addr = "
					+ SSHTool.getInstance().curSessionOwner + " Is Occupying";
			result.setData(JsonUtil.toFormatString(str));
			return SUCCESS;
		}

		String releaseOutServerOption = request
				.getParameter("releaseOutServerOption");
		String branchesName = request.getParameter("releaseBranchesName");
		SSHTool.getInstance().clearShellOutput();
		String outerDeploySh = "";
		String resPckType = "";
		if (releaseOutServerOption.equals("true")) {
			resPckType = "o";
			outerDeploySh = " && sh upload_client_res.sh ";
		} else if (releaseOutServerOption.equals("false")) {
			resPckType = "i";
		} else
			return INPUT;

		String exeCmd = "";

		if (resPckType.equals("o")) {
			exeCmd = "cd " + Constants.WORKSPACE_PATH
					+ "shell/ && sh jsc_release.sh " + branchesName
					+ " && sh res_pck.sh " + resPckType + " " + branchesName
					+ outerDeploySh;
		} else {
			exeCmd = "cd " + Constants.WORKSPACE_PATH
					+ "shell/ && sh res_pck.sh " + resPckType + " "
					+ branchesName;
		}

		SSHTool.getInstance().exec(exeCmd, request.getRemoteAddr());

		result.setData(JsonUtil.toFormatString(str));
		return SUCCESS;
	}

	/**
	 * getShellResult
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getShellResult", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String getShellResult() throws Exception {
		result = new _Result();

		List<String> strList = new ArrayList<String>();
		synchronized (SSHTool.getInstance().mapShellOutPut) {
			int i = 0;
			int j = 0;
			for (i = SSHTool.getInstance().shellOutPutLineIndex; i <= SSHTool
					.getInstance().mapShellOutPut.size(); ++i) {
				if (++j > 30
						&& (SSHTool.getInstance().mapShellOutPut.size() - SSHTool
								.getInstance().shellOutPutLineIndex) > 30)
					break;
				strList.add(SSHTool.getInstance().mapShellOutPut.get(i));
			}
			SSHTool.getInstance().shellOutPutLineIndex = SSHTool.getInstance().mapShellOutPut
					.size() + 1;
		}
		result.setData(JsonUtil.toFormatString(strList));
		return SUCCESS;
	}

	/**
	 * sendSignalToShell
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "sendSignalToShell", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String sendSignalToShell() throws Exception {
		result = new _Result();
		SSHTool.getInstance().sendSignal();
		return SUCCESS;
	}

	/**
	 * sendSignalToShellWhenPageLoad
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "sendSignalToShellWhenPageLoad", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String sendSignalToShellWhenPageLoad() throws Exception {
		result = new _Result();
		if (!SSHTool.getInstance().bCanExec
				&& SSHTool.getInstance().curSessionOwner.equals(request
						.getRemoteAddr()))
			SSHTool.getInstance().sendSignal();
		return SUCCESS;
	}

	/**
	 * getGMList
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getGMList", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String getGMList() throws Exception {
		result = new _Result();
		List<GM> gmList = gmMapper.getAll();
		result.setData(JsonUtil.toString(gmList));
		return SUCCESS;
	}

	/**
	 * getServerList
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getServerList", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String getServerList() throws Exception {
		String preFix = "";
		preFix = Constants.PRIVATE_SERVER_PREFIX;

		result = new _Result();
		List<GameserverInfo> gameserverLsit = gameserverInfoMapper.getAll();
		for (GameserverInfo instance : gameserverLsit) {
			instance.setServer_name(preFix + instance.getServer_name());
		}

		// 获取外部服务器列表信息 暂时不需要
		/*
		 * Map<String, Object> paramMap = new HashMap<String, Object>(); String
		 * _strResult = this .sendRequestWithResult(
		 * "http://bhop.imobile-ent.com:8988/BHTools/portal/getServerListOut",
		 * paramMap); if (!_strResult.equals("")) { _Result result =
		 * JsonUtil.readValue(_strResult, _Result.class); OutServers outServers
		 * = JsonUtil.readValue(result.getData() .toString(), OutServers.class);
		 * for (GameserverInfo instance : outServers.getGameserverList()) {
		 * instance.setServer_name(Constants.PUBLIC_SERVER_PREFIX +
		 * instance.getServer_name()); }
		 * 
		 * gameserverLsit.addAll(outServers.getGameserverList()); }
		 */

		result.setData(JsonUtil.toString(gameserverLsit));
		return SUCCESS;
	}

	/**
	 * getIp
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getIp", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String getIp() throws Exception {
		result = new _Result();
		String ip = request.getRemoteAddr();
		result.setData(JsonUtil.toString(ip));
		return SUCCESS;
	}

	/**
	 * getBranches
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getBranches", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String getBranches() throws Exception {
		result = new _Result();
		List<String> strList = new ArrayList<String>();
		strList.add("trunk");
		strList.addAll(Arrays.asList(SSHTool.getInstance().exec(
				"cd /data/workspace/bh/client/branches && ls").split("\n")));
		result.setData(JsonUtil.toString(strList));
		return SUCCESS;
	}

	/**
	 * not used yet but keep instead SaveClientErrorMsg
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "saveClientErrorMsg", results = { @Result(type = "json", name = "success", params = {
			"root", "result" }) })
	public String saveClientErrorMsg() throws Exception {
		result = new _Result();
		String errorMsg = request.getParameter("errorMsg");
		// 通行证编号
		String accountId = request.getParameter("accountId");
		// 角色编号
		String roleId = request.getParameter("roleId");
		String serverId = request.getParameter("serverId");
		String clientVer = request.getParameter("clientVer");
		String resourceVer = request.getParameter("resourceVer");
		logger.error("accountId: " + accountId + " roleId: " + roleId
				+ " serverId: " + serverId + " clientVer: " + clientVer
				+ " resourceVer: " + resourceVer + " " + errorMsg);
		return SUCCESS;
	}

	// TODO add next action

	public _Result getResult() {
		return result;
	}

	public void setResult(_Result result) {
		this.result = result;
	}

	public Map<String, String[]> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String[]> paramMap) {
		this.paramMap = paramMap;
	}
}
