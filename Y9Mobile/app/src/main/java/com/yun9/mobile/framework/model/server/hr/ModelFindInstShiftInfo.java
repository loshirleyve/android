package com.yun9.mobile.framework.model.server.hr;

/**
 * 获取机构班次信息
 * @author Kass
 *
 */
public class ModelFindInstShiftInfo {
	//班次类型
	private String schedulingtype;  
	
	//打卡时间
	private String checktime; 
	
	//班次名称
	private String label; 
	
	// 班次编号
	private String shiftno ;

	public String getSchedulingtype() {
		return schedulingtype;
	}

	public void setSchedulingtype(String schedulingtype) {
		this.schedulingtype = schedulingtype;
	}

	public String getChecktime() {
		return checktime;
	}

	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getShiftno() {
		return shiftno;
	}

	public void setShiftno(String shiftno) {
		this.shiftno = shiftno;
	} 
	
}
