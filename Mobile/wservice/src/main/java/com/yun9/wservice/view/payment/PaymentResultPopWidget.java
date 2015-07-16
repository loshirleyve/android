package com.yun9.wservice.view.payment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/15/15.
 */
public class PaymentResultPopWidget extends JupiterRelativeLayout{

    private TextView uploadTv;

    private ImageView uploadImageIv;

    private EditText confirmContentEt;

    private TextView confirmTv;

    public PaymentResultPopWidget(Context context) {
        super(context);
    }

    public PaymentResultPopWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaymentResultPopWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.pop_payment_result_widget;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        uploadTv = (TextView) this.findViewById(R.id.upload_tv);
        uploadImageIv = (ImageView) this.findViewById(R.id.upload_image_iv);
        confirmContentEt = (EditText) this.findViewById(R.id.confirm_content_et);
        confirmTv = (TextView) this.findViewById(R.id.confirm_tv);
    }

    public TextView getUploadTv() {
        return uploadTv;
    }

    public ImageView getUploadImageIv() {
        return uploadImageIv;
    }

    public EditText getConfirmContentEt() {
        return confirmContentEt;
    }

    public TextView getConfirmTv() {
        return confirmTv;
    }
}
