package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.R;

/**
 * Created by Leon on 15/4/22.
 */
public class JupiterRowStyleTitleLayout extends JupiterRelativeLayout {

    private TextView titleTV;

    private ImageView mainIV;

    private ImageView arrowRightIV;

    private TextView hotNitoceTV;

    public JupiterRowStyleTitleLayout(Context context) {
        super(context);
    }

    public JupiterRowStyleTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterRowStyleTitleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return  R.layout.row_style_title;
    }

    protected void initViews(Context context, AttributeSet attrs, int defStyle){

        this.titleTV = (TextView) this.findViewById(R.id.title_tv);
        this.mainIV = (ImageView) this.findViewById(R.id.main_iv);
        this.arrowRightIV = (ImageView) this.findViewById(R.id.arrow_right_iv);
        this.hotNitoceTV = (TextView) this.findViewById(R.id.hot_notice);
    }


}
