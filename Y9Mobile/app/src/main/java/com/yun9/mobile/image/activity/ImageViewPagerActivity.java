package com.yun9.mobile.image.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.view.HackyViewPager;
import com.yun9.mobile.image.view.PhotoView;
import com.yun9.mobile.imageloader.MyImageLoader;

public class ImageViewPagerActivity extends Activity {

	private HackyViewPager mViewPager;
	private TextView viewsize;
	public  List<String> sDrawables;
	public  int currentItem;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageview);
		initview();
	}

	private void initview()
	{
		sDrawables=getIntent().getStringArrayListExtra("imagelist");
		currentItem=getIntent().getIntExtra("currentItem",0);
		mViewPager =(HackyViewPager)findViewById(R.id.imageView);
		viewsize=(TextView)findViewById(R.id.viewsize);
		viewsize.setText(currentItem+1+"/"+sDrawables.size());
		mViewPager.setAdapter(new SamplePagerAdapter());
		mViewPager.setCurrentItem(currentItem);
	}
	
	class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return sDrawables.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			MyImageLoader.getInstance().displayImage(sDrawables.get(position),photoView);
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageScrollStateChanged(int arg0) {
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				@Override
				public void onPageSelected(int arg0) {
					viewsize.setText(arg0+1+"/"+getCount());
				}

			});
			return photoView;
		}

		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		

	}
}
