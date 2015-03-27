package com.maoye.form.model.form.cell.data;

import java.util.List;

import com.maoye.form.model.form.ModelAttchmentFile;
import com.maoye.form.model.form.ModelAttchmentPic;

public class ModelDataAttchmentCell extends ModelDataTextCell {

	
	private List<String> pics;
	private List<String> attchments;
	
	public ModelDataAttchmentCell() {
	}

	public List<String> getPics() {
		return pics;
	}


	public void setPics(List<String> pics) {
		this.pics = pics;
	}


	public List<String> getAttchments() {
		return attchments;
	}


	public void setAttchments(List<String> attchments) {
		this.attchments = attchments;
	}
	
	
	
	
//	private List<ModelAttchmentPic> pics;
//	private List<ModelAttchmentFile> attchments;
//	
//	

//
//
//	public List<ModelAttchmentPic> getPics() {
//		return pics;
//	}
//
//
//	public void setPics(List<ModelAttchmentPic> pics) {
//		this.pics = pics;
//	}
//
//
//	public List<ModelAttchmentFile> getAttchments() {
//		return attchments;
//	}
//
//
//	public void setAttchments(List<ModelAttchmentFile> attchments) {
//		this.attchments = attchments;
//	}
}
