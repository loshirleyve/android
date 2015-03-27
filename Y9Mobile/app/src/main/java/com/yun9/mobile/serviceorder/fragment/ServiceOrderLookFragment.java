package com.yun9.mobile.serviceorder.fragment;

import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.serviceorder.activity.ServiceOrderActivity;

/**
 * 查看订单 这个类还没完善是copy的没有 还没确定怎么处理查看订单页面
 * 
 * @author rxy
 *
 */
public class ServiceOrderLookFragment extends Fragment {

	private Context mContext;
	private View baseView;

	private ImageButton returnButton;
	private TextView titleview;
	private Map<String, Object> params;
	private LinearLayout contactLayout;
	private TextView commit_contact;
	private Button commit;

	public ServiceOrderLookFragment(Context context, ImageButton returnButton,
			TextView titleview, Map<String, Object> params, Button commit) {
		this.mContext = context;
		this.returnButton = returnButton;
		this.titleview = titleview;
		this.params = params;
		this.commit = commit;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater
				.inflate(R.layout.fragment_serviceorder_submit, null);
		initweight();
		bindEvn();
		return baseView;
	}

	public void initweight() {
		titleview.setText("附件资料");
		contactLayout = (LinearLayout) baseView
				.findViewById(R.id.commitContactLayout);
		contactLayout.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#b0000000")));
		commit_contact = (TextView) baseView.findViewById(R.id.commit_contact);
		commit_contact.setText("完成");
	}

	private void bindEvn() {
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				replace(params);
			}
		});

	}

	public void replace(Map<String, Object> params) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ServiceOrderListItemsFragment fragment = new ServiceOrderListItemsFragment(
				mContext, params, returnButton, titleview, commit);
		ft.replace(R.id.fl_content, fragment,
				ServiceOrderActivity.class.getName());
		ft.commit();
	}

	public void showToast(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
	}
}
