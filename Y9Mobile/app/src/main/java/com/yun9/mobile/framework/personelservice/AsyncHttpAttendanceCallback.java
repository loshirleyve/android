package com.yun9.mobile.framework.personelservice;

import java.util.List;

import com.yun9.mobile.framework.model.ModelUserCheckinginInfo;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   AsyncHttpAttendanceCallback
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2015-1-8下午3:56:43
 * 修改人：ruanxiaoyu  
 * 修改时间：2015-1-8下午3:56:43  
 * 修改备注：    
 * @version     
 *     
 */
public interface AsyncHttpAttendanceCallback {

	public void handler(List<ModelUserCheckinginInfo> attendances);
}
