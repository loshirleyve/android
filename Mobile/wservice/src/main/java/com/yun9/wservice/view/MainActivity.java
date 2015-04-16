package com.yun9.wservice.view;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.yun9.jupiter.actvity.JupiterActivity;
import com.yun9.wservice.R;


public class MainActivity extends JupiterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initView();
    }

    private void initView(){

    }
}
