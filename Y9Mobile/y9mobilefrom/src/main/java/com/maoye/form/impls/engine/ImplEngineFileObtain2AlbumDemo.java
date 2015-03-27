package com.maoye.form.impls.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.maoye.form.model.ModelPic;

public class ImplEngineFileObtain2AlbumDemo implements EngineFileObtain{

	private Context context;
	private String name;
	
	
	/**
	 * @param context
	 * @param name
	 */
	public ImplEngineFileObtain2AlbumDemo(Context context, String name) {
		super();
		this.context = context;
		this.name = name;
	}

	@Override
	public String obtainFileMethodName() {
		return name;
	}

	@Override
	public void obtainFile(List<ModelPic> files, List<ModelPic> pics, CallBack callBack) {
		
		
	}

}
