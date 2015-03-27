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
import com.yun9.mobile.serviceorder.fragment.ServiceOrderListFragment;

public class ServiceOrderActivity extends FragmentActivity {

	private Context mContext;
	private TextView titleview;
	private ImageButton returnButton;
	private TextView commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serviceorder);
		initWeight();
		load();
	}

	public void initWeight() {
		mContext = getApplicationContext();
		titleview = (TextView) findViewById(R.id.title_txt);
		returnButton = (ImageButton) findViewById(R.id.return_btn);
		commit = (TextView) findViewById(R.id.commit);
		commit.setVisibility(View.GONE);
	}

	public void load() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ServiceOrderListFragment fragment = new ServiceOrderListFragment(
				mContext, returnButton, titleview, commit);
		ft.replace(R.id.fl_content, fragment,
				ServiceOrderActivity.class.getName());
		ft.commit();
	}

}
