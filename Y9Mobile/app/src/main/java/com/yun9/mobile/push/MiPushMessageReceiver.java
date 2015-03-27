package com.yun9.mobile.push;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.cache.Yun9Cache;
import com.yun9.mobile.framework.util.AssertValue;

public class MiPushMessageReceiver extends PushMessageReceiver {

	public static final String CACHE_KEY_REG_ID = "xiaomi_reg_id";

	public static final String PUSH_TYPE = "xiaomi";
	
	/**
	 * 接收推送设备注册器
	 */
	private PushDeviceRegisterFactory pushDeviceRegisterFactory;
	
	/**
	 * 消息处理工厂
	 */
	private PushMessageActionFactory pushMessageActionFactory; 
	
	private String mRegId;
	private long mResultCode = -1;
	private String mReason;
	private String mCommand;
	private String mMessage;
	private String mTopic;
	private String mAlias;
	private String mStartTime;
	private String mEndTime;
	
	public MiPushMessageReceiver() {
		super();
		pushDeviceRegisterFactory = BeanConfig.getInstance().getBeanContext().get(PushDeviceRegisterFactory.class);
		pushMessageActionFactory = BeanConfig.getInstance().getBeanContext().get(PushMessageActionFactory.class);
	}

	@Override
	public void onReceiveMessage(Context context, MiPushMessage message) {
		mMessage = message.getContent();
		if (!TextUtils.isEmpty(message.getTopic())) {
			mTopic = message.getTopic();
		} else if (!TextUtils.isEmpty(message.getAlias())) {
			mAlias = message.getAlias();
		}
		if (AssertValue.isNotNullAndNotEmpty(mMessage)) {
			pushMessageActionFactory.dealWith(context,mMessage);
		}
	}

	@Override
	public void onCommandResult(Context context, MiPushCommandMessage message) {
		if (!PushSynchronization.isXiaomi()) {
			return;
		}
		String command = message.getCommand();
		List<String> arguments = message.getCommandArguments();
		String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments
				.get(0) : null);
		String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments
				.get(1) : null);
		if (MiPushClient.COMMAND_REGISTER.equals(command)) {
			if (message.getResultCode() == ErrorCode.SUCCESS) {
				mRegId = cmdArg1;
				pushDeviceRegisterFactory.bind(Utils.getMetaValue(context, "miapp_id"), cmdArg1, null, null,PUSH_TYPE);
				Yun9Cache.getInstance().put(CACHE_KEY_REG_ID, cmdArg1);
			}
		} else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
			if (message.getResultCode() == ErrorCode.SUCCESS) {
				mAlias = cmdArg1;
			}
		} else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
			if (message.getResultCode() == ErrorCode.SUCCESS) {
				mAlias = cmdArg1;
			}
		} else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
			if (message.getResultCode() == ErrorCode.SUCCESS) {
				mTopic = cmdArg1;
			}
		} else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
			if (message.getResultCode() == ErrorCode.SUCCESS) {
				mTopic = cmdArg1;
			}
		} else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
			if (message.getResultCode() == ErrorCode.SUCCESS) {
				mStartTime = cmdArg1;
				mEndTime = cmdArg2;
			}
		}
	}
}
