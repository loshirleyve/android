package com.yun9.mobile.serviceorder.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.BizSaleOrderList;
import com.yun9.mobile.framework.model.BizStateObjet;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.serviceorder.activity.ServiceOrderActivity;
import com.yun9.mobile.serviceorder.callback.AsyncHttpServiceOrderCallback;
import com.yun9.mobile.serviceorder.support.ServiceOrderService;

public class ServiceOrderListFragment extends Fragment {

	private Context mContext;
	private View baseView;

	private RelativeLayout allorders;
	private RelativeLayout nopanyment_order;
	private RelativeLayout no_submited_information_order;
	private RelativeLayout servicing_order;
	private RelativeLayout comment_order;
	private RelativeLayout complete_order;

	private TextView allorders_tishitext;
	private TextView nopanyment_order_tishitext;
	private TextView no_submited_information_order_tishitext;
	private TextView servicing_order_tishitext;
	private TextView comment_order_tishitext;
	private TextView complete_order_tishitext;
	private Map<String, Object> params;
	private ImageButton returnButton;
	private TextView titleview;
	private TextView commit;

	private ProgressDialog progressDialog;

	public ServiceOrderListFragment(Context context, ImageButton returnButton,
			TextView titleview, TextView commit) {
		this.mContext = context;
		this.returnButton = returnButton;
		this.titleview = titleview;
		this.commit = commit;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_serviceorder, null);
		initweight();
		bindEvn();
		return baseView;
	}

	public void initweight() {
		titleview.setText("服务订单");
		commit.setVisibility(View.GONE);
		commit.setVisibility(View.GONE);
		allorders_tishitext = (TextView) baseView
				.findViewById(R.id.allorder_tishitext);
		nopanyment_order_tishitext = (TextView) baseView
				.findViewById(R.id.nopanyment_order_tishitext);
		no_submited_information_order_tishitext = (TextView) baseView
				.findViewById(R.id.no_submited_information_order_tishitext);
		servicing_order_tishitext = (TextView) baseView
				.findViewById(R.id.servicing_order_tishitext);
		comment_order_tishitext = (TextView) baseView
				.findViewById(R.id.comment_order_tishitext);
		complete_order_tishitext = (TextView) baseView
				.findViewById(R.id.complete_order_tishitext);

		allorders = (RelativeLayout) baseView.findViewById(R.id.allorders);
		nopanyment_order = (RelativeLayout) baseView
				.findViewById(R.id.nopanyment_order);
		no_submited_information_order = (RelativeLayout) baseView
				.findViewById(R.id.no_submited_information_order);
		servicing_order = (RelativeLayout) baseView
				.findViewById(R.id.servicing_order);
		comment_order = (RelativeLayout) baseView
				.findViewById(R.id.comment_order);
		complete_order = (RelativeLayout) baseView
				.findViewById(R.id.complete_order);
	}

	private void bindEvn() {
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});

		allorders.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!allorders_tishitext.getText().equals("0")) {
					titleview.setText("全部订单");
					replace(null);
				} else
					showToast("没有任何订单信息哦");
			}
		});
		nopanyment_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!nopanyment_order_tishitext.getText().equals("0")) {
					params = new HashMap<String, Object>();
					params.put("state", "waitingpay");
					titleview.setText("待支付订单");
					replace(params);
				} else
					showToast("没有待支付订单哦");
			}
		});
		no_submited_information_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!no_submited_information_order_tishitext.getText().equals(
						"0")) {
					params = new HashMap<String, Object>();
					params.put("state", "waitingdoc");
					titleview.setText("待提交资料订单");
					replace(params);
				} else
					showToast("没有待提交资料订单哦");
			}
		});
		servicing_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!servicing_order_tishitext.getText().equals("0")) {
					params = new HashMap<String, Object>();
					params.put("state", "running");
					titleview.setText("服务中订单");
					replace(params);
				} else
					showToast("没有服务中订单哦");
			}
		});
		comment_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!comment_order_tishitext.getText().equals("0")) {
					params = new HashMap<String, Object>();
					params.put("state", "waitingcomment");
					titleview.setText("待评价订单");
					replace(params);
				} else
					showToast("没有待评价订单哦");
			}
		});
		complete_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!complete_order_tishitext.getText().equals("0")) {
					params = new HashMap<String, Object>();
					params.put("state", "complete");
					titleview.setText("已完成订单");
					replace(params);
				} else
					showToast("没有已完成订单哦");
			}
		});
		load();
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

	public void load() {
		progressDialog = TipsUtil.openDialog(null, false, getActivity());
		ServiceOrderService service = new ServiceOrderService();
		service.getGroupOrderCallBack(null,
				new AsyncHttpServiceOrderCallback() {

					@Override
					public void handler(BizSaleOrderList order) {
						if (AssertValue.isNotNull(order)) {
							allorders_tishitext.setText(String.valueOf(order
									.getCountnum()));
							List<BizStateObjet> orderobject = order
									.getBizlist();
							if (AssertValue.isNotNullAndNotEmpty(orderobject)) {
								for (BizStateObjet bizStateObjet : orderobject) {
									if (bizStateObjet.getState().equals(
											"complete")) {
										complete_order_tishitext.setText(String
												.valueOf(bizStateObjet
														.getNumx()));
									}
									if (bizStateObjet.getState().equals(
											"waitingpay")) {
										nopanyment_order_tishitext
												.setText(String
														.valueOf(bizStateObjet
																.getNumx()));
									}
									if (bizStateObjet.getState().equals(
											"waitingdoc")) {
										no_submited_information_order_tishitext
												.setText(String
														.valueOf(bizStateObjet
																.getNumx()));
									}
									if (bizStateObjet.getState().equals(
											"running")) {
										servicing_order_tishitext
												.setText(String
														.valueOf(bizStateObjet
																.getNumx()));
									}
									if (bizStateObjet.getState().equals(
											"waitingcomment")) {
										comment_order_tishitext.setText(String
												.valueOf(bizStateObjet
														.getNumx()));

									}
								}
							}

						}
						progressDialog.dismiss();
					}
				});
	}

	public void showToast(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
	}
}
