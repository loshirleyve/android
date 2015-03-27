package com.maoye.form.impls.engine;

import java.util.List;

import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;

import com.maoye.form.dialog.DialogRadio;
import com.maoye.form.interfaces.engine.EngineRadioDialog;

public class ImplEngineRadioDialog implements EngineRadioDialog{

	protected Context context;
	protected DialogRadio dialog;
	protected List<String> radios;

	protected OnItemClickListener onItemClickListener;
	/**
	 * @param context
	 * @param list
	 */
	public ImplEngineRadioDialog(Context context, List<String> radios) {
		super();
		this.context = context;
		this.radios = radios;
		init();
	}

	private void init() {
		initDialog();
	}
	
	private void initDialog(){
		this.dialog = new DialogRadio(context, radios);
		this.dialog.setTitleText("请选择");
	}
	
	public void setTitle(String text){
		this.dialog.setTitleText(text);
	}
	
	
	@Override
	public void getChoiceItem(OnItemClickListener onItemClickListener) {
		this.dialog.setOnItemClickListener(onItemClickListener);
		this.dialog.show();
	}

}
