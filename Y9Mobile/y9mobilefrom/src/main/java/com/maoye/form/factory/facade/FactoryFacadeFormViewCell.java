package com.maoye.form.factory.facade;

import java.util.List;

import android.content.Context;

import com.maoye.form.impls.facade.ImplFacadeRadioCell;
import com.maoye.form.impls.facade.ImplFacadeTimeCell;
import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.maoye.form.interfaces.facade.FacadeAttchCell;
import com.maoye.form.interfaces.facade.FacadeMulSelectedCell;
import com.maoye.form.interfaces.facade.FacadeRadioCell;
import com.maoye.form.interfaces.facade.FacadeTimeCell;

public abstract class FactoryFacadeFormViewCell {

	public abstract FacadeRadioCell creatFacadeRadioCell(Context context);
	
	public abstract FacadeTimeCell creatFacadeTimeCell(Context context);
	
	public abstract FacadeAttchCell creatFacadeAttchCell(Context context, List<EngineFileObtain> listEngineFileObtain);

	public abstract FacadeMulSelectedCell creatFacadeMulSelectedCell(Context context);
}
