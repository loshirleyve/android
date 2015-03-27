package com.maoye.form.view.form;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ViewFormShell extends LinearLayout {
	
	public ViewFormShell(Context context) {
		super(context);
	}
	
	public ViewFormShell(Context context, AttributeSet attrs ) {
		super(context, attrs);
	}
	
	public ViewFormShell(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	public void loadForm(ViewForm form){
		unloadForm();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		addView(form, params);
	}
	
	public void unloadForm(){
		removeAllViews();
	}
}
