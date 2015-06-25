var serverArr;
var timerId = null;
var closeDialogTimerId = null;
var refreshShellOutPutFreq = 250;
var shellOutputCloseTime = 600000;
var maxShellOutputLineNum = 400;

// todo do all init
$(function() {
	// 重启服务器
	$("#restartServer").click(function() {
		var param = {};
		var selectId = $("#selectServer").val();
		if (selectId >= 0) {
			param["serverId"] = getSelectServerId();
		} else {
			param["serverId"] = selectId;
		}
		sendRequest("/Tools/portal/restartServer", param, openShellDialog);
	});

	// 发布资源
	$("#releaseResource").click(
			function() {
				var param = {};
				param["releaseOutServerOption"] = $("#releaseOutServerOption")
						.is(':checked');
				param["releaseBranchesName"] = $("#releaseBranchesName").val();
				sendRequest("/Tools/portal/releaseResource", param,
						openShellDialog);
			});

	// 发布游戏服务器
	$("#releaseServer").click(function() {
		var param = {};
		var selectId = $("#selectServer").val();
		if (selectId >= 0) {
			param["serverId"] = getSelectServerId();
		} else {
			param["serverId"] = selectId;
		}

		sendRequest("/Tools/portal/releaseServer", param, openShellDialog);
	});

	// 获取GM信息
	sendRequest("/Tools/portal/getGMList", null, function(msg) {
		gmArr = JSON.parse(msg.data);
		for ( var i = 0; i < gmArr.length; ++i) {
			$("#gmTable>tbody").append(
					'<tr><td>' + gmArr[i].gmname + '</td><td>'
							+ gmArr[i].format + '</td><td>' + gmArr[i].param
							+ '</td><td>' + gmArr[i].gmdesc + '</td></tr>');
		}
	}, null);

	// 获取本机IP
	sendRequest("/Tools/portal/getIp", null, function(msg) {
		var data = JSON.parse(msg.data);
		$("#ipHref").text(data);
	});

	// 获取分支列表
	sendRequest("/Tools/portal/getBranches", null, function(msg) {
		branches = JSON.parse(msg.data);
		$("#releaseBranchesName").append(
				"<option value='" + (-1) + "'>" + "分支选择..." + "</option>");
		for ( var i = 0; i < branches.length; ++i) {
			$("#releaseBranchesName").append(
					"<option value='" + branches[i] + "'>" + branches[i]
							+ "</option>");
		}
	});
	
	// 获取服务器列表
	sendRequest("/Tools/portal/getServerList", null, function(msg) {
		serverArr = JSON.parse(msg.data);
		$("#selectServer").append(
				"<option value='" + (-1) + "'>" + "服务器选择..." + "</option>");
		for ( var i = 0; i < serverArr.length; ++i) {
			$("#selectServer").append(
					"<option value='" + i + "'>" + serverArr[i].server_name
							+ "</option>");
		}

		$("#selectServer").change(function() {
			if ($("#selectServer").val() >= 0) {
				var server = serverArr[$("#selectServer").val()];
				$("#textGameserverName").val(server.server_name);
				$("#textClientVersion").val(server.version);
				$("#textGameResVersion").val(server.res_version);
			} else {
				$("#textGameserverName").val("不可编辑");
				$("#textClientVersion").val("");
				$("#textGameResVersion").val("");
			}
		});
	});
});

// init panel
$(function() {
	$("#dialog-form")
			.dialog(
					{
						autoOpen : false,
						height : 500,
						width : 800,
						modal : true,
						show : "slide",
						buttons : [ {
							text : "关闭",
							click : function() {
								$(this).dialog("close");
							}
						} ],
						close : function() {
							sendRequest("/Tools/portal/sendSignalToShell");
						},
						open : function() {
							$(document).unmask();
							clearInterval(closeDialogTimerId);
							closeDialogTimerId = null;
							$("#dialog-form").empty();
							$("#dialog-form")
									.append(
											"<textarea id='txtAreaShellOutput' rows='1000' cols='1000' style='width:98%;height:92%'></textarea>");
							closeDialogTimerId = setTimeout(function() {
								$("#dialog-form").dialog("close");
							}, shellOutputCloseTime);
						},
					});
});

function getSelectServerId() {
	if ($("#selectServer").val() > 0) {
		var result = serverArr[$("#selectServer").val()].server_name.substring(
				0, 4)
				+ serverArr[$("#selectServer").val()].server_id;
		return result;
	}
	return -1;
}

function openShellDialog(msg) {
	if (msg.status < 0) {
		alert(msg.data);
	} else {
		$("#dialog-form").dialog("close");
		$("#dialog-form").dialog("open");
		if (timerId != null) {
			clearInterval(timerId);
		}
		timerId = setInterval(
				function() {
					sendRequest(
							"/Tools/portal/getShellResult",
							null,
							function(msg) {
								var resultArr = JSON.parse(msg.data);
								for ( var i = 0; i < resultArr.length; ++i) {
									$("#txtAreaShellOutput").append(
											resultArr[i] + "\n");
									$('#txtAreaShellOutput')
											.scrollTop(
													$('#txtAreaShellOutput')[0].scrollHeight);

									var txtArray = $("#txtAreaShellOutput")
											.val().split("\n");
									if ($("#txtAreaShellOutput").val().split(
											"\n").length > maxShellOutputLineNum) {
										txtArray = txtArray.slice(1, $(
												"#txtAreaShellOutput").val()
												.split("\n").length);
										$("#txtAreaShellOutput").empty();
									}
								}
							});
				}, refreshShellOutPutFreq);
	}
}

// called by page body
function onload() {
	sendRequest("/Tools/portal/sendSignalToShellWhenPageLoad");
}

// called by page body
function onunload() {
	sendRequest("/Tools/portal/sendSignalToShellWhenPageLoad");
}
