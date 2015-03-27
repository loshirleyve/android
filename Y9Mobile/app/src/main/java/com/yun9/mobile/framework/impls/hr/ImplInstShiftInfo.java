package com.yun9.mobile.framework.impls.hr;

import java.util.HashMap;
import java.util.Map;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.interfaces.hr.InstShiftInfo;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.session.SessionManager;

public class ImplInstShiftInfo implements InstShiftInfo {
	@Override
	public void getInstShiftInfo(AsyncHttpResponseCallback callback) {
        Map<String, Object> header = new HashMap<String, Object>();
        Map<String,Object> data = new HashMap<String, Object>();
        
        SessionManager sessionManager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
        System.out.println(sessionManager);
        // 机构ID
        String  instid = sessionManager.getAuthInfo().getInstinfo().getId();
		header.put("instid", instid);
		data.put(null, null);
		doGetInstShiftInfo(header, data, callback);
	}

	private void doGetInstShiftInfo(Map<String, Object> header, Map<String, Object> data, AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance().getBeanContext().get(ResourceFactory.class);
		Resource service = resourceFactory.create("BizHrFindInstShiftService");
        service.setHeader(header);
        service.setParams(data);
        service.invok(callback);
	}

}
