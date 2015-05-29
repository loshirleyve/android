package com.yun9.wservice.view.org;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;

/**
 * Created by Leon on 15/5/29.
 */
public class OrgListActivity extends JupiterFragmentActivity {

    private OrgListCommand command;

    public static void startByHr(Context context, OrgListCommand command) {
        start(context, command);
    }

    public static void startByGroup(Context context, OrgListCommand command) {
        start(context, command);
    }

    public static void start(Context context, OrgListCommand command) {
        Intent intent = new Intent(context, OrgListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrgListCommand) this.getIntent().getSerializableExtra("command");


    }
}
