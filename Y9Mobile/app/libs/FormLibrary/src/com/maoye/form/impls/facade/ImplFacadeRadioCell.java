package com.maoye.form.impls.facade;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.maoye.form.dialog.DialogRadio;
import com.maoye.form.factory.engine.FactoryEngineDialogDefault;
import com.maoye.form.interfaces.engine.EngineRadioDialog;
import com.maoye.form.interfaces.facade.FacadeRadioCell;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.cell.ModelViewFormRadioCell;
import com.maoye.form.model.form.cell.ModelViewFormTextCell;

public class ImplFacadeRadioCell implements FacadeRadioCell{
	protected DialogRadio dialog;
	protected Context context;
	protected CallBack callBack;
	protected List<String> radios;
	public ImplFacadeRadioCell(Context context){
		this.context = context;
	}
	

	@Override
	public void getRadio(List<String> radios, CallBack callBack) {
		this.callBack = callBack;
		this.radios = radios;
		EngineRadioDialog engineRadioDialog = new FactoryEngineDialogDefault().creatEngineRadioDialog(context, radios);
		engineRadioDialog.getChoiceItem(onItemClickListener);
	}
	
	
	protected OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			callBack.onSuccess(position);
		}
	};
	
	

	
}
