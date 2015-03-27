package com.yun9.mobile.framework.model;
/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   InstUser
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-15下午2:24:58
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-15下午2:24:58  
 * 修改备注：    
 * @version     
 *     
 */
public class InstUser implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String instid;
	private String userid;
	
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

	
	
}
