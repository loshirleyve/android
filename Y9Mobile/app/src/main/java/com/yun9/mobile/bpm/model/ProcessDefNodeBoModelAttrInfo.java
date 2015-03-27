package com.yun9.mobile.bpm.model;

import java.io.Serializable;

public class ProcessDefNodeBoModelAttrInfo implements Serializable {

	public class Type {
		/**
		 * 字符串
		 */
		public final static String STRING = "string";
		/**
		 * 数字
		 */
		public final static String NUMBER = "number";
		/**
		 * 日期
		 */
		public final static String DATE = "date";
		/**
		 * 布尔
		 */
		public final static String BOOLEAN = "boolean";

	}

	private static final long serialVersionUID = 1L;

	private String name; // 名称
	private String desc; // 描述
	private String type; // 类型,String,number,date,boolean
	private boolean required; // 是否必填

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

}
