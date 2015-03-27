package com.maoye.form.interfaces.engine;

import java.util.List;

import com.maoye.form.model.ModelPic;

public interface EngineFileObtain{
	
	public String obtainFileMethodName();
	
	public void obtainFile(List<ModelPic> files, List<ModelPic> pics, CallBack callBack);
	
	interface CallBack{
		public void onSuccess(List<ModelPic> files, List<ModelPic> pics);
		
		public void onFailure();
	}

}
