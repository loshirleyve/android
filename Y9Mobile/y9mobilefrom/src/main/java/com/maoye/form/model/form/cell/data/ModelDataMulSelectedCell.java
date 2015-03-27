package com.maoye.form.model.form.cell.data;

import java.util.List;

import com.maoye.form.model.form.ModelMulSelected;

public class ModelDataMulSelectedCell extends ModelDataTextCell {
	List<String> selectValue;
	
	public ModelDataMulSelectedCell() {
		// TODO 自动生成的构造函数存根
	}

	public String selectValue2Str() {
		String value = null;
		if(selectValue != null){
			for(String selected : selectValue){
				value += selected + " ";
			}
		}
		return value;
	}

	public List<String> getSelectValue() {
		return selectValue;
	}

	public void setSelectValue(List<String> selectValue) {
		this.selectValue = selectValue;
	}
	
	
	
}
