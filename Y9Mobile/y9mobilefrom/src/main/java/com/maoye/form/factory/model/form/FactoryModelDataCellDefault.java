package com.maoye.form.factory.model.form;

import com.maoye.form.model.form.cell.data.ModelDataAttchmentCell;
import com.maoye.form.model.form.cell.data.ModelDataEditTextCell;
import com.maoye.form.model.form.cell.data.ModelDataMulSelectedCell;
import com.maoye.form.model.form.cell.data.ModelDataRadioCell;
import com.maoye.form.model.form.cell.data.ModelDataTextCell;
import com.maoye.form.model.form.cell.data.ModelDataTimeCell;

public class FactoryModelDataCellDefault extends FactoryModelDataCell {

	@Override
	public ModelDataTextCell creatModelDataTextCell() {
		return new ModelDataTextCell();
	}

	@Override
	public ModelDataEditTextCell creatModelDataEditTextCell() {
		return new ModelDataEditTextCell();
	}

	@Override
	public ModelDataRadioCell creatModelDataRadioCell() {
		return new ModelDataRadioCell();
	}

	@Override
	public ModelDataTimeCell creatModelDataTimeCell() {
		return new ModelDataTimeCell();
	}

	@Override
	public ModelDataMulSelectedCell creatModelDataMulSelectedCell() {
		return new ModelDataMulSelectedCell();
	}

	@Override
	public ModelDataAttchmentCell creatModelDataAttchmentCell() {
		return new ModelDataAttchmentCell();
	}

}
