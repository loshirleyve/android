package com.yun9.mobile.framework.model;
/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   UserContact
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-11-21上午10:21:28
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-11-21上午10:21:28  
 * 修改备注：    
 * @version     
 *     
 */
public class UserContact implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String userid;
	private String contactkey; /*weixin,email*/
	private String contactvalue;
	private long createdata;
	private String createby;
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getContactkey() {
		return contactkey;
	}
	public void setContactkey(String contactkey) {
		this.contactkey = contactkey;
	}
	public String getContactvalue() {
		return contactvalue;
	}
	public void setContactvalue(String contactvalue) {
		this.contactvalue = contactvalue;
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
