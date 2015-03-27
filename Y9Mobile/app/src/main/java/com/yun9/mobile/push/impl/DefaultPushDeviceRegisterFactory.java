package com.yun9.mobile.push.impl;

import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.push.PushDeviceRegisterFactory;

public class DefaultPushDeviceRegisterFactory implements
		PushDeviceRegisterFactory, Bean{
	
	/** TAG to Log */
	public static final String TAG = DefaultPushDeviceRegisterFactory.class.getSimpleName();

	@Override
	public void bind(String appid, String userId, String channelId,
			String requestId,String pushType) {
		Resource resource = ResourceUtil.get("SysUserPushSaveService");
		resource.param("bindchannelid", channelId);
		resource.param("binduserid", userId);
		resource.param("pushtype", pushType);
		resource.param("requestid", requestId);
		resource.param("devicetype", "android");
		resource.invok(emptyCallback);
	}


	@Override
	public void unBind(String binduserid) {
		Resource resource = ResourceUtil.get("SysUserPushUpdateStateService");
		resource.param("binduserid", binduserid);
		resource.param("state", "unbind");
		resource.invok(emptyCallback);
	}

	@Override
	public Class<?> getType() {
		return PushDeviceRegisterFactory.class;
	}

	
	private AsyncHttpResponseCallback emptyCallback = new AsyncHttpResponseCallback() {
		
		@Override
		public void onSuccess(Response response) {
			//Log.d(TAG, response.getData());
		}
		
		@Override
		public void onFailure(Response response) {
			//Log.d(TAG, response.getData());
		}
	};
}
