package com.maoye.form.interfaces.facade;

import java.util.List;

import com.maoye.form.model.form.ModelMulSelected;

public interface FacadeMulSelectedCell{

	public void selectOptions(List<ModelMulSelected> options, CallBack callBack);
	
	
	interface CallBack{
		public void onSuccess(List<ModelMulSelected> options);
		
		public void onFailure();
	}
}


