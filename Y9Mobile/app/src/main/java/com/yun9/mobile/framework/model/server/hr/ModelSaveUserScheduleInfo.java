package com.yun9.mobile.framework.model.server.hr;

/**
 * 新增/修改用户排班信息
 * @author Kass
 *
 */
public class ModelSaveUserScheduleInfo {
	// id
	private String id;
	
	// 机构id
	private String instid;   
	
	// 用户id
	private String userid; 
	
	// 排班类型
	private String type;   
	
	// 上班日期
	private long workdate; 
	
	// 班次编号
	private String shiftno;
	
	private String createby;
	
	private String updateby;
	
	private long createdate;
	
	private long updatedate;
	
	private int disabled;
	
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstid() {
		return instid;
	}

	public void setInstid(String instid) {
		this.instid = instid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getWorkdate() {
		return workdate;
	}

	public void setWorkdate(long workdate) {
		this.workdate = workdate;
	}

	public String getShiftno() {
		return shiftno;
	}

	public void setShiftno(String shiftno) {
		this.shiftno = shiftno;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public long getCreatedate() {
		return createdate;
	}

	public void setCreatedate(long createdate) {
		this.createdate = createdate;
	}

	public long getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(long updatedate) {
		this.updatedate = updatedate;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
