package com.yun9.mobile.framework.impls.presenter;

import java.io.File;

import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.util.Log;

import com.yun9.mobile.MainApplication;
import com.yun9.mobile.framework.engine.EngineUpdateApp;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.presenter4ui.Pre4UiMainActivity;
import com.yun9.mobile.framework.interfaces.ui4presenter.UI4PreMainActivity;
import com.yun9.mobile.framework.model.server.sys.ModelAppCheckForUpdateService;
import com.yun9.mobile.framework.util.UtilPackage;

public class ImplPre4UiMainActivity implements Pre4UiMainActivity{

	protected static final String tag = ImplPre4UiMainActivity.class.getSimpleName();
	protected Activity activity;
	protected EngineUpdateApp engineUpdateApp;
	protected boolean isFocus;
	protected boolean isUpdate;
	protected String log;
	protected String apkUrl;
	protected UI4PreMainActivity ui4pre;
	public ImplPre4UiMainActivity(Activity activity, UI4PreMainActivity ui4pre) {
		super();
		this.activity = activity;
		this.ui4pre = ui4pre;
		
		init();
	}
	
	private void init(){
		engineUpdateApp = new EngineUpdateApp();
		// 检查app更新
		checkAppUpdate();
	}
	

	
	
	private void checkAppUpdate() {
		engineUpdateApp.getUpdateInfo(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				ModelAppCheckForUpdateService appInfo = (ModelAppCheckForUpdateService) response.getPayload();
				
				MainApplication.mInstance.setAppUpdateInfo(appInfo);
//				appInfo.setFocus(false);
				
				isFocus = appInfo.isFocus();
				isUpdate = appInfo.isUpdate();
				log = appInfo.getLog();
				apkUrl = appInfo.getUrl();
				isFocus = false;
				
				
				if(!isUpdate){
					return ;
				}
				if(isFocus){
					// 强制升级
					ui4pre.showFocusUpdateDlg("有必须要更新的版本:" + appInfo.getInternalversion(), log);
				}else{
					ui4pre.showUpdateDlg("有新的版本的app:" + appInfo.getInternalversion(), log);
					// 提示是否升级
				}
			}
			
			@Override
			public void onFailure(Response response) {
				ui4pre.showToast("获取app更新信息失败");
			}
		});
	}

	@Override
	public void updateEnsureOnclickListener() {
		updateApp();
	}

	
	@Override
	public void updateCancelOnClickListener() {
		
	}
	
	
	private void updateApp(){
		try {
			engineUpdateApp.downApk(apkUrl, "yidiantong.apk", new AjaxCallBack<File>(){
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					ui4pre.closeUpdateProDlg();
					ui4pre.showToast("下载app失败" + strMsg);
					super.onFailure(t, errorNo, strMsg);
				}

				@Override
				public void onLoading(long count, long current) {
					super.onLoading(count, current);
					// 当前下载百分比
					int progress = (int) (current * 100 / count);
					String msg = "下载进度：" + progress + "%";
					ui4pre.showUpdateProDlg("更新app", msg);
				}

				@Override
				public void onSuccess(File t) {
					super.onSuccess(t);
					ui4pre.closeUpdateProDlg();
					UtilPackage.installAPK(t, activity);
					activity.finish();
				}
			});
		} catch (Exception e) {
			ui4pre.showToast("app无法下载更新，SD卡不存在或没挂载");
			ui4pre.closeUpdateProDlg();
		}	
	}
	
}
