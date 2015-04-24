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

public class MsgCardListActivity extends JupiterFragmentActivity {

    public static final String ARG_TYPE = "type";

    public static final String ARG_VALUE = "value";

    private String mType;

    private String mValue;

    @ViewInject(id=R.id.msg_card_list_title)
    private JupiterTitleBarLayout titleBar;

    public static void start(Context context,Bundle bundle){
        Intent intent = new Intent(context,MsgCardListActivity.class);
        if (AssertValue.isNotNull(bundle)){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取传递的参数
        if (AssertValue.isNotNull(this.getIntent().getExtras())) {
            mType = this.getIntent().getExtras().getString(ARG_TYPE);
            mValue = this.getIntent().getExtras().getString(ARG_VALUE);
        }

        titleBar.getTitleLeft().setVisibility(View.VISIBLE);
        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgCardListActivity.this.finish();
            }
        });

   }

    @Override
    protected int getContentView() {
        return R.layout.activity_msg_card_list;
    }
}
