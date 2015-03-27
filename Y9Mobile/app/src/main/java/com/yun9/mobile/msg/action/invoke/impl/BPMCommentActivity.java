package com.yun9.mobile.msg.action.invoke.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.JsonUtil;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.msg.action.entity.ActionContext;
import com.yun9.mobile.msg.action.entity.ActionParams;
import com.yun9.mobile.msg.model.MyMsgCard;

public class BPMCommentActivity extends Activity {
	

	private static final String PARAM_MSG_CARD_ID = "msgcardid";
	private static final String PARAM_MSG_CARD_COMMENT_CONTEXT = "content";

	private ImageView ivSend;
	private EditText editText;
	private TextView cancel;
	private ProgressDialog progressDialog;
	private static ActionContext actionContext;
	
	public static Activity startActivity(ActionContext ac)
    {
        actionContext = ac;
        Intent intent = new Intent(actionContext.getActivity(), BPMCommentActivity.class);
        actionContext.getActivity().startActivity(intent);
        BPMCommentActivity activity = new BPMCommentActivity();
        return activity;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_x5_comment);
		this.initView();
		this.bindEvt();
	}

	private void bindEvt() {
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				close();
			}
		});
	}

	private void initView() {
		ivSend = (ImageView) findViewById(R.id.send);
		cancel = (TextView) findViewById(R.id.cancel);
		editText = (EditText) findViewById(R.id.text);
		ivSend.setOnClickListener(sendOnClickListener);
	}

	private OnClickListener sendOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Editable content = editText.getText();

			if (!AssertValue.isNotNullAndNotEmpty(content.toString())) {
				Toast.makeText(getApplicationContext(),
						R.string.option_notnull, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			final String contentString = content.toString();
			progressDialog = TipsUtil.openDialog(null, false, BPMCommentActivity.this);
			
			doToDo(contentString,new AsyncHttpResponseCallback() {
				@Override
				public void onSuccess(Response response) {
					sendComment(actionContext.getParams().getHeader().getNodeName()+
							"("+actionContext.getLabel()+")："+contentString,sendNewCommentCallback);
				}
				
				@Override
				public void onFailure(Response response) {
					progressDialog.dismiss();
					Toast.makeText(getApplicationContext(),
							response.getCause(), Toast.LENGTH_SHORT).show();
				
				}
			});
			
		}
		private void doToDo(String contentString,
				AsyncHttpResponseCallback callback) {
			Resource bpmDoToDoService = ResourceUtil.get("BpmDoToDoService");
			SessionManager sessionManager = BeanConfig.getInstance()
					.getBeanContext().get(SessionManager.class);

			String userid = sessionManager.getAuthInfo().getUserinfo().getId();
			String instid = sessionManager.getAuthInfo().getInstinfo().getId();
			bpmDoToDoService.header(Resource.HEADER.USERID, userid);
			bpmDoToDoService.header(Resource.HEADER.INSTID, instid);
			
			ActionParams params = actionContext.getParams();
			params.getHeader().putString("opinion", contentString);
			
			bpmDoToDoService.param("ext", JsonUtil.fromString(params.getString()));
			bpmDoToDoService.invok(callback);
			
		}
		private void sendComment(String content,AsyncHttpResponseCallback callback) {
			Resource sysMsgCardCommSaveRes = ResourceUtil.get("SysMsgCardCommentSaveService");
			MyMsgCard msgCard = (MyMsgCard) actionContext.get(MyMsgCard.PARAMS_MSG_CARD);
			SessionManager sessionManager = BeanConfig.getInstance()
					.getBeanContext().get(SessionManager.class);

			String userid = sessionManager.getAuthInfo().getUserinfo().getId();
			String instid = sessionManager.getAuthInfo().getInstinfo().getId();

			sysMsgCardCommSaveRes.header(Resource.HEADER.USERID, userid);
			sysMsgCardCommSaveRes.header(Resource.HEADER.INSTID, instid);
			sysMsgCardCommSaveRes.param(PARAM_MSG_CARD_COMMENT_CONTEXT, content);
			sysMsgCardCommSaveRes.param(PARAM_MSG_CARD_ID, msgCard.getMain().getId());

			sysMsgCardCommSaveRes.invok(callback);
		}
	};
	
	private AsyncHttpResponseCallback sendNewCommentCallback = new AsyncHttpResponseCallback() {
		@Override
		public void onSuccess(Response response) {
			progressDialog.dismiss();
			actionContext.getHandler().callback(actionContext);
			Toast.makeText(getApplicationContext(),
					R.string.success, Toast.LENGTH_SHORT).show();
			finish();
		}
		
		@Override
		public void onFailure(Response response) {
			progressDialog.dismiss();
			Toast.makeText(getApplicationContext(),
					response.getCause(), Toast.LENGTH_SHORT).show();
		}
	};
	

	private void close() {
		editText.clearFocus();
		hideInputMethod();
		finish();
	}

	private void hideInputMethod() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(BPMCommentActivity.this
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

}
