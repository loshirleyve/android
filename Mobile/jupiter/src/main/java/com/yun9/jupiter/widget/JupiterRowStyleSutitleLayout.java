package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yun9.jupiter.R;
import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.model.ISelectable;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterBadgeView;

import java.util.Collection;
import java.util.Iterator;


/**
 * Created by Leon on 15/4/21.
 */
public class JupiterRowStyleSutitleLayout extends JupiterRelativeLayout implements ISelectable{
    private TextView titleTV;

    private ImageView mainIV;

    private TextView sutitleTv;

    private TextView timeTv;

    private ImageView arrowRightIV;

    private Button arrowRightButton;

    private ImageView selectModeIV;

    private TextView titleTipTV;

    private boolean selected;

    private OnSelectListener onSelectListener;


    private TextView hotNitoceTV;

    private LinearLayout subItemContainer;

    private JupiterBadgeView badgeView;

    private LinearLayout mainContentLl;

    public JupiterRowStyleSutitleLayout(Context context) {
        super(context);
    }

    public JupiterRowStyleSutitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterRowStyleSutitleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.row_style_sutitle;
    }

    protected void initViews(Context context, AttributeSet attrs, int defStyle){
        this.mainIV = (ImageView) this.findViewById(R.id.main_iv);
        this.setTitleTV((TextView) this.findViewById(R.id.title_tv));
        this.sutitleTv = (TextView) this.findViewById(R.id.sutitle_tv);
        this.arrowRightIV = (ImageView) this.findViewById(R.id.arrow_right_iv);
        this.arrowRightButton= (Button) this.findViewById(R.id.arrow_right_button);
        this.timeTv = (TextView) this.findViewById(R.id.time_tv);
        this.selectModeIV = (ImageView) this.findViewById(R.id.selectmode_iv);
        this.hotNitoceTV = (TextView) this.findViewById(R.id.hot_notice);
        this.subItemContainer = (LinearLayout) this.findViewById(R.id.subtitle_item_container);
        this.titleTipTV = (TextView) this.findViewById(R.id.title_tip_tv);
        this.mainContentLl = (LinearLayout) this.findViewById(R.id.main_content_ll);

        this.initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs){
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterRowStyleSutitleLayout);

        try{
            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleTitleText)){
                String titleText = typedArray.getString(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleTitleText);

                this.getTitleTV().setText(titleText);
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleText)){
                String sutitleText= typedArray.getString(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleText);
                this.getSutitleTv().setText(sutitleText);
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowMainImage)){
                boolean showMainImage = typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowMainImage, false);

                if (showMainImage){
                    this.mainIV.setVisibility(View.VISIBLE);
                }else{
                    this.mainIV.setVisibility(View.GONE);
                }
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowSutitleText)){
                boolean showSutitleText = typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowSutitleText,false);

                if (showSutitleText){
                    this.sutitleTv.setVisibility(View.VISIBLE);
                }else{
                    this.sutitleTv.setVisibility(View.GONE);
                }
            }

//        if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowHotText)){
//            boolean showHotText = typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowHotText,false);
//        }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowTimeText)){
                boolean showTimeText = typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowTimeText,false);

                if (showTimeText){
                    this.timeTv.setVisibility(View.VISIBLE);
                }else{
                    this.timeTv.setVisibility(View.GONE);
                }
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowArrow)){
                boolean showArrowImage = typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowArrow,false);

                if (showArrowImage){
                    this.arrowRightIV.setVisibility(View.VISIBLE);
                }else{
                    this.arrowRightIV.setVisibility(View.GONE);
                }
            }
            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowArrowButton)){
                boolean showArrowButton= typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowArrowButton,false);

                if (showArrowButton){
                    this.arrowRightButton.setVisibility(View.VISIBLE);
                }else{
                    this.arrowRightButton.setVisibility(View.GONE);
                }
            }
            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleMainImage)){
                Drawable mainImage = typedArray.getDrawable(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleMainImage);
                if (mainImage != null){
                    this.mainIV.setImageDrawable(mainImage);
                }
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleMaintContentGravity)){
                int gravity = typedArray.getInt(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleMaintContentGravity,
                        Gravity.TOP);
                setMainContentGravity(gravity);
            }
        }finally{
            typedArray.recycle();
        }

    }

    public void setShowTime(boolean isShow) {
        int show = isShow?View.VISIBLE:View.GONE;
        this.getTimeTv().setVisibility(show);
    }

    public void setShowMainImage(boolean isShow) {
        int show = isShow?View.VISIBLE:View.GONE;
        this.getMainIV().setVisibility(show);
    }

    public void setShowArrow(boolean isShow) {
        int show = isShow?View.VISIBLE:View.GONE;
        this.getArrowRightIV().setVisibility(show);
    }

    public void setShowArrowButton(boolean isShow) {
        int show = isShow?View.VISIBLE:View.GONE;
        this.getArrowRightButton().setVisibility(show);
    }


    public void setShowSutitleText(boolean isShow){
        int show = isShow?View.VISIBLE:View.GONE;
        this.getSutitleTv().setVisibility(show);
    }

    public void setSelectMode(boolean mode) {
        if (mode) {
            selected = false;

            this.selectModeIV.setVisibility(View.VISIBLE);
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected = !selected;
                    select(selected);
                    if (AssertValue.isNotNull(onSelectListener)) {
                        onSelectListener.onSelect(JupiterRowStyleSutitleLayout.this, selected);
                    }
                }
            });
        } else {
            this.selectModeIV.setVisibility(View.GONE);
            this.setOnClickListener(null);
        }
    }

    public void select(boolean selected){
        this.selected = selected;

        if (this.selected) {
            selectModeIV.setImageResource(R.drawable.selector);
        } else {
            selectModeIV.setImageResource(R.drawable.selector_empty);
        }
    }

    public void showSubItems(Collection<String> items) {
        subItemContainer.removeAllViews();
        subItemContainer.setVisibility(VISIBLE);
        //sutitleTv.setVisibility(GONE);
        Iterator<String> iterator = items.iterator();
        while (iterator.hasNext()) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setTextSize(11);
            textView.setPadding(2,2,2,2);
            textView.setBackgroundResource(R.drawable.textview_border_blue);
            textView.setText(iterator.next());
            subItemContainer.addView(textView);
        }
    }

    public void toggleSubItems() {
        if (subItemContainer.getVisibility() == View.GONE) {
            subItemContainer.setVisibility(VISIBLE);
            sutitleTv.setVisibility(GONE);
        } else {
            subItemContainer.setVisibility(GONE);
            sutitleTv.setVisibility(VISIBLE);
        }
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    public void setTitleText(String text) {
        this.getTitleTV().setText(text);
    }

    public void setSubTitleText(String text) {
        this.getSutitleTv().setText(text);
    }

    public ImageView getMainIV() {
        return mainIV;
    }

    public void setMainIV(ImageView mainIV) {
        this.mainIV = mainIV;
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

    public Button getArrowRightButton() {
        return arrowRightButton;
    }

    public void setArrowRightButton(Button arrowRightButton) {
        this.arrowRightButton = arrowRightButton;
    }

    public TextView getTimeTv() {
        return timeTv;
    }

    public void setTimeTv(TextView timeTv) {
        this.timeTv = timeTv;
    }


    public TextView getTitleTV() {
        return titleTV;
    }

    public void setTitleTV(TextView titleTV) {
        this.titleTV = titleTV;
    }

    public TextView getHotNitoceTV() {
        return hotNitoceTV;
    }

    public void setHotNitoceTV(TextView hotNitoceTV) {
        this.hotNitoceTV = hotNitoceTV;
    }

    public LinearLayout getSubItemContainer() {
        return subItemContainer;
    }

    public void setSubItemContainer(LinearLayout subItemContainer) {
        this.subItemContainer = subItemContainer;
    }

    public ImageView getSelectModeIV() {
        return selectModeIV;
    }

    public void setSelectModeIV(ImageView selectModeIV) {
        this.selectModeIV = selectModeIV;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public TextView getTitleTipTV() {
        return titleTipTV;
    }

    public JupiterRowStyleSutitleLayout setTitleTipTV(TextView titleTipTV) {
        this.titleTipTV = titleTipTV;
        return this;
    }

    public void hideCornerIco() {
        if (badgeView != null){
            badgeView.setVisibility(GONE);
            badgeView.hide();
        }
    }

    public void showCornerIco() {
        if (badgeView != null){
            badgeView.setVisibility(VISIBLE);
            badgeView.show();
        }
    }

    public void setCornerNum(View parent,int num) {
        if (badgeView == null){
            badgeView = new JupiterBadgeView(getContext(), parent);
            badgeView.setBadgePosition(JupiterBadgeView.POSITION_TOP_RIGHT_EDGE);
            badgeView.setTextSize(12);
            badgeView.setGravity(Gravity.CENTER);
            badgeView.setBadgeSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (num > 99){
            badgeView.setText("99+");
        } else {
            badgeView.setText("" + num);
        }
    }

    public void setMainContentGravity(int gravity) {
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) mainContentLl.getLayoutParams();
        layoutParams.gravity = gravity;
        mainContentLl.setLayoutParams(layoutParams);
    }

    public LinearLayout getMainContentLl() {
        return mainContentLl;
    }
}
