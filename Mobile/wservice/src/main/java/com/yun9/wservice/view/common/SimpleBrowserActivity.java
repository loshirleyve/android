package com.yun9.wservice.view.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/11/15.
 */
public class SimpleBrowserActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.webview)
    private WebView webView;

    private SimpleBrowserCommand command;

    private ProgressDialog progressDialog;

    public static void start(Context context,SimpleBrowserCommand command) {
        Intent intent = new Intent(context,SimpleBrowserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND,command);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (SimpleBrowserCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        buildView();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleBrowserActivity.this.finish();
            }
        });
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url,Bitmap favicon) {//网页页面开始加载的时候
                if (progressDialog == null) {
                    progressDialog=new ProgressDialog(SimpleBrowserActivity.this);
                    progressDialog.setMessage(getResources().getString(R.string.app_wating));
                    progressDialog.show();
                    webView.setEnabled(false);// 当加载网页的时候将网页进行隐藏
                }
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {//网页加载结束的时候
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                    webView.setEnabled(true);
                }
            }
        });
        if (AssertValue.isNotNull(command.getTitle())){
            titleBarLayout.getTitleTv().setText(command.getTitle());
        }
        if (AssertValue.isNotNullAndNotEmpty(command.getUrl())){
            webView.loadUrl(command.getUrl());
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_simple_browser;
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
//            if(webView.canGoBack())
//            {
//                webView.goBack();//返回上一页面
//                return true;
//            }
//            else
//            {
                SimpleBrowserActivity.this.fileList();
//            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
