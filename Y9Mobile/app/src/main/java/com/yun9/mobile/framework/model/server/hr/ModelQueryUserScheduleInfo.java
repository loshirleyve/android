package com.yun9.mobile.framework.model.server.hr;

/**
 * 获取用户排班信息
 * @author Kass
 *
 */
public class ModelQueryUserScheduleInfo {
	// 用户id
	private String userid; 
	
	// 机构id
	private String instid;
	
	// 排班类型 bizday 行政班 根据机构的行政上班日期打卡上班， day 日常班 根据排班表的每日排班信息打卡上班
	private String schedulingtype;  
	
	// 上班日期
	private String workdate;  
	
	// 班次编号
	private String shiftno; 
	
	// 班次名称
	private String shiftlabel;  
	
	// 打卡类型
	private String checktype; 
	
	// 打卡名称
	private String checkname; 
	
	// 打卡时间
	private String checktime; 
	
	// 允许打卡上下波动时间 超过这个时间按迟到算
	private int checktimeoffset;
	
	// 旷工的时间 打卡迟到或早退的时间超过这个时间 则为旷工
	private int absenteeismoffset;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getInstid() {
		return instid;
	}

	public void setInstid(String instid) {
		this.instid = instid;
	}

	public String getSchedulingtype() {
		return schedulingtype;
	}

	public void setSchedulingtype(String schedulingtype) {
		this.schedulingtype = schedulingtype;
	}

	public String getWorkdate() {
		return workdate;
	}

	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}

	public String getShiftno() {
		return shiftno;
	}

	public void setShiftno(String shiftno) {
		this.shiftno = shiftno;
	}

	public String getShiftlabel() {
		return shiftlabel;
	}

	public void setShiftlabel(String shiftlabel) {
		this.shiftlabel = shiftlabel;
	}

	public String getChecktype() {
		return checktype;
	}

	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}

	public String getCheckname() {
		return checkname;
	}

	public void setCheckname(String checkname) {
		this.checkname = checkname;
	}

	public String getChecktime() {
		return checktime;
	}

	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}

	public int getChecktimeoffset() {
		return checktimeoffset;
	}

	public void setChecktimeoffset(int checktimeoffset) {
		this.checktimeoffset = checktimeoffset;
	}

	public int getAbsenteeismoffset() {
		return absenteeismoffset;
	}

	public void setAbsenteeismoffset(int absenteeismoffset) {
		this.absenteeismoffset = absenteeismoffset;
	}
	
}
