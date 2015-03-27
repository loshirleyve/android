package com.yun9.mobile.bpm.model;

import java.io.Serializable;
import java.util.Map;

public class ProcessDefNodeBoModelInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name; // 名称
	private String desc; // 描述
	private Map<String,String> hasones; //name -- model name
	private Map<String,String> hasmanys; //name -- model name
	private Map<String, ProcessDefNodeBoModelAttrInfo> boAttributes;	//属性列表
	
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
	public Map<String, String> getHasones() {
		return hasones;
	}
	public void setHasones(Map<String, String> hasones) {
		this.hasones = hasones;
	}
	public Map<String, String> getHasmanys() {
		return hasmanys;
	}
	public void setHasmanys(Map<String, String> hasmanys) {
		this.hasmanys = hasmanys;
	}
	public Map<String, ProcessDefNodeBoModelAttrInfo> getBoAttributes() {
		return boAttributes;
	}
	public void setBoAttributes(
			Map<String, ProcessDefNodeBoModelAttrInfo> boAttributes) {
		this.boAttributes = boAttributes;
	}

}
