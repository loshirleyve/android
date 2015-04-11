package com.yun9.wservice;

import android.app.Application;
import com.yun9.wservice.sys.AppConfig;
import com.yun9.wservice.util.Logger;


public class MainApplication extends Application {

	public static MainApplication mInstance;

	private static final Logger logger = Logger
			.getLogger(MainApplication.class);

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		Logger.setDebug(true);
		logger.d("onCreate");
		// 初始化
		this.init();
	}

	private void init() {
        //初始化应用,init方法只在第一次执行时有效
        AppConfig.getInstance().init(mInstance.getApplicationContext());
	}
}
