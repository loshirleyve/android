package com.yun9.mobile.framework.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class BaseRelativeLayout extends RelativeLayout {

	public BaseRelativeLayout(Context context) {
		super(context);
	}

	public BaseRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

}
