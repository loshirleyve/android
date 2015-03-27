package com.maoye.form.model.form.cell;

import org.json.JSONObject;

import com.maoye.form.model.form.ModelViewFormElement;
import com.maoye.form.model.form.cell.data.ModelDataCell;

public  class ModelViewFormCell extends ModelViewFormElement{
	
	/**
	 * @param tag
	 */
	public ModelViewFormCell(String tag) {
		super(tag);
		setType(ModelViewFormCell.class.getSimpleName());
	}

	
	/**
	 * @param tag
	 */
	public ModelViewFormCell() {
		super();
		setType(ModelViewFormCell.class.getSimpleName());
	}
	
	
	// 需要被子类复写
	public Object getModelData(){
		
		return null;
	}


	// 需要被子类复写
	public ModelDataCell initModelDataCell(ModelDataCell dataCell){
		
		return dataCell;
	}
	
	// 需要被子类复写
	public void loadDataCell(JSONObject object) throws Exception{
		
	}
}
