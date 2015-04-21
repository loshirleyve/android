package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.yun9.jupiter.R;


/**
 * Created by Leon on 15/4/21.
 */
public class RowCell extends JupiterRelativeLayout{

    private Object tag;

    private ImageView mainIV;

    private TextView titleTV;

    private TextView sutitleTv;

    private TextView timeTv;

    private ImageView arrowRightIV;

    public RowCell(Context context) {
        super(context);
        this.initViews();
    }

    public RowCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initViews();
    }

    public RowCell(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initViews();
    }

    private void initViews(){
        this.inflate(R.layout.row_cell);

        this.mainIV = (ImageView) this.findViewById(R.id.main_iv);
        this.titleTV = (TextView) this.findViewById(R.id.title_tv);
        this.sutitleTv = (TextView) this.findViewById(R.id.sutitle_tv);
        this.arrowRightIV = (ImageView) this.findViewById(R.id.arrow_right_iv);
        this.timeTv = (TextView) this.findViewById(R.id.time_tv);
    }

    public ImageView getMainIV() {
        return mainIV;
    }

    public void setMainIV(ImageView mainIV) {
        this.mainIV = mainIV;
    }

    public TextView getTitleTV() {
        return titleTV;
    }

    public void setTitleTV(TextView titleTV) {
        this.titleTV = titleTV;
    }

    public TextView getSutitleTv() {
        return sutitleTv;
    }

    public void setSutitleTv(TextView sutitleTv) {
        this.sutitleTv = sutitleTv;
    }

    public ImageView getArrowRightIV() {
        return arrowRightIV;
    }

    public void setArrowRightIV(ImageView arrowRightIV) {
        this.arrowRightIV = arrowRightIV;
    }

    public TextView getTimeTv() {
        return timeTv;
    }

    public void setTimeTv(TextView timeTv) {
        this.timeTv = timeTv;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public void setTag(Object tag) {
        this.tag = tag;
    }
}
