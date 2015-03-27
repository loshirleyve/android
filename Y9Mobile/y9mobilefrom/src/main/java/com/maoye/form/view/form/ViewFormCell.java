package com.maoye.form.view.form;

import com.maoye.form.engine.EngineFormCommitResult;
import com.maoye.form.engine.EngineFormCommitResult.OberverFormCommitResult;
import com.maoye.form.interfaces.oberver.OberverViewForm;
import com.maoye.form.model.form.ModelViewForm;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.utils.UtilDebugLog;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class ViewFormCell extends LinearLayout implements OberverViewForm{
	
	private ModelViewFormCell model;
	private EngineFormCommitResult engineFormCommitResult;
	
	public ViewFormCell(Context context,  ModelViewFormCell model) {
		super(context);
		this.model = model;
	}
	

	public void notifyFormCommitResult(boolean isOK){
		if(engineFormCommitResult != null){
			engineFormCommitResult.notifyFormCommitResult(isOK);
		}
	
	}
	
	public abstract void initWithModel(ModelViewFormCell model);

	public EngineFormCommitResult getEngineFormCommitResult() {
		return engineFormCommitResult;
	}

	public void setEngineFormCommitResult(EngineFormCommitResult engineFormCommitResult) {
		this.engineFormCommitResult = engineFormCommitResult;
	}


}
