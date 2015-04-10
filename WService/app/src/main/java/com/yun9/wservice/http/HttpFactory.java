package com.yun9.wservice.http;


import com.yun9.wservice.repository.Resource;

import java.io.File;

public interface HttpFactory {

	public void get(Request request, AsyncHttpResponseCallback callback);

	public void post(Resource resource, AsyncHttpResponseCallback callback);
	
	public void postSync(Resource resource, AsyncHttpResponseCallback callback);

	public void uploadFile(String userid, String instid, String floderid, String level, String filetype, String descr, File file, AsyncHttpResponseCallback callback);

	public void uploadFileSync(String userid, String instid, String floderid, String level, String filetype, String descr, File file, final AsyncHttpResponseCallback callback);
}
