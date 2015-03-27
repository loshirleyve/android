package com.yun9.mobile.msg.adapter;

import java.io.Serializable;

public class MsgCardQueryGroup implements Serializable {
	
	public static class Group {
		public static final String PENDING = "pending";
		public static final String FROMME = "fromme";
		public static final String SENDTOME = "sendtome";
		public static final String COMMENT = "commented";
	}

	private static final long serialVersionUID = 1L;
	
	
	public MsgCardQueryGroup(String label, String queryGroup, String content) {
		super();
		this.label = label;
		this.queryGroup = queryGroup;
		this.content = content;
	}

	public MsgCardQueryGroup(String label, String queryGroup) {
		super();
		this.label = label;
		this.queryGroup = queryGroup;
	}

	private String label;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getQueryGroup() {
		return queryGroup;
	}

	public void setQueryGroup(String queryGroup) {
		this.queryGroup = queryGroup;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private String queryGroup;
	
	private String content;

}
