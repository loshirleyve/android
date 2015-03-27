package com.yun9.mobile.form.factory;

import android.content.Context;

import com.maoye.form.factory.engine.FactoryEngineFileObtain;
import com.maoye.form.impls.engine.ImplEngineFileObtain2CameraDemo;
import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.yun9.mobile.form.impl.ImplEngineFileObtain2Album;

public class FactoryEngineFileObtain2Album extends FactoryEngineFileObtain{

	@Override
	public EngineFileObtain creatEngineFileObtain(Context context, String name) {
		return new ImplEngineFileObtain2Album(context, name);
	}

}
