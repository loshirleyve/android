package com.maoye.form.dialog;


import java.util.Random;



import com.maoye.form.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;



public class DialogProgressTip{
	Dialog dlg;
	private Activity activity;
	private View view;
	private View btnCancel;
	private View btnConfirm;
	private TextView tvTitle;
	private TextView tvContent;
	
	public DialogProgressTip(Activity activity) {
		this.activity = activity;
		init();
	}

	private void init(){
		this.view = activity.getLayoutInflater().inflate(R.layout.form_dlg_pro_tip, null);
		this.dlg = new Dialog(activity, R.style.form_alert_dialog);
		this.dlg.setContentView(this.view);
		
		findView();
		setEvent();
		
		
		setCanceledOnTouchOutside(false);
		setCancelable(false);
	}
	
	private void setCancelable(boolean isCancenlable) {
		this.dlg.setCancelable(isCancenlable);
	}
		

	private void setEvent() {
	}

	private void findView() {
		this.tvTitle = (TextView) this.view.findViewById(R.id.tvTitle);
		this.tvContent = (TextView) this.view.findViewById(R.id.tvContent);
	}
	
	public void show() {
		this.dlg.show();
	}
	
	
	public void dismiss() {
		this.dlg.dismiss();
	}
	
	private void setCanceledOnTouchOutside(boolean isCancel){
		this.dlg.setCanceledOnTouchOutside(isCancel);
	}
}
