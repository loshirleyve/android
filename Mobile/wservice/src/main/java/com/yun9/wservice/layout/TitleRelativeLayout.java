package com.yun9.wservice.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.conf.PropertiesManager;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.widget.JupiterRelativeLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/4/16.
 */
public class TitleRelativeLayout extends JupiterRelativeLayout {

    private static final Logger logger = Logger.getLogger(TitleRelativeLayout.class);


    @ViewInject(id=R.id.title_left_ll)
    private LinearLayout titleLeftLL;

    @ViewInject(id=R.id.title_tv)
    private TextView titleTV;

    @BeanInject
    private PropertiesManager propertiesManager;

    public TitleRelativeLayout(Context context) {
        super(context);
        this.initView();
    }

    public TitleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public TitleRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initView();
    }

    private void initView(){
        this.inflate(R.layout.title_bar);
        logger.d("title 初始化完成！");
    }


}
