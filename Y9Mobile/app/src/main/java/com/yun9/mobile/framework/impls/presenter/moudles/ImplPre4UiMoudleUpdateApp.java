package com.yun9.mobile.framework.impls.presenter.moudles;

import java.io.File;

import net.tsz.afinal.http.AjaxCallBack;
import android.content.Context;

import com.yun9.mobile.framework.engine.EngineUpdateApp;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.presenter4ui.moudles.Pre4UiMoudleUpdateApp;
import com.yun9.mobile.framework.interfaces.ui4presenter.moudles.UI4PreMoudleUpdateApp;
import com.yun9.mobile.framework.model.server.sys.ModelAppCheckForUpdateService;
import com.yun9.mobile.framework.util.UtilPackage;

public class ImplPre4UiMoudleUpdateApp implements Pre4UiMoudleUpdateApp{
	
	protected UI4PreMoudleUpdateApp ui4Pre;
	protected Context context;
	
	protected EngineUpdateApp engineUpdateApp;
	protected boolean isFocus;
	protected boolean isUpdate;
	protected String log;
	protected String apkUrl;
	
	/**
	 * @param ui4Pre
	 * @param context
	 */
	public ImplPre4UiMoudleUpdateApp(UI4PreMoudleUpdateApp ui4Pre,
			Context context) {
		super();
		this.ui4Pre = ui4Pre;
		this.context = context;
		
		init();
	}
	
	private void init(){
		engineUpdateApp = new EngineUpdateApp();
	}
	
	
	@Override
	public void work(){
		// 检查app更新
		checkAppUpdate();
	}
	
	
	private void checkAppUpdate() {
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
					return ;
				}
				if(isFocus){
					// 强制升级
					ui4Pre.showFocusUpdateDlg("有必须要更新的版本", log);
				}else{
					// 改成提示红图标
//					ui4Pre.showUpdateDlg("有新的版本的app", log);
				}
			}
			
			@Override
			public void onFailure(Response response) {
				ui4Pre.showToast("获取app更新信息失败");
			}
		});
	}

	/* （非 Javadoc）
	 * 点击更新
	 * @see com.yun9.mobile.framework.interfaces.presenter4ui.moudles.Pre4UiMoundleUpdateApp#updateEnsureOnclickListener()
	 */
	@Override
	public void updateEnsureOnclickListener() {
		updateApp();
	}

	/* （非 Javadoc）
	 * 点击取消更新
	 * @see com.yun9.mobile.framework.interfaces.presenter4ui.moudles.Pre4UiMoundleUpdateApp#updateCancelOnClickListener()
	 */
	@Override
	public void updateCancelOnClickListener() {
		
	}
	private void updateApp(){
		try {
			engineUpdateApp.downApk(apkUrl, "yidiantong.apk", new AjaxCallBack<File>(){
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
					UtilPackage.installAPK(t, context);
				}
			});
		} catch (Exception e) {
			ui4Pre.showToast("app无法下载更新，SD卡不存在或没挂载");
			ui4Pre.closeUpdateProDlg();
		}	
	}
}
