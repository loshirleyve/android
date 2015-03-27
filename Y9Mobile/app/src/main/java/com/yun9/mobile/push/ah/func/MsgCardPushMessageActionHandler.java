package com.yun9.mobile.push.ah.func;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.msg.activity.MsgCardActivity;
import com.yun9.mobile.msg.model.MyMsgCard;
import com.yun9.mobile.push.HandlerType;

public class MsgCardPushMessageActionHandler extends
		AbsFuncPushMessageActionHandler {
	
	private static final String TAG = MsgCardPushMessageActionHandler.class.getSimpleName();

	private static final String PARAM_MSG_CARD_ID = "msgcardid";

	@Override
	public void handle(final Context context, JsonObject actionContext) {
		JsonObject params = actionContext.getAsJsonObject("params");
		String msgcardid = params.get(PARAM_MSG_CARD_ID).getAsString();
		if (!AssertValue.isNotNullAndNotEmpty(msgcardid)) {
			Toast.makeText(context, "无法获取消息卡片ID", Toast.LENGTH_SHORT).show();
			return;
		}
		Log.d(TAG, "正在进入消息卡片："+msgcardid);
		this.enter(msgcardid, new AsyncHttpResponseCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Response response) {
				List<MyMsgCard> msgCards =  (List<MyMsgCard>) response.getPayload();
				Intent intent = new Intent(context,
						MsgCardActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				Bundle bundle = new Bundle();
				bundle.putSerializable("msgcard", msgCards.get(0));
				intent.putExtras(bundle);
				context.startActivity(intent);
			}

			@Override
			public void onFailure(Response response) {
				Toast.makeText(context, response.getCause(), Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void enter(String msgcardid, AsyncHttpResponseCallback callback) {
		Resource resource = ResourceUtil
				.get("SysMsgCardQueryMyCardByIdService");
		resource.param("id", msgcardid);
		resource.setFromService(true);
		resource.invok(callback);
	}

	@Override
	public HandlerType getHandlerType() {
		return HandlerType.FUNC_MSGCARD;
	}

}
