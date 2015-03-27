package com.maoye.form.factory.facade;

import java.util.List;

import android.content.Context;

import com.maoye.form.impls.engine.ImplEngineMulSelectedDialog;
import com.maoye.form.impls.facade.ImplFacadeAttchCell;
import com.maoye.form.impls.facade.ImplFacadeMulSelectedCell;
import com.maoye.form.impls.facade.ImplFacadeRadioCell;
import com.maoye.form.impls.facade.ImplFacadeTimeCell;
import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.maoye.form.interfaces.facade.FacadeAttchCell;
import com.maoye.form.interfaces.facade.FacadeMulSelectedCell;
import com.maoye.form.interfaces.facade.FacadeRadioCell;
import com.maoye.form.interfaces.facade.FacadeTimeCell;

public class FactoryFacadeFormViewCellDefault extends FactoryFacadeFormViewCell{

	@Override
	public FacadeRadioCell creatFacadeRadioCell(Context context) {
		return 	new ImplFacadeRadioCell(context);
	}

	@Override
	public FacadeTimeCell creatFacadeTimeCell(Context context) {
		return new ImplFacadeTimeCell(context);
	}

	@Override
	public FacadeAttchCell creatFacadeAttchCell(Context context, List<EngineFileObtain> listEngineFileObtain) {
		return new ImplFacadeAttchCell(context, listEngineFileObtain);
	}

	@Override
	public FacadeMulSelectedCell creatFacadeMulSelectedCell(Context context) {
		return new ImplFacadeMulSelectedCell(context);
	}
	
}
