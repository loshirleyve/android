package com.yun9.mobile.framework.model;


/**    
 *     
 * 项目名称：yun9mobile   
 * 类名称：   Ent
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-11-5下午5:19:53
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-11-5下午5:19:53  
 * 修改备注：    
 * @version     
 *     
 */
public class Ent implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String no;
	private String name;
	private String fullname;
	private String property;
	private long   establishdate;
	private String regcode;
	private String regaddr;
	private String businessscope;
	private String category;
    private long createdata;
	private String createby;
	private String remark;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public long getEstablishdate() {
		return establishdate;
	}
	public void setEstablishdate(long establishdate) {
		this.establishdate = establishdate;
	}
	public String getRegcode() {
		return regcode;
	}
	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}
	public String getRegaddr() {
		return regaddr;
	}
	public void setRegaddr(String regaddr) {
		this.regaddr = regaddr;
	}
	public String getBusinessscope() {
		return businessscope;
	}
	public void setBusinessscope(String businessscope) {
		this.businessscope = businessscope;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public long getCreatedata() {
		return createdata;
	}
	public void setCreatedata(long createdata) {
		this.createdata = createdata;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
