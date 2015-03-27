package com.yun9.mobile.framework.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.os.Environment;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.impls.server.sys.ImpleAppCheckForUpdateService;
import com.yun9.mobile.framework.interfaces.engine.UiFaceUpdateApp;
import com.yun9.mobile.framework.interfaces.server.sys.AppCheckForUpdateService;
import com.yun9.mobile.framework.model.server.sys.ModelAppCheckForUpdateService;
import com.yun9.mobile.framework.util.UtilPackage;

/**
 * 
 * @author lhk
 *
 */
public class EngineUpdateApp {
	protected String apkUrl;
	private String APKDIR = "maoye";
	/**
	 * 
	 */
	public EngineUpdateApp() {
		super();
	}

	/**
	 * 获取更新信息
	 */
	public void getUpdateInfo(AsyncHttpResponseCallback callBack){
		AppCheckForUpdateService service = new ImpleAppCheckForUpdateService();
		service.getAppUpdateInfo(callBack);
	}
	
	
	public void downApk(String url, String apkDir, String apkName, AjaxCallBack<File> callBack) throws IOException{
		FinalHttp finalhttp = new FinalHttp();
		File file = new File(apkDir);
		String wholeName = apkDir + apkName;
		if(!file.exists()){
			boolean result = file.mkdirs();
			finalhttp.download(url, wholeName, callBack);
		}else{
			finalhttp.download(url, wholeName, callBack);
		}
	}
	
	public void downApk(String url, String apkName, AjaxCallBack<File> callBack) throws IOException{
		String apkDir = null;
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			apkDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + APKDIR + File.separatorChar;
			downApk(url, apkDir, apkName, callBack);
		}else{
			throw new FileNotFoundException("SD卡不存在或没挂载");
		}
	}
}
