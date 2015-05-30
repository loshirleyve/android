package com.yun9.wservice.view.org;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/5/29.
 */
public class OrgListActivity extends JupiterFragmentActivity {

    private OrgListCommand command;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    public static void startByHr(Context context, OrgListCommand command) {
        String title = context.getResources().getString(R.string.org_list_title_hr);
        command.setDimType("hr");
        command.setTitle(title);
        start(context, command);
    }

    public static void startByGroup(Context context, OrgListCommand command) {
        String title = context.getResources().getString(R.string.org_list_title_group);
        command.setTitle(title);
        command.setDimType("group");
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
        return R.layout.activity_org_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrgListCommand) this.getIntent().getSerializableExtra("command");

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getTitle())){
            this.titleBarLayout.getTitleTv().setText(command.getTitle());
        }
    }
}
