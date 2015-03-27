package com.yun9.mobile.framework.model.server.sys;

public class ModelAppCheckForUpdateService {
	private String id;
	private boolean update;
	private boolean focus;
	private String log;
	private String url;
	private String version;
	private String internalversion;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	public boolean isFocus() {
		return focus;
	}
	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getInternalversion() {
		return internalversion;
	}
	public void setInternalversion(String internalversion) {
		this.internalversion = internalversion;
	}
}
