package com.yun9.mobile.framework.dlg.sweetAlert;


import java.util.Random;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.dlg.nifty.Effectstype;
import com.yun9.mobile.framework.dlg.nifty.effects.BaseEffects;

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



public class DlgAlertCommon{
	Dialog dlg;
	private Activity activity;
	private View view;
	private View btnCancel;
	private View btnConfirm;
	private TextView tvTitle;
	private TextView tvContent;
	
	public DlgAlertCommon(Activity activity) {
		this.activity = activity;
		init();
	}

	private void init(){
		this.view = activity.getLayoutInflater().inflate(R.layout.dlg_common, null);
		this.dlg = new Dialog(activity, R.style.alert_dialog);
		this.dlg.setContentView(this.view);
	
		findView();
		setEvent();
	}

	private void setEvent() {
		this.btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DlgAlertCommon.this.dlg.dismiss();
			}
		});
		
		this.btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DlgAlertCommon.this.dlg.dismiss();
			}
		});
		
		this.dlg.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				startEffectstype(randEffect());
			}
		});
	}

	private void findView() {
		this.btnCancel = this.view.findViewById(R.id.btnCancel);
		this.btnConfirm = this.view.findViewById(R.id.btnConfirm);
		this.tvTitle = (TextView) this.view.findViewById(R.id.tvTitle);
		this.tvContent = (TextView) this.view.findViewById(R.id.tvContent);
	}
	
	public void setCancelOnClick(OnClickListener onClick){
		this.btnCancel.setOnClickListener(onClick);
	}
	public void setConfirmOnClick(OnClickListener onClick){
		this.btnConfirm.setOnClickListener(onClick);
	}

	public void show() {
		this.dlg.show();
	}
	
	
	private void startEffectstype(Effectstype type){
	        BaseEffects animator = type.getAnimator();
	        animator.start(this.view);
	}
	
	private Effectstype randEffect(){
		Effectstype effect;
		int max=14;
		int min=1;
		Random random = new Random();
		int rand = random.nextInt(max)%(max-min+1) + min;
		switch(rand){
			case 1:
				effect=Effectstype.Fadein;break;
	        case 2:
	        	effect=Effectstype.Slideright;break;
	        case 3:
	        	effect=Effectstype.Slideleft;break;
	        case 4:
	        	effect=Effectstype.Slidetop;break;
	        case 5:
	        	effect=Effectstype.SlideBottom;break;
	        case 6:
	        	effect=Effectstype.Newspager;break;
	        case 7:
	        	effect=Effectstype.Fall;break;
	        case 8:
	        	effect=Effectstype.Sidefill;break;
	        case 9:
	        	effect=Effectstype.Fliph;break;
	        case 10:
	        	effect=Effectstype.Flipv;break;
	        case 11:
	        	effect=Effectstype.RotateBottom;break;
	        case 12:
	        	effect=Effectstype.RotateLeft;break;
	        case 13:
	        	effect=Effectstype.Slit;break;
	        case 14:
	        	effect=Effectstype.Shake;break;
	        default:
	        	effect=Effectstype.Fadein;break;
		}
		return effect;
	}
	
	public void dismiss() {
		this.dlg.dismiss();
	}
	
	
	public void setTitle(String title){
		this.tvTitle.setText(title);
	}
	public void setContent(String content){
		this.tvContent.setText(content);
	}
	
	
}
