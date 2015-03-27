package com.yun9.mobile.framework.impls.server.sys;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.conf.PropertiesFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.interfaces.server.sys.AppCheckForUpdateService;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.util.UtilPackage;

public class ImpleAppCheckForUpdateService implements AppCheckForUpdateService {
	
	private PropertiesFactory propertiesFactory;
	private ResourceFactory resourceFactory;
	private Context context;
	private String version;
	private String os = "android";
	private String type = "app";
	private String internalversion;
	
	
	public ImpleAppCheckForUpdateService() {
		super();
		init();
	}
	
	private void init(){
		context = BeanConfig.getInstance().getBeanContext().getApplicationContext();
		resourceFactory = BeanConfig.getInstance().getBeanContext().get(ResourceFactory.class);
		propertiesFactory = BeanConfig.getInstance().getBeanContext().get(PropertiesFactory.class);
		initInputParm();
	}
	
	private void initInputParm() {
		version = UtilPackage.getAppVersion(context);
		
		internalversion = propertiesFactory.getString("app-internal-version", "utf-8");
	}

	@Override
	public void getAppUpdateInfo(AsyncHttpResponseCallback callback) {
		Map<String,Object> params = new HashMap<String, Object>();
        Map<String, Object> header = new HashMap<String, Object>();
        
		header.put(null, null);
		params.put("version", version);
		params.put("type", type);
		params.put("os", os);
		params.put("internalversion", internalversion);
		doGetAppUpdateInfo(params, header, callback);
	}

	private void doGetAppUpdateInfo(Map<String,Object> params,Map<String,Object> header,AsyncHttpResponseCallback callback){
		Resource service = resourceFactory.create("AppCheckForUpdateService");
        service.setHeader(header);
        service.setParams(params);
        service.invok(callback);
	}
}
