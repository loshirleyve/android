package com.maoye.form.factory.model.form;

import com.maoye.form.model.form.cell.data.ModelDataAttchmentCell;
import com.maoye.form.model.form.cell.data.ModelDataEditTextCell;
import com.maoye.form.model.form.cell.data.ModelDataMulSelectedCell;
import com.maoye.form.model.form.cell.data.ModelDataRadioCell;
import com.maoye.form.model.form.cell.data.ModelDataTextCell;
import com.maoye.form.model.form.cell.data.ModelDataTimeCell;

public abstract class FactoryModelDataCell {

	public abstract ModelDataTextCell creatModelDataTextCell();
	
	public abstract ModelDataEditTextCell creatModelDataEditTextCell();
	
	public abstract ModelDataRadioCell creatModelDataRadioCell();
	
	public abstract ModelDataTimeCell creatModelDataTimeCell();
	
	public abstract ModelDataMulSelectedCell creatModelDataMulSelectedCell();
	
	public abstract ModelDataAttchmentCell creatModelDataAttchmentCell();
	
}
