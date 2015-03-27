package com.maoye.form.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maoye.form.constact.ConstactForm;
import com.maoye.form.model.form.ModelViewForm;
import com.maoye.form.model.form.ModelViewFormRow;
import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.cell.ModelViewFormEditTextCell;
import com.maoye.form.model.form.cell.ModelViewFormRadioCell;
import com.maoye.form.model.form.cell.ModelViewFormTextCell;
import com.maoye.form.model.form.cell.ModelViewFormTimeCell;

public class UtilModelAndJson4Form {
	
	
	
	
	
	public static ModelViewForm json2Model(String json){
		
		Class modelClass[] = ConstactForm.MODEL_VIEWFORM_CLASS;
		
		JSONObject jsonObject;
		JSONArray rows;
		JSONObject row;
		JSONArray cells;
		JSONObject cell;
		
		String KEY_ROWS = ConstactForm.KEY_ROWS;
		String KEY_CELLS = ConstactForm.KEY_CELLS;
		String KEY_TYPE = ConstactForm.KEY_TYPE;
		
		
		ModelViewForm modelViewForm;
		ModelViewFormRow modelViewFormRow;
		
		try {
			modelViewForm = (ModelViewForm) JSONObject2Object(json, ModelViewForm.class);
			modelViewForm.clearRows();
			modelViewForm.setRows(null);
		
			jsonObject = new JSONObject(json);
		
			rows = jsonObject.getJSONArray(KEY_ROWS);
			
			for(int i = 0; i < rows.length(); i++){
				row = rows.getJSONObject(i);
				modelViewFormRow = (ModelViewFormRow) JSONObject2Object(row.toString(), ModelViewFormRow.class);
				modelViewFormRow.setCells(null);
				
				cells = row.getJSONArray(KEY_CELLS);
				for(int j = 0 ; j < cells.length(); j++){
					cell = cells.getJSONObject(j);
					
					String type = (String) cell.get(KEY_TYPE);
					
					for(int k = 0; k < modelClass.length; k++){
						if(type.equals(modelClass[k].getSimpleName())){
							modelViewFormRow.addCell((ModelViewFormCell) JSONObject2Object(cell.toString(), modelClass[k]));
						}
					}
				}
				modelViewForm.addRow(modelViewFormRow);
			}
			return modelViewForm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Object JSONObject2Object(String json, Class classType){
		Gson gson = new Gson();
//		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.fromJson(json, classType);
	}
	
	
	public static String model2Json(ModelViewForm form){
		Gson gson = new Gson();
//		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(form);
	}
	
	public static String model2ValueJson(ModelViewForm form){
		Map<String, Object> valueMap = new HashMap<String, Object>();
		for(ModelViewFormRow row : form.getRows()){
			for(ModelViewFormCell cell : row.getCells()){
				String tag = cell.getTag();
				valueMap.put(tag, cell.getModelData());
			}
		}
//		Gson gson = new Gson();
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(valueMap);
	}

}
