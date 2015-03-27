package com.maoye.form.factory.view.form;

import java.util.List;

import android.content.Context;

import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.maoye.form.interfaces.facade.FacadeAttchCell;
import com.maoye.form.interfaces.facade.FacadeMulSelectedCell;
import com.maoye.form.interfaces.facade.FacadeRadioCell;
import com.maoye.form.interfaces.facade.FacadeTimeCell;
import com.maoye.form.model.form.ModelViewFormRow;
import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.cell.ModelViewFormEditTextCell;
import com.maoye.form.model.form.cell.ModelViewFormImageCell;
import com.maoye.form.model.form.cell.ModelViewFormMulSelectedCell;
import com.maoye.form.model.form.cell.ModelViewFormRadioCell;
import com.maoye.form.model.form.cell.ModelViewFormTextCell;
import com.maoye.form.model.form.cell.ModelViewFormTimeCell;
import com.maoye.form.view.form.ViewForm;
import com.maoye.form.view.form.ViewFormAttchmentCell;
import com.maoye.form.view.form.ViewFormCell;
import com.maoye.form.view.form.ViewFormEditTextCell;
import com.maoye.form.view.form.ViewFormImageCell;
import com.maoye.form.view.form.ViewFormMulSelectedCell;
import com.maoye.form.view.form.ViewFormRadioCell;
import com.maoye.form.view.form.ViewFormRow;
import com.maoye.form.view.form.ViewFormTextCell;
import com.maoye.form.view.form.ViewFormTimeCell;

public abstract class FactoryViewForm {
	
	public abstract ViewForm creatViewForm(Context context);
	
	public abstract ViewFormRow creatViewFormRow(Context context, ModelViewFormRow model);
	
	public abstract ViewFormTextCell creatViewFormTextCell(Context context, ModelViewFormTextCell model);
	
	public abstract ViewFormRadioCell creatViewFormRadioCell(Context context, ModelViewFormRadioCell model);
	
	public abstract ViewFormTimeCell creatViewFormTimeCell(Context context, ModelViewFormTimeCell model);
	
	public abstract ViewFormEditTextCell creatViewFormEditTextCell(Context context, ModelViewFormEditTextCell model);
	
	public abstract ViewFormAttchmentCell creatViewFormAttchmentCell(Context context, ModelViewFormAttchmentCell model);
	
	public abstract ViewFormMulSelectedCell creatViewFormMulSelectedCell(Context context, ModelViewFormMulSelectedCell model);
	
	public abstract ViewFormImageCell creatViewFormImageCell(Context context, ModelViewFormImageCell model);
	
	public abstract ViewFormCell creatViewFormCell(Context context, String type, ModelViewFormCell model);


}
