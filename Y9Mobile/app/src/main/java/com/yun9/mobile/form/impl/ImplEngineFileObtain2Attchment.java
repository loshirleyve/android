package com.yun9.mobile.form.impl;

import java.util.List;

import android.content.Context;
import android.widget.Toast;
import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.maoye.form.model.ModelPic;

public class ImplEngineFileObtain2Attchment implements EngineFileObtain{

	private Context context;
	private String name;
	
	/**
	 * @param context
	 * @param name
	 */
	public ImplEngineFileObtain2Attchment(Context context, String name) {
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
		Toast.makeText(context, "没开放 " + name, 0).show();
	}

}
