package com.yun9.mobile.mycustomer.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.BizSaleClient;
import com.yun9.mobile.mycustomer.callback.AsyncHttpMyCustomerCallback;
import com.yun9.mobile.mycustomer.fragment.AddCustomerFragment;
import com.yun9.mobile.mycustomer.fragment.MyCustomerList;
import com.yun9.mobile.mycustomer.support.MyCustomerService;

/**
 * 
 * 项目名称：WelcomeActivity 类名称： MyCustomerActivity 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2015-1-8下午4:59:37 修改人：ruanxiaoyu 修改时间：2015-1-8下午4:59:37 修改备注：
 * 
 * @version
 * 
 */
public class MyCustomerActivity extends FragmentActivity {

	private Button addcustomer;
	private ImageButton returnButton;
	private Button commit_addcustomer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycustomer);
		initWeight();
		load();
	}

	public void initWeight() {
		addcustomer = (Button) findViewById(R.id.addcustomer);
		commit_addcustomer = (Button) findViewById(R.id.commit_addcustomer);
		returnButton = (ImageButton) findViewById(R.id.return_btn);
		addcustomer.setOnClickListener(replaceAddCustomer);
	}

	OnClickListener replaceAddCustomer = new OnClickListener() {

		@Override
		public void onClick(View v) {
			addcustomer.setVisibility(View.GONE);
			commit_addcustomer.setVisibility(View.VISIBLE);
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			AddCustomerFragment customerFragment = new AddCustomerFragment(
					getApplicationContext(), returnButton, commit_addcustomer,
					addcustomer);
			ft.replace(R.id.fl_content, customerFragment,
					MyCustomerActivity.class.getName());
			ft.commit();
		}
	};

	public void load() {
		MyCustomerService service = new MyCustomerService();
		service.myCustomerCallBack(null, new AsyncHttpMyCustomerCallback() {

			@Override
			public void handler(List<BizSaleClient> clients) {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				MyCustomerList myCustomerList = new MyCustomerList(
						getApplicationContext(), clients, returnButton);
				ft.replace(R.id.fl_content, myCustomerList,
						MyCustomerActivity.class.getName());
				ft.commit();
			}
		});
	}

}
