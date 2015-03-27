package com.maoye.form.view.form;



import com.maoye.form.factory.facade.FactoryFacadeFormViewCellDefault;
import com.maoye.form.interfaces.facade.FacadeTimeCell;
import com.maoye.form.interfaces.facade.FacadeTimeCell.CallBack;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.cell.ModelViewFormTextCell;
import com.maoye.form.model.form.cell.ModelViewFormTimeCell;

import android.content.Context;
import android.view.View;

public class ViewFormTimeCell extends ViewFormTextCell{
	
	protected FacadeTimeCell facade;
	private ModelViewFormTimeCell model;
	
	
	public ViewFormTimeCell(Context context, ModelViewFormTimeCell model, FacadeTimeCell facade) {
		super(context, model);
		
		initTimeCell();
		
		initWithModel(model);
		
		this.facade = facade;
	}
	
	
	protected void initTimeCell(){
		facade = new FactoryFacadeFormViewCellDefault().creatFacadeTimeCell(getContext());
		setEvent();
	}
	
	
	private void setEvent() {
		form_cell.setOnClickListener(OnClickCell);
	}
	
	private OnClickListener OnClickCell =  new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			facade.getDateTime(new CallBack() {
				@Override
				public void onSuccess(String value, long date) {
					
					engineSetValue(value, date);
				}
			});
		}
	};
	
	
	@Override
	public void initWithModel(ModelViewFormCell model) {
		this.model = (ModelViewFormTimeCell) model;
		super.initWithModel(this.model);
	}
	
	
	private void engineSetValue(String value, long time){
		model.setValue(value);
		model.setTime(time);
		setViewValue(value);
	}
	
	@Override
	public void formCommit() {
		super.formCommit();
	}
}
