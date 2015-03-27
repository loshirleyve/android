package com.maoye.form.interfaces.facade;

public interface FacadeTimeCell {
	public void getDateTime(CallBack callBack);
	
	interface CallBack{
		public void onSuccess(String value, long date);
		
	}
}
