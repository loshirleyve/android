package com.yun9.wservice.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/5/25.
 */
public class DemoFormActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.demoform)
    private JupiterRowStyleSutitleLayout formitem;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_form_demo;
    }


    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, DemoFormActivity.class);
        if (AssertValue.isNotNull(bundle)) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoFormActivity.this.finish();
            }
        });

        formitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormActivity.start(DemoFormActivity.this, new DemoFormAdapter());
            }
        });
    }
}
