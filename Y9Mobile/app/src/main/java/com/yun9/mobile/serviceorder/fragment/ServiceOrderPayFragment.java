package com.yun9.mobile.serviceorder.fragment;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.ServiceOrder;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.serviceorder.activity.ServiceOrderActivity;

/**
 * 订单支付
 * 
 * @author rxy
 *
 */
public class ServiceOrderPayFragment extends Fragment {

	private Context mContext;
	private View baseView;

	private ImageButton returnButton;
	private TextView titleview;
	private Map<String, Object> params;

	private ServiceOrder order;
	private TextView companyname;
	private TextView company_money;
	private TextView service_name;
	private TextView service_money;

	private TextView commit;

	// 现金支付
	private TextView zhifubao_text;
	private TextView weizhifu_text;
	private TextView yinghangzhuanzhang_text;
	private TextView xianjin_text;
	private TextView houfukuan_text;

	public ServiceOrderPayFragment(Context context, ImageButton returnButton,
			TextView titleview, Map<String, Object> params, ServiceOrder order,
			TextView commit) {
		this.mContext = context;
		this.returnButton = returnButton;
		this.titleview = titleview;
		this.params = params;
		this.order = order;
		this.commit = commit;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_serviceorder_pay, null);
		initweight();
		bindEvn();
		return baseView;
	}

	public void initweight() {
		titleview.setText("支付");
		commit.setVisibility(View.GONE);
		companyname = (TextView) baseView.findViewById(R.id.companyname);
		company_money = (TextView) baseView.findViewById(R.id.company_money);
		service_name = (TextView) baseView.findViewById(R.id.service_name);
		service_money = (TextView) baseView.findViewById(R.id.service_money);

		zhifubao_text = (TextView) baseView.findViewById(R.id.zhifubao_text);
		weizhifu_text = (TextView) baseView.findViewById(R.id.weizhifu_text);
		yinghangzhuanzhang_text = (TextView) baseView
				.findViewById(R.id.yinghangzhuanzhang_text);
		xianjin_text = (TextView) baseView.findViewById(R.id.xianjin_text);
		houfukuan_text = (TextView) baseView.findViewById(R.id.houfukuan_text);

		companyname.setText(order.getBuyerinstname());
		company_money.setText(order.getBuymoney().toString());
		service_name.setText(order.getProductname());
		service_money.setText(order.getMoneytimes().toString());
		if (!AssertValue.isNotNullAndNotEmpty(params))
			returnButton.setVisibility(View.GONE);
	}

	private void bindEvn() {
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				replace(params);
			}
		});

		zhifubao_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		weizhifu_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		yinghangzhuanzhang_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		houfukuan_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		xianjin_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ServiceOrderXianJinPayFragment fragment = new ServiceOrderXianJinPayFragment(
						mContext, returnButton, titleview, params, order,
						commit);
				ft.replace(R.id.fl_content, fragment,
						ServiceOrderActivity.class.getName());
				ft.commit();
			}
		});

	}

	public void replace(Map<String, Object> params) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		titleview.setText(order.getOrderstatecodename() + "订单");
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
