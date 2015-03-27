package com.yun9.mobile.framework.model;


/**    
 *     
 * 项目名称：biz-hr   
 * 类名称：   Attendance
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2015-1-9下午4:59:53
 * 修改人：ruanxiaoyu  
 * 修改时间：2015-1-9下午4:59:53  
 * 修改备注：    
 * @version     
 *     
 */
public class AttendanceInfo implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String no;
	private String name;
	private String label;
	private long   workdate;
	private long   checkstartdatetime;
	private String checkstartlocationlabel;
	private long   checkenddatetime;
	private String checkendlocationlabel;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public long getWorkdate() {
		return workdate;
	}
	public void setWorkdate(long workdate) {
		this.workdate = workdate;
	}
	public long getCheckstartdatetime() {
		return checkstartdatetime;
	}
	public void setCheckstartdatetime(long checkstartdatetime) {
		this.checkstartdatetime = checkstartdatetime;
	}
	public String getCheckstartlocationlabel() {
		return checkstartlocationlabel;
	}
	public void setCheckstartlocationlabel(String checkstartlocationlabel) {
		this.checkstartlocationlabel = checkstartlocationlabel;
	}
	public long getCheckenddatetime() {
		return checkenddatetime;
	}
	public void setCheckenddatetime(long checkenddatetime) {
		this.checkenddatetime = checkenddatetime;
	}
	public String getCheckendlocationlabel() {
		return checkendlocationlabel;
	}
	public void setCheckendlocationlabel(String checkendlocationlabel) {
		this.checkendlocationlabel = checkendlocationlabel;
	}
	
	
}
