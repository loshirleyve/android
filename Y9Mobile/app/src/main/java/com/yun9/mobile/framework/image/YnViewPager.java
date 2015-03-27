package com.yun9.mobile.framework.image;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class YnViewPager extends ViewPager {

	private Context mContext;
	private ImageViewTouch imageView;

	public YnViewPager(Context context) {
		super(context);
		this.mContext = context;
	}

	public YnViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

}
