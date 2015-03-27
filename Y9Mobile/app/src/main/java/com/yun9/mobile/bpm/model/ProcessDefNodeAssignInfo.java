package com.yun9.mobile.bpm.model;

import java.io.Serializable;

public class ProcessDefNodeAssignInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public class Type {
		public static final String DEPT = "dept";
		public static final String USER = "user";
	}

	private String userId;	// 用户ID
	private String instId;	// 机构ID
	private String type;	// 	 类型 user | dept
	private String dimsId;	// 维度ID
	
	public ProcessDefNodeAssignInfo() {
	}

	public ProcessDefNodeAssignInfo(String userId, String instId) {
		super();
		this.userId = userId;
		this.instId = instId;
	}
	
	public ProcessDefNodeAssignInfo(String userId, String instId, String type) {
		super();
		this.userId = userId;
		this.instId = instId;
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDimsId() {
		return dimsId;
	}

	public void setDimsId(String dimsId) {
		this.dimsId = dimsId;
	}

}
