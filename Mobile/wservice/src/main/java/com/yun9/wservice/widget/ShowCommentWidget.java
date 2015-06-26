package com.yun9.wservice.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RatingBar;
import android.widget.TextView;
import com.yun9.jupiter.util.DateFormatUtil;
import com.yun9.jupiter.util.StringPool;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/26.
 */
public class ShowCommentWidget extends JupiterRelativeLayout{

    private TextView titleTv;

    private TextView starBarTipTv;

    private RatingBar starBar;

    private TextView timeTv;

    private TextView contentTv;

    public ShowCommentWidget(Context context) {
        super(context);
    }

    public ShowCommentWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowCommentWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_show_comment;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        this.contentTv = (TextView) this.findViewById(R.id.content_tv);
        this.titleTv = (TextView) this.findViewById(R.id.title_tv);
        this.timeTv = (TextView) this.findViewById(R.id.time_tv);
        this.starBar = (RatingBar) this.findViewById(R.id.rating_star_rb);
        this.starBarTipTv = (TextView) this.findViewById(R.id.rating_bar_tip_tv);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs,
                                                                R.styleable.ShowCommentWidget);
        if (typedArray.hasValue(R.styleable.ShowCommentWidget_content)){
            String value = typedArray.getString(R.styleable.ShowCommentWidget_content);
            contentTv.setText(value);
        }

        if (typedArray.hasValue(R.styleable.ShowCommentWidget_headTitle)){
            String value = typedArray.getString(R.styleable.ShowCommentWidget_headTitle);
            titleTv.setText(value);
        }

        if (typedArray.hasValue(R.styleable.ShowCommentWidget_time)){
            String value = typedArray.getString(R.styleable.ShowCommentWidget_time);
            timeTv.setText(value);
        }

        if (typedArray.hasValue(R.styleable.ShowCommentWidget_numsOfStar)){
            int value = typedArray.getInt(R.styleable.ShowCommentWidget_numsOfStar, 5);
            starBar.setNumStars(value);
        }

        if (typedArray.hasValue(R.styleable.ShowCommentWidget_rating)){
            float value = typedArray.getFloat(R.styleable.ShowCommentWidget_rating, 1);
            starBar.setRating(value);
        }

        if (typedArray.hasValue(R.styleable.ShowCommentWidget_isIndicator)){
            boolean value = typedArray.getBoolean(R.styleable.ShowCommentWidget_isIndicator,false);
            starBar.setIsIndicator(value);
        }

    }

    public void setTitle(String title) {
        this.titleTv.setText(title);
    }

    public void setTime(long time) {
        this.timeTv.setText(DateFormatUtil.format(time,
                StringPool.DATE_FORMAT_DATE));
    }

    public void setContent(String content) {
        this.contentTv.setText(content);
    }

    public void setRating(float rating) {
        this.starBar.setRating(rating);
    }

    public void setIndicator(boolean indicator) {
        this.starBar.setIsIndicator(indicator);
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public RatingBar getStarBar() {
        return starBar;
    }

    public TextView getTimeTv() {
        return timeTv;
    }

    public TextView getContentTv() {
        return contentTv;
    }

    public TextView getStarBarTipTv() {
        return starBarTipTv;
    }
}
