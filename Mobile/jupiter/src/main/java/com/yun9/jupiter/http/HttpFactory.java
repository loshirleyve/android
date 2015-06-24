package com.yun9.jupiter.http;


import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.repository.Resource;

import java.io.File;
import java.io.InputStream;

public interface HttpFactory {

	public void get(Request request, AsyncHttpResponseCallback callback);

	public void post(Resource resource, AsyncHttpResponseCallback callback);
	
	public void postSync(Resource resource, AsyncHttpResponseCallback callback);

	public void uploadFile(String userid, String instid, String floderid, String level, String filetype, String descr, File file, AsyncHttpResponseCallback callback);

	public void uploadFile(String userid, String instid, String floderid, String level, String filetype, String descr, InputStream file, AsyncHttpResponseCallback callback);

	public void uploadFile(FileBean fileBean,InputStream fileIo,AsyncHttpResponseCallback callback);

	//public void uploadFileSync(String userid, String instid, String floderid, String level, String filetype, String descr, File file, final AsyncHttpResponseCallback callback);
}
