package com.yun9.mobile.serviceorder.fragment;

import java.util.HashMap;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.ServiceOrder;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.serviceorder.activity.ServiceOrderActivity;
import com.yun9.mobile.serviceorder.callback.AsyncHttpServiceOrderCodeCallback;
import com.yun9.mobile.serviceorder.support.ServiceOrderService;

/**
 * 评论订单
 * 
 * @author rxy
 *
 */
public class ServiceOrderCommentFragment extends Fragment {

	private Context mContext;
	private View baseView;

	private ImageButton returnButton;
	private TextView titleview;
	private Map<String, Object> params;
	private ImageView productImage;
	private TextView companyname;
	private TextView servicename;
	private TextView servicedesc;
	private EditText comment_text;
	private EditText sorce_text;
	private ServiceOrder order;
	private TextView commit;

	private LinearLayout contactLayout;
	private TextView commit_contact;

	public ServiceOrderCommentFragment(Context context,
			ImageButton returnButton, TextView titleview, ServiceOrder order,
			Map<String, Object> params, TextView commit) {
		this.mContext = context;
		this.returnButton = returnButton;
		this.titleview = titleview;
		this.params = params;
		this.commit = commit;
		this.order = order;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_serviceorder_comment,
				null);
		initweight();
		bindEvn();
		return baseView;
	}

	public void initweight() {
		titleview.setText("发表评论");
		commit.setVisibility(View.GONE);
		productImage = (ImageView) baseView.findViewById(R.id.productImage);
		companyname = (TextView) baseView.findViewById(R.id.companyname);
		servicename = (TextView) baseView.findViewById(R.id.servicename);
		servicedesc = (TextView) baseView.findViewById(R.id.servicedesc);
		comment_text = (EditText) baseView.findViewById(R.id.comment_text);
		sorce_text = (EditText) baseView.findViewById(R.id.sorce_text);
		MyImageLoader.getInstance().displayImage(order.getProductimage(),
				productImage);
		companyname.setText(order.getBuyerinstname());
		servicename.setText(order.getProductname());
		servicedesc.setText(order.getProductdesc());

		contactLayout = (LinearLayout) baseView
				.findViewById(R.id.commitContactLayout);
		contactLayout.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#b0000000")));
		commit_contact = (TextView) baseView.findViewById(R.id.commit_contact);
		commit_contact.setText("发布");
	}

	private void bindEvn() {
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				replace(params);
			}
		});

		commit_contact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ServiceOrderService service = new ServiceOrderService();
				if (!AssertValue.isNotNullAndNotEmpty(sorce_text.getText()
						.toString())) {
					sorce_text.setText("5");
				}

				if (!AssertValue.isNotNullAndNotEmpty(comment_text.getText()
						.toString())) {
					showToast("评论内容不能为空哦");
				} else {
					Map<String, String> scoretext = new HashMap<String, String>();
					scoretext.put("orderid", order.getId());
					scoretext.put("scoretype", "order");
					scoretext
							.put("scorevalue", sorce_text.getText().toString());
					service.orderScoreCallBack(scoretext,
							new AsyncHttpServiceOrderCodeCallback() {

								@Override
								public void handler(String code) {
								}
							});

					Map<String, String> ordercomment = new HashMap<String, String>();
					ordercomment.put("orderid", order.getId());
					ordercomment.put("commenttype", "order");
					ordercomment.put("commenttext", comment_text.getText()
							.toString());
					service.orderCommentCallBack(ordercomment,
							new AsyncHttpServiceOrderCodeCallback() {

								@Override
								public void handler(String code) {
									if (code.equals("100"))
										showToast("评论内容成功");
									if (AssertValue
											.isNotNullAndNotEmpty(params))
										replace(params);
								}
							});
				}
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
