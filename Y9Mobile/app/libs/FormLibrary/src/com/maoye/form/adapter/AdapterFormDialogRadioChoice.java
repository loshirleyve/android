package com.maoye.form.adapter;

import java.util.List;
import com.maoye.form.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterFormDialogRadioChoice extends BaseAdapter {

	private List<String> texts;
	private Context context;
	
	/**
	 * @param texts
	 * @param context
	 */
	public AdapterFormDialogRadioChoice(List<String> texts, Context context) {
		super();
		this.texts = texts;
		this.context = context;
	}

	@Override
	public int getCount() {
		return this.texts.size();
	}

	@Override
	public Object getItem(int position) {
		return texts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView != null){
			viewHolder = (ViewHolder) convertView.getTag();
		}else{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(this.context).inflate(R.layout.form_item_dialog_radiochoice, null);
			viewHolder.text = (TextView) convertView.findViewById(R.id.tvText);
			convertView.setTag(viewHolder);
		}
		
		viewHolder.text.setText(this.texts.get(position));
		return convertView;
	}

	class ViewHolder{
		TextView text;
	}
}
