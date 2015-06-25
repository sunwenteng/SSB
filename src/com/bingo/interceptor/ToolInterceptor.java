package com.bingo.interceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ToolInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger(ToolInterceptor.class);

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public String intercept(ActionInvocation invoke) throws Exception {
		if (!invoke.getProxy().getMethod().equals("getShellResult")) {
			logger.info(invoke.getAction() + " "
					+ invoke.getProxy().getMethod());
		}
		String result = invoke.invoke();
		return result;
	}

}
