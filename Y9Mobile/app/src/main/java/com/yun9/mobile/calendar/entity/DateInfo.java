package com.yun9.mobile.calendar.entity;

/**
 * 日历信息
 * Created by Kass on 2014/12/8.
 */
public class DateInfo {

    private int date;  // 阳历日期
    private String lunarDate;   // 农历日期
    private boolean isThisMonth;    // 是否这个月
    private boolean isWeekend;  // 是否周末
    private boolean isHoliday;  // 是否节假日

    public DateInfo() {
    	
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getLunarDate() {
        return lunarDate;
    }

    public void setLunarDate(String lunarDate) {
        this.lunarDate = lunarDate;
    }

    public boolean isThisMonth() {
        return isThisMonth;
    }

    public void setThisMonth(boolean isThisMonth) {
        this.isThisMonth = isThisMonth;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public void setWeekend(boolean isWeekend) {
        this.isWeekend = isWeekend;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

}
