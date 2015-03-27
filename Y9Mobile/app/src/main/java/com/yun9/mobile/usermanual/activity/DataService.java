package com.yun9.mobile.usermanual.activity;

import java.util.List;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.UserManual;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.usermanual.callback.AsyncHttpUserManualCallback;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   DataService
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-5下午5:55:12
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-5下午5:55:12  
 * 修改备注：    
 * @version     
 *     
 */
public class DataService {
	
	// 用户手册的回调函数
	public void UserManual(final AsyncHttpUserManualCallback callback) {
		   getUserManual(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<UserManual> userManuals = (List<UserManual>) response
						.getPayload();
				callback.handler(userManuals);
			}
			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}
	
	// 查询用户手册
	public void getUserManual(AsyncHttpResponseCallback callback) {
			ResourceFactory resourceFactory = BeanConfig.getInstance()
					.getBeanContext().get(ResourceFactory.class);

			Resource sysInstUserManualQueryServiceResource = resourceFactory
					.create("SysInstUserManualQueryService");
			sysInstUserManualQueryServiceResource.header("","");
			sysInstUserManualQueryServiceResource.param("","");
			try {
				sysInstUserManualQueryServiceResource.invok(callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
