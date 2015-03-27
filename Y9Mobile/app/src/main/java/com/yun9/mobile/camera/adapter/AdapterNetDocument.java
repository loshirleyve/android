package com.yun9.mobile.camera.adapter;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.domain.DmNetDocument;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterNetDocument extends BaseAdapter {

	private List<DmNetDocument> list;
	private Context context;

	/**
	 * @param list
	 * @param context
	 */
	public AdapterNetDocument(List<DmNetDocument> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_netdocument, null);
			viewHolder.photo = (ImageView) convertView.findViewById(R.id.photo);
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			viewHolder.chk = (CheckBox) convertView.findViewById(R.id.chk);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		DmNetDocument document = list.get(position);
		viewHolder.photo.setBackgroundResource(document.getPhoto());
		viewHolder.tvName.setText(document.getName());
		viewHolder.tvTime.setText(document.getTime());
		viewHolder.chk.setChecked(document.isChecked());
		return convertView;
	}
	
	
	class ViewHolder{
		ImageView photo;
		TextView tvName;
		TextView tvTime;
		CheckBox chk;
	}


	public void setChecked(int position, boolean isCheck, View view) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		viewHolder.chk.setChecked(isCheck);
	}

}
