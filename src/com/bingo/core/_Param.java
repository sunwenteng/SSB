package com.bingo.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class _Param implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1631079245653176771L;

	private Map<String, String> data;

	public _Param() {
		data = new HashMap<String, String>();
	}

	public _Param(String msg) {
		data = new HashMap<String, String>();
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
