package com.yun9.mobile.bpm.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ProcessDefNodeInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;	// 节点类型 userTask | serviceTask
	private String name;	// 节点名称
	private String assignStarter;	// 节点任务执行人为流程发起人
	private boolean allowReject;	// 任务允许驳回 可选 指定后将默认第一个节点为发起人确认
	private boolean async;	// 是否异步，默认false，表示同步执行
	private String url;	// 服务地址
	private String token;	// 授权码
	private String action;	// Action类
	private Map<String,Object> json;	// 服务入参
	private String contentType;	// MIME类型
	private String encoding;	// 	 编码类型
	private String varName;	// 返回值赋值变量
	private Map<String,Object> param;	// 额外参数{key : value}
	private List<ProcessDefNodeAssignInfo> assigns;	// 代理人集合： 1个表示任务代理人，多个表示任务候选人
	private List<ProcessDefNodeRefInfo> refs;	// BO引用, 节点表单采取与BO同名查找配置
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignStarter() {
		return assignStarter;
	}
	public void setAssignStarter(String assignStarter) {
		this.assignStarter = assignStarter;
	}
	public boolean isAllowReject() {
		return allowReject;
	}
	public void setAllowReject(boolean allowReject) {
		this.allowReject = allowReject;
	}
	public boolean isAsync() {
		return async;
	}
	public void setAsync(boolean async) {
		this.async = async;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Map<String, Object> getJson() {
		return json;
	}
	public void setJson(Map<String, Object> json) {
		this.json = json;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	public List<ProcessDefNodeAssignInfo> getAssigns() {
		return assigns;
	}
	public void setAssigns(List<ProcessDefNodeAssignInfo> assigns) {
		this.assigns = assigns;
	}
	public List<ProcessDefNodeRefInfo> getRefs() {
		return refs;
	}
	public void setRefs(List<ProcessDefNodeRefInfo> refs) {
		this.refs = refs;
	}
}
