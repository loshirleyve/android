package com.maoye.form.factory.engine;

import android.content.Context;

import com.maoye.form.impls.engine.ImplEngineFileObtain2CameraDemo;
import com.maoye.form.interfaces.engine.EngineFileObtain;

public class FactoryEngineFileObtain2AlbumDemo extends FactoryEngineFileObtain{

	@Override
	public EngineFileObtain creatEngineFileObtain(Context context, String name) {
		// TODO 自动生成的方法存根
		return new ImplEngineFileObtain2CameraDemo(context, name);
	}
}
