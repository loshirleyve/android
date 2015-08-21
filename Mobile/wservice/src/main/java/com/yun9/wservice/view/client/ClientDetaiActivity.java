package com.yun9.wservice.view.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.CacheClientProxy;
import com.yun9.wservice.cache.ClientProxyCache;
import com.yun9.wservice.model.Client;

/**
 * Created by li on 2015/8/10.
 */
public class ClientDetaiActivity extends JupiterFragmentActivity {
    private EditClientCommand command;
    private String clientid;
    @BeanInject
    private ResourceFactory resourceFactory;
    @ViewInject(id = R.id.clientNameTv)
    private TextView clientNameTv;
    @ViewInject(id = R.id.contactManTv)
    private TextView contactManTv;
    @ViewInject(id = R.id.contactPhoneTv)
    private TextView contactPhoneTv;
    @ViewInject(id = R.id.contactPhoneIv)
    private ImageView contactPhoneIv;
    @ViewInject(id = R.id.client_detai_title)
    private JupiterTitleBarLayout titleBarLayout;
    @ViewInject(id = R.id.btnInitInst)
    private Button btnInitInst;
    @ViewInject(id = R.id.btnCheckDetail)
    private Button btnCheckDetail;
    @ViewInject(id = R.id.btnProxyOrder)
    private Button btnProxyOrder;
    private Client client;

    public ClientDetaiActivity() {
    }

    public static void start(Activity activity, EditClientCommand command){
        Intent intent = new Intent(activity, ClientDetaiActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (EditClientCommand) getIntent().getSerializableExtra("command");
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getClientId())) {
            clientid = command.getClientId();
        }
        if (AssertValue.isNotNullAndNotEmpty(clientid)) {
            queryInstClientById(clientid);
        }
        contactPhoneIv.setOnClickListener(onContactPhoneIvClickListener);
        titleBarLayout.getTitleLeftIV().setOnClickListener(onBackClickListener);
        titleBarLayout.getTitleRightTv().setOnClickListener(onEditorClickListener);
        btnInitInst.setOnClickListener(onBtnInitInstClickListener);
        btnCheckDetail.setOnClickListener(onEditorClickListener);
        btnProxyOrder.setOnClickListener(onBtnProxyOrderClickListener);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_client_detai;
    }
    private void queryInstClientById(String clientid) {
        Resource resource = resourceFactory.create("QueryInstClientById");
        resource.param("instClient", clientid);
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.app_wating), true);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                client = (Client) response.getPayload();
                displayClientData(client);
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
    private void displayClientData(Client client){
        clientNameTv.setText(client.getName());
        contactManTv.setText(client.getContactman());
        contactPhoneTv.setText(client.getContactphone());
    }

    private View.OnClickListener onContactPhoneIvClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.CALL");
            intent.setData(Uri.parse("tel:" + contactPhoneTv.getText().toString()));
            startActivity(intent);
        }
    };
    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener onEditorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ClientDetailActivity.start(ClientDetaiActivity.this, command);
        }
    };
    private View.OnClickListener onBtnInitInstClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initClient(client);
        }
    };
    private void refresh() {
        ClientDetaiActivity.start(this, command);
    }
    private void initClient(Client client) {
        final ProgressDialog registerDialog =
                ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("InstInit");
        resource.param("companyName",client.getFullname());
        resource.param("companyNo",client.getSn());
        resource.param("companyScale",client.getScaleid());
        resource.param("userNo", client.getContactphone());
        resource.param("userName", client.getContactman());
        resource.param("clientId", client.getId());
        resource.param("simpleName", client.getName());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                showToast(getString(R.string.init_inst_success));
                refresh();
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
            }
        });
    }
    private View.OnClickListener onBtnProxyOrderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            proxyClient(client);
        }
    };
    private void proxyClient(Client client) {
        if (!AssertValue.isNotNullAndNotEmpty(client.getClientinstid())){
            showToast(R.string.the_client_is_not_init_yet);
            return;
        }
        CacheClientProxy proxy = ClientProxyCache.getInstance().getProxy();
        if (proxy != null && client.getClientadminid().equals(proxy.getUserId())
                && proxy.getInstId().equals(client.getClientinstid())){
            ClientProxyCache.getInstance().putClientProxy(null);
            showToast(R.string.success_cancel_proxy);
        } else {
            CacheClientProxy clientProxy = new CacheClientProxy();
            clientProxy.setInstId(client.getClientinstid());
            clientProxy.setUserId(client.getClientadminid());
            clientProxy.setClientId(client.getId());
            ClientProxyCache.getInstance().putClientProxy(clientProxy);
            showToast(getResources().getString(R.string.success_proxy_client,client.getFullname()));
        }
    }

}