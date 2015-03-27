package com.yun9.mobile.framework.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class UtilPopWin {
	/**
	 * popwind 常用
	 * 
	 * @return  透明背景图
	 */
	public static Drawable getDrawable(Context context) {
		ShapeDrawable bgdrawable = new ShapeDrawable(new OvalShape());
		bgdrawable.getPaint().setColor(context.getResources().getColor(android.R.color.transparent));
		return bgdrawable;
	}
}
