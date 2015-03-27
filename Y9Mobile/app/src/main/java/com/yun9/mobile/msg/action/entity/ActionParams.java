package com.yun9.mobile.msg.action.entity;

import com.yun9.mobile.framework.util.JsonUtil;

public class ActionParams extends JsonParam {

	public static final String PARAM_HEADER = "header";
	public static final String PARAM_VALUES = "values";
	public static final String PARAM_DATA = "data";
	public static final String PARAM_FORM = "form";
	public static final String PARAM_TYPE = "type";

	private static final long serialVersionUID = 1L;

	private ActionHeader header;
	private ActionValues values;
	private ActionData data;
	private ActionForm form;
	private String type;

	public ActionParams(String json) {
		super(json);
		parse();

	}

	private void parse() {
		if (this.isContain(PARAM_HEADER)) {
			this.header = new ActionHeader(this.getJsonObject(PARAM_HEADER));
		} else  {
			throw new RuntimeException("无法获取ActionParams的header属性");
		}
		if (this.isContain(PARAM_VALUES)) {
			this.values = new ActionValues(this.getJsonObject(PARAM_VALUES));
		}
		if (this.isContain(PARAM_DATA)) {
			this.data = new ActionData(this.getJsonObject(PARAM_DATA));
		} else {
			this.setData(new ActionData("{}"));
		}
		if (this.isContain(PARAM_FORM)) {
			this.form = new ActionForm(this.getJsonObject(PARAM_FORM));
		}
		if (this.isContain(PARAM_TYPE)) {
			this.type = this.getString(PARAM_TYPE);
		} else {
			throw new RuntimeException("无法获取ActionParams的type属性");
		}
	}

	public ActionHeader getHeader() {
		return header;
	}

	public void setHeader(ActionHeader header) {
		this.header = header;
	}

	public ActionValues getValues() {
		return values;
	}

	public void setValues(ActionValues values) {
		this.values = values;
		this.put(PARAM_VALUES, JsonUtil.fromString(values.getString()));
	}

	public ActionData getData() {
		return data;
	}

	public void setData(ActionData data) {
		this.data = data;
		this.put(PARAM_DATA, JsonUtil.fromString(data.getString()));
	}

	public ActionForm getForm() {
		return form;
	}

	public void setForm(ActionForm form) {
		this.form = form;
		this.put(PARAM_FORM ,JsonUtil.fromString(form.getString()));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
