package com.yun9.mobile.form.engine;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import android.content.Context;
import com.maoye.form.model.ModelPic;
import com.maoye.form.model.ModelFile.FileType;
import com.yun9.mobile.framework.factory.command.FactoryCommandNetworkServiceYiDianTong;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.command.CommandNetworkService;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.util.UtilImageCompress;

public class EngineUploadPic implements Runnable{

	private String path;
	private CountDownLatch end;
	private List<ModelPic> uploadPics;
	private CommandNetworkService command ;
	private Context context;
	/**
	 * @param path
	 * @param begin
	 * @param end
	 */
	public EngineUploadPic(String path, CountDownLatch end,Context context, List<ModelPic> uploadPics) {
		super();
		this.path = path;
		this.end = end;
		this.context = context;
		this.uploadPics = uploadPics;
		init();
	}

	
	private void init(){
		command = new FactoryCommandNetworkServiceYiDianTong().creatCommandNetworkService();
	}
	
	@Override
	public void run() {
             
             File file = UtilImageCompress.compressPicFile(path, context);
             if(file == null){
            	 countDown();    
            	 return ;
             }
             command.uploadImgFileUserLevelSync(file, new AsyncHttpResponseCallback() {
				
				@Override
				public void onSuccess(Response response) {
					
					FileInfo fileInfo = (FileInfo) response.getPayload();
					ModelPic pic = new ModelPic();
					pic.setValue(fileInfo.getId());
					pic.setEmumType(FileType.PicNetWork);
					uploadPics.add(pic);	
					
					countDown();    
				}
				
				@Override
				public void onFailure(Response response) {
					
					countDown();
				}
			});
	}
	
	private void countDown(){
		end.countDown();    //使end状态减1，最终减至0
	}
}
