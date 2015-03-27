package com.yun9.mobile.framework.presenters;


import java.io.File;

import javax.crypto.spec.IvParameterSpec;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.yun9.mobile.MainApplication;
import com.yun9.mobile.framework.activity.AboutAppActivity;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.impls.server.sys.ImpleAppCheckForUpdateService;
import com.yun9.mobile.framework.interfaces.server.sys.AppCheckForUpdateService;
import com.yun9.mobile.framework.interfaces.ui4presenter.UI4preMineFragment;
import com.yun9.mobile.framework.model.server.sys.ModelAppCheckForUpdateService;
import com.yun9.mobile.framework.util.UtilPackage;

public class PresenterMineFragment {

	private UI4preMineFragment ui4pre;
	private Activity activity;
	
	private boolean isFocus;
	private String apkUrl;
	private String log;
	private boolean isUpdate;
	
	public void updateOnClickLister() {
		checkUpdate();
	}
	/**
	 * @param ui4pre
	 */
	public PresenterMineFragment(Activity activity, UI4preMineFragment ui4pre) {
		super();
		this.ui4pre = ui4pre;
		this.activity = activity;
	}
	
	public void work(){
//		checkUpdate();
		
		isShowNewVersion();
	}
	
	private void isShowNewVersion(){
		ModelAppCheckForUpdateService appInfo = MainApplication.mInstance.getAppUpdateInfo();
		
		boolean isUpdate = appInfo.isUpdate();
		ui4pre.isShowNewVersionTip(isUpdate);
	}
	
	
	protected void checkUpdate() {
		
		AppCheckForUpdateService service = new ImpleAppCheckForUpdateService();
		service.getAppUpdateInfo(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				ModelAppCheckForUpdateService appInfo = (ModelAppCheckForUpdateService) response.getPayload();
				isFocus = appInfo.isFocus();
				isUpdate = appInfo.isUpdate();
				log = appInfo.getLog();
				apkUrl = appInfo.getUrl();
				
				if(isUpdate == false){
					ui4pre.isShowNewVersionTip(false);
				}
				else{
					ui4pre.isShowNewVersionTip(true);
				}
			}
			
			@Override
			public void onFailure(Response response) {
			}
		});
	}
	
	public void go2aboutApp(){
		Intent intent = new Intent(activity, AboutAppActivity.class);
		activity.startActivity(intent);
	}
}
