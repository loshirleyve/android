package com.maoye.form.impls.engine;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface.OnMultiChoiceClickListener;

import com.maoye.form.dialog.DialogMulSelected;
import com.maoye.form.dialog.DialogRadio;
import com.maoye.form.interfaces.engine.EngineMulSelectedDialog;
import com.maoye.form.model.form.ModelMulSelected;

public class ImplEngineMulSelectedDialog implements EngineMulSelectedDialog{

	private Context context;
	private DialogMulSelected dialog;
	private List<ModelMulSelected> options;
	
	/**
	 * @param context
	 */
	public ImplEngineMulSelectedDialog(Context context, List<ModelMulSelected> options) {
		super();
		this.context = context;
		this.options = options;
		init();
	}
	
	private void init() {
		initDialog();
	}
	
	private void initDialog(){
		dialog = new DialogMulSelected(context, options);
		dialog.setTitleText("请选择");
	}
	

	@Override
	public void selectedOptions() {
		
		dialog.show();
	}

}
