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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;



public class DialogNormal{
	Dialog dlg;
	private Context context;
	private View view;
	private View cancel;
	private View ok;
	private TextView tvTitle;
	private TextView tvContent;
	
	public DialogNormal(Context context) {
		this.context = context;
		init();
	}

	private void init(){
		this.view = LayoutInflater.from(context).inflate(R.layout.form_dlg_normal, null);
		this.dlg = new Dialog(context, R.style.form_alert_dialog);
		this.dlg.setContentView(this.view);
		
		findView();
		setEvent();
		
		
//		setCanceledOnTouchOutside(false);
//		setCancelable(false);
	}
	
	public void setCancelable(boolean isCancenlable) {
		this.dlg.setCancelable(isCancenlable);
	}
		

	private void setEvent() {
		this.ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();				
			}
		});
		
		this.cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();					
			}
		});
	
	
	}

	private void findView() {
		this.tvTitle = (TextView) this.view.findViewById(R.id.tvTitle);
		this.tvContent = (TextView) this.view.findViewById(R.id.tvContent);
		this.cancel = this.view.findViewById(R.id.cancel);
		this.ok = this.view.findViewById(R.id.ok);
	}
	
	public void show() {
		this.dlg.show();
	}
	
	
	public void dismiss() {
		this.dlg.dismiss();
	}
	
	public void setCanceledOnTouchOutside(boolean isCancel){
		this.dlg.setCanceledOnTouchOutside(isCancel);
	}
	
	public void setTitle(String text){
		this.tvTitle.setText(text);
	}
	
	public void setContent(String text){
		this.tvContent.setText(text);
	}
	
	
	public void setOnClickListenerOK(OnClickListener listener){
		this.ok.setOnClickListener(listener);
	}
	
	public void setOnClickListenerCancel(OnClickListener listener){
		this.cancel.setOnClickListener(listener);
	}
}
