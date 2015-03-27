package com.yun9.mobile.bpm.model;

import java.io.Serializable;
import java.util.List;

public class FormDefColInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String label;
	private String type;
	private String defaultValue;
	private List<FormDefColValidatorInfo> validators;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDefault() {
		return defaultValue;
	}
	public void setDefault(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<FormDefColValidatorInfo> getValidators() {
		return validators;
	}
	public void setValidators(List<FormDefColValidatorInfo> validators) {
		this.validators = validators;
	}

}
