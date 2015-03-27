package com.maoye.form.dialog;

import java.util.List;

import com.maoye.form.R;
import com.maoye.form.adapter.AdapterFormDialogRadioChoice;
import com.maoye.form.utils.UtilDeviceInfo;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DialogRadio{
	private Dialog dlg;
	
	private Context context;
	private View view;
	private TextView form_dialog_titleText;
	private Button form_dialog_cancel;
	private ListView lvContent;
	
	private List<String> texts;
	
	private AdapterFormDialogRadioChoice adapter;
	private OnItemClickListener listener;
	
	public DialogRadio(Context context, List<String> texts) {
		this.context = context;
		this.texts = texts;
		init();
	}

	private void init(){
		this.view = LayoutInflater.from(this.context).inflate(R.layout.form_dlg_radio, null);
		this.dlg = new Dialog(this.context, R.style.form_alert_dialog);
		this.dlg.setContentView(this.view);
		
		findView();
		initListView();
		setEvent();
	}

	private void setEvent() {
		
		this.lvContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismiss();
			}
		});
		
		this.form_dialog_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();				
			}
		});
		
//		dlg.setOnShowListener(new OnShowListener() {
//			
//			@Override
//			public void onShow(DialogInterface dialog) {
//				Window w = dlg.getWindow();
//				View view = w.getDecorView();
//				int height = view.getHeight();
//				int showHeight = UtilDeviceInfo.getDeviceHeightPixels(context)/2;
//				if(height >= showHeight){
//					view.setLayoutParams(new LayoutParams(view.getWidth(), showHeight));
//				}
//			}
//		});
	}

	private void findView() {
		this.form_dialog_titleText = (TextView) this.view.findViewById(R.id.form_dialog_titleText);
		this.lvContent = (ListView) this.view.findViewById(R.id.lvContent);
		this.form_dialog_cancel = (Button) this.view.findViewById(R.id.form_dialog_cancel);
	}
	
	private void initListView(){
		this.adapter = new AdapterFormDialogRadioChoice(this.texts, this.context);
		this.lvContent.setAdapter(this.adapter);
	}
	
	/**
	 * 设置标题内容
	 * @param text
	 */
	public void setTitleText(String text){
		this.form_dialog_titleText.setText(text);
	}
	
	public void show() {
		
		this.dlg.show();
	}
	
	
	public void dismiss() {
		this.dlg.dismiss();
	}
	
	public void setOnItemClickListener(final OnItemClickListener listener){
		this.listener = listener;
		this.lvContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listener.onItemClick(parent, view, position, id);
				dismiss();
			}
		});
	}
}
