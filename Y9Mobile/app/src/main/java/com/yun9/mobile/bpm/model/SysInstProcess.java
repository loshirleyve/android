package com.yun9.mobile.bpm.model;

import java.io.Serializable;

public class SysInstProcess implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String instid;
	private String type;
	private String processtype;
	private String processdefid;
	private String name;
	private String desc;
	private String params;
	private String dynamicnode;
	private String icon;
	
	/**
	 * 是否支持动态增加节点
	 * @return true|false
	 */
	public boolean isDynamicNode() {
		return Boolean.valueOf(getDynamicnode());
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstid() {
		return instid;
	}
	public void setInstid(String instid) {
		this.instid = instid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProcesstype() {
		return processtype;
	}
	public void setProcesstype(String processtype) {
		this.processtype = processtype;
	}
	public String getProcessdefid() {
		return processdefid;
	}
	public void setProcessdefid(String processdefid) {
		this.processdefid = processdefid;
	}
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
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getDynamicnode() {
		return dynamicnode;
	}
	public void setDynamicnode(String dynamicnode) {
		this.dynamicnode = dynamicnode;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public class ProcessType {
		public static final String STATIC = "static";
		public static final String DYNAMIC = "dynamic";
	}

}
