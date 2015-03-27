package com.maoye.form.impls.facade;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.maoye.form.dialog.DialogRadio;
import com.maoye.form.factory.engine.FactoryEngineDialogDefault;
import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.maoye.form.interfaces.engine.EngineRadioDialog;
import com.maoye.form.interfaces.facade.FacadeAttchCell;
import com.maoye.form.model.ModelPic;

public class ImplFacadeAttchCell implements FacadeAttchCell{

	protected DialogRadio dialog;
	protected CallBack callBack;
	protected Context context;
	protected List<String> radios;
	
	protected List<EngineFileObtain> listEngineFileObtain;
	
	protected List<ModelPic> files;
	protected List<ModelPic> pics;
	
	/**
	 * @param context
	 */
	public ImplFacadeAttchCell(Context context, List<EngineFileObtain> listEngineFileObtain) {
		super();
		this.context = context;
		this.listEngineFileObtain = listEngineFileObtain;
		
		init();
	}
	
	private void init(){
		radios = new ArrayList<String>();
		for(EngineFileObtain engineFileObtain : listEngineFileObtain){
			radios.add(engineFileObtain.obtainFileMethodName());
		}
	}
	

	@Override
	public void obtainFile(List<ModelPic> files, List<ModelPic> pics, CallBack callBack) {
		this.callBack = callBack;
		this.files = files;
		this.pics = pics;
		EngineRadioDialog engineRadioDialog = new FactoryEngineDialogDefault().creatEngineRadioDialog(context, radios);
		engineRadioDialog.getChoiceItem(onItemClickListener);
	}

	protected OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			
			EngineFileObtain fileObtain = listEngineFileObtain.get(position);
			fileObtain.obtainFile(files, pics, new EngineFileObtain.CallBack() {
				
				@Override
				public void onSuccess(List<ModelPic> files, List<ModelPic> pics) {
					callBack.onSuccess(files, pics);
				}
				
				@Override
				public void onFailure() {
					
				}
			});
			
		}
	};
}
