package com.yun9.mobile.framework.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
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
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;

public class EditUserSignatureActivity extends Activity{
	
	public static int EDIT_SIGNATURE_ACTIVITY_RESULT = 100;
	
	private static final String SIGNATURE = "signature";


	private ImageView ivSend;
	private EditText etText;
	private TextView cancel;
	private String signature;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_signature);
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
		etText = (EditText) findViewById(R.id.text);
		 SessionManager manager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
		etText.setText(manager.getAuthInfo().getUserinfo().getSignature());
		etText.setSelection(etText.getText().length());
		ivSend.setOnClickListener(sendOnClickListener);
	}

	private void reset() {
		etText.setText("");

	}

	private OnClickListener sendOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Editable content = etText.getText();
			signature = content.toString();
			progressDialog = TipsUtil.openDialog(null, false, EditUserSignatureActivity.this);
			Resource sysMsgCardCommSaveRes = ResourceUtil.get("SysUserUpdateSignatureService");
			sysMsgCardCommSaveRes.param(SIGNATURE, signature);
			sysMsgCardCommSaveRes.invok(sendAsyncHttpResponseCallback);

		}
	};

	private AsyncHttpResponseCallback sendAsyncHttpResponseCallback = new AsyncHttpResponseCallback() {

		@Override
		public void onSuccess(Response response) {
			reset();
			progressDialog.dismiss();
			Intent intent = new Intent();
			intent.putExtra("signature", signature);
			setResult(EDIT_SIGNATURE_ACTIVITY_RESULT,intent);
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
		inputMethodManager.hideSoftInputFromWindow(EditUserSignatureActivity.this
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
