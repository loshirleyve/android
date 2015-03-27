package com.yun9.mobile.bpm.model;

import java.io.Serializable;

public class ProcessDefNodeRefInfo implements Serializable {

	public ProcessDefNodeRefInfo(String name, boolean readonly) {
		super();
		this.name = name;
		this.readonly = readonly;
	}
	private static final long serialVersionUID = 1L;
	
	private String name;
	private boolean readonly;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isReadonly() {
		return readonly;
	}
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

}
