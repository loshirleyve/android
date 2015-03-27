package com.yun9.mobile.form.view;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.baidu.android.bbalbs.common.a.c;
import com.maoye.form.interfaces.AdapterShowImage;
import com.maoye.form.interfaces.facade.FacadeAttchCell;
import com.maoye.form.model.ModelFile;
import com.maoye.form.model.ModelPic;
import com.maoye.form.model.ModelFile.FileType;
import com.maoye.form.model.form.ModelAttchmentPic;
import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.utils.UtilCommon;
import com.maoye.form.utils.UtilDebugLog;
import com.maoye.form.view.form.ViewFormAttchmentCell;
import com.yun9.mobile.camera.activity.AlbumActivity;
import com.yun9.mobile.framework.factory.command.FactoryCommandNetworkServiceYiDianTong;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.command.CommandNetworkService;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.util.UtilImageCompress;

public class ViewFormAttchmentCellYiDianTong extends ViewFormAttchmentCell {
	

	private List<ModelPic> uploadPics = new ArrayList<ModelPic>();
	
	public ViewFormAttchmentCellYiDianTong(Context context, ModelViewFormAttchmentCell model, FacadeAttchCell facade, AdapterShowImage imageLoader) {
		super(context, model, facade, imageLoader);
	}
	
	@Override
	public void formCommit() {
		doFormCommit();
	}
	

	public void doFormCommit(){
	
		uploadAttchmentFile();
		
		engineSetPic();
		
		notifyFormCommitResult(true);
	}
	
	private void uploadAttchmentFile(){
		if(listModelAttchmentPic != null && listModelAttchmentPic.size() > 0){
			CountDownLatch end;
			int uploadPicNum = getLocalPicNum(listModelAttchmentPic);
			if (uploadPicNum < 1) {
				return;
			}
			end  = new CountDownLatch(uploadPicNum);
			ExecutorService pool = Executors.newFixedThreadPool(uploadPicNum);
			for( ModelPic localPic : listModelAttchmentPic){
				if(localPic.getEmumType() == FileType.PicLocal){
					pool.execute(new UploadPic(localPic.getPath(), end, getContext()));
				}
			}
			
			try {
				end.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void engineSetPic() {
		
		List<ModelAttchmentPic> pics = new ArrayList<ModelAttchmentPic>();
		
		if(listModelAttchmentPic != null && listModelAttchmentPic.size() > 0){
			for(ModelPic file : listModelAttchmentPic){
				if(file.getEmumType() == FileType.PicNetWork){
					ModelAttchmentPic pic = new ModelAttchmentPic();
					pic.setValue(file.getValue());
					pics.add(pic);
				}
			}
		}
		if(uploadPics.size() > 0){
			for(ModelPic file : uploadPics){
				if(file.getEmumType() == FileType.PicNetWork){
					ModelAttchmentPic pic = new ModelAttchmentPic();
					pic.setValue(file.getValue());
					pics.add(pic);
				}
			}
		}
		model.setPics(pics);		
	}



	private class UploadPic implements Runnable{
		
		private String path;
		private CountDownLatch end;
		
		private CommandNetworkService command ;
		private Context context;
		/**
		 * @param path
		 * @param begin
		 * @param end
		 */
		public UploadPic(String path, CountDownLatch end,Context context) {
			super();
			this.path = path;
			this.end = end;
			this.context = context;
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
	
	
	private int getLocalPicNum(List<? extends ModelFile> modelFiles){
		int num = 0;
		if(modelFiles != null && modelFiles.size() > 0){
			for(ModelFile file : modelFiles){
				if(file.getEmumType() == FileType.PicLocal){
					num++;
				}
			}
		}
		
		return num;
	}
	
	
	
}
