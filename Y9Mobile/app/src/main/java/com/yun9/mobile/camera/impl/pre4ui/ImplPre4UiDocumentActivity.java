package com.yun9.mobile.camera.impl.pre4ui;

import java.io.File;

import android.app.Activity;
import android.content.Intent;

import com.yun9.mobile.camera.domain.DmDocument;
import com.yun9.mobile.camera.interfaces.presenter4ui.Pre4uiDocumentActivity;
import com.yun9.mobile.camera.interfaces.ui4Presenter.Ui4PreDocumentActivity;
import com.yun9.mobile.framework.util.UtilFileIntent;

public class ImplPre4UiDocumentActivity implements Pre4uiDocumentActivity{

	public static final String MODE_STR = "MODE_STR";
	public static final int MODE_DEFAULT = 1;
	
	public static final String FILEPATH = "FILEPATH";
	
	private Ui4PreDocumentActivity ui;
	private Activity activity;
	private int mode;
	private DmDocument dmDocument;
	
	/**
	 * @param ui
	 */
	public ImplPre4UiDocumentActivity(Ui4PreDocumentActivity ui, Activity activity) {
		super();
		this.ui = ui;
		this.activity = activity;
		init();
	}
	
	private void init(){
		
	}

	@Override
	public void work() {
		isLegal();
		
		this.ui.setTitleRightText("打开");
		
		if(this.mode == ImplPre4UiDocumentActivity.MODE_DEFAULT){
			this.ui.showDocumentPhoto(this.dmDocument.getPhoto());
			this.ui.showDocumentName(this.dmDocument.getName());
			this.ui.showDocumentSize(this.dmDocument.getSize());
		}
	}
	
	/**
	 * 启动activity的来源合法判断（模式）
	 */
	private void isLegal() {
		Intent intent = this.activity.getIntent();
		this.mode = intent.getIntExtra(ImplPre4UiDocumentActivity.MODE_STR, 0);
		if(this.mode == ImplPre4UiDocumentActivity.MODE_DEFAULT){
			String path  = intent.getStringExtra(ImplPre4UiDocumentActivity.FILEPATH);
			File file = new File(path);
			this.dmDocument = new DmDocument(file);
		}else{
			this.ui.showToast("非法启动");
			this.activity.finish();
		}
	}


	@Override
	public void OnClickTitleLeft() {
		this.activity.finish();
	}

	@Override
	public void OnClickTitleRight() {
		if(this.mode == ImplPre4UiDocumentActivity.MODE_DEFAULT){
			// 打开文件意图
			Intent intent = UtilFileIntent.getOpenFileIntent(dmDocument.getFile().getAbsolutePath());
			this.activity.startActivity(intent);
		}
	}
	
}
