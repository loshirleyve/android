package com.yun9.mobile.camera.adapter;

import java.io.File;
import java.util.List;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.domain.DmDocument;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterAllDocument extends BaseAdapter {

	private List<DmDocument> list;
	private Context context;

	/**
	 * @param list
	 * @param context
	 */
	public AdapterAllDocument(List<DmDocument> list, Context context) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_alldocuments, null);
			viewHolder.photo = (ImageView) convertView.findViewById(R.id.photo);
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			viewHolder.tvSize = (TextView) convertView.findViewById(R.id.tvSize);
			viewHolder.chk = (CheckBox) convertView.findViewById(R.id.chk);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		DmDocument dmDocument = this.list.get(position);
		
		viewHolder.photo.setBackgroundResource(dmDocument.getPhoto());
		viewHolder.tvName.setText(dmDocument.getName());
		viewHolder.tvSize.setText(dmDocument.getSize());
		viewHolder.tvTime.setText(dmDocument.getTime());
		viewHolder.chk.setChecked(dmDocument.isChecked());
		
		return convertView;
	}
	
	
	class ViewHolder{
		ImageView photo;
		TextView tvName;
		TextView tvTime;
		TextView tvSize;
		CheckBox chk;
	}


	public void setChecked(int position, boolean isCheck, View view) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		viewHolder.chk.setChecked(isCheck);
	}

}
