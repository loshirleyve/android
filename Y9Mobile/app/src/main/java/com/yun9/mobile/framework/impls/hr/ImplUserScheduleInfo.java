package com.yun9.mobile.framework.impls.hr;

import java.util.HashMap;
import java.util.Map;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.interfaces.hr.UserScheduleInfo;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.session.SessionManager;

public class ImplUserScheduleInfo implements UserScheduleInfo {
	@Override
	public void getUserScheduleInfo(long begindate, long enddate, AsyncHttpResponseCallback callback) {
		String name = "BizHrQueryUserSchedulingService";
        Map<String,Object> data = new HashMap<String, Object>();
        
		data.put("begindate", begindate);
		data.put("enddate", enddate);
		
        doUserScheduleInfo(name, data, callback);
	}
	
	@Override
	public void saveUserScheduleInfo(long workdate, String type, String shiftno, AsyncHttpResponseCallback callback) {
		String name = "BizHrSchedulingSaveService";
		Map<String,Object> data = new HashMap<String, Object>();
        
		data.put("workdate", workdate);
		data.put("type", type);
		data.put("shiftno", shiftno);
		
        doUserScheduleInfo(name, data, callback);
	}
	
	private void doUserScheduleInfo(String name, Map<String,Object> data, AsyncHttpResponseCallback callback) {	
		Map<String, Object> header = new HashMap<String, Object>();
		SessionManager sessionManager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
        System.out.println(sessionManager);
        // 机构ID
        String  instid = sessionManager.getAuthInfo().getInstinfo().getId();
        // 用户ID
        String  userid = sessionManager.getAuthInfo().getUserinfo().getId();
		header.put("instid", instid);
		header.put("userid", userid);
		
		ResourceFactory resourceFactory = BeanConfig.getInstance().getBeanContext().get(ResourceFactory.class);
		Resource service = resourceFactory.create(name);
        service.setHeader(header);
        service.setParams(data);
        service.invok(callback);
	}
	
}
