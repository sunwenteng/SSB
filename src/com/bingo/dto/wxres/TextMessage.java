package com.bingo.dto.wxres;

/**
 * 文本消息
 * 
 * @author bingo
 */
public class TextMessage extends BaseMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
