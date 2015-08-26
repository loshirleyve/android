package com.yun9.wservice.view.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.CtrlCodeDefNo;
import com.yun9.wservice.model.Client;
import com.yun9.wservice.model.ClientAndUsers;
import com.yun9.wservice.model.ClientUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2015/8/25.
 */
public class ClientConsultantSalesmanActivity extends JupiterFragmentActivity {
    @ViewInject(id = R.id.client_consultant_salesman_title)
    private JupiterTitleBarLayout titleBarLayout;
    @ViewInject(id = R.id.consultantTv)
    private TextView consultantTv;
    @ViewInject(id = R.id.salesmanTv)
    private TextView salesmanTv;
    private String clientid;
    private EditClientCommand command;

    @BeanInject
    private ResourceFactory resourceFactory;

    public static void start(Activity activity, EditClientCommand command){
        Intent intent = new Intent(activity, ClientConsultantSalesmanActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_client_consultant_salesman;
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
                List<ClientUser> clientUsers = new ArrayList<ClientUser>();
                if(AssertValue.isNotNull(clientAndUsers.getClientUsers())) {
                    for (int i = 0; i < clientAndUsers.getClientUsers().size(); i++) {
                        ClientUser clientUser = clientAndUsers.getClientUsers().get(i);
                        clientUsers.add(clientUser);
                    }
                    for (final ClientUser clientUser : clientUsers) {
                        Resource resource1 = resourceFactory.create("QueryUserInfoByIdService");
                        resource1.param("userid", clientUser.getUserid());
                        resource1.invok(new AsyncHttpResponseCallback() {
                            @Override
                            public void onSuccess(Response response) {
                                User user = (User) response.getPayload();
                                if(AssertValue.isNotNull(user)) {
                                    switch (clientUser.getUserrole()){
                                        case "adviser":
                                            consultantTv.setText(user.getName());
                                            break;
                                        case "salesman":
                                            salesmanTv.setText(user.getName());
                                    }
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
}
