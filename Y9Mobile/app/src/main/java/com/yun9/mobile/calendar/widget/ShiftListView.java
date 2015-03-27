package com.yun9.mobile.calendar.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 不滑动的ListView
 * @author Kass
 */
public class ShiftListView extends ListView {

	public ShiftListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ShiftListView(Context context) {
		super(context);
	}
	
	public ShiftListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/** 
 	 * 设置不滚动 
 	 */  
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
    	int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
    	super.onMeasure(widthMeasureSpec, expandSpec);  
	}  
}
