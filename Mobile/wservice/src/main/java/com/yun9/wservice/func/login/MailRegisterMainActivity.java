package com.yun9.wservice.func.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by xia on 2015/5/21.
 */
public class MailRegisterMainActivity extends JupiterActivity {

    @ViewInject(id= R.id.mailreg01_title)
    private JupiterTitleBarLayout mailreg01_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_register_first);
        mailreg01_title.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MailRegisterMainActivity.this.finish();
            }
        });

        mailreg01_title.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MailRegisterMainActivity.this, MailRegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
