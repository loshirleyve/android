package com.yun9.mobile.framework.personelservice;

import java.util.List;
import java.util.Map;

import com.yun9.mobile.department.callback.AsyncHttpUserCallback;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.AttendanceInfo;
import com.yun9.mobile.framework.model.ModelUserCheckinginInfo;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.util.AssertValue;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   BizHrAttendanceService
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2015-1-8下午3:53:04
 * 修改人：ruanxiaoyu  
 * 修改时间：2015-1-8下午3:53:04  
 * 修改备注：    
 * @version     
 *     
 */
public class BizHrAttendanceService {


	public void getSingListCallBack(Map<String, Object> params,
			final AsyncHttpAttendanceInfoCallback callback) {
		getSingList(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<AttendanceInfo> attendances = (List<AttendanceInfo>) response.getPayload();
				callback.handler(attendances);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}
	public void getSingList(Map<String, Object> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource getInstAttendanceQueryServiceResource = resourceFactory
				.create("GetInstAttendanceQueryService");
		getInstAttendanceQueryServiceResource.param("","");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				getInstAttendanceQueryServiceResource.param(key, params.get(key));
			}
		}
		try {
			getInstAttendanceQueryServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void getSinghistoryList(Map<String, Object> params,
			final AsyncHttpAttendanceCallback callback) {
		getSingList(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<ModelUserCheckinginInfo> attendances = (List<ModelUserCheckinginInfo>) response.getPayload();
				callback.handler(attendances);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}
	public void getSinghistoryList(Map<String, Object> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource getUserInfoByInstServiceResource = resourceFactory
				.create("BizHrAttendanceService");
		DataInfoService data=new DataInfoService();
		String instid = data.getUserInst().getId();
		getUserInfoByInstServiceResource.header("instid", instid);
		getUserInfoByInstServiceResource.param("","");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				getUserInfoByInstServiceResource.param(key, params.get(key));
			}
		}
		try {
			getUserInfoByInstServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getUsernameCallBack(String userid,
			final AsyncHttpUserCallback callback) {
		getUsername(userid, new AsyncHttpResponseCallback() {
			
			@Override
			public void onSuccess(Response response) {
				List<User>	users = (List<User>) response.getPayload();
				callback.handler(users);
			}
			
			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}
	
	
	public void getUsername(String userid,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource sysUserQueryResource = resourceFactory
				.create("SysUserQuery");
		sysUserQueryResource.param("userid",userid);
		try {
			sysUserQueryResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
