package com.yun9.mobile.position.adapter;

import java.util.List;

import com.baidu.mapapi.search.core.PoiInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.yun9.mobile.R;

public class PositionListAdapter extends BaseAdapter {

	private Context context;
	private List<PoiInfo> poiList;
	
	/**
	 * @param poiList
	 */
	public PositionListAdapter(List<PoiInfo> poiList, Context context) {
		super();
		this.poiList = poiList;
		this.context = context;
	}

	public void addPoi(List<PoiInfo> poiList){
		this.poiList.addAll(poiList);
	}
	
	
	@Override
	public int getCount() {
		return poiList.size();
	}

	@Override
	public Object getItem(int position) {
		return poiList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		/**
		 * 进行ListView 的优化
		 */
		if (convertView == null) {
			convertView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.lvitem_position, parent, false);
			
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvDetails = (TextView) convertView.findViewById(R.id.tvDetails);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		PoiInfo poi = poiList.get(position);
		holder.tvTitle.setText(poi.name);
		holder.tvDetails.setText(poi.address);
		
		return convertView;
	}

	
	public class ViewHolder {
		public TextView tvTitle = null;
		public TextView tvDetails = null;

	}
}
