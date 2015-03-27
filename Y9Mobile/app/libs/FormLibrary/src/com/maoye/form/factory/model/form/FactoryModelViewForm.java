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

public abstract class FactoryModelViewForm {
	public abstract ModelViewForm creatModelViewForm(String tag);
	
	public abstract ModelViewFormRow creatModelViewFormRow(String tag);
	
	public abstract ModelViewFormTextCell creatModelViewFormTextCell(String tag);
	
	public abstract ModelViewFormRadioCell creatModelViewFormRadioCell(List<String> radios, String tag);
	
	public abstract ModelViewFormEditTextCell creatModelViewFormEditTextCell(String tag);

	public abstract ModelViewFormTimeCell creatModelViewFormTimeCell(String tag);

	public abstract ModelViewFormAttchmentCell creatModelViewFormAttchmentCell(String tag);

	public abstract ModelViewFormMulSelectedCell creatModelViewFormMulSelectedCell(String tag, List<ModelMulSelected> options);

	public abstract ModelViewFormImageCell creatModelViewFormImageCell(String tag);
}
