package com.yun9.mobile.serviceorder.fragment;

import java.util.HashMap;
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
import com.yun9.mobile.serviceorder.callback.AsyncHttpServiceOrderCodeCallback;
import com.yun9.mobile.serviceorder.support.ServiceOrderService;

/**
 * 订单支付
 * 
 * @author rxy
 *
 */
public class ServiceOrderXianJinPayFragment extends Fragment {

	private Context mContext;
	private View baseView;

	private ImageButton returnButton;
	private TextView titleview;
	private Map<String, Object> params;

	private ServiceOrder order;
	private TextView companyname;
	private TextView company_money;

	private TextView commit;

	public ServiceOrderXianJinPayFragment(Context context,
			ImageButton returnButton, TextView titleview,
			Map<String, Object> params, ServiceOrder order, TextView commit) {
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
		baseView = inflater.inflate(R.layout.fragment_serviceorder_xianjinpay,
				null);
		initweight();
		bindEvn();
		return baseView;
	}

	public void initweight() {
		titleview.setText("现金支付页面");
		returnButton.setVisibility(View.VISIBLE);
		companyname = (TextView) baseView.findViewById(R.id.companyname);
		company_money = (TextView) baseView.findViewById(R.id.company_money);
		commit.setVisibility(View.VISIBLE);
		companyname.setText(order.getBuyerinstname());
		company_money.setText(order.getSalemoney().toString());
	}

	private void bindEvn() {
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				replace(params);
			}
		});

		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!AssertValue.isNotNullAndNotEmpty(company_money.getText()
						.toString())) {
					showToast("支付金额不能为空哦");

				} else {
					ServiceOrderService service = new ServiceOrderService();

					Map<String, String> orderparams = new HashMap<String, String>();

					// ordersn |y |订单编号

					// |paymode |Y |支付方式

					// |payerinstid |Y |客户机构id

					// |source |Y |收款信息来源

					// |collectamount |Y |应收金额

					// |overduerate |Y |滞纳金比率

					// |payamount |Y |实付金额

					// |collectdate |Y |收款日期（此参数格式待确认，暂时使用当前时间）

					orderparams.put("ordersn", order.getOrdersn());

					orderparams.put("paymode", "xianjinzhifu");

					orderparams.put("source", "so");

					orderparams.put("collectamount", order.getSalemoney()
							.toString());

					orderparams.put("payamount", company_money.getText()
							.toString());

					service.payOrderCallBack(orderparams,

					new AsyncHttpServiceOrderCodeCallback() {

						@Override
						public void handler(String code) {

							if (code.equals("100")) {

								showToast("付款成功，正在审核中");
								if (AssertValue.isNotNullAndNotEmpty(params))
									replaceOnPaySuccess(params);
								else
									getActivity().finish();
							}
						}

					});
				}

			}

		});

	}

	public void replace(Map<String, Object> params) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ServiceOrderPayFragment fragment = new ServiceOrderPayFragment(
				mContext, returnButton, titleview, params, order, commit);
		ft.replace(R.id.fl_content, fragment,
				ServiceOrderActivity.class.getName());
		ft.commit();
	}

	public void replaceOnPaySuccess(Map<String, Object> params) {
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
