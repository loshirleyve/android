package com.yun9.mobile.msg.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yun9.mobile.R;

public class ShowUrlActivity extends Activity {
	
	private WebView webview;
	private ImageButton return_btn;
	private TextView titleTxt;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.fragment_webview);
		webview = (WebView) findViewById(R.id.webview);
		titleTxt = (TextView) findViewById(R.id.title_txt);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(getIntent().getStringExtra("url"));
		webview.setWebViewClient(new MyWebViewClient());//禁止点击链接时跳出程序打开其他浏览器
		return_btn = (ImageButton) findViewById(R.id.return_btn);
		return_btn.setOnClickListener(returnOnclick);
		titleTxt.setText("网页正文");
	}
	
	class MyWebViewClient extends WebViewClient{//获取页面的点击事件，有自己来处理
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			webview.loadUrl(url);
			return true;
		}
	}
	
	// 返回按钮点击事件
		OnClickListener returnOnclick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		};

}
