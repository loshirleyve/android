package com.maoye.form.model.form.cell;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.maoye.form.factory.model.form.FactoryModelDataCell;
import com.maoye.form.factory.model.form.FactoryModelDataCellDefault;
import com.maoye.form.model.form.ModelAttchmentFile;
import com.maoye.form.model.form.ModelAttchmentPic;
import com.maoye.form.model.form.cell.data.ModelDataAttchmentCell;
import com.maoye.form.model.form.cell.data.ModelDataCell;
import com.maoye.form.model.form.cell.data.ModelDataMulSelectedCell;
import com.maoye.form.utils.UtilModelAndJson4Form;

public class ModelViewFormAttchmentCell extends ModelViewFormTextCell{
	
	private List<ModelAttchmentPic> pics;
	private List<ModelAttchmentFile> attchments;
	
	/**
	 * @param tag
	 */
	public ModelViewFormAttchmentCell(String tag) {
		super(tag);
		setType(ModelViewFormAttchmentCell.class.getSimpleName());
	}

	/**
	 * @param tag
	 */
	public ModelViewFormAttchmentCell() {
		super();
		setType(ModelViewFormAttchmentCell.class.getSimpleName());
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

	public List<ModelAttchmentFile> getAttchments() {
		if(attchments == null){
			attchments = new ArrayList<ModelAttchmentFile>();
		}
		return attchments;
	}

	public void setAttchments(List<ModelAttchmentFile> attchments) {
		this.attchments = attchments;
	}
	
	@Override
	public Object getModelData() {
		FactoryModelDataCell factoryModelDataCell = new FactoryModelDataCellDefault();
		ModelDataAttchmentCell modelDataCell = factoryModelDataCell.creatModelDataAttchmentCell();
		
		
		modelDataCell.setPics(pics2DataPics());
		modelDataCell.setAttchments(attchment2Dataattchment());
		modelDataCell.setValue(getValue());
		return modelDataCell;
	}
	
	
	private List<String> pics2DataPics(){
		List<String> data = new ArrayList<String>();
		
		if(pics != null){
			for(ModelAttchmentPic file : pics){
				data.add(file.getValue());
			}
		}
		
		return data;
	}
	
	private List<String> attchment2Dataattchment(){
		List<String> data = new ArrayList<String>();
		if(attchments != null){
			for(ModelAttchmentFile file : attchments){
				data.add(file.getValue());
			}
		}
		return data;
	}
	
	
	private List<ModelAttchmentPic> dataPic2Pic(List<String> list){
		List<ModelAttchmentPic>  files = new ArrayList<ModelAttchmentPic>();
		ModelAttchmentPic model;
		if(list != null && list.size() > 0){
			for(String value : list){
				model = new ModelAttchmentPic();
				model.setValue(value);
				files.add(model);
			}
		}
		return files;
	}
	
	private List<ModelAttchmentFile> dataAttchment2Attchment(List<String> list){
		List<ModelAttchmentFile>  files = new ArrayList<ModelAttchmentFile>();
		ModelAttchmentFile model;
		if(list != null && list.size() > 0){
			for(String value : list){
				model = new ModelAttchmentFile();
				model.setValue(value);
				files.add(model);
			}
		}
		return files;
	}
	
	
//	@Override
//	public ModelDataCell initModelDataCell(ModelDataCell dataCell) {
//		ModelDataAttchmentCell modelDataCell = (ModelDataAttchmentCell) dataCell;
//		modelDataCell.setPics(getPics());
//		modelDataCell.setAttchments(getAttchments());
//		modelDataCell.setValue(getValue());
//		return modelDataCell;
//	}
	
	
	@Override
	public void loadDataCell(JSONObject object) throws Exception {
//		String json = object.toString();
//		ModelDataAttchmentCell dataCell = (ModelDataAttchmentCell) UtilModelAndJson4Form.JSONObject2Object(json, ModelDataAttchmentCell.class);
//
//		setPics(dataCell.getPics());
//		setAttchments(dataCell.getAttchments());
//		super.loadDataCell(object);
		
		JSONObject attchment = object.getJSONObject(getTag());
		ModelDataAttchmentCell dataCell = (ModelDataAttchmentCell) UtilModelAndJson4Form.JSONObject2Object(attchment.toString(), ModelDataAttchmentCell.class);
		setPics(dataPic2Pic(dataCell.getPics()));
		setAttchments(dataAttchment2Attchment(dataCell.getAttchments()));
		setValue(dataCell.getValue());
	}
}
