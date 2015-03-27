package com.yun9.mobile.framework.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.department.callback.AsyncHttpInstCallback;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.adapter.InstListAdatper;
import com.yun9.mobile.framework.annotation.view.ViewInject;
import com.yun9.mobile.framework.auth.AuthManager;
import com.yun9.mobile.framework.base.activity.FinalActivity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.exception.AuthException;
import com.yun9.mobile.framework.exception.NetworkException;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.Inst;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.Logger;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.usermanual.activity.UserManualActivity;

public class LoginActivity extends FinalActivity {

	@ViewInject(id = R.id.loginInstIdTextView)
	private TextView loginInstIdTextView;
	@ViewInject(id = R.id.loginPasswordTextView)
	private TextView loginPasswordTextView;
	
	
	@ViewInject(id = R.id.loginUserNoText)
	private EditText userNoEditText;
	@ViewInject(id = R.id.loginInstIdText)
	private EditText instEditText;
	@ViewInject(id = R.id.loginPasswordText)
	private EditText passwordEditText;
	
	@ViewInject(id = R.id.lv_inst_list, click = "onGetInstClick")
	private ListView lv_inst_list;
	@ViewInject(id = R.id.inst_list)
	private RelativeLayout inst_list;
	
	@ViewInject(id = R.id.loginGetInst, click = "onGetInstClick")
	private Button loginGetInst;
	@ViewInject(id = R.id.loginSubmitButton, click = "onLoginClick")
	private Button loginButton;
	@ViewInject(id = R.id.resetButton, click = "onResetClick")
	private Button resetButton;
	@ViewInject(id = R.id.moniButton, click = "onMoniClick")
	private Button moniButton;//演示系统
	@ViewInject(id = R.id.manualButton, click = "onManualClick")
	private Button manualButton;//新手教程
	
	private InstListAdatper mAdapter;
	private List<Inst> instList;
	private ProgressDialog progressDialog;
	private static final Logger logger = Logger.getLogger(LoginActivity.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_login);

//		this.userNoEditText.setText("7959598");
//		this.passwordEditText.setText("happy");
//		this.instEditText.setText("1");
	}

	public void onLoginClick(View v) {
		logger.d("begin login!");
		String userno = userNoEditText.getText().toString();
		String instId = instEditText.getText().toString();
		String password = passwordEditText.getText().toString();

		if (!AssertValue.isNotNullAndNotEmpty(userno)) {
			userNoEditText.setFocusable(true);
			showToast(R.string.login_userno_required);
			return;
		}

		if (!AssertValue.isNotNullAndNotEmpty(instId)) {
			instEditText.setFocusable(true);
			showToast(R.string.login_instid_required);
			return;
		}

		if (!AssertValue.isNotNullAndNotEmpty(password)) {
			passwordEditText.setFocusable(true);
			showToast(R.string.login_password_required);
			return;
		}

		openDialog();

		try {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("userno", userNoEditText.getText().toString());
			params.put("instid", instEditText.getText().toString());
			params.put("password", passwordEditText.getText().toString());

			AuthManager authManager = BeanConfig.getInstance().getBeanContext()
					.get(AuthManager.class);

			authManager.login(params, new AsyncHttpResponseCallback() {
				@Override
				public void onSuccess(Response response) {
					hideDialog();
					// 跳转到home
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}

				@Override
				public void onFailure(Response response) {
					hideDialog();
					//showToast(R.string.login_submit_error);
					showToast(response.getCause());
				}
			});

		} catch (AuthException e) {
			hideDialog();
			showToast(R.string.login_submit_error);
		} catch (NetworkException e) {
			hideDialog();
			showToast(R.string.network_error);
		} finally {

		}
	}
	/**
	 * 登录演示系统
	 * @param v
	 */
	public void onMoniClick(View v)
	{
		userNoEditText.setText("hjm");
		passwordEditText.setText("1");
		instEditText.setText("3");
		loginButton.performClick();
	}
	/**
	 * 查看用户使用手册
	 * @param v
	 */
	public void onManualClick(View v)
	{
		Intent intent=new Intent(getApplicationContext(), UserManualActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 获取用户所在的所有机构 
	 */
	public void onGetInstClick(View v)
	{
		progressDialog = TipsUtil.openDialog(null, false,this);
		String userno = userNoEditText.getText().toString();
		if (!AssertValue.isNotNullAndNotEmpty(userno)) {
			userNoEditText.setFocusable(true);
			showToast(R.string.login_userno_required);
			progressDialog.dismiss();
			return;
		}
		else
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userno", userNoEditText.getText().toString());
			DataInfoService data=new DataInfoService();
			data.getInstListCallback(params, new AsyncHttpInstCallback() {
				
				@Override
				public void handler(List<Inst> insts) {
					if(AssertValue.isNotNullAndNotEmpty(insts))
					{
						instList=insts;
						inst_list.setVisibility(View.VISIBLE);
						loginInstIdTextView.setVisibility(View.VISIBLE);
						mAdapter = new InstListAdatper(getApplicationContext(),insts,0);
				        lv_inst_list.setAdapter(mAdapter);
				        lv_inst_list.setOnItemClickListener(instOnItemListener);
				        instEditText.setText(insts.get(0).getId());
						loginGetInst.setVisibility(View.GONE);
						
						loginPasswordTextView.setVisibility(View.VISIBLE);
						passwordEditText.setVisibility(View.VISIBLE);
						loginButton.setVisibility(View.VISIBLE);
						resetButton.setVisibility(View.VISIBLE);
						loginPasswordTextView.setFocusable(true);
						progressDialog.dismiss();
					}
					else
					{
						progressDialog.dismiss();
						userNoEditText.setFocusable(true);
						showToast("此用户不存在,请核对信息重新输入！");
					}
				}
			});
		}
	}
	
	 private AdapterView.OnItemClickListener instOnItemListener = new AdapterView.OnItemClickListener()
	    {
	        @Override
	        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
	        {
	        	instEditText.setText(instList.get(i).getId());
	            mAdapter = new InstListAdatper(getApplicationContext(),instList,i);
	            lv_inst_list.setAdapter(mAdapter);
	            lv_inst_list.setSelection(i);
	        }
	    };
	    
	/**
	 * 重置
	 * @param v
	 */
    public void onResetClick(View v)
	{  
    	userNoEditText.setText("");
    	loginInstIdTextView.setVisibility(View.GONE);
    	inst_list.setVisibility(View.GONE);
    	loginGetInst.setVisibility(View.VISIBLE);
    	loginPasswordTextView.setVisibility(View.GONE);
    	passwordEditText.setText("");
		passwordEditText.setVisibility(View.GONE);
		loginButton.setVisibility(View.GONE);
		resetButton.setVisibility(View.GONE);
	}
}
