package com.yun9.mobile.mycustomer.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.BizSaleClient;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.mycustomer.activity.MyCustomerActivity;
import com.yun9.mobile.mycustomer.callback.AsyncHttpMyCustomerCallback;
import com.yun9.mobile.mycustomer.callback.AsyncHttpSaleClientCallback;
import com.yun9.mobile.mycustomer.support.MyCustomerService;

/**
 * 
 * 项目名称：WelcomeActivity 类名称： AddCustomerFragment 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2015-1-8下午5:49:29 修改人：ruanxiaoyu 修改时间：2015-1-8下午5:49:29 修改备注：
 * 
 * @version
 * 
 */
public class AddCustomerFragment extends Fragment {
	private Context mContext;
	private View baseView;
	private EditText customer_name;
	private EditText customer_fullname;
	private EditText customer_contact;
	private EditText customer_phone;
	private EditText customer_contactposition;
	private EditText customer_region;
	private EditText customer_source;
	private EditText customer_type;
	private EditText customer_industry;
	private EditText customer_address;
	private ImageButton returnButton;
	private Button addcommit;
	private Button addcustomer;

	public AddCustomerFragment(Context context, ImageButton returnButton,
			Button addcommit, Button addcustomer) {
		this.mContext = context;
		this.returnButton = returnButton;
		this.addcommit = addcommit;
		this.addcustomer = addcustomer;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_addcustomer, null);
		initWeight();
		return baseView;
	}

	public void initWeight() {
		customer_name = (EditText) baseView.findViewById(R.id.customer_name);
		customer_fullname = (EditText) baseView
				.findViewById(R.id.customer_fullname);
		customer_contact = (EditText) baseView
				.findViewById(R.id.customer_contact);
		customer_phone = (EditText) baseView.findViewById(R.id.customer_phone);
		customer_address = (EditText) baseView
				.findViewById(R.id.customer_address);
		customer_contactposition = (EditText) baseView
				.findViewById(R.id.customer_contactposition);
		customer_region = (EditText) baseView
				.findViewById(R.id.customer_region);
		customer_source = (EditText) baseView
				.findViewById(R.id.customer_source);
		customer_type = (EditText) baseView.findViewById(R.id.customer_type);
		customer_industry = (EditText) baseView
				.findViewById(R.id.customer_industry);
		addcommit.setOnClickListener(addCustomer);
		returnButton.setOnClickListener(returnbtn);
	}

	// 重写父对象的retuan按钮事件
	OnClickListener returnbtn = new OnClickListener() {

		@Override
		public void onClick(View v) {
			MyCustomerService service = new MyCustomerService();
			service.myCustomerCallBack(null, new AsyncHttpMyCustomerCallback() {

				@Override
				public void handler(List<BizSaleClient> clients) {
					FragmentManager fm = getFragmentManager();
					FragmentTransaction ft = fm.beginTransaction();
					MyCustomerList myCustomerList = new MyCustomerList(
							mContext, clients, returnButton);
					ft.replace(R.id.fl_content, myCustomerList,
							MyCustomerActivity.class.getName());
					ft.commit();
					addcommit.setVisibility(View.GONE);
					addcustomer.setVisibility(View.VISIBLE);
				}
			});

		}
	};

	OnClickListener addCustomer = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!AssertValue.isNotNullAndNotEmpty(customer_name.getText()
					.toString())) {
				customer_name.setFocusable(true);
				showToast("请输入客户简称");
			}
			if (!AssertValue.isNotNullAndNotEmpty(customer_fullname.getText()
					.toString())) {
				customer_fullname.setFocusable(true);
				showToast("请输入客户全称");
			}
			if (!AssertValue.isNotNullAndNotEmpty(customer_contact.getText()
					.toString())) {
				customer_contact.setFocusable(true);
				showToast("请输入联系人");
			}
			if (!AssertValue.isNotNullAndNotEmpty(customer_phone.getText()
					.toString())) {
				customer_phone.setFocusable(true);
				showToast("请输入联系人电话");
			}
			if (!AssertValue.isNotNullAndNotEmpty(customer_contactposition
					.getText().toString())) {
				customer_contactposition.setFocusable(true);
				showToast("请输入联系人职位");
			}
			if (!AssertValue.isNotNullAndNotEmpty(customer_region.getText()
					.toString())) {
				customer_region.setFocusable(true);
				showToast("请输入公司所属区域");
			}
			if (!AssertValue.isNotNullAndNotEmpty(customer_source.getText()
					.toString())) {
				customer_source.setFocusable(true);
				showToast("请输入公司签约来源");
			}
			if (!AssertValue.isNotNullAndNotEmpty(customer_type.getText()
					.toString())) {
				customer_type.setFocusable(true);
				showToast("请输入公司类型");
			}
			if (!AssertValue.isNotNullAndNotEmpty(customer_industry.getText()
					.toString())) {
				customer_industry.setFocusable(true);
				showToast("请输入经营行业类型");
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", customer_name.getText().toString());
			params.put("fullname", customer_fullname.getText().toString());
			params.put("contactman", customer_contact.getText().toString());
			params.put("contactphone", customer_phone.getText().toString());
			params.put("address", customer_address.getText().toString());
			params.put("contactposition", customer_contactposition.getText()
					.toString());
			params.put("region", customer_region.getText().toString());
			params.put("source", customer_source.getText().toString());
			params.put("type", customer_type.getText().toString());
			params.put("industry", customer_industry.getText().toString());
			params.put("level", "A");
			MyCustomerService service = new MyCustomerService();
			service.addCustomerCallBack(params,
					new AsyncHttpSaleClientCallback() {

						@Override
						public void handler(BizSaleClient client) {
							if (client != null)
								showToast("添加客戶成功，您可以用联系方式作为用户名，密码登录");
							else
								showToast("添加客戶失败");
						}
					});
		}
	};

	public void showToast(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
	}

}
