package com.maoye.form.view.form;

import java.util.ArrayList;
import java.util.List;

import com.maoye.form.factory.facade.FactoryFacadeFormViewCell;
import com.maoye.form.factory.facade.FactoryFacadeFormViewCellDefault;
import com.maoye.form.interfaces.facade.FacadeRadioCell;
import com.maoye.form.interfaces.facade.FacadeRadioCell.CallBack;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.cell.ModelViewFormRadioCell;
import com.maoye.form.model.form.cell.ModelViewFormTextCell;

import android.content.Context;
import android.view.View;


public class ViewFormRadioCell extends ViewFormTextCell{
	
	private ModelViewFormRadioCell model;
	
	protected static final String tag = ViewFormRadioCell.class.getSimpleName();
	
	protected FacadeRadioCell facade;
	
	protected List<String> radios;
	
	public ViewFormRadioCell(Context context, ModelViewFormRadioCell modelCellRadio, FacadeRadioCell facade) {
		super(context, modelCellRadio);
		
		this.model = modelCellRadio;
		this.facade = facade;
		
		RadioCellInit();
		setEvent();
		initWithModel(modelCellRadio);
	}
	
	protected void RadioCellInit(){
		this.radios = model.getRadios();
	}
	
	
	private void setEvent() {
		form_cell.setOnClickListener(OnClickCell);
	}
	
	private OnClickListener OnClickCell =  new OnClickListener() {
		@Override
		public void onClick(View v) {
			facade.getRadio(radios,  new CallBack() {
				@Override
				public void onSuccess(int position) {
					engineSetValue(position);
				}
				
				@Override
				public void onFailure() {
					
				}
			});
		}
		
	};
	
	@Override
	public void initWithModel(ModelViewFormCell model) {
		this.model = (ModelViewFormRadioCell) model;
		super.initWithModel(this.model);
		setRadios(this.model.getRadios());
	}

	public List<String> getRadios() {
		return radios;
	}

	public void setRadios(List<String> radios) {
		this.radios = radios;
	};
	
	
	private void engineSetValue(int position){
		setViewValue(radios.get(position));
		model.setValue(radios.get(position));
		model.setPosition(position);
	}
	
	@Override
	public void formCommit() {
		super.formCommit();
	}
}
