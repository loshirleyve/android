package com.yun9.wservice.view.org;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/5/29.
 * 组织结构综合显示界面。
 */

public class OrgCompositeActivity extends JupiterFragmentActivity {


    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, OrgCompositeActivity.class);
        if (AssertValue.isNotNull(bundle)) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_org_composite;
    }
}
