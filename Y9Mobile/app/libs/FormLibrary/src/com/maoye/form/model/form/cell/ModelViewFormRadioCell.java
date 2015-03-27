package com.maoye.form.model.form.cell;

import java.util.List;

import org.json.JSONObject;

import com.maoye.form.factory.model.form.FactoryModelDataCell;
import com.maoye.form.factory.model.form.FactoryModelDataCellDefault;
import com.maoye.form.model.form.cell.data.ModelDataCell;
import com.maoye.form.model.form.cell.data.ModelDataRadioCell;
import com.maoye.form.model.form.cell.data.ModelDataTimeCell;
import com.maoye.form.utils.UtilModelAndJson4Form;
import com.maoye.form.utils.UtilTime;

public class ModelViewFormRadioCell extends ModelViewFormTextCell{
	
	/**
	 * 单选数据
	 */
	private List<String> radios;
	
	protected Integer position;
	
	/**
	 * @param texts
	 */
	public ModelViewFormRadioCell(List<String> radios, String tag) {
		super(tag);
		this.radios = radios;
		setType(ModelViewFormRadioCell.class.getSimpleName());
	}
	

	public ModelViewFormRadioCell() {
		super();
		setType(ModelViewFormRadioCell.class.getSimpleName());
	}

	public List<String> getRadios() {
		return radios;
	}

	public void setRadios(List<String> radios) {
		this.radios = radios;
	}


	public Integer getPosition() {
		return position;
	}


	public void setPosition(Integer position) {
		this.position = position;
	}

	@Override
	public Object getModelData() {
//		setRadios(null);
//		setPosition(null);
//		return super.init2DataJson();
//		FactoryModelDataCell factoryModelDataCell = new FactoryModelDataCellDefault();
//		ModelDataRadioCell modelDataCell = factoryModelDataCell.creatModelDataRadioCell();
		
//		String value = getValue();
//		if(value == null){
//			value = "";
//		}
		return getValue();
	}


//	@Override
//	public ModelDataCell initModelDataCell(ModelDataCell dataCell) {
//		return super.initModelDataCell(dataCell);
//	}
	
	
	@Override
	public void loadDataCell(JSONObject object) throws Exception {
//		String json = object.toString();
//		ModelDataRadioCell dataCell = (ModelDataRadioCell) UtilModelAndJson4Form.JSONObject2Object(json, ModelDataRadioCell.class);
//		setPosition(cell.getPosition());
		
//		String value = null;
//		if(!object.isNull(getTag())){
//			value = object.getString(getTag());
//		}
//		setValue(value);
		
		super.loadDataCell(object);
	}
}
