package com.maoye.form.model.form.cell;

import org.json.JSONObject;

import com.maoye.form.factory.model.form.FactoryModelDataCell;
import com.maoye.form.factory.model.form.FactoryModelDataCellDefault;
import com.maoye.form.model.form.cell.data.ModelDataCell;
import com.maoye.form.model.form.cell.data.ModelDataTextCell;
import com.maoye.form.utils.UtilModelAndJson4Form;

public class ModelViewFormTextCell extends ModelViewFormCell{
	
	/**
	 * @param tag
	 */
	public ModelViewFormTextCell(String tag) {
		super(tag);
		setType(ModelViewFormTextCell.class.getSimpleName());
	}
	
	
	/**
	 * @param tag
	 */
	public ModelViewFormTextCell() {
		super();
		setType(ModelViewFormTextCell.class.getSimpleName());
	}


	@Override
	public Object getModelData() {
//		setFormStat(null);
//		setLabel(null);
//		setTag(null);
//		setType(null);
//		
//		FactoryModelDataCell factoryModelDataCell = new FactoryModelDataCellDefault();
//		ModelDataTextCell modelDataTextCell = factoryModelDataCell.creatModelDataTextCell();
//		return initModelDataCell(modelDataTextCell);
		
		return getValue();
	}
	
	
	
	@Deprecated
	@Override
	public ModelDataCell initModelDataCell(ModelDataCell dataCell) {
//		ModelDataTextCell modelDataTextCell = (ModelDataTextCell) dataCell;
//		modelDataTextCell.setValue(getValue());
//		return super.initModelDataCell(modelDataTextCell);
		return null;
	}
	
	
	@Override
	public void loadDataCell(JSONObject object) throws Exception {
//		String json = object.toString();
//		ModelViewFormTextCell cell = (ModelViewFormTextCell) UtilModelAndJson4Form.JSONObject2Object(json, ModelViewFormTextCell.class);
//		ModelDataTextCell dataCell = (ModelDataTextCell) UtilModelAndJson4Form.JSONObject2Object(json, ModelDataTextCell.class);
//		setValue(dataCell.getValue());
//		super.loadDataCell(object);
		
		String value = null;
		if(!object.isNull(getTag())){
			value = object.getString(getTag());
		}
		setValue(value);
	}
	
	

}
