package com.yun9.mobile.bpm.model;

import java.io.Serializable;

public class SysInstProcessUserdef implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String instprocessid;
	private String params;
	private String data;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getInstprocessid() {
		return instprocessid;
	}
	public void setInstprocessid(String instprocessid) {
		this.instprocessid = instprocessid;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
