package com.yun9.mobile.framework.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;








import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.ScopeModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ScopeAdapter extends BaseAdapter {

	private List<ScopeModel> datas;
	private Context context = null;
	/**
	 * CheckBox 是否选择的存储集合,key 是 position , value 是该position是否选中
	 */
	private Map<ScopeModel, Boolean> isCheckMap;
	
	/**
	 * @param list
	 * @param context
	 */
	public ScopeAdapter(List<ScopeModel> datas, Context context) {
		super();
		this.datas = datas;
		this.context = context;
		
		isCheckMap = new HashMap<ScopeModel, Boolean>();
		// 初始化,默认都没有选中
		configCheckMap(false);
	}
	public ScopeAdapter(List<ScopeModel> datas, Context context, int position) {
		super();
		this.datas = datas;
		this.context = context;
		
		isCheckMap = new HashMap<ScopeModel, Boolean>();
		// 初始化,默认都没有选中
		configCheckMap(false);
		
		// 设置第position个选中
		setChecked(true, position);
	}
	

	private void setChecked(boolean isCheck, int position) {
		if(position < 0 || position > datas.size()){
			return ;
		}else{
			isCheckMap.put(datas.get(position), isCheck);
		}
		
	}
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_scope, parent, false);
			holder = new ViewHolder();
			holder.cb = (CheckBox) convertView.findViewById(R.id.cbCheckBox);
			holder.tv = (TextView) convertView.findViewById(R.id.tvText);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		boolean choose = isCheckMap.get(datas.get(position));
		holder.cb.setChecked(choose);
		holder.tv.setText(datas.get(position).getContent());
		
		return convertView;
	}

	public List<ScopeModel> getDatas() {
		return datas;
	}

	public void setDatas(List<ScopeModel> datas) {
		this.datas = datas;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
	 */
	public void configCheckMap(boolean bool) {
		for (int i = 0; i < datas.size(); i++) {
			isCheckMap.put(datas.get(i), bool);
		}

	}
	
	
	class ViewHolder {
		private CheckBox cb;
		private TextView tv;
	}


	public void radioNotifyDataSetChanged(int position) {
		
		// 获取点击之前的状态
		Boolean currentStat = isCheckMap.get(datas.get(position));
		
		// 将所有的条目都设置成未选择状态
		configCheckMap(false);
		
		// 设置点击后的状态
		isCheckMap.put(datas.get(position), true);
		
		notifyDataSetChanged();
	}
	
	
	
}
