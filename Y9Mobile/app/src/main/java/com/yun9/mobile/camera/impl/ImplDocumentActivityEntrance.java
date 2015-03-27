package com.yun9.mobile.camera.impl;

import android.content.Context;
import android.content.Intent;

import com.yun9.mobile.camera.activity.DocumentActivity;
import com.yun9.mobile.camera.impl.pre4ui.ImplPre4UiDocumentActivity;
import com.yun9.mobile.camera.interfaces.DocumentActivityEntrance;

public class ImplDocumentActivityEntrance implements DocumentActivityEntrance{

	private Context context;
	
	/**
	 * @param context
	 */
	public ImplDocumentActivityEntrance(Context context) {
		super();
		this.context = context;
	}


	@Override
	public void go2Document(String filePath) {
		doGo2Document(filePath);
	}
	
	private void doGo2Document(String filePath){
		Intent intent = new Intent(this.context, DocumentActivity.class);
		intent.putExtra(ImplPre4UiDocumentActivity.MODE_STR, ImplPre4UiDocumentActivity.MODE_DEFAULT);
		intent.putExtra(ImplPre4UiDocumentActivity.FILEPATH, filePath);
		context.startActivity(intent);
	}
}
