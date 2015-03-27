package com.yun9.mobile.msg.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   MyListView
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-24下午5:59:35
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-24下午5:59:35  
 * 修改备注：    
 * @version     
 *     
 */
public class MyListView extends ListView{

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context) {
		super(context);
	}
	
	@Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
	    super.onMeasure(widthMeasureSpec, expandSpec);
    } 

}
