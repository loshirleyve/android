package com.yun9.mobile.camera.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yun9.mobile.R;
import com.yun9.mobile.camera.domain.DmLocalAlbum;
import com.yun9.mobile.camera.util.SingletonImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 相册适配器
 * 
 * @author GuiLin
 */
public class PhotoFolderAdapter extends BaseAdapter {
	private DisplayImageOptions options;
	private LayoutInflater mInflater;
	private List<DmLocalAlbum> list;
	private ViewHolder viewHolder;
	private ImageLoader imageLoader;
	private Context context;

	public PhotoFolderAdapter(Context context, List<DmLocalAlbum> list) {
		this.list = list;
		this.context = context;

		mInflater = LayoutInflater.from(context);
		imageLoader = SingletonImageLoader.getInstance().getImageLoader();
		options = SingletonImageLoader.getInstance().getOptions();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_photofolder, null);
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.imageView);
			viewHolder.text = (TextView) convertView.findViewById(R.id.info);
			viewHolder.num = (TextView) convertView.findViewById(R.id.num);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		convertView.setVisibility(View.VISIBLE);
		final DmLocalAlbum albumInfo = list.get(position);
		String path = list.get(position).getPath_file();
		imageLoader.displayImage(path, viewHolder.image, options);
		viewHolder.text.setText(albumInfo.getName_album());
		
		// 去掉一张相机
		int size = list.get(position).getList().size() - 1;
		viewHolder.num.setText("(" + size + "张)");

		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
		public TextView text;
		public TextView num;
	}
}
