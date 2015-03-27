package com.yun9.mobile.framework.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.activity.MainActivity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.conf.PropertiesFactory;

public class WebViewFragment extends Fragment {
	private Context mContext;
	private WebView webview;
	private View mBaseView;
	private ImageButton return_btn;
	public WebViewFragment(Context context)
	{
		this.mContext=context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mBaseView = inflater.inflate(R.layout.fragment_webview, null);
		init();
		PropertiesFactory propertiesFactory = BeanConfig.getInstance()
				.getBeanContext().get(PropertiesFactory.class);

		webview = (WebView) mBaseView.findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(propertiesFactory.getString("app.config.jm.url"));
		webview.setWebViewClient(new MyWebViewClient());//禁止点击链接时跳出程序打开其他浏览器
		return mBaseView;
	}

	class MyWebViewClient extends WebViewClient{//获取页面的点击事件，有自己来处理
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webview.loadUrl(url);
				return true;
			}
		}
	
	public void init()
	{
		return_btn = (ImageButton) mBaseView.findViewById(R.id.return_btn);
		return_btn.setOnClickListener(returnOnclick);
	}
	
	// 返回按钮点击事件
	OnClickListener returnOnclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(webview.canGoBack())//当页面可以返回时就返回，当不可以返回即回到最上层时执行else
			{
				webview.goBack();
			}
			else
			{
				FoundFragment foundFragment = new FoundFragment(mContext);
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.fl_content, foundFragment,
						MainActivity.class.getName());
				ft.commit();
			}
		}
	};
}
