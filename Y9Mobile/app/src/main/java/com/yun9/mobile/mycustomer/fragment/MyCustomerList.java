package com.yun9.mobile.mycustomer.fragment;

import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.BizSaleClient;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.mycustomer.activity.MyCustomerDetailActivity;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   MyCustomerList
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2015-1-12上午11:59:20
 * 修改人：ruanxiaoyu  
 * 修改时间：2015-1-12上午11:59:20  
 * 修改备注：    
 * @version     
 *     
 */
public class MyCustomerList extends Fragment{
	private Context mContext;
	private View baseView;
	private ListView mycustomerList;
	private List<BizSaleClient> mycustomers;
	private LayoutInflater listContainer; // 视图容器
	public final class Customer { // 自定义控件集合
		public TextView companyname;
		public TextView contactname;
		public TextView phone;
		public TextView type;
		public BizSaleClient client;
	}
	private ImageButton returnButton;
	private Customer customerView;
	public MyCustomerList(Context context,List<BizSaleClient> mycustomers,ImageButton returnButton)
	{
		this.mContext=context;
		this.mycustomers=mycustomers;
		this.returnButton=returnButton;
		listContainer = LayoutInflater.from(mContext); // 创建视图容器并设置上下文
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_mycustomer_list, null);
		initweight();
		return baseView;
	}

	public void initweight()
	{
		mycustomerList=(ListView)baseView.findViewById(R.id.mycustomerList);
		mycustomerList.setAdapter(new ListAdapter());
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
	});
	}
	
	class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if(AssertValue.isNotNullAndNotEmpty(mycustomers))
				return mycustomers.size();
			else
				return 0;
			
		}

		@Override
		public Object getItem(int position) {
			return mycustomers.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null)
			{
				customerView=new Customer();
				convertView = listContainer.inflate(R.layout.mycustomer_listview_items,
						null);
				customerView.companyname=(TextView)convertView.findViewById(R.id.companyname);
				customerView.contactname=(TextView)convertView.findViewById(R.id.contactname);
				customerView.phone=(TextView)convertView.findViewById(R.id.phone);
				customerView.type=(TextView)convertView.findViewById(R.id.type);
				convertView.setTag(customerView);
				convertView.setOnClickListener(onclickdetail);
			}
			else
			{
				customerView=(Customer)convertView.getTag();
			}
			customerView.companyname.setText(mycustomers.get(position).getFullname().trim());
			customerView.contactname.setText(mycustomers.get(position).getContactman().trim());
			customerView.phone.setText("电话："+mycustomers.get(position).getContactphone().trim());
			customerView.type.setText(mycustomers.get(position).getLevel().trim()+"类客户");
			customerView.client=mycustomers.get(position);
			return convertView;
		}
		
	}
	
	
	OnClickListener onclickdetail=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			getActivity().finish();
			Customer c=(Customer)v.getTag();
			Intent intent = new Intent(mContext, MyCustomerDetailActivity.class);
			intent.putExtra("customer",(Serializable)c.client); 
			startActivity(intent);
		}
	};

}
