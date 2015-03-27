package com.yun9.mobile.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.base.view.BaseRelativeLayout;

public class TitleBarView extends BaseRelativeLayout {

	private Context mContext;
	private ImageButton btnLeft;
	private ImageButton btnFuncNav;
	private ImageButton btnReturn;
	private TextView tvTitle;

	public TitleBarView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.title_bar, this);
		btnLeft = (ImageButton) findViewById(R.id.title_btn_left);
		btnReturn = (ImageButton) findViewById(R.id.title_btn_return);
        btnFuncNav = (ImageButton) findViewById(R.id.title_btn_funcnav);
		tvTitle = (TextView) findViewById(R.id.title_txt);

	}

	public ImageButton getBtnLeft() {
		return btnLeft;
	}

	public TextView getTvTitle() {
		return tvTitle;
	}

	public ImageButton getBtnFuncNav() {
		return btnFuncNav;
	}

	public ImageButton getBtnReturn() {
		return btnReturn;
	}

}
