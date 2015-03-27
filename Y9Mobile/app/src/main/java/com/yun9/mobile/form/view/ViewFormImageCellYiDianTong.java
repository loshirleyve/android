package com.yun9.mobile.form.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;

import com.maoye.form.interfaces.AdapterShowImage;
import com.maoye.form.interfaces.facade.FacadeAttchCell;
import com.maoye.form.model.ModelFile;
import com.maoye.form.model.ModelPic;
import com.maoye.form.model.ModelFile.FileType;
import com.maoye.form.model.form.ModelAttchmentPic;
import com.maoye.form.model.form.cell.ModelViewFormImageCell;
import com.maoye.form.view.form.ViewFormImageCell;
import com.yun9.mobile.form.engine.EngineUploadPic;
import com.yun9.mobile.framework.factory.command.FactoryCommandNetworkServiceYiDianTong;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.command.CommandNetworkService;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.util.UtilImageCompress;


public class ViewFormImageCellYiDianTong extends ViewFormImageCell {


	private List<ModelPic> uploadPics = new ArrayList<ModelPic>();
	
	public ViewFormImageCellYiDianTong(Context context, ModelViewFormImageCell model, FacadeAttchCell facade, AdapterShowImage imageLoade) {
		super(context, model, facade, imageLoade);
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
					pool.execute(new EngineUploadPic(localPic.getPath(), end, getContext(), uploadPics));
				}
			}
			
			try {
				end.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
}
