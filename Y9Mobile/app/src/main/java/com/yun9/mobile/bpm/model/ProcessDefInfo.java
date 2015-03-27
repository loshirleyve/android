package com.yun9.mobile.bpm.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import ProcessDefInfo.ProcessDefFormInfo;

public class ProcessDefInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String,Map<String,Object>> dataObjects; // 表单数据： {表单名称: 数据，。。。}
	
	private String name;	//流程名称
	
	private String title;	//流程实例标题
	
	private List<ProcessDefNodeInfo> nodes;	//	流程节点信息列表
	
	private List<ProcessDefNodeBoInfo> bos;
	
	private List<ProcessDefFormInfo> forms;

	public List<ProcessDefNodeInfo> getNodes() {
		return nodes;
	}

	public void setNodes(List<ProcessDefNodeInfo> nodes) {
		this.nodes = nodes;
	}

	public Map<String,Map<String, Object>> getDataObjects() {
		return dataObjects;
	}

	public void setDataObjects(Map<String,Map<String, Object>> dataObjects) {
		this.dataObjects = dataObjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ProcessDefNodeBoInfo> getBos() {
		return bos;
	}

	public void setBos(List<ProcessDefNodeBoInfo> bos) {
		this.bos = bos;
	}

	public List<ProcessDefFormInfo> getForms() {
		return forms;
	}

	public void setForms(List<ProcessDefFormInfo> forms) {
		this.forms = forms;
	}

}
