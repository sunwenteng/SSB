package com.bingo.dto.wxreq;

/**
 * 图片消息
 * 
 * @author bingo
 */
public class ImageMessage extends BaseMessage {
	// 图片URL
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

}
