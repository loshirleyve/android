package com.maoye.form.model.form.cell;

import org.json.JSONObject;

import com.maoye.form.factory.model.form.FactoryModelDataCell;
import com.maoye.form.factory.model.form.FactoryModelDataCellDefault;
import com.maoye.form.model.form.cell.data.ModelDataCell;
import com.maoye.form.model.form.cell.data.ModelDataEditTextCell;
import com.maoye.form.model.form.cell.data.ModelDataTimeCell;
import com.maoye.form.utils.UtilModelAndJson4Form;
import com.maoye.form.utils.UtilTime;

public class ModelViewFormTimeCell extends ModelViewFormTextCell{
	
	private Long time;
	
	/**
	 * @param tag
	 */
	public ModelViewFormTimeCell(String tag) {
		super(tag);
		setType(ModelViewFormTimeCell.class.getSimpleName());
	}
	
	
	/**
	 * @param tag
	 */
	public ModelViewFormTimeCell() {
		super();
		setType(ModelViewFormTimeCell.class.getSimpleName());
	}


	public Long getTime() {
		return time;
	}


	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public void loadDataCell(JSONObject object) throws Exception {
//		String json = object.toString();
//		ModelViewFormTimeCell cell = (ModelViewFormTimeCell) UtilModelAndJson4Form.JSONObject2Object(json, ModelViewFormTimeCell.class);
//		ModelDataTimeCell dataCell = (ModelDataTimeCell) UtilModelAndJson4Form.JSONObject2Object(json, ModelDataTimeCell.class);
//		Long time = dataCell.getTime();
//		if(time != null){
//			setTime(time);
//			setValue(UtilTime.getStringDate(time));
//		}
		
		
		
		if(!object.isNull(getTag())){
			time = object.getLong(getTag());
			setTime(time);
			setValue(UtilTime.getStringDate(time));
		}
		
		
		
//		String tag = getTag();
//		String value = object.getString(tag);
//		if(!value.isEmpty()){
//			time = Long.valueOf(value);
//			setValue(UtilTime.getStringDate(time));
//		}
//		setValue(value);
//		super.loadDataCell(object);
	}
	@Override
	public Object getModelData() {
//		FactoryModelDataCell factoryModelDataCell = new FactoryModelDataCellDefault();
//		ModelDataTimeCell modelDataCell = factoryModelDataCell.creatModelDataTimeCell();
//		return initModelDataCell(modelDataCell);
		
//		return getTime2Str();
		return getTime();
	}
	
//	@Override
//	public ModelDataCell initModelDataCell(ModelDataCell dataCell) {
//		// TODO 自动生成的方法存根
//		
//		ModelDataTimeCell modelDataCell = (ModelDataTimeCell) dataCell;
//		
//		modelDataCell.setTime(getTime());
//		
//		return modelDataCell;
//	}
	
	private String getTime2Str(){
		String value = "";
		if(time == null){
			return value;
		}
		
		value = String.valueOf(time);
		return value;
	}
}
