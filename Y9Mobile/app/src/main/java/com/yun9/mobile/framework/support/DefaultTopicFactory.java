package com.yun9.mobile.framework.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.HttpFactory;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.Topic;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.topic.TopicFactory;
import com.yun9.mobile.framework.util.AssertValue;

public class DefaultTopicFactory implements TopicFactory, Bean, Injection{
    private ResourceFactory resourceFactory;
    private HttpFactory httpFactory;
	@Override
	public void injection(BeanContext beanContext) {
		resourceFactory = beanContext.get(ResourceFactory.class);
		httpFactory = beanContext.get(HttpFactory.class);		
	}

	@Override
	public Class<?> getType() {
		return TopicFactory.class;
	}

	@Override
	public void getTopics(String instid, AsyncHttpResponseCallback callback) {
		Map<String, Object> header = new HashMap<String, Object>();
        Map<String,Object> data = new HashMap<String, Object>();
        
        header.put("instid", instid);
        data.put(null, null);
        doGetTopics(header, data, callback);
	}

	private void doGetTopics(Map<String,Object> header, Map<String,Object> data, AsyncHttpResponseCallback callback){
		
		Resource service = resourceFactory.create("SysMsgTopicQueryService");
		service.setHeader(header);
		service.setParams(data);
		service.invok(callback);
	}

	@Override
	public void getTopics(String instid, String userid, String name,
			AsyncHttpResponseCallback callback) {
		
		Map<String, Object> header = new HashMap<String, Object>();
        Map<String,Object> data = new HashMap<String, Object>();
        
        header.put("instid", instid);
        header.put("userid", userid);
        data.put("name", name);
        data.put("type", "type");
        doGetTopics(header, data, callback);
	}
}
