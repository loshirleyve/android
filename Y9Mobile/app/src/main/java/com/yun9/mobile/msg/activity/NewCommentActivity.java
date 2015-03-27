package com.yun9.mobile.msg.activity;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.yun9.mobile.framework.conf.PropertiesFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.StringUtils;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.msg.model.MyMsgCard;

/**
 * 新增评论Activity
 * @author yun9
 *
 */
public class NewCommentActivity extends Activity {
	
	public static int NEW_COMMENT_ACTIVITY_RESULT = 100;
	
	private static final String PARAM_MSG_CARD_ID = "msgcardid";
	private static final String PARAM_MSG_CARD_COMMENT_CONTEXT = "content";


	private ImageView ivSend;
	private EditText etText;
	private MyMsgCard msgCard;
	private TextView cancel;
	private ProgressDialog progressDialog;
	
	private PropertiesFactory propertiesFactory;
	private int limitWordNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_comment);
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
		propertiesFactory = BeanConfig.getInstance().getBeanContext().get(PropertiesFactory.class);
		ivSend = (ImageView) findViewById(R.id.send);
		cancel = (TextView) findViewById(R.id.cancel);
		etText = (EditText) findViewById(R.id.text);
		ivSend.setOnClickListener(sendOnClickListener);
		msgCard = (MyMsgCard) this.getIntent().getSerializableExtra(MyMsgCard.PARAMS_MSG_CARD);
		
		limitWordNum = propertiesFactory.getInt("app.config.msgcard.comment.limitnum", 100);
		
	}

	private void reset() {
		etText.setText("");

	}

	private OnClickListener sendOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Editable content = etText.getText();

			if (!AssertValue.isNotNullAndNotEmpty(content.toString())) {
				Toast.makeText(getApplicationContext(),
						R.string.msgcard_new_notnull, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			
			if (content.toString().length() > limitWordNum) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.msgcard_comment_limitnum, limitWordNum), Toast.LENGTH_SHORT)
						.show();
				return;
			}
			String byte4String = checkByte4(etText.getText().toString());
			if (AssertValue.isNotNullAndNotEmpty(byte4String)) {
				TipsUtil.showToast("包含不支持的字符串："+byte4String, getApplicationContext());
				return;
			}
			progressDialog = TipsUtil.openDialog(null, false, NewCommentActivity.this);

			Resource sysMsgCardCommSaveRes = ResourceUtil.get("SysMsgCardCommentSaveService");
			SessionManager sessionManager = BeanConfig.getInstance()
					.getBeanContext().get(SessionManager.class);

			String userid = sessionManager.getAuthInfo().getUserinfo().getId();

			sysMsgCardCommSaveRes.header(Resource.HEADER.USERID, userid);
			sysMsgCardCommSaveRes.param(PARAM_MSG_CARD_COMMENT_CONTEXT, content.toString());
			sysMsgCardCommSaveRes.param(PARAM_MSG_CARD_ID, msgCard.getMain().getId());

			sysMsgCardCommSaveRes.invok(sendAsyncHttpResponseCallback);

		}

		private String checkByte4(String source) {
			String byte4String = null;
			try {
				byte4String = StringUtils.checkByte4(source, null);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return byte4String;
		}
	};

	private AsyncHttpResponseCallback sendAsyncHttpResponseCallback = new AsyncHttpResponseCallback() {

		@Override
		public void onSuccess(Response response) {
			Toast.makeText(getApplicationContext(),
					R.string.msgcard_new_success, Toast.LENGTH_SHORT).show();
			reset();
			progressDialog.dismiss();
			setResult(NEW_COMMENT_ACTIVITY_RESULT);
			close();
		}

		@Override
		public void onFailure(Response response) {
			progressDialog.dismiss();
			Toast.makeText(getApplicationContext(),
					R.string.sys_exception_msg, Toast.LENGTH_SHORT).show();
		}
	};
	
	private void close() {
		hideInputMethod();
		finish();
	}
	
	private void hideInputMethod() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(NewCommentActivity.this
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
