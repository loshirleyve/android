package com.yun9.mobile.department.support;

import java.util.Map;

import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;


/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   SelectContactUser
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-11-21下午5:09:33
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-11-21下午5:09:33  
 * 修改备注：    
 * @version     
 *     
 */
public interface SelectContactUser {
	
	public void selectContactUser(MsgScopeCallBack  callBack,int selectUserOrOrg);

	public void selectContactUser(MsgScopeCallBack callBack,Map<String, User> users, Map<String, Org> orgs,int selectUserOrOrg);
}
