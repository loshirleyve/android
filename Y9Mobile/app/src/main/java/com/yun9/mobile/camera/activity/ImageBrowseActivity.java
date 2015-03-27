package com.yun9.mobile.camera.activity;

import java.util.ArrayList;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.fragment.ImageDetailFragment;
import com.yun9.mobile.camera.view.HackyViewPager;
import com.yun9.mobile.framework.view.TitleBarView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ImageBrowseActivity extends FragmentActivity {
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";
	private static final CharSequence TITLE = "图片浏览";
	public static final String EXTRA_IMAGE_FROM = "image_from";
	
	/**
	 * 来自本地相册
	 */
	public static final int EXTRA_IMAGE_FROM_LOCALALBUM = 0;
	
	
	/**
	 * 来自云端相册
	 */
	public static final int EXTRA_IMAGE_FROM_NETALBUM = 1;
	
	private HackyViewPager mPager;
	private int pagerPosition;
	private TextView indicator;
	private TitleBarView tbvTitleBar;
	private ImageButton vLeft;
	private int from;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagebrowse);

		init();
		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		from = getIntent().getIntExtra(EXTRA_IMAGE_FROM, 0);
		
		@SuppressWarnings("unchecked")
		ArrayList<String> urls = (ArrayList<String>) getIntent().getSerializableExtra(EXTRA_IMAGE_URLS);
		
		mPager = (HackyViewPager) findViewById(R.id.pager);
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
		mPager.setAdapter(mAdapter);
		indicator = (TextView) findViewById(R.id.indicator);
		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
		indicator.setText(text);
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText(text);
			}

		});
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		mPager.setCurrentItem(pagerPosition);
	}

	private void init() {
		titleInit();
	}

	private void titleInit(){
		tbvTitleBar = (TitleBarView) findViewById(R.id.tbvTitleBar);
		vLeft = tbvTitleBar.getBtnLeft();
		vLeft.setVisibility(View.VISIBLE);
		vLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		TextView tvTitle = tbvTitleBar.getTvTitle();
		tvTitle.setText(TITLE);
		tvTitle.setVisibility(View.VISIBLE);		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentStatePagerAdapter {

		public ArrayList<String> fileList;

		public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.size();
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList.get(position);
			return ImageDetailFragment.newInstance(url, from);
		}

	}
}