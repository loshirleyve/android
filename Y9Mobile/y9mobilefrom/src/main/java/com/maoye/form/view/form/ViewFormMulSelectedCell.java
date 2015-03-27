package com.maoye.form.view.form;

import java.util.ArrayList;
import java.util.List;

import com.maoye.form.interfaces.facade.FacadeMulSelectedCell;
import com.maoye.form.interfaces.facade.FacadeMulSelectedCell.CallBack;
import com.maoye.form.model.form.ModelMulSelected;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.cell.ModelViewFormMulSelectedCell;

import android.content.Context;
import android.view.View;


public class ViewFormMulSelectedCell extends ViewFormTextCell{
	
	private ModelViewFormMulSelectedCell model;
	
	private static final String tag = ViewFormMulSelectedCell.class.getSimpleName();
	
	private FacadeMulSelectedCell facade;
	
	private List<ModelMulSelected> options;

	public ViewFormMulSelectedCell(Context context, ModelViewFormMulSelectedCell model, FacadeMulSelectedCell facade) {
		super(context, model);
		
		this.model = model;
		this.facade = facade;
		
		MulSelectedInit();
		MulSelectedSetEvent();
		
	}
	
	private void MulSelectedInit(){
		options = model.getOptions();
		
		initWithModel(model);
	}
	
	
	private void MulSelectedSetEvent() {
		form_cell.setOnClickListener(OnClickCell);
	}
	
	private OnClickListener OnClickCell =  new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			facade.selectOptions(options, new CallBack() {
				
				@Override
				public void onSuccess(List<ModelMulSelected> options) {
					model.setOptions(options);
					engineSetValue(options);
				}
				
				@Override
				public void onFailure() {
					
				}
			});
		}
		
	};
	
	@Override
	public void initWithModel(ModelViewFormCell model) {
		this.model = (ModelViewFormMulSelectedCell) model;
		super.initWithModel(this.model);
	}

	
	private void engineSetValue(List<ModelMulSelected> options){
		String value = optionsValue(options);
		setViewValue(value);
		model.setValue(value);
		
		List<ModelMulSelected> selects = new ArrayList<ModelMulSelected>();
		for(ModelMulSelected option : options){
			if(option.isCheck()){
				selects.add(option);
			}
		}
		model.setSelects(selects);
	}
	
	@Override
	public void formCommit() {
		super.formCommit();
	}
	
	private String optionsValue(List<ModelMulSelected> options) {
		String value = "";
		if(options != null){
			for(ModelMulSelected selected : options){
				if(selected.isCheck()){
					value += selected.getValue() + " ";
				}
			}
		}
		return value;
	}
}
