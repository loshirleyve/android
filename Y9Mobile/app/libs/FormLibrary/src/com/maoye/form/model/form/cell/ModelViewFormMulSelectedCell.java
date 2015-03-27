package com.maoye.form.model.form.cell;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.maoye.form.factory.model.form.FactoryModelDataCell;
import com.maoye.form.factory.model.form.FactoryModelDataCellDefault;
import com.maoye.form.model.form.ModelMulSelected;
import com.maoye.form.model.form.cell.data.ModelDataAttchmentCell;
import com.maoye.form.model.form.cell.data.ModelDataCell;
import com.maoye.form.model.form.cell.data.ModelDataMulSelectedCell;
import com.maoye.form.model.form.cell.data.ModelDataRadioCell;
import com.maoye.form.utils.UtilModelAndJson4Form;

public class ModelViewFormMulSelectedCell extends ModelViewFormTextCell{
	
	/**
	 * 多选数据
	 */
	private List<ModelMulSelected> options;
	
	
	private List<ModelMulSelected> selects;
	
	
	private List<String> selectValues;
	
	/**
	 * @param texts
	 */
	public ModelViewFormMulSelectedCell(List<ModelMulSelected> options, String tag) {
		super(tag);
		this.options = options;
		setType(ModelViewFormMulSelectedCell.class.getSimpleName());
	}
	

	public ModelViewFormMulSelectedCell() {
		super();
		setType(ModelViewFormMulSelectedCell.class.getSimpleName());
	}


	public List<ModelMulSelected> getOptions() {
		return options;
	}

	public void setOptions(List<ModelMulSelected> options) {
		this.options = options;
	}
	
	@Override
	public Object getModelData() {
//		setOptions(null);
//		
//		selectValues = new ArrayList<String>();
//		if(selects == null){
//			
//		}else{
//			for(ModelMulSelected select : selects){
//				selectValues.add(select.getValue());
//			}
//		}
//		
//		
//		setSelects(null);
//		return super.init2DataJson();
		
//		
//		FactoryModelDataCell factoryModelDataCell = new FactoryModelDataCellDefault();
//		ModelDataMulSelectedCell modelDataCell = factoryModelDataCell.creatModelDataMulSelectedCell();
//		return initModelDataCell(modelDataCell);
		
		
		List<String> selectValue = new ArrayList<String>();
		if(selects != null){
			for(ModelMulSelected select : selects){
				selectValue.add(select.getValue());
			}
		}
		return selectValue;
	}
	

//	@Override
//	public ModelDataCell initModelDataCell(ModelDataCell dataCell) {
//		
//		ModelDataMulSelectedCell modelDataCell = (ModelDataMulSelectedCell) dataCell;
//		List<String> selectValue = new ArrayList<String>();
//		if(selects != null){
//			for(ModelMulSelected select : selects){
//				selectValue.add(select.getValue());
//			}
//		}
//		modelDataCell.setSelectValue(selectValue);
//		
//		return modelDataCell;
//	}
	
	
	@Override
	public void loadDataCell(JSONObject object) throws Exception {
		
		JSONArray mulSelect = object.getJSONArray(getTag());
		List<String> datas = new ArrayList<String>();
		if(mulSelect != null){
			for(int i = 0; i < mulSelect.length(); i++){
				datas.add(mulSelect.getString(i));
			}
		}
		
		selects = new ArrayList<ModelMulSelected>();
		for(String value : datas){
			selects.add(new ModelMulSelected(value, true));
		}
		
		setSelectValues(datas);
		setValue(select2Str(datas));
		
//		setValue(dataCell.selectValue2Str());
		
//		ModelDataMulSelectedCell dataCell = (ModelDataMulSelectedCell) UtilModelAndJson4Form.JSONObject2Object(json, ModelDataMulSelectedCell.class);
//		setValue(dataCell.selectValue2Str());
		
//		String json = object.toString();
//		ModelDataMulSelectedCell dataCell = (ModelDataMulSelectedCell) UtilModelAndJson4Form.JSONObject2Object(json, ModelDataMulSelectedCell.class);
//		List<String> datas = dataCell.getSelectValue();
//		List<ModelMulSelected> selects =new ArrayList<ModelMulSelected>();
//		for(String data : datas){
//			selects.add(new ModelMulSelected(data, true));
//		}
//		
//		setValue(dataCell.selectValue2Str());
		
//		selects = new ArrayList<ModelMulSelected>();
//		for(String value : selectValues){
//			selects.add(new ModelMulSelected(value, true));
//		}
//		setSelects(cell.getSelects());
		
//		super.loadDataCell(object);
	}
	

	public List<ModelMulSelected> getSelects() {
		return selects;
	}


	public void setSelects(List<ModelMulSelected> selects) {
		this.selects = selects;
	}


	public List<String> getSelectValues() {
		return selectValues;
	}


	public void setSelectValues(List<String> selectValues) {
		this.selectValues = selectValues;
	}
	
	private String select2Str(List<String> options) {
		String value = "";
		if(options != null){
			for(String selected : options){
					value += selected + " ";
			}
		}
		return value;
	}

}
