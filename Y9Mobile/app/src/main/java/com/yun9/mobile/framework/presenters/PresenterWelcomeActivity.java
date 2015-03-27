package com.yun9.mobile.framework.presenters;

import java.io.File;
import java.io.FileNotFoundException;

import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.content.Intent;
import com.yun9.mobile.framework.activity.LoginActivity;
import com.yun9.mobile.framework.activity.MainActivity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.engine.EngineUpdateApp;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.ui4presenter.Ui4preWelcomeActivity;
import com.yun9.mobile.framework.model.server.sys.ModelAppCheckForUpdateService;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.UtilPackage;

public class PresenterWelcomeActivity {

	protected Ui4preWelcomeActivity ui4pre;
	protected EngineUpdateApp engineUpdateApp;
	protected boolean isFocus;
	protected boolean isUpdate;
	protected String log;
	protected String apkUrl;
	protected Activity activity;
	
	public PresenterWelcomeActivity(Activity activity){
		super();
		this.activity = activity;
		this.ui4pre = (Ui4preWelcomeActivity)activity;
//		init();
	}
	
	
	private void init(){
//		engineUpdateApp = new EngineUpdateApp();
		// 检查app更新
//		checkAppUpdate();
	}



	private void checkAppUpdate() {
		// showDlg 检查更新信息
		
		ui4pre.showProgressDlg("检查App更新信息", "");
		engineUpdateApp.getUpdateInfo(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				ModelAppCheckForUpdateService appInfo = (ModelAppCheckForUpdateService) response.getPayload();
				isFocus = appInfo.isFocus();
				isUpdate = appInfo.isUpdate();
				log = appInfo.getLog();
				apkUrl = appInfo.getUrl();
				isFocus = false;
				if(!isUpdate){
					// 进入主界面
					enterApp();
				}
				
				if(isFocus){
					// 强制升级
					focusUpdate();
				}else{
					ui4pre.closeProgressDlg();
					ui4pre.showUpdateDlg("有新的版本的app", log);
					// 提示是否升级
				}
			}
			
			@Override
			public void onFailure(Response response) {
				// 进入app
				ui4pre.closeProgressDlg();
				ui4pre.showToast("获取信息失败");
				enterApp();
			}
		});
		
	}
	/**
	 * 进入app
	 */
	private void enterApp(){
		ui4pre.closeProgressDlg();
		SessionManager sessionManager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
		boolean isFirst = sessionManager.isFirst();
		boolean isLogin = sessionManager.isLogin();
		// 第一次打开，转到登录界面
		if (isFirst) {
			Intent intent = new Intent(activity, LoginActivity.class);
			activity.startActivity(intent);
			activity.finish();
		} else if (!isFirst && isLogin) { // 已经打开过，并且已经登录
			sessionManager.setFirst(false);
			Intent intent = new Intent(activity, MainActivity.class);
			activity.startActivity(intent);
			activity.finish();
		} else { // 其他情况
			Intent intent = new Intent(activity, LoginActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}
	}

	protected void focusUpdate() {
		updateApp();
	}

	private void updateApp(){
		try {
			engineUpdateApp.downApk(apkUrl, "/yidiantong/yidiantong.apk", new AjaxCallBack<File>(){
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					ui4pre.showToast("下载失败");
					enterApp();
					super.onFailure(t, errorNo, strMsg);
				}

				@Override
				public void onLoading(long count, long current) {
					super.onLoading(count, current);
					
					// 当前下载百分比
					int progress = (int) (current * 100 / count);
					String msg = "下载进度：" + progress + "%";
					ui4pre.showProgressDlg("正在下载新的app", msg);
				}

				@Override
				public void onSuccess(File t) {
					super.onSuccess(t);
					ui4pre.closeProgressDlg();
					UtilPackage.installAPK(t, activity);
					activity.finish();
				}
				
			});
		} catch (Exception e) {
			ui4pre.showToast("SD卡不存在或没挂载");
			enterApp();
		}	
	}

	public void updateEnsureOnclickListener() {
		updateApp();
	}


	public void updateCancelOnClickListener() {
		enterApp();
	}
}
