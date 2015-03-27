package com.yun9.mobile.msg.model;

import java.io.Serializable;

public class MyMsgCardRelationship implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean like;	// 用户是否点赞赞
	
	private String state;	// 消息卡片是否被处理了

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
