package com.maoye.form.adapter;

import java.util.List;

import com.maoye.form.R;
import com.maoye.form.interfaces.AdapterShowImage;
import com.maoye.form.model.ModelPic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AdapterViewFormAttchmentPic extends BaseAdapter {

	private List<ModelPic> listModelAttchmentFile;
	private Context context;
	private AdapterShowImage imageLoader;
	
	/**
	 * @param listModelAttchmentPic
	 * @param context
	 */
	public AdapterViewFormAttchmentPic(List<ModelPic> listModelAttchmentFile, Context context, AdapterShowImage imageLoader) {
		super();
		this.listModelAttchmentFile = listModelAttchmentFile;
		this.context = context;
		this.imageLoader = imageLoader;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.form_item_viewform_attchmentpic, null);
			viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic);
			viewHolder.cancel = (ImageView) convertView.findViewById(R.id.cancel);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		if(imageLoader != null){
			imageLoader.showImage(viewHolder.pic, listModelAttchmentFile.get(position));
		}
		
		
//		viewHolder.pic.setImageResource(R.drawable.meinv);
		
//		viewHolder.cancel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(context, "cancel position = " + position, 0).show();
//			}
//		});
		
		return convertView;
	}
	
	class ViewHolder{
		private ImageView pic;
		private ImageView cancel;
	}

	public List<ModelPic> getListModelAttchmentFile() {
		return listModelAttchmentFile;
	}

	public void setListModelAttchmentFile(List<ModelPic> listModelAttchmentFile) {
		this.listModelAttchmentFile = listModelAttchmentFile;
	}


}
