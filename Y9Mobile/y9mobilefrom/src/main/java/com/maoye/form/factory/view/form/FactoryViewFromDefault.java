package com.maoye.form.factory.view.form;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.maoye.form.constact.ConstactForm;
import com.maoye.form.factory.engine.FactoryEngineFileObtain;
import com.maoye.form.factory.engine.FactoryEngineFileObtain2AlbumDemo;
import com.maoye.form.factory.engine.FactoryEngineFileObtain2AttchmentDemo;
import com.maoye.form.factory.engine.FactoryEngineFileObtain2CameraDemo;
import com.maoye.form.factory.facade.FactoryFacadeFormViewCellDefault;
import com.maoye.form.interfaces.AdapterShowImage;
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

public abstract class FactoryViewFromDefault extends FactoryViewForm{

	@Override
	public ViewForm creatViewForm(Context context) {
		ViewForm form = new ViewForm(context);
		form.setFactoryViewForm(this);
		return form;
	}


	@Override
	public ViewFormRow creatViewFormRow(Context context, ModelViewFormRow model) {
		return new ViewFormRow(context, model);
	}
	

	@Override
	public ViewFormTextCell creatViewFormTextCell(Context context, ModelViewFormTextCell model) {
		return new ViewFormTextCell(context, model);
	}
	
	
	@Override
	public ViewFormRadioCell creatViewFormRadioCell(Context context, ModelViewFormRadioCell model) {
		
		FacadeRadioCell facade = new FactoryFacadeFormViewCellDefault().creatFacadeRadioCell(context);
		return new ViewFormRadioCell(context, model, facade);
	}


	@Override
	public ViewFormTimeCell creatViewFormTimeCell(Context context, ModelViewFormTimeCell model) {
		
		FacadeTimeCell facade = new FactoryFacadeFormViewCellDefault().creatFacadeTimeCell(context);
		return new ViewFormTimeCell(context, model, facade);
	}

	@Override
	public ViewFormEditTextCell creatViewFormEditTextCell(Context context, ModelViewFormEditTextCell model) {
		return new ViewFormEditTextCell(context, model);
	}
	
	
	@Override
	public ViewFormMulSelectedCell creatViewFormMulSelectedCell(Context context, ModelViewFormMulSelectedCell model) {
		FacadeMulSelectedCell facade = new FactoryFacadeFormViewCellDefault().creatFacadeMulSelectedCell(context);
		return new ViewFormMulSelectedCell(context, model, facade);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public ViewFormCell creatViewFormCell(Context context, String type, ModelViewFormCell model) {
		Class modelClass[] = ConstactForm.MODEL_VIEWFORM_CLASS;
		
		for(int i = 0; i < modelClass.length; i++){
			
			if(type.equals(modelClass[i].getSimpleName())){
			
				if(modelClass[i].equals(ConstactForm.MODEL_VIEWFORM_TEXTCELL)){
					
					return creatViewFormTextCell(context, (ModelViewFormTextCell)model);
				
				}else if(modelClass[i].equals(ConstactForm.MODEL_VIEWFORM_RADIOCELL)){
					
					return creatViewFormRadioCell(context, (ModelViewFormRadioCell)model);
				
				}else if(modelClass[i].equals(ConstactForm.MODEL_VIEWFORM_EDITTEXTCELL)){
					
					return creatViewFormEditTextCell(context, (ModelViewFormEditTextCell)model);
				
				}else if(modelClass[i].equals(ConstactForm.MODEL_VIEWFORM_TIMECELL)){
					
					return creatViewFormTimeCell(context, (ModelViewFormTimeCell)model);
				
				}else if(modelClass[i].equals(ConstactForm.MODEL_VIEWFORM_ATTCHMENTCELL)){
					
					return creatViewFormAttchmentCell(context, (ModelViewFormAttchmentCell)model);
				
				}else if(modelClass[i].equals(ConstactForm.MODEL_VIEWFORM_MULSELECTEDCELL)){
					
					return creatViewFormMulSelectedCell(context, (ModelViewFormMulSelectedCell)model);
				}else if(modelClass[i].equals(ConstactForm.MODEL_VIEWFORM_IMAGECELL)){
					return creatViewFormImageCell(context, (ModelViewFormImageCell)model);
				}
			}
		}
		
		throw new RuntimeException("无效表单单元");
	}




}
