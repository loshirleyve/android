package com.yun9.mobile.camera.dlg;


import com.yun9.mobile.R;
import com.yun9.mobile.camera.interfaces.Album4PopAlbumOption;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class PopAlbumOption extends PopupWindow {
	private View mainView;
	private Activity activity;
	private Album4PopAlbumOption album4Pop;
	/**
	 * @param activity
	 */
	public PopAlbumOption(Activity activity) {
		super();
		this.activity = activity;
		this.album4Pop = (Album4PopAlbumOption)activity;
		
		init();
		
		setEvent();
	}
	
	private void setEvent() {
		this.mainView.findViewById(R.id.llChosen).setOnClickListener(chosen);
		this.mainView.findViewById(R.id.llBrowse).setOnClickListener(browse);
		this.mainView.findViewById(R.id.llCancel).setOnClickListener(cancel);
		this.mainView.findViewById(R.id.llUpload).setOnClickListener(upload);
		this.mainView.findViewById(R.id.llDelete).setOnClickListener(delete);
	}

	private OnClickListener delete = new OnClickListener() {
		@Override
		public void onClick(View v) {
			album4Pop.popDeleteOnClick(v);
			PopAlbumOption.this.dismiss();
		}
	};
	
	private OnClickListener upload = new OnClickListener() {
		@Override
		public void onClick(View v) {
			PopAlbumOption.this.dismiss();
			album4Pop.popUploadOnClick(v);
			
		}
	};
	
	
	private OnClickListener chosen = new OnClickListener() {
		@Override
		public void onClick(View v) {
			album4Pop.popChoiceOnClick(v);
			PopAlbumOption.this.dismiss();
		}
	};
	
	private OnClickListener browse = new OnClickListener() {
		@Override
		public void onClick(View v) {
			album4Pop.popBrowseOnClick(v);
			PopAlbumOption.this.dismiss();
		}
	};
	
	
	private OnClickListener cancel = new OnClickListener() {
		@Override
		public void onClick(View v) {
			album4Pop.popCencelOnClick(v);
			PopAlbumOption.this.dismiss();
		}
	};
	
	
	protected void init(){
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mainView = inflater.inflate(R.layout.pop_album_option, null);
		int h = activity.getWindowManager().getDefaultDisplay().getHeight();
		int w = activity.getWindowManager().getDefaultDisplay().getWidth();
		this.setContentView(mainView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(w/2);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.pop_album_option);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//		mainView.setOnTouchListener(new OnTouchListener() {
//			
//			public boolean onTouch(View v, MotionEvent event) {
//				
//				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//				int y=(int) event.getY();
//				if(event.getAction()==MotionEvent.ACTION_UP){
//					if(y<height){
//						dismiss();
//					}
//				}				
//				return true;
//			}
//		});
		
		this.setBackgroundDrawable(getDrawable());
		this.setFocusable(true);
		this.setOutsideTouchable(true);
	}
    /**
     * @return popwinw 透明背景图
     */
    private Drawable getDrawable(){
        ShapeDrawable bgdrawable =new ShapeDrawable(new OvalShape());
        bgdrawable.getPaint().setColor(activity.getResources().getColor(android.R.color.transparent));
        return bgdrawable;
    }
}
