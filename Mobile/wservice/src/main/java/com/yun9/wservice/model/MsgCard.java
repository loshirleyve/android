package com.yun9.wservice.model;

import android.view.View;

import java.util.List;


public class MsgCard implements java.io.Serializable {
	
	public static final String PARAMS_MSG_CARD = "msgCard";
	
	public static class Source {
		public static final String USER="none";
	}
	
	private static final long serialVersionUID = 1L;
	private MsgCardMain main;
	private int commentcount;
	private int sharecount;
	private int praisecount;
	private List<String> praiseusername;
	private SampleUser formuser;
	private List<MsgCardAttachment> attachments;
	private List<MsgCardAction> actions;
	private List<MsgCardComment> commentlist;
	private MsgCardRelationship relationship;

	public MsgCardMain getMain() {
		return main;
	}

	public void setMain(MsgCardMain main) {
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

	public SampleUser getFormuser() {
		return formuser;
	}

	public void setFormuser(SampleUser formuser) {
		this.formuser = formuser;
	}

	public List<MsgCardAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<MsgCardAttachment> attachments) {
		this.attachments = attachments;
	}

	public List<MsgCardAction> getActions() {
		return actions;
	}

	public void setActions(List<MsgCardAction> actions) {
		this.actions = actions;
	}

	public MsgCardRelationship getRelationship() {
		return relationship;
	}

	public void setRelationship(MsgCardRelationship relationship) {
		this.relationship = relationship;
	}

	public List<String> getPraiseusername() {
		return praiseusername;
	}

	public void setPraiseusername(List<String> praiseusername) {
		this.praiseusername = praiseusername;
	}

	public List<MsgCardComment> getCommentlist() {
		return commentlist;
	}

	public void setCommentlist(List<MsgCardComment> commentlist) {
		this.commentlist = commentlist;
	}

}
