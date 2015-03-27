package com.maoye.form.model.form.cell;


import org.json.JSONObject;

import com.maoye.form.factory.model.form.FactoryModelDataCell;
import com.maoye.form.factory.model.form.FactoryModelDataCellDefault;
import com.maoye.form.interfaces.FormInputType;
import com.maoye.form.model.form.cell.data.ModelDataCell;
import com.maoye.form.model.form.cell.data.ModelDataEditTextCell;
import com.maoye.form.model.form.cell.data.ModelDataTextCell;


public class ModelViewFormEditTextCell extends ModelViewFormTextCell{
	
	Integer inputType = FormInputType.TYPE_CLASS_TEXT;
//	int inputType = FormInputType.TYPE_CLASS_NUMBER | FormInputType.TYPE_NUMBER_FLAG_DECIMAL;
	
	/**
	 * @param tag
	 */
	public ModelViewFormEditTextCell(String tag) {
		super(tag);
		setType(ModelViewFormEditTextCell.class.getSimpleName());
	}

	/**
	 * @param tag
	 */
	public ModelViewFormEditTextCell() {
		super();
		setType(ModelViewFormEditTextCell.class.getSimpleName());
	}

	public int getInputType() {
		return inputType;
	}
	
	public void setInputType(Integer inputType) {
		this.inputType = inputType;
	}

	
	@Override
	public void loadDataCell(JSONObject object) throws Exception {
		super.loadDataCell(object);
	}
	
	@Override
	public Object getModelData() {
//		setInputType(null);
//		
//		FactoryModelDataCell factoryModelDataCell = new FactoryModelDataCellDefault();
//		ModelDataEditTextCell modelDataCell = factoryModelDataCell.creatModelDataEditTextCell();
//		
//		return initModelDataCell(modelDataCell);
		
		return getValue();
//		return super.init2DataJson();
	}
	

//	@Override
//	public ModelDataCell initModelDataCell(ModelDataCell dataCell) {
//
//		ModelDataEditTextCell modelDataCell = (ModelDataEditTextCell) dataCell;
//		
//		return super.initModelDataCell(modelDataCell);
//	}
}
