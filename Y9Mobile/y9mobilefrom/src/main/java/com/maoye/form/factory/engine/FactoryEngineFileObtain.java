package com.maoye.form.factory.engine;

import android.content.Context;

import com.maoye.form.interfaces.engine.EngineFileObtain;

public abstract class FactoryEngineFileObtain {
	
	public abstract EngineFileObtain creatEngineFileObtain(Context context, String name);
}
