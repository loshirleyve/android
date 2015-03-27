package com.yun9.mobile.calendar.entity;

/**
 * 排班信息
 * @author Kass
 *
 */
public class ScheduleInfo {
	// 日期 "yyyy-MM-dd"
    private String date;
    // 班次名称
    private String label;
    // 打卡名称"上班"
    private String checkName;   
    // 打卡时间"09:00"
    private String checkTime;
    
    public ScheduleInfo() {
    	
    }
    
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
    
}
