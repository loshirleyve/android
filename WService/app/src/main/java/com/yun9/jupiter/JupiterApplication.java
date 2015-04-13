package com.yun9.jupiter;

import android.app.Application;

import com.yun9.jupiter.context.AppContext;
import com.yun9.jupiter.context.support.DefaultAppContextFactory;
import com.yun9.jupiter.util.Logger;


public class JupiterApplication extends Application {

	public static JupiterApplication mInstance;

    public AppContext appContext;

	private static final Logger logger = Logger
			.getLogger(JupiterApplication.class);

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		logger.d("onCreate");
		// 初始化
		this.init();
	}

	private void init() {
        //设置日志级别
        Logger.setDebug(true);
        //初始化应用程序上下文
        appContext = DefaultAppContextFactory.getInstance().create(this.getApplicationContext());

	}

    public AppContext getAppContext(){
       return this.appContext;
    }

}
