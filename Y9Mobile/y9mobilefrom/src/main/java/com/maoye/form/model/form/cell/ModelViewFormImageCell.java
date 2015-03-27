package com.maoye.form.model.form.cell;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.maoye.form.model.form.ModelAttchmentPic;

public class ModelViewFormImageCell extends ModelViewFormTextCell {
	private List<ModelAttchmentPic> pics;

	public ModelViewFormImageCell(String tag) {
		super(tag);
		setType(ModelViewFormImageCell.class.getSimpleName());
	
	}
	
	public List<ModelAttchmentPic> getPics() {
		if(pics == null){
			pics = new ArrayList<ModelAttchmentPic>();
		}
		return pics;
	}

	public void setPics(List<ModelAttchmentPic> pics) {
		this.pics = pics;
	}
	
	@Override
	public Object getModelData() {
		return getData();
	}
	@Override
	public void loadDataCell(JSONObject object) throws Exception {
		
		JSONArray jsonArray = object.getJSONArray(getTag());
		List<ModelAttchmentPic> datas = getPics();
		if(jsonArray != null){
			for(int i = 0; i < jsonArray.length(); i++){
				ModelAttchmentPic pic = new ModelAttchmentPic();
				pic.setValue(jsonArray.getString(i));
				datas.add(pic);
			}
		}
		
		setPics(datas);
	}
	
	private List<String> getData(){
		List<String> data = new ArrayList<String>();
		if(pics != null && pics.size() > 0){
			for(ModelAttchmentPic pic : pics){
				data.add(pic.getValue());
			}
		}
		return data;
	}
	
}
