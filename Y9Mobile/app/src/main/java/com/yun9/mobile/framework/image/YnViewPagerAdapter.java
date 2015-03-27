package com.yun9.mobile.framework.image;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ImgUtil;
import com.yun9.mobile.framework.util.ImgUtil.OnLoadBitmapListener;

public class YnViewPagerAdapter extends PagerAdapter {

	private List<DmImageItem> imageItems;
	private List<ImageView> imageViews = new ArrayList<ImageView>();
	private Context mContext;
	private HashMap<String, SoftReference<Bitmap>> hashMaps = new HashMap<String, SoftReference<Bitmap>>();

	public YnViewPagerAdapter(Context context, List<DmImageItem> imageItems) {
		this.imageItems = imageItems;
		this.mContext = context;
		this.initView();
	}

	private void initView() {
		if (AssertValue.isNotNullAndNotEmpty(this.imageItems)) {
			for (DmImageItem imageItem : this.imageItems) {
				ImageView imageView = new ImageView(this.mContext);
				imageView.setTag(imageItem);
				imageViews.add(imageView);
			}

			// 载入图片
			for (ImageView imageView : this.imageViews) {
				this.loadBitmap(imageView);
			}
		}

	}

	@Override
	public int getCount() {
		if (AssertValue.isNotNull(imageItems)) {
			return this.imageItems.size();
		} else {
			return 0;
		}
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(View container, int position) { // 这个方法用来实例化页卡

		ImageView iv = this.imageViews.get(position);
		((ViewPager) container).addView(iv, 0);
		return iv;
	}

	private void loadBitmap(ImageView imageView) {
		DmImageItem imageItem = (DmImageItem) imageView.getTag();

		Bitmap bitmap = null;

		if (hashMaps.containsKey(imageItem.getImageUrl())) {
			bitmap = hashMaps.get(imageItem.getImageUrl()).get();
		}

		if (AssertValue.isNotNull(bitmap)) {
			imageView.setImageBitmap(bitmap);
		} else {
			ImgUtil.getInstance().loadBitmap(imageItem.getImageUrl(),
					imageView, new OnLoadBitmapListener() {
						@Override
						public void loadImage(Bitmap bitmap, String path,
								View view) {

							for (ImageView iv : imageViews) {
								DmImageItem ii = (DmImageItem) iv.getTag();

								if (bitmap != null
										&& path.equals(ii.getImageUrl())) {
									iv.setImageBitmap(bitmap);
									iv.invalidate();
									if (!hashMaps.containsKey(path)) {
										hashMaps.put(path,
												new SoftReference<Bitmap>(
														bitmap));
									}
								}
							}
						}
					});
		}

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;// 官方提示这样写
	}

}
