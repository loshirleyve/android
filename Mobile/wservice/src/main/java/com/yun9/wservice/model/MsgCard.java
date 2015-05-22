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

    transient
    private View.OnClickListener onPraiseClickListener;
    transient
    private View.OnClickListener onForwardClickListener;
    transient
    private View.OnClickListener onCommentClickListener;
    transient
    private View.OnClickListener onActionClickListener;

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

    public View.OnClickListener getOnPraiseClickListener() {
        return onPraiseClickListener;
    }

    public void setOnPraiseClickListener(View.OnClickListener onPraiseClickListener) {
        this.onPraiseClickListener = onPraiseClickListener;
    }

    public View.OnClickListener getOnForwardClickListener() {
        return onForwardClickListener;
    }

    public void setOnForwardClickListener(View.OnClickListener onForwardClickListener) {
        this.onForwardClickListener = onForwardClickListener;
    }

    public View.OnClickListener getOnCommentClickListener() {
        return onCommentClickListener;
    }

    public void setOnCommentClickListener(View.OnClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    public View.OnClickListener getOnActionClickListener() {
        return onActionClickListener;
    }

    public void setOnActionClickListener(View.OnClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }
}
