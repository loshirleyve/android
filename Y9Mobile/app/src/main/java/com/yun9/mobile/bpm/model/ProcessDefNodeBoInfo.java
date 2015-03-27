package com.yun9.mobile.bpm.model;

import java.io.Serializable;
import java.util.Map;

public class ProcessDefNodeBoInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;	//名称
	private String desc;	// 描述
	private String root;	//root模型对象名称
	private String type;	//定义类型 json | xml
	private String jpaService;	//数据持久化服务名称， 关联proxy-http配置
	private String boRules;	//验证规则
	private Map<String, ProcessDefNodeBoModelInfo> boModels;	//数据模型定义列表
	
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
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJpaService() {
		return jpaService;
	}
	public void setJpaService(String jpaService) {
		this.jpaService = jpaService;
	}
	public String getBoRules() {
		return boRules;
	}
	public void setBoRules(String boRules) {
		this.boRules = boRules;
	}
	public Map<String, ProcessDefNodeBoModelInfo> getBoModels() {
		return boModels;
	}
	public void setBoModels(Map<String, ProcessDefNodeBoModelInfo> boModels) {
		this.boModels = boModels;
	}

}
