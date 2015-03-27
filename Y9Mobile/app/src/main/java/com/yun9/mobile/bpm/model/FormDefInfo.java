package com.yun9.mobile.bpm.model;

import java.io.Serializable;
import java.util.List;

/**
 * APP表单定义
 * @author yun9
 *
 */
public class FormDefInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String label;
	private String desc;
	private List<FormDefColInfo> cols;
	
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<FormDefColInfo> getCols() {
		return cols;
	}
	public void setCols(List<FormDefColInfo> cols) {
		this.cols = cols;
	}

}
