package com.yun9.jupiter.app;

import android.content.Context;

import com.yun9.jupiter.bean.BeanManager;


public interface JupiterApplication {
    public BeanManager getBeanManager();

    public Context getApplicationContext();
}
