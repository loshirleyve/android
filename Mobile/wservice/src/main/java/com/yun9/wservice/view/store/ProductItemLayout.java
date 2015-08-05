package com.yun9.wservice.view.store;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by li on 2015/6/8.
 */
public class ProductItemLayout extends JupiterRelativeLayout {

    private TextView TitleTV;
    private TextView SutitleTV;
    private ImageView MainIV;
    private TextView HotnoticeTV;
    private TextView companyTv;

    public ProductItemLayout(Context context) {
        super(context);
    }

    public ProductItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_product_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        this.TitleTV = (TextView) findViewById(R.id.title_tv);
        SutitleTV = (TextView)findViewById(R.id.sutitle_tv);
        companyTv= (TextView)findViewById(R.id.company_tv);
        MainIV = (ImageView)findViewById(R.id.main_iv);
        HotnoticeTV = (TextView)findViewById(R.id.hot_notice);
    }

    public TextView getTitleTV() {
        return TitleTV;
    }

    public void setTitleTV(TextView TitleTV) {
        this.TitleTV = TitleTV;
    }

    public TextView getSutitleTV() {
        return SutitleTV;
    }

    public TextView getCompanyTv() {
        return companyTv;
    }

    public void setCompanyTv(TextView companyTv) {
        this.companyTv = companyTv;
    }

    public void setSutitleTV(TextView sutitleTV) {
        SutitleTV = sutitleTV;
    }

    public ImageView getMainIV() {
        return MainIV;
    }

    public void setMainIV(ImageView mainIV) {
        MainIV = mainIV;
    }

    public TextView getHotnoticeTV() {
        return HotnoticeTV;
    }

    public void setHotnoticeTV(TextView hotnoticeTV) {
        HotnoticeTV = hotnoticeTV;
    }
}
