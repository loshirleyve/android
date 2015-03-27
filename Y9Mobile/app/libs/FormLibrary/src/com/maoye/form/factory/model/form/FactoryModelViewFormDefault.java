package com.maoye.form.factory.model.form;

import java.util.List;

import com.maoye.form.model.form.ModelMulSelected;
import com.maoye.form.model.form.ModelViewForm;
import com.maoye.form.model.form.ModelViewFormRow;
import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.model.form.cell.ModelViewFormEditTextCell;
import com.maoye.form.model.form.cell.ModelViewFormImageCell;
import com.maoye.form.model.form.cell.ModelViewFormMulSelectedCell;
import com.maoye.form.model.form.cell.ModelViewFormRadioCell;
import com.maoye.form.model.form.cell.ModelViewFormTextCell;
import com.maoye.form.model.form.cell.ModelViewFormTimeCell;

public class FactoryModelViewFormDefault extends FactoryModelViewForm{
	
	
	@Override
	public ModelViewForm creatModelViewForm(String tag) {
		return new ModelViewForm(tag);
	}

	@Override
	public ModelViewFormRow creatModelViewFormRow(String tag) {
		return new ModelViewFormRow(tag);
	}
	
	@Override
	public ModelViewFormTextCell creatModelViewFormTextCell(String tag) {
		return new ModelViewFormTextCell(tag);
	}

	@Override
	public ModelViewFormRadioCell creatModelViewFormRadioCell( List<String> radios, String tag) {
		return new ModelViewFormRadioCell(radios, tag);
	}

	@Override
	public ModelViewFormEditTextCell creatModelViewFormEditTextCell(String tag) {
		return new ModelViewFormEditTextCell(tag);
	}

	@Override
	public ModelViewFormTimeCell creatModelViewFormTimeCell(String tag) {
		return new ModelViewFormTimeCell(tag);
	}

	@Override
	public ModelViewFormAttchmentCell creatModelViewFormAttchmentCell(String tag) {
		return new ModelViewFormAttchmentCell(tag);
	}

	@Override
	public ModelViewFormMulSelectedCell creatModelViewFormMulSelectedCell(String tag, List<ModelMulSelected> options) {
		return new ModelViewFormMulSelectedCell(options, tag);
	}

	@Override
	public ModelViewFormImageCell creatModelViewFormImageCell(String tag) {
		return new ModelViewFormImageCell(tag);
	}


}
