package com.yun9.mobile.framework.impls.hr;

import java.util.HashMap;
import java.util.Map;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.HttpFactory;
import com.yun9.mobile.framework.interfaces.hr.UserCheckinginInfo;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;

public class implUserCheckinginInfo implements UserCheckinginInfo{
	private ResourceFactory resourceFactory;
    
	public implUserCheckinginInfo() {
		super();
		
		init();
	}

	
	private void init(){
		resourceFactory = BeanConfig.getInstance().getBeanContext().get(ResourceFactory.class);
	}
	
	@Override
	public void getUserCheckinginInfo(String instid, String userid, String begindate, String enddate, AsyncHttpResponseCallback callback) {
		Map<String,Object> params = new HashMap<String, Object>();
        Map<String, Object> header = new HashMap<String, Object>();
        
        header.put("instid", instid);
        header.put("userid", userid);
        params.put("begindate", begindate);
        params.put("enddate", enddate);
        
        doGetUserCheckingInfo(params, header, callback);
	}

	private void doGetUserCheckingInfo(Map<String,Object> params,Map<String,Object> header,AsyncHttpResponseCallback callback){
		
		Resource service = resourceFactory.create("BizHrAttendanceService");
        service.setHeader(header);
        service.setParams(params);
        service.invok(callback);
	}
}
