package com.yun9.mobile.serviceorder.fragment;

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
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.ServiceOrder;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.serviceorder.activity.ServiceOrderActivity;
import com.yun9.mobile.serviceorder.callback.AsyncHttpServiceOrderListCallback;
import com.yun9.mobile.serviceorder.fragment.ServiceOrderListItemsFragment.MyOrderListAdapter.OrderServiceView;
import com.yun9.mobile.serviceorder.support.ServiceOrderService;

public class ServiceOrderListItemsFragment extends Fragment {

	private Context mContext;
	private View baseView;
	private ListView serviceorderlist;
	private Map<String, Object> params;
	private List<ServiceOrder> serviceorders;
	private ImageButton returnButton;
	private TextView titleview;
	private TextView commit;

	private ProgressDialog progressDialog;
	public ServiceOrderListItemsFragment(Context context,
			Map<String, Object> params, ImageButton returnButton,
			TextView titleview, TextView commit) {
		this.mContext = context;
		this.params = params;
		this.returnButton = returnButton;
		this.titleview = titleview;
		this.commit = commit;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_serviceorder_list, null);
		initweight();
		return baseView;
	}

	public void initweight() {
		commit.setVisibility(View.GONE);
		serviceorderlist = (ListView) baseView
				.findViewById(R.id.serviceorderlist);
		load();
		returnButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ServiceOrderListFragment fragment = new ServiceOrderListFragment(
						mContext, returnButton, titleview, commit);
				ft.replace(R.id.fl_content, fragment,
						ServiceOrderActivity.class.getName());
				ft.commit();

			}
		});

	}

	public void load() {
		progressDialog = TipsUtil.openDialog(null, false, getActivity());
		ServiceOrderService service = new ServiceOrderService();
		service.getServiceOrderByStateCallBack(params,
				new AsyncHttpServiceOrderListCallback() {

					@Override
					public void handler(List<ServiceOrder> orders) {
						if (AssertValue.isNotNullAndNotEmpty(orders)) {
							serviceorders = orders;
							serviceorderlist
									.setAdapter(new MyOrderListAdapter());
						}
						progressDialog.dismiss();
					}
				});
	}

	class MyOrderListAdapter extends BaseAdapter {

		private LayoutInflater listContainer; // 视图容器

		public MyOrderListAdapter() {
			listContainer = LayoutInflater.from(mContext); // 创建视图容器并设置上下文
		}

		public final class OrderServiceView {
			private ImageView productImage;
			private TextView companyname;
			private TextView servicename;
			private TextView servicedesc;
			private TextView servicestate;
			private TextView servicemoney;
			private TextView servicesbumit;
			private TextView servicescomment;
			private TextView serviceslook;
			private TextView servicespay;
			private ServiceOrder order;
		}

		private OrderServiceView orderserviceview;

		@Override
		public int getCount() {
			if (AssertValue.isNotNullAndNotEmpty(serviceorders))
				return serviceorders.size();
			else
				return 0;

		}

		@Override
		public Object getItem(int position) {
			return serviceorders.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			orderserviceview = new OrderServiceView();
			if (convertView == null) {
				convertView = listContainer.inflate(
						R.layout.serviceorder_listview_items, null);
				orderserviceview.productImage = (ImageView) convertView
						.findViewById(R.id.productImage);
				orderserviceview.companyname = (TextView) convertView
						.findViewById(R.id.companyname);
				orderserviceview.servicename = (TextView) convertView
						.findViewById(R.id.servicename);
				orderserviceview.servicedesc = (TextView) convertView
						.findViewById(R.id.servicedesc);
				orderserviceview.servicestate = (TextView) convertView
						.findViewById(R.id.servicestate);
				orderserviceview.servicemoney = (TextView) convertView
						.findViewById(R.id.servicemoney);
				orderserviceview.servicesbumit = (TextView) convertView
						.findViewById(R.id.servicesbumit);
				orderserviceview.servicescomment = (TextView) convertView
						.findViewById(R.id.servicescomment);
				orderserviceview.serviceslook = (TextView) convertView
						.findViewById(R.id.serviceslook);
				orderserviceview.servicespay = (TextView) convertView
						.findViewById(R.id.servicespay);
				convertView.setTag(orderserviceview);
				orderserviceview.servicesbumit.setTag(orderserviceview);
				orderserviceview.servicescomment.setTag(orderserviceview);
				orderserviceview.serviceslook.setTag(orderserviceview);
				orderserviceview.servicespay.setTag(orderserviceview);
				orderserviceview.servicesbumit
						.setOnClickListener(submitoncListener);
				orderserviceview.servicescomment
						.setOnClickListener(commentoncListener);
				orderserviceview.serviceslook.setOnClickListener(null);
				orderserviceview.servicespay.setOnClickListener(payoncListener);
			} else {
				orderserviceview = (OrderServiceView) convertView.getTag();
			}
			orderserviceview.servicesbumit.setVisibility(View.GONE);
			orderserviceview.servicescomment.setVisibility(View.GONE);
			orderserviceview.servicespay.setVisibility(View.GONE);
			orderserviceview.serviceslook.setVisibility(View.GONE);
			MyImageLoader.getInstance().displayImage(
					serviceorders.get(position).getProductimage(),
					orderserviceview.productImage);
			orderserviceview.companyname.setText(serviceorders.get(position)
					.getBuyerinstname());
			orderserviceview.servicename.setText(serviceorders.get(position)
					.getProductname());
			if (serviceorders.get(position).getProductdesc().length() > 12) {
				orderserviceview.servicedesc.setText(serviceorders
						.get(position).getProductdesc().subSequence(0, 12)
						+ "...");
			} else {
			orderserviceview.servicedesc.setText(serviceorders.get(position)
					.getProductdesc());
			}
			orderserviceview.servicestate.setText(serviceorders.get(position)
					.getOrderstatecodename());
			orderserviceview.servicemoney.setText(serviceorders.get(position)
					.getMoneytimes());
			orderserviceview.order = serviceorders.get(position);
			if (serviceorders.get(position).getOrderstate()
					.equals("waitingdoc"))
				orderserviceview.servicesbumit.setVisibility(View.VISIBLE);
			if (serviceorders.get(position).getOrderstate()
					.equals("waitingcomment"))
				orderserviceview.servicescomment.setVisibility(View.VISIBLE);
			if (serviceorders.get(position).getOrderstate()
					.equals("waitingpay"))
				orderserviceview.servicespay.setVisibility(View.VISIBLE);
			if (serviceorders.get(position).getOrderstate().equals("running"))
				orderserviceview.serviceslook.setVisibility(View.VISIBLE);
			return convertView;
		}

	}

	OnClickListener submitoncListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			OrderServiceView orderserviceview = (OrderServiceView) v.getTag();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ServiceOrderSubmitFragment fragment = new ServiceOrderSubmitFragment(
					mContext, returnButton, titleview, orderserviceview.order,
					params, commit);
			ft.replace(R.id.fl_content, fragment,
					ServiceOrderActivity.class.getName());
			ft.commit();
		}
	};

	OnClickListener payoncListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			OrderServiceView orderserviceview = (OrderServiceView) v.getTag();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ServiceOrderPayFragment fragment = new ServiceOrderPayFragment(
					mContext, returnButton, titleview, params,
					orderserviceview.order, commit);
			ft.replace(R.id.fl_content, fragment,
					ServiceOrderActivity.class.getName());
			ft.commit();

			// Intent intent = new Intent(mContext,
			// ServiceOrderPayActivity.class);
			// intent.putExtra("order", (Serializable) orderserviceview.order);
			// startActivity(intent);
		}
	};

	OnClickListener commentoncListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			OrderServiceView orderserviceview = (OrderServiceView) v.getTag();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ServiceOrderCommentFragment fragment = new ServiceOrderCommentFragment(
					mContext, returnButton, titleview, orderserviceview.order,
					params, commit);
			ft.replace(R.id.fl_content, fragment,
					ServiceOrderActivity.class.getName());
			ft.commit();

		}
	};

}
