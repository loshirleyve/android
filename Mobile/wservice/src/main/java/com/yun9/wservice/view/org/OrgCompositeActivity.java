package com.yun9.wservice.view.org;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/29.
 * 组织结构综合显示界面。
 */

public class OrgCompositeActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.myself)
    private JupiterRowStyleSutitleLayout myselfLL;

    @ViewInject(id = R.id.org_hr)
    private JupiterRowStyleSutitleLayout orgHrLL;

    @ViewInject(id = R.id.org_group)
    private JupiterRowStyleSutitleLayout orgGroupLL;

    @ViewInject(id = R.id.userlist)
    private ListView userListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orgHrLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgListActivity.startByHr(OrgCompositeActivity.this, new OrgListCommand());
            }
        });

        orgGroupLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgListActivity.startByGroup(OrgCompositeActivity.this, new OrgListCommand());
            }
        });

        //刷新界面数据
        this.refresh();
    }

    private void refresh(){
        List<OrgUserBean> orgUserBeans = this.builderUserInfo();


    }

    private List<OrgUserBean> builderUserInfo(){
        List<OrgUserBean> orgUserBeans = new ArrayList<OrgUserBean>();

        for(int i=0;i<20;i++){
            OrgUserBean orgUserBean = new OrgUserBean();
            orgUserBean.setId(i+"");
            orgUserBean.setName("测试用户" + i);
            orgUserBean.setNo("00" + i);
            orgUserBean.setSelected(false);
            orgUserBeans.add(orgUserBean);
        }

        return orgUserBeans;
    }
}
