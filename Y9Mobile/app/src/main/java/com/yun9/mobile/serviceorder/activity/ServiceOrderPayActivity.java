package com.yun9.mobile.serviceorder.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.ServiceOrder;
import com.yun9.mobile.serviceorder.fragment.ServiceOrderPayFragment;

/**
 * 订单支付
 * 
 * @author rxy
 *
 */
public class ServiceOrderPayActivity extends FragmentActivity {

	private Context mContext;
	private TextView titleview;
	private ImageButton returnButton;
	private TextView commit;
	private ServiceOrder order;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_serviceorder);
		initWeight();
		load();
	}

	public void initWeight() {
		mContext = getApplicationContext();
		titleview = (TextView) findViewById(R.id.title_txt);
		returnButton = (ImageButton) findViewById(R.id.return_btn);
		commit = (TextView) findViewById(R.id.commit);
		commit.setVisibility(View.GONE);
		returnButton.setVisibility(View.GONE);
		order = (ServiceOrder) getIntent().getSerializableExtra("order");
	}

	public void load() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ServiceOrderPayFragment fragment = new ServiceOrderPayFragment(
				mContext, returnButton, titleview, null, order, commit);
		ft.replace(R.id.fl_content, fragment,
				ServiceOrderActivity.class.getName());
		ft.commit();

	}

}
