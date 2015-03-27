package com.maoye.form.model.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maoye.form.constact.ConstactForm;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.utils.UtilModelAndJson4Form;

public class ModelViewForm extends ModelViewFormElement{
	private List<ModelViewFormRow> rows;
	private String name;
	
	
	/**
	 * @param tag
	 */
	public ModelViewForm(String tag) {
		super(tag);
		setType(ModelViewForm.class.getSimpleName());
	}

	
	/**
	 * @param tag
	 */
	public ModelViewForm() {
		super();
		setType(ModelViewForm.class.getSimpleName());
	}
	
	public List<ModelViewFormRow> getRows() {
		return rows;
	}


	public void setRows(List<ModelViewFormRow> rows) {
		this.rows = rows;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addRow(ModelViewFormRow row){
		if(this.rows == null){
			this.rows = new ArrayList<ModelViewFormRow>();
		}
		this.rows.add(row);
	}

	
	public void clearRows(){
		if(rows != null){
			rows.clear();
		}
	}
	
	
	public ModelViewFormCell findCellByTag(String tag){
		
		try {
			for(ModelViewFormRow row : rows){
				for(ModelViewFormCell cell : row.getCells()){
					if(tag.equals(cell.getTag())){
						return cell;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public boolean loadData(String dataJson){
		try {
			JSONObject jsonObject = new JSONObject(dataJson);
			List<ModelViewFormCell> cells = getCells();
			for(ModelViewFormCell cell : cells){
//				String key = cell.getTag();
//				JSONObject data = jsonObject.getJSONObject(key);
//				cell.loadDataCell(data);
				
				cell.loadDataCell(jsonObject);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}


	private List<ModelViewFormCell> getCells() {
		List<ModelViewFormCell> cells = new ArrayList<ModelViewFormCell>();
		try {
			for(ModelViewFormRow row : rows){
				cells.addAll(row.getCells());
			}
			return cells;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cells;
	}
}
