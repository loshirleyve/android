package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;

/**
 * Created by Leon on 15/4/24.
 */
public class MsgCardDetailActivity extends JupiterFragmentActivity{

    public final static  String ARG_MSG_CARD = "MSG_CARD";

    private MsgCard msgCard;

    @ViewInject(id = R.id.msg_card_detail_title)
    private JupiterTitleBarLayout titleBar;

    public static void start(Context context,Bundle bundle){
        Intent intent = new Intent(context,MsgCardDetailActivity.class);
        if (AssertValue.isNotNull(bundle)){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AssertValue.isNotNull(this.getIntent().getExtras())){
            msgCard = (MsgCard) this.getIntent().getExtras().getSerializable(ARG_MSG_CARD);
        }

        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_msg_card_detail;
    }

}
