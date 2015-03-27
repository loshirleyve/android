package com.maoye.form.adapter;

import java.util.List;

import com.maoye.form.R;
import com.maoye.form.model.ModelPic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterViewFormAttchmentFile extends BaseAdapter {

	private List<ModelPic> listModelAttchmentFile;
	private Context context;
	private OnClickItemListener listener;
	
	/**
	 * @param listModelAttchmentPic
	 * @param context
	 */
	public AdapterViewFormAttchmentFile(List<ModelPic> listModelAttchmentFile, Context context) {
		super();
		this.listModelAttchmentFile = listModelAttchmentFile;
		this.context = context;
	}

	@Override
	public int getCount() {
		return listModelAttchmentFile.size();
	}

	@Override
	public Object getItem(int position) {
		return listModelAttchmentFile.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			
			convertView = LayoutInflater.from(context).inflate(R.layout.form_item_viewform_attchmentfile, null);
			viewHolder.photo = (ImageView) convertView.findViewById(R.id.photo);
			viewHolder.file = (TextView) convertView.findViewById(R.id.file);
			viewHolder.body = convertView.findViewById(R.id.body);
			viewHolder.cancel = convertView.findViewById(R.id.cancel);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ModelPic model = this.listModelAttchmentFile.get(position);
//		viewHolder.photo.setBackgroundResource(model.getPhoto());
//		viewHolder.file.setText(model.getName());
		viewHolder.body.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null){
					listener.onClickItem(v, position);
				}				
			}
		});
		viewHolder.cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null){
					listener.onClickItem(v, position);
				}				
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		private ImageView photo;
		private TextView file;
		private View body;
		private View cancel;
	}

	
	public void setOnClickItemListener(OnClickItemListener listener){
		this.listener = listener;
	}
	
	public interface OnClickItemListener{
		public void onClickItem(View view,  int position);
	}
}
