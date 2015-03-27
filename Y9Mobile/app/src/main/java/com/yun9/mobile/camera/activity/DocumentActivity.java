package com.yun9.mobile.camera.activity;


import com.yun9.mobile.R;
import com.yun9.mobile.camera.impl.pre4ui.ImplPre4UiDocumentActivity;
import com.yun9.mobile.camera.interfaces.presenter4ui.Pre4uiDocumentActivity;
import com.yun9.mobile.camera.interfaces.ui4Presenter.Ui4PreDocumentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DocumentActivity extends Activity implements Ui4PreDocumentActivity{
	
	private ImageView ivPhoto;
	private TextView tvName;
	private TextView tvSize;
	
	private View ibtnTitleLeft;
	private Button btnTitlRight;
	
	/**
	 * 业务处理
	 */
	private Pre4uiDocumentActivity pre;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_document);
		
		findView();
		init();
		setEvent();
	}

	private void setEvent() {
		
		ibtnTitleLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pre.OnClickTitleLeft();
			}
		});
		
		btnTitlRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pre.OnClickTitleRight();
			}
		});
	}

	private void init() {
		pre = new ImplPre4UiDocumentActivity(this, this);
		pre.work();
	}

	private void findView() {
		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
		tvName = (TextView) findViewById(R.id.tvName);
		tvSize = (TextView) findViewById(R.id.tvSize);
		
		ibtnTitleLeft = findViewById(R.id.ibtnTitleLeft);
		btnTitlRight = (Button) findViewById(R.id.btnTitlRight);
	}

	@Override
	public void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showDocumentPhoto(int photo) {
		this.ivPhoto.setBackgroundResource(photo);
	}

	@Override
	public void showDocumentName(String name) {
		this.tvName.setText(name);
	}

	@Override
	public void showDocumentSize(String size) {
		this.tvSize.setText(size);
	}

	@Override
	public void setTitleRightText(String text) {
		this.btnTitlRight.setText(text);
	}
}
