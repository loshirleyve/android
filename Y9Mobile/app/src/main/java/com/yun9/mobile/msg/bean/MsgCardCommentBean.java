package com.yun9.mobile.msg.bean;

import java.io.Serializable;

import com.yun9.mobile.msg.model.MsgCardComment;
import com.yun9.mobile.msg.model.MyMsgCardUser;

public class MsgCardCommentBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private MsgCardComment comment;
	private MyMsgCardUser user;
	
	public MsgCardComment getComment() {
		return comment;
	}
	public void setComment(MsgCardComment comment) {
		this.comment = comment;
	}
	public MyMsgCardUser getUser() {
		return user;
	}
	public void setUser(MyMsgCardUser user) {
		this.user = user;
	}

}
