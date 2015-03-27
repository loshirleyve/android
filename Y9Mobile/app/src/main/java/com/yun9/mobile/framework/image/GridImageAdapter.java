package com.yun9.mobile.framework.image;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ImgUtil;
import com.yun9.mobile.framework.util.ImgUtil.OnLoadBitmapListener;
import com.yun9.mobile.framework.util.SystemMethod;

public class GridImageAdapter extends BaseAdapter {
	private List<DmImageItem> imageItems;

	private Context mContext;

	private GridView gridView;

	private HashMap<String, SoftReference<Bitmap>> hashMaps = new HashMap<String, SoftReference<Bitmap>>();

	public GridImageAdapter(Context context, List<DmImageItem> imageItems,
			GridView gridView) {
		this.mContext = context;
		this.imageItems = imageItems;
		this.gridView = gridView;
	}

	@Override
	public int getCount() {
		return imageItems.size();
	}

	@Override
	public Object getItem(int position) {
		return imageItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		DmImageItem imageItem = (DmImageItem) getItem(position);
		if (AssertValue.isNotNull(convertView)) {
			holder = (Holder) convertView.getTag();
		} else {
			holder = new Holder();
			convertView = View
					.inflate(mContext, R.layout.grid_image_item, null);
			holder.ivPic = (ImageView) convertView.findViewById(R.id.image);
			// holder.ivSelected = (ImageView) convertView
			// .findViewById(R.id.isselected);
			// holder.tvName = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		}

		String path = imageItem.getThumbnailUrl();

		//holder.tvName.setText(imageItem.getName());
		holder.ivPic.setTag(path);
		holder.imageItem = imageItem;

		if (AssertValue.isNotNullAndNotEmpty(path)) {
			Bitmap bitmap = null;

			if (hashMaps.containsKey(path)) {
				bitmap = hashMaps.get(path).get();
			}

			if (AssertValue.isNotNull(bitmap)) {
				holder.ivPic.setImageBitmap(bitmap);
			} else
				ImgUtil.getInstance().loadBitmap(path, null,
						new OnLoadBitmapListener() {
							@Override
							public void loadImage(Bitmap bitmap, String path,
									View view) {
								ImageView iv = (ImageView) gridView
										.findViewWithTag(path);

								if (bitmap != null && iv != null) {
									bitmap = SystemMethod.toRoundCorner(bitmap,
											15);
									iv.setImageBitmap(bitmap);

									if (!hashMaps.containsKey(path)) {
										hashMaps.put(path,
												new SoftReference<Bitmap>(
														bitmap));
									}
								}
							}
						});
		}

		return convertView;
	}

	class Holder {
		ImageView ivPic;

		ImageView ivSelected;

		TextView tvName;

		DmImageItem imageItem;
	}

}
