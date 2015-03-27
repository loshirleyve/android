package com.yun9.mobile.msg.model;

import java.io.Serializable;

/**
 * 消息卡片动作
 * @author yun9
 *
 */
public class MyMsgCardAction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public class State {
		public static final String PENDING = "pending";	//待处理
		public static final String DONE = "done";	//	已完成
	}
	
	private String id;
	private String label;
	private String name;
	private String actors;
	private String params;
	private String state;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
