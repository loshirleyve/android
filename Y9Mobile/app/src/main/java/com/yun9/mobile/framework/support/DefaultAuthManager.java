package com.yun9.mobile.framework.support;

import java.util.HashMap;
import java.util.Map;

import com.yun9.mobile.framework.auth.AuthManager;
import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.AuthInfo;
import com.yun9.mobile.framework.model.Device;
import com.yun9.mobile.framework.model.DeviceRegisterInfo;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.session.SessionManager;

public class DefaultAuthManager implements AuthManager, Bean, Injection {

	private SessionManager sessionManager;

	private ResourceFactory resourceFactory;

	@Override
	public void logout() {
		sessionManager.setLogin(false);
		sessionManager.cleanLocalParams();
		sessionManager.clean();
	}

	@Override
	public void login(Map<String, Object> params,
			final AsyncHttpResponseCallback callback) {
		authenticate(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				AuthInfo authInfo = (AuthInfo) response.getPayload();
				sessionManager.setAuthInfo(authInfo);
				sessionManager.setLocationParams();
				sessionManager.setLogin(true);
				
				//TODO 注册用户设备信息
				registerDevice();
			
				callback.onSuccess(response);
			}

			@Override
			public void onFailure(Response response) {
				callback.onFailure(response);
			}
		});
	}

	private void authenticate(Map<String, Object> params,
			AsyncHttpResponseCallback callback) {
		Resource sysUserValidateResource = resourceFactory
				.create("SysUserPasswdValidate");
		sysUserValidateResource.setParams(params);
		sysUserValidateResource.invok(callback);
	}

	@Override
	public void injection(BeanContext beanContext) {
		sessionManager = beanContext.get(SessionManager.class);
		resourceFactory = beanContext.get(ResourceFactory.class);
	}

	@Override
	public Class<?> getType() {
		return AuthManager.class;
	}
	@Override
	public void registerDevice() {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> dataParms = new HashMap<String, Object>();
		
		
		Device device = sessionManager.getDevice();
		AuthInfo authInfo = sessionManager.getAuthInfo();
		
		header.put("userid", authInfo.getUserinfo().getId());
		header.put("instid", authInfo.getInstinfo().getId());
		params.put("model", device.getModel());
		params.put("serial", device.getSerial());
		dataParms.put("serialVersionUID", device.serialVersionUID);
		dataParms.put("id", device.getId());
		dataParms.put("deviceid", device.getDeviceid());
		dataParms.put("board", device.getBoard());
		dataParms.put("brand", device.getBrand());
		dataParms.put("fingerprint", device.getFingerprint());
		dataParms.put("others", device.getOthers());
		
		params.put("params", dataParms);
		doRegisterDevice(params, header, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				DeviceRegisterInfo drInfo = (DeviceRegisterInfo) response.getPayload();
				drInfo.getId();
			}
			@Override
			public void onFailure(Response response) {
				response.getCode();
			}
		});
	}
	
	private void doRegisterDevice(Map<String,Object> params,Map<String,Object> header,AsyncHttpResponseCallback callback){
		Resource service = resourceFactory.create("SysUserDeviceRegisterService");
		service.setHeader(header);
		service.setParams(params);
		service.invok(callback);
	}
}
