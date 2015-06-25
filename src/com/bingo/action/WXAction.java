package com.bingo.action;

import java.io.PrintWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.bingo.core.BaseAction;
import com.bingo.core.Constants;
import com.bingo.core._Result;
import com.qq.weixin.mp.aes.SHA1;
/**
 * @author Elvis Sun
 * 
 */
@ParentPackage("struts-default")
@Namespace("/portal")
public class WXAction extends BaseAction {

	public static Logger logger = Logger.getLogger(WXAction.class);

	private static final long serialVersionUID = -4371642493340401521L;

	private _Result result;

	/**
	 * test
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "test")
	public String test() throws Exception {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		String ret = SHA1.getSHA1(Constants.WX_TOKEN, timestamp, nonce, echostr);
		System.out.println("signature="+signature+" timestamp="+timestamp+" nonce="+nonce+" echostr="+echostr+" ret="+ret);
		PrintWriter out = response.getWriter();
		/*if( ret.equals(signature) )
		{
			out.print(echostr);
		}*/
		out.print(echostr);
		out.close();
		out = null;
		return null;
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
