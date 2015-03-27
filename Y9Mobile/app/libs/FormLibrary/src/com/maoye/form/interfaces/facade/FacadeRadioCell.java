package com.maoye.form.interfaces.facade;

import java.util.List;

public interface FacadeRadioCell{
	public void getRadio(List<String> radios, CallBack callBack);


	interface CallBack{
		public void onSuccess(int position);
		
		public void onFailure();
	}
}


