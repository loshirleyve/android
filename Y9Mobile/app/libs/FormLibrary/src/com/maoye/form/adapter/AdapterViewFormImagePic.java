package com.maoye.form.adapter;

import java.util.List;

import com.maoye.form.R;
import com.maoye.form.adapter.AdapterViewFormAttchmentPic.ViewHolder;
import com.maoye.form.interfaces.AdapterShowImage;
import com.maoye.form.model.ModelPic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AdapterViewFormImagePic extends BaseAdapter {

	
	
	private List<ModelPic> pics;
	private Context context;
	private AdapterShowImage imageLoader;
	
	
	
	/**
	 * @param pics
	 * @param context
	 * @param imageLoader
	 */
	public AdapterViewFormImagePic(List<ModelPic> pics, Context context,
			AdapterShowImage imageLoader) {
		super();
		this.pics = pics;
		this.context = context;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return pics.size();
	}

	@Override
	public Object getItem(int position) {
		return pics.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.form_item_viewform_imagepic, null);
			viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic);
			viewHolder.cancel = (ImageView) convertView.findViewById(R.id.cancel);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		if(imageLoader != null){
			imageLoader.showImage(viewHolder.pic, pics.get(position));
		}
		
		return convertView;
	}

	
	private class ViewHolder{
		private ImageView pic;
		private ImageView cancel;
	}


	public List<ModelPic> getPics() {
		return pics;
	}

	public void setPics(List<ModelPic> pics) {
		this.pics = pics;
	}
	
}
