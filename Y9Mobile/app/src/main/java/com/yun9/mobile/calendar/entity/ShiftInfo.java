package com.yun9.mobile.calendar.entity;

import java.io.Serializable;

/**
 * 班次信息
 * @author Kass
 *
 */
@SuppressWarnings("serial")
public class ShiftInfo implements Serializable {
	// 日期 "yyyy-MM-dd"
    private String date;
    // 班次名称
    private String label;
    // 上班时间"09:00-18:00"
    private String workTime;
    
    public ShiftInfo() {
    	
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

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
    
}
