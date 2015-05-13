package com.yun9.jupiter.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.yun9.jupiter.bean.injected.BeanInjectedUtil;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.injected.ViewInjectedUtil;

public abstract class JupiterFragmentActivity extends JupiterBaseFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getContentView());
    }
}
