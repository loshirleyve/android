package com.yun9.mobile.bpm.model;

import java.io.Serializable;

public class FormDefColValidatorInfo implements Serializable {
	
	public class ValidType {
		public static final String CLIENT = "client";
		public static final String SERVER = "server";
	}

	private static final long serialVersionUID = 1L;

	private String name;
	private String value;
	private String validType;
	private String msg;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValidType() {
		return validType;
	}
	public void setValidType(String validType) {
		this.validType = validType;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
