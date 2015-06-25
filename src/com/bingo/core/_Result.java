package com.bingo.core;

import java.io.Serializable;
import java.util.ArrayList;

public class _Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1631079245653176771L;

	private Integer total = 0;

	private String msg;

	private Object data;
	/** 正常0 */
	private Integer status;

	public _Result() {
		setTips(0, "操作成功！");
		data = new ArrayList<Object>();
	}

	public _Result(String msg) {
		setTips(0, msg);
		data = new ArrayList<Object>();
	}

	public void setTips(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
