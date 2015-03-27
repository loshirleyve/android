package com.maoye.form.dialog;

import java.util.List;

import com.maoye.form.R;
import com.maoye.form.adapter.AdapterFormDialogMulSelected;
import com.maoye.form.adapter.AdapterFormDialogRadioChoice;
import com.maoye.form.model.form.ModelMulSelected;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DialogMulSelected{
	private Dialog dlg;
	
	private Context context;
	private View view;
	private TextView form_dialog_titleText;
	private Button form_dialog_cancel;
	private View form_dialog_ok;
	private ListView lvContent;
	
	private List<ModelMulSelected> options;
	
	private AdapterFormDialogMulSelected adapter;
	private OnMulSelected onMulSelected;
	
	
	public DialogMulSelected(Context context, List<ModelMulSelected> options) {
		this.context = context;
		this.options = options;
		init();
	}

	private void init(){
		
		view = LayoutInflater.from(this.context).inflate(R.layout.form_dlg_mulselected, null);
		dlg = new Dialog(context, R.style.form_alert_dialog);
		dlg.setContentView(view);
		
		findView();
		initListView();
		setEvent();
	}

	private void setEvent() {
		
		
		form_dialog_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();				
			}
		});
		
		form_dialog_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(onMulSelected != null){
					onMulSelected.selectOptions(options);
				}
				dismiss();	
			}
		});
	}

	private void findView() {
		form_dialog_titleText = (TextView) view.findViewById(R.id.form_dialog_titleText);
		lvContent = (ListView) view.findViewById(R.id.lvContent);
		form_dialog_cancel = (Button) view.findViewById(R.id.form_dialog_cancel);
		form_dialog_ok = view.findViewById(R.id.form_dialog_ok);
	}
	
	private void initListView(){
		adapter = new AdapterFormDialogMulSelected(options, context);
		lvContent.setAdapter(adapter);
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
	
	
	public interface OnMulSelected{
		public void selectOptions(List<ModelMulSelected> options);
	}
	
	public void setOnMulSelected(OnMulSelected onMulSelected){
		this.onMulSelected = onMulSelected;
	}
	
//	public void setOnItemClickListener(final OnItemClickListener listener){
//		this.listener = listener;
//		this.lvContent.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				listener.onItemClick(parent, view, position, id);
//				dismiss();
//			}
//		});
//	}
	
}
