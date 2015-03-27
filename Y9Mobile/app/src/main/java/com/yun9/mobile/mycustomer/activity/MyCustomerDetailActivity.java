package com.yun9.mobile.mycustomer.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.BizSaleClient;
import com.yun9.mobile.mycustomer.callback.AsyncHttpSaleClientCallback;
import com.yun9.mobile.mycustomer.support.MyCustomerService;

/**
 * 
 * 项目名称：WelcomeActivity 类名称： MyCustomerActivity 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2015-1-8下午4:59:37 修改人：ruanxiaoyu 修改时间：2015-1-8下午4:59:37 修改备注：
 * 
 * @version
 * 
 */
public class MyCustomerDetailActivity extends FragmentActivity {

	private TextView titleview;
	private ImageButton returnButton;
	private LinearLayout levelqian;
	private Button setupstate;
	private TextView customercontact;
	private TextView type;
	private TextView customerphone;
	private TextView customeraddress;
	private LinearLayout levelvalue;
	private BizSaleClient customer;
	private RadioGroup levelgroup;
	private RadioButton qianyue;
	private RadioButton alei;
	private RadioButton blei;
	private RadioButton clei;
	private Button commit;
	private String level;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycustomer_setuplevel);
		initWeight();
		load();
	}

	public void initWeight() {
		customer = (BizSaleClient) getIntent().getSerializableExtra("customer");
		titleview = (TextView) findViewById(R.id.title_txt);
		levelqian = (LinearLayout) findViewById(R.id.levelqian);
		setupstate = (Button) findViewById(R.id.setupstate);
		customercontact = (TextView) findViewById(R.id.customercontact);
		type = (TextView) findViewById(R.id.type);
		customerphone = (TextView) findViewById(R.id.customerphone);
		customeraddress = (TextView) findViewById(R.id.customeraddress);
		levelvalue = (LinearLayout) findViewById(R.id.levelvalue);
		levelgroup = (RadioGroup) findViewById(R.id.level);
		qianyue = (RadioButton) findViewById(R.id.qianyue);
		alei = (RadioButton) findViewById(R.id.alei);
		blei = (RadioButton) findViewById(R.id.blei);
		clei = (RadioButton) findViewById(R.id.clei);
		commit = (Button) findViewById(R.id.commit);
		returnButton = (ImageButton) findViewById(R.id.return_btn);
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				Intent intent = new Intent(getApplicationContext(),
						MyCustomerActivity.class);
				startActivity(intent);
			}
		});
		if (customer.getLevel().trim().equals("已签约"))
			levelqian.setVisibility(View.VISIBLE);
		else {
			setupstate.setVisibility(View.VISIBLE);
			setupstate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					levelvalue.setVisibility(View.VISIBLE);
					setupstate.setVisibility(View.GONE);
				}
			});
			levelgroup
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							if (qianyue.getId() == checkedId)
								level = qianyue.getText().toString();
							if (alei.getId() == checkedId)
								level = alei.getText().toString();
							if (blei.getId() == checkedId)
								level = blei.getText().toString();
							if (clei.getId() == checkedId)
								level = clei.getText().toString();

						}
					});

			commit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					MyCustomerService service = new MyCustomerService();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", customer.getId());
					params.put("level", level);
					params.put("name", customer.getName());
					params.put("sn", customer.getSn());
					params.put("instid", customer.getInstid());
					params.put("fullname", customer.getFullname());
					params.put("contactman", customer.getContactman());
					params.put("contactphone", customer.getContactphone());
					params.put("address", customer.getAddress());
					params.put("createdate", System.currentTimeMillis());
					params.put("region", customer.getRegion());
					params.put("source", customer.getSource());
					params.put("type", customer.getType());
					params.put("industry", customer.getIndustry());
					params.put("contactposition", customer.getContactposition());
					service.addCustomerCallBack(params,
							new AsyncHttpSaleClientCallback() {

								@Override
								public void handler(BizSaleClient client) {
									if (client != null) {
										type.setText(client.getLevel() + "类客户");
										showToast("变更状态成功!");
									} else
										showToast("变更失败！");
								}
							});
				}
			});
		}

	}

	public void load() {
		titleview.setText(customer.getFullname().trim());
		customercontact.setText(customer.getContactman().trim());
		type.setText(customer.getLevel().trim() + "类客户");
		customerphone.setText(customer.getContactphone().trim());
		customeraddress.setText(customer.getAddress().trim());
		if (customer.getLevel().trim()
				.equals(qianyue.getText().toString().trim()))
			qianyue.setChecked(true);
		if (customer.getLevel().trim().equals(alei.getText().toString().trim()))
			alei.setChecked(true);
		if (customer.getLevel().trim().equals(blei.getText().toString().trim()))
			blei.setChecked(true);
		if (customer.getLevel().trim().equals(clei.getText().toString().trim()))
			clei.setChecked(true);
	}

	public void showToast(String content) {
		Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT)
				.show();
	}

}
