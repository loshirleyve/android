package com.maoye.form.model.form;

public abstract class ModelViewFormElement {

	private String type = ModelViewFormElement.class.getSimpleName();
	
	private String tag;
	
	private String label;
	
	private String value;
	
	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	private FormStat formStat = FormStat.OnWrite;
	
	public enum FormStat{
		OnWrite, OnRead
	}
	
	/**
	 * @param tag
	 */
	public ModelViewFormElement(String tag) {
		super();
		this.tag = tag;
	}

	
	/**
	 * @param tag
	 */
	public ModelViewFormElement() {
		super();
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getType() {
		return type;
	}


	protected void setType(String type) {
		this.type = type;
	}


	public FormStat getFormStat() {
		return formStat;
	}

	public void setFormStat(FormStat formStat) {
		this.formStat = formStat;
	}
	

}
