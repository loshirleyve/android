package com.yun9.mobile.push;

import android.content.Context;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.cache.Yun9Cache;
import com.yun9.mobile.framework.util.AssertValue;

public class PushSynchronization {

	public static final String CACHE_KEY_PUSH_TYPE = "push_type";
	
	static {
		Log.d(PushSynchronization.class.getSimpleName(), "清除pushType");
		Yun9Cache.getInstance().remove(CACHE_KEY_PUSH_TYPE);
	}

	public static synchronized boolean isBaidu() {
//		String cachePushType = Yun9Cache.getInstance().getAsString(
//				CACHE_KEY_PUSH_TYPE);
//		if (AssertValue.isNotNullAndNotEmpty(cachePushType)) {
//			return PushMessageReceiver.PUSH_TYPE.equals(cachePushType);
//		}
//		Yun9Cache.getInstance().put(CACHE_KEY_PUSH_TYPE, "baidu");
		return false;
	}

	public static synchronized boolean isXiaomi() {
		String cachePushType = Yun9Cache.getInstance().getAsString(
				CACHE_KEY_PUSH_TYPE);
		if (AssertValue.isNotNullAndNotEmpty(cachePushType)) {
			return MiPushMessageReceiver.PUSH_TYPE.equals(cachePushType);
		}
		Yun9Cache.getInstance().put(CACHE_KEY_PUSH_TYPE, "xiaomi");
		return true;
	}

	public static void registerPush(Context context) {
		// 启动百度push绑定
//		PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY,
//				Utils.getMetaValue(context, "api_key"));
		// 启用小米push绑定
		MiPushClient.registerPush(context,
				Utils.getMetaValue(context, "miapp_id"),
				Utils.getMetaValue(context, "miapi_key"));
	}

	public static void unregisterPush(Context context) {
		if (isBaidu()) {
			PushManager.stopWork(context);
		} else {
			MiPushClient.unregisterPush(context);
			PushDeviceRegisterFactory pushDeviceRegisterFactory = BeanConfig
					.getInstance().getBeanContext()
					.get(PushDeviceRegisterFactory.class);
			pushDeviceRegisterFactory.unBind(Yun9Cache.getInstance()
					.getAsString(MiPushMessageReceiver.CACHE_KEY_REG_ID));
		}
		Yun9Cache.getInstance().remove(CACHE_KEY_PUSH_TYPE);
	}
}
