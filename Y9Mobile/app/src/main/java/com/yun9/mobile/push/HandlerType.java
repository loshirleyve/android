package com.yun9.mobile.push;


public enum HandlerType {
	FUNC_MSGCARD("func", "msgcard"),FUNC_CHECK_DUTY("func", "checkduty");

	private String type;
	private String value;

	public static HandlerType getByTypeAndValue(String type, String value) {
		HandlerType[] types = HandlerType.values();
		for (HandlerType t : types) {
			if (t.getType().equals(type) && t.getValue().equals(value)) {
				return t;
			}
		}
		return null;
	}

	private HandlerType(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
