package com.maoye.form.factory.engine;

import java.util.List;

import android.content.Context;

import com.maoye.form.impls.engine.ImplEngineRadioDialog;
import com.maoye.form.interfaces.engine.EngineMulSelectedDialog;
import com.maoye.form.interfaces.engine.EngineRadioDialog;

public class FactoryEngineDialogDefault extends FactoryEngineDialog{

	@Override
	public EngineRadioDialog creatEngineRadioDialog(Context context, List<String> radios) {
		return new ImplEngineRadioDialog(context, radios);
	}

}
