package com.yun9.mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.baidu.frontia.FrontiaApplication;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.handler.CrashHandler;
import com.yun9.mobile.framework.model.server.sys.ModelAppCheckForUpdateService;
import com.yun9.mobile.framework.util.Logger;

public class MainApplication extends FrontiaApplication {

	public static MainApplication mInstance;
	
	private static final String TAG = MainApplication.class.getSimpleName();

	private static final Logger logger = Logger
			.getLogger(MainApplication.class);

	
	private ModelAppCheckForUpdateService appUpdateInfo;
	
	@Override
	public void onCreate() {
		super.onCreate();

		mInstance = this;
		Logger.setDebug(true);
		logger.d("onCreate");

		// 初始化异常处理
		CrashHandler.getInstance().init(getApplicationContext());
		// 初始化
		this.init();

	}

	private void init() {
		// 初始化Bean
		BeanConfig.getInstance().load(this.getApplicationContext());

		initImageLoader(getApplicationContext());

		this.initPush();
	}

	private void initImageLoader(Context context) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.flat_picture).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(50 * 1024 * 1024))
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.defaultDisplayImageOptions(options).writeDebugLogs()
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	private void initPush() {
		
	}

	public ModelAppCheckForUpdateService getAppUpdateInfo() {
		return appUpdateInfo;
	}

	public void setAppUpdateInfo(ModelAppCheckForUpdateService appUpdateInfo) {
		this.appUpdateInfo = appUpdateInfo;
	}
}
