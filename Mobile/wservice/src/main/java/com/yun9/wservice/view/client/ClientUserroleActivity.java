package com.yun9.wservice.view.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.ClientAndUsers;
import com.yun9.wservice.model.ClientUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2015/8/25.
 */
public class ClientUserroleActivity extends JupiterFragmentActivity {
    @ViewInject(id = R.id.client_consultant_salesman_title)
    private JupiterTitleBarLayout titleBarLayout;
    @ViewInject(id = R.id.client_consultant_salesman_list)
    private SwipeMenuListView listView;

    private List<ClientUser> clientUsers;
    private String clientid;
    private EditClientCommand command;

    @BeanInject
    private ResourceFactory resourceFactory;

    public static void start(Activity activity, EditClientCommand command){
        Intent intent = new Intent(activity, ClientUserroleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_client_userrole;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarLayout.getTitleLeftIV().setOnClickListener(onBackClickListener);
        command = (EditClientCommand) getIntent().getSerializableExtra("command");
        if(AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getClientId())){
            clientid = command.getClientId();
        }
        getConsultantSalesman();
    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void getConsultantSalesman(){
        final Resource resource = resourceFactory.create("QueryInstClientInfoById");
        resource.param("instClient", clientid);
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.app_wating), true);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                ClientAndUsers clientAndUsers = (ClientAndUsers) response.getPayload();
                clientUsers = new ArrayList<ClientUser>();
                if(AssertValue.isNotNull(clientAndUsers.getClientUsers())) {
                    for (int i = 0; i < clientAndUsers.getClientUsers().size(); i++) {
                        ClientUser clientUser = clientAndUsers.getClientUsers().get(i);
                        clientUsers.add(clientUser);
                    }
                    showUp();
                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                progressDialog.dismiss();
            }
        });
    }

    private void showUp(){
        listView.setAdapter(new ClientConsultantSalesmanAdapter(mContext));
    }
    private class ClientConsultantSalesmanAdapter extends JupiterAdapter{
        private Context mContext;
        public ClientConsultantSalesmanAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return clientUsers.size();
        }

        @Override
        public Object getItem(int position) {
            return clientUsers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ClientUserroleItemWidget clientUserroleItemWidget = null;
            ClientUser clientUser = clientUsers.get(position);

            if(AssertValue.isNotNull(convertView)){
                clientUserroleItemWidget = (ClientUserroleItemWidget) convertView;
            }else {
                clientUserroleItemWidget = new ClientUserroleItemWidget(mContext);
            }
            if(AssertValue.isNotNull(clientUser)){
                switch (clientUser.getUserrole()){
                    case "adviser":
                        clientUserroleItemWidget.getTitleTv().setText(R.string.exclusive_advisor);
                        break;
                    case "salesman":
                        clientUserroleItemWidget.getTitleTv().setText(R.string.salesman);
                        break;
                }
                queryAndDisplayUserName(clientUser, clientUserroleItemWidget);
            }
            return clientUserroleItemWidget;
        }
    }

    private void queryAndDisplayUserName(final ClientUser clientUser, final ClientUserroleItemWidget clientUserroleItemWidget){
        Resource resource1 = resourceFactory.create("QueryUserInfoByIdService");
        resource1.param("userid", clientUser.getUserid());
        resource1.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                User user = (User) response.getPayload();
                if(AssertValue.isNotNull(user)) {
                    clientUserroleItemWidget.getContentTv().setText(user.getName());
                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }
}
