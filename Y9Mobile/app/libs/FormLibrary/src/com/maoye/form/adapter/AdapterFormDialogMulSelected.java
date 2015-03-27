package com.maoye.form.adapter;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.maoye.form.R;
import com.maoye.form.model.form.ModelMulSelected;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class AdapterFormDialogMulSelected extends BaseAdapter {

	private List<ModelMulSelected> options;
	private Context context;
	
	/**
	 * @param texts
	 * @param context
	 */
	public AdapterFormDialogMulSelected(List<ModelMulSelected> options, Context context) {
		super();
		this.options = options;
		this.context = context;
		
		init();
	}

	private void init(){
		
	}
	
	
	@Override
	public int getCount() {
		return options.size();
	}

	@Override
	public Object getItem(int position) {
		return options.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView != null){
			viewHolder = (ViewHolder) convertView.getTag();
		}else{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(this.context).inflate(R.layout.form_item_dialog_mulselected, null);
			viewHolder.text = (TextView) convertView.findViewById(R.id.tvText);
			viewHolder.chk = (CheckBox) convertView.findViewById(R.id.chk);
			
		
			
			convertView.setTag(viewHolder);
		}
		
		
		viewHolder.chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				options.get(position).setCheck(isChecked);
			}
		});
		
		ModelMulSelected option = options.get(position);
		
		viewHolder.chk.setChecked(option.isCheck());
	
		viewHolder.text.setText(option.getValue());
		
		
		return convertView;
	}

	class ViewHolder{
		TextView text;
		CheckBox chk;
	}
}
