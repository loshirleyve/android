package com.yun9.mobile.framework.impls.presenter;

import java.io.File;

import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;

import com.yun9.mobile.MainApplication;
import com.yun9.mobile.framework.engine.EngineUpdateApp;
import com.yun9.mobile.framework.impls.presenter.moudles.ImplPre4UiMoudleUpdateApp;
import com.yun9.mobile.framework.interfaces.presenter4ui.Pre4UiAboutAppActivity;
import com.yun9.mobile.framework.interfaces.ui4presenter.UI4PreAboutAppActivity;
import com.yun9.mobile.framework.model.server.sys.ModelAppCheckForUpdateService;
import com.yun9.mobile.framework.util.UtilPackage;

public class ImplPre4UiAboutAppActivity implements Pre4UiAboutAppActivity{

	private UI4PreAboutAppActivity ui4Pre;
	protected Activity activity;
	
	private ModelAppCheckForUpdateService appInfo;
	/**
	 * @param ui4Pre
	 */
	public ImplPre4UiAboutAppActivity(UI4PreAboutAppActivity ui4Pre, Activity activity) {
		super();
		this.ui4Pre = ui4Pre;
		this.activity = activity;
		init();
	}

	private void init() {
		
	}
	
	
	private void isShowNewVersion(){
		appInfo = MainApplication.mInstance.getAppUpdateInfo();
		boolean isUpdate = appInfo.isUpdate();
		ui4Pre.isShowNewVersionTip(isUpdate);
	}
	
	@Override
	public void work(){
		isShowNewVersion();
	}
	
	
	@Override
	public void updateEnsureOnclickListener() {
		updateApp();
	}

	@Override
	public void updateCancelOnClickListener() {
		
	}

	@Override
	public void updateOnClick() {
		if(!appInfo.isUpdate()){
			ui4Pre.showToast("当前是最新版本");
		}else{
			// 强制更新
			if(appInfo.isFocus()){
				ui4Pre.showFocusUpdateDlg("有必须更新的版本: " + appInfo.getInternalversion(), appInfo.getLog());
			}
			// 弹出更新对话框
			else{
				ui4Pre.showUpdateDlg("有新的版本的app:" + appInfo.getInternalversion(), appInfo.getLog());
			}
		}
	}
	
	private void updateApp(){
		try {
			EngineUpdateApp engineUpdateApp = new EngineUpdateApp();
			engineUpdateApp.downApk(appInfo.getUrl(), "yidiantong.apk", new AjaxCallBack<File>(){
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					ui4Pre.closeUpdateProDlg();
					ui4Pre.showToast("下载app失败" + strMsg);
					super.onFailure(t, errorNo, strMsg);
				}

				@Override
				public void onLoading(long count, long current) {
					super.onLoading(count, current);
					// 当前下载百分比
					int progress = (int) (current * 100 / count);
					String msg = "下载进度：" + progress + "%";
					ui4Pre.showUpdateProDlg("更新app", msg);
				}

				@Override
				public void onSuccess(File t) {
					super.onSuccess(t);
					ui4Pre.closeUpdateProDlg();
					UtilPackage.installAPK(t, activity);
				}
			});
		} catch (Exception e) {
			ui4Pre.showToast("app无法下载更新，SD卡不存在或没挂载");
			ui4Pre.closeUpdateProDlg();
		}	
	}

}
