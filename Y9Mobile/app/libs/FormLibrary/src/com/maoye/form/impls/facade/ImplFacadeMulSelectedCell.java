package com.maoye.form.impls.facade;

import java.util.List;

import android.content.Context;

import com.maoye.form.dialog.DialogMulSelected;
import com.maoye.form.dialog.DialogMulSelected.OnMulSelected;
import com.maoye.form.dialog.DialogRadio;
import com.maoye.form.factory.engine.FactoryEngineDialogDefault;
import com.maoye.form.interfaces.engine.EngineMulSelectedDialog;
import com.maoye.form.interfaces.engine.EngineRadioDialog;
import com.maoye.form.interfaces.facade.FacadeMulSelectedCell;
import com.maoye.form.model.form.ModelMulSelected;

public class ImplFacadeMulSelectedCell implements FacadeMulSelectedCell{

	private Context context;
	
	/**
	 * @param context
	 */
	public ImplFacadeMulSelectedCell(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void selectOptions(List<ModelMulSelected> options, final CallBack callBack) {
		
		DialogMulSelected dialog = new DialogMulSelected(context, options);
		dialog.setTitleText("请选择");
		dialog.setOnMulSelected(new OnMulSelected() {
			
			@Override
			public void selectOptions(List<ModelMulSelected> options) {
				callBack.onSuccess(options);
			}
		});
		dialog.show();
		
	}

}
