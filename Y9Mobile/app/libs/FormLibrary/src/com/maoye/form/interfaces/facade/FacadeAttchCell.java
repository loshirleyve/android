package com.maoye.form.interfaces.facade;

import java.util.List;

import com.maoye.form.model.ModelPic;

public interface FacadeAttchCell {
	
	public void obtainFile(List<ModelPic> files, List<ModelPic> pics, CallBack callBack);
	
	interface CallBack{
		public void onSuccess(List<ModelPic> files, List<ModelPic> pics);
		
		public void onFailure();
	}
}
