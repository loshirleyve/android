package com.yun9.mobile.msg.model;

import java.util.List;

import com.yun9.mobile.msg.bean.MsgCardCommentBean;

public class MyMsgCard implements java.io.Serializable {
	
	public static final String PARAMS_MSG_CARD = "msgCard";
	
	public static class Source {
		public static final String USER="none";
	}
	
	private static final long serialVersionUID = 1L;
	private MyMsgCardMain main;
	private int commentcount;
	private int sharecount;
	private int praisecount;
	private List<String> praiseusername;
	private MyMsgCardUser formuser;
	private List<MyMsgCardAttachment> attachments;
	private List<MyMsgCardAction> actions;
	private List<MsgCardCommentBean> commentlist;
	private MyMsgCardRelationship relationship;

	public MyMsgCardMain getMain() {
		return main;
	}

	public void setMain(MyMsgCardMain main) {
		this.main = main;
	}

	public int getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}

	public int getSharecount() {
		return sharecount;
	}

	public void setSharecount(int sharecount) {
		this.sharecount = sharecount;
	}

	public int getPraisecount() {
		return praisecount;
	}

	public void setPraisecount(int praisecount) {
		this.praisecount = praisecount;
	}

	public MyMsgCardUser getFormuser() {
		return formuser;
	}

	public void setFormuser(MyMsgCardUser formuser) {
		this.formuser = formuser;
	}

	public List<MyMsgCardAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<MyMsgCardAttachment> attachments) {
		this.attachments = attachments;
	}

	public List<MyMsgCardAction> getActions() {
		return actions;
	}

	public void setActions(List<MyMsgCardAction> actions) {
		this.actions = actions;
	}

	public MyMsgCardRelationship getRelationship() {
		return relationship;
	}

	public void setRelationship(MyMsgCardRelationship relationship) {
		this.relationship = relationship;
	}

	public List<String> getPraiseusername() {
		return praiseusername;
	}

	public void setPraiseusername(List<String> praiseusername) {
		this.praiseusername = praiseusername;
	}

	public List<MsgCardCommentBean> getCommentlist() {
		return commentlist;
	}

	public void setCommentlist(List<MsgCardCommentBean> commentlist) {
		this.commentlist = commentlist;
	}
	
}
