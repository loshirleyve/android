package com.yun9.mobile.usermanual.fragment;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.UserManual;
import com.yun9.mobile.framework.util.AssertValue;
/**
 * 
 * 项目名称：yun9mobile 类名称： UserViewPageAdapter 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2014-10-20上午10:07:19 修改人：ruanxiaoyu 修改时间：2014-10-20上午10:07:19 修改备注：
 * 
 * @version
 * 
 */
public class UserManualAdapter extends BaseAdapter {
	private List<UserManual> listItems; // 用户手册列表
	private LayoutInflater listContainer; // 视图容器
	private OnClickListener onclick;
	private UserManualListItemView manual;
	public final class UserManualListItemView { // 自定义控件集合
		public TextView name;
		public UserManual usermanual;
	}

	public UserManualAdapter(Context context,List<UserManual> listItems) {
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems=listItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 自定义视图
		if (convertView == null) {
			manual=new UserManualListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.usermanual_listview_items,
					null);
			manual.name=(TextView)convertView.findViewById(R.id.manualname);
			convertView.setTag(manual);
			convertView.setOnClickListener(onclick);
		} else {
			manual=(UserManualListItemView)convertView.getTag();
		}
		// 设置文字
		manual.name.setText(listItems.get(position).getItemtext());
		manual.usermanual=listItems.get(position);
		return convertView;
	}
	

	@Override
	public int getCount() {
		if(AssertValue.isNotNullAndNotEmpty(listItems))
		{
			return listItems.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		listItems.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public OnClickListener getOnclick() {
		return onclick;
	}

	public void setOnclick(OnClickListener onclick) {
		this.onclick = onclick;
	}


}
