package com.maoye.form.factory.engine;

import java.util.List;

import android.content.Context;

import com.maoye.form.interfaces.engine.EngineMulSelectedDialog;
import com.maoye.form.interfaces.engine.EngineRadioDialog;

public abstract class FactoryEngineDialog {
	public abstract EngineRadioDialog creatEngineRadioDialog(Context context, List<String> radios);

}
