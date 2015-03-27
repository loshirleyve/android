package com.yun9.mobile.framework.model;

public enum AttendanceState {
	
	NORMAL("normal","正常"),EARLY("early","早退"),LATE("late","迟到"),
	ABSENTEEISM("absenteeism","旷工"),OVERTIME("overtime","加班");
	
	private AttendanceState(String name,String value)
	{
		this.name = name;
		this.value = value;
	}
	
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;
	
	public static AttendanceState getByName(String name) {
		AttendanceState[] types = AttendanceState.values();
		for (AttendanceState t : types) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
	
	public static String getValueByName(String name) {
		AttendanceState[] types = AttendanceState.values();
		for (AttendanceState t : types) {
			if (t.getName().equals(name)) {
				return t.getValue();
			}
		}
		return name;
	}

}
