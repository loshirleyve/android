package com.yun9.mobile.framework.model;
/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   UserPassword
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-4下午6:32:05
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-4下午6:32:05  
 * 修改备注：    
 * @version     
 *     
 */
public class UserPassword implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userid;
	
	private String password;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
