package com.maoye.form.model.form;

public class ModelMulSelected {
	
	String value;
	
	boolean isCheck;

	/**
	 * @param value
	 */
	public ModelMulSelected(String value) {
		super();
		this.value = value;
	}
	
	public ModelMulSelected(String value, boolean isCheck) {
		this(value);
		this.isCheck = isCheck;
	}
	
	public ModelMulSelected() {
		super();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
}
