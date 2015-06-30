package com.yun9.wservice.view.org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.BasicJupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditIco;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTextIcoWithoutCorner;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrgDetailInfoBean;
import com.yun9.wservice.view.dynamic.OrgAndUserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/2.
 */
public class OrgChooseAddUserActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.add_use_org_to)
    private JupiterRowStyleTitleLayout adduseorg;

    @ViewInject(id = R.id.add_use_phonebook_to)
    private JupiterRowStyleTitleLayout addusephonebook;

    @ViewInject(id=R.id.orgname)
    private TextView orgname;

    private OrgChooseAddUserCommand command;



    public static void start(Activity activity, OrgChooseAddUserCommand command) {
        Intent intent = new Intent(activity, OrgChooseAddUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, OrgChooseAddUserCommand.REQUEST_CODE);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_choose_adduser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrgChooseAddUserCommand) this.getIntent().getSerializableExtra("command");
        initView();

    }


    public void initView()
    {
        orgname.setText(command.getOrgname());
        titleBarLayout.getTitleTv().setText("添加新成员");
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        adduseorg.setOnClickListener(onAddUserOrgClickListener);
        addusephonebook.setOnClickListener(null);
    }

    private View.OnClickListener onAddUserOrgClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            OrgCompositeCommand orgCompositeCommand = new OrgCompositeCommand().setEdit(true).setOnlyUsers(true).setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK);
            OrgCompositeActivity.start(OrgChooseAddUserActivity.this, orgCompositeCommand);
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
