package com.maoye.form.impls.facade;

import java.text.SimpleDateFormat;

import android.app.AlertDialog;
import android.content.Context;

import com.maoye.form.dialog.DialogDateTimePicker;
import com.maoye.form.dialog.DialogDateTimePicker.OnDateTimeSetListener;
import com.maoye.form.interfaces.facade.FacadeTimeCell;
import com.maoye.form.utils.UtilTime;

public class ImplFacadeTimeCell implements FacadeTimeCell{

	
	protected CallBack callBack;
	private DialogDateTimePicker dialog;
	protected Context context;
	public ImplFacadeTimeCell(Context context){
		this.context = context;
//		init();
	}
	
	private void init() {
		initDialog();
	}
	
	private void initDialog(){
		this.dialog = new DialogDateTimePicker(context);
		this.dialog.setOnDateTimeSetListener(onDateTimeSetListener);
	}
	
	private OnDateTimeSetListener onDateTimeSetListener = new OnDateTimeSetListener() {
		@Override
		public void OnDateTimeSet(AlertDialog dialog, long date) {
			String value = UtilTime.getStringDate(date);
			callBack.onSuccess(value, date);
			dialog.dismiss();
		}
	};
	
	@Override
	public void getDateTime(CallBack callBack) {
		initDialog();
		this.callBack = callBack;
		this.dialog.show();
	}
}
