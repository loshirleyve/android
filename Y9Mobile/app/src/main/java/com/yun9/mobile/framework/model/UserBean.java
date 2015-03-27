package com.yun9.mobile.framework.model;

import java.util.List;


/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   UserBean
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-25下午4:14:51
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-25下午4:14:51  
 * 修改备注：    
 * @version     
 *     
 */
public class UserBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private List<UserContact> usercontacts;
	private Org org;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<UserContact> getUsercontacts() {
		return usercontacts;
	}
	public void setUsercontacts(List<UserContact> usercontacts) {
		this.usercontacts = usercontacts;
	}
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	
	
}
