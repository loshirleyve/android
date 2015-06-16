package com.yun9.wservice.view.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.TextFormCellBean;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.StringUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterSearchInputLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Client;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by li on 2015/6/11.
 */
public class ClientActivity extends JupiterFragmentActivity {

    private EditText clientSearchEdt;
    private JupiterTitleBarLayout titleBarLayout;
    private EditText searchEdt;

    private JupiterSearchInputLayout jupiterSearchInputLayout;

    private List<Client> clients = new ArrayList<>();
    private List<Client> showClients = new ArrayList<>();

    private ClientListAdapter clientListAdapter;
    private ListView clientListView;
    private Client client;

    private int formRequestCode = 1000;

    private PtrClassicFrameLayout mPtrFrame;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarLayout = (JupiterTitleBarLayout) findViewById(R.id.client_title);
        clientListView = (ListView) findViewById(R.id.client_list_ptr);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame_client);

        jupiterSearchInputLayout = (JupiterSearchInputLayout)this.findViewById(R.id.searchRL);

        jupiterSearchInputLayout.getSearchET().addTextChangedListener(textWatcher);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        titleBarLayout.getTitleRightTv().setOnClickListener(onTitleRightTvClickListener);
        titleBarLayout.getTitleLeftIV().setOnClickListener(onTitleLeftClickListener);

        clientListAdapter = new ClientListAdapter(this, showClients);
        clientListView.setAdapter(clientListAdapter);

        clientListView.postDelayed(new Runnable() {
            @Override
            public void run() {
               mPtrFrame.autoRefresh();
            }
        },100);

    }

    private void refresh() {
        Resource resource = resourceFactory.create("QueryInstClients");

        resource.param("instid", sessionManager.getInst().getId());
        resource.param("createby", sessionManager.getUser().getId());
        showClients.clear();
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                clients = (List<Client>) response.getPayload();
                if (AssertValue.isNotNullAndNotEmpty(clients)) {
                    for (Client client : clients) {
                        showClients.add(client);
                    }
                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(ClientActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                mPtrFrame.refreshComplete();
                clientListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_client;
    }

    public FormBean fakeData() {
        FormBean formBean = FormBean.getInstance();
        formBean.setTitle("测试表单");
        formBean.setKey("demoform");

        TextFormCellBean textFormCell1 = new TextFormCellBean();
        textFormCell1.setType(TextFormCell.class.getSimpleName());
        textFormCell1.setKey("name");
        textFormCell1.setDefaultValue("客户名XXX有限公司");
        textFormCell1.setLabel("客户名称");
        textFormCell1.setRequired(true);
        formBean.putCellBean(textFormCell1);

        TextFormCellBean textFormCell2 = new TextFormCellBean();
        textFormCell2.setType(TextFormCell.class.getSimpleName());
        textFormCell2.setKey("contactName");
        textFormCell2.setDefaultValue("联系人名");
        textFormCell2.setLabel("联系人名");
        textFormCell2.setRequired(true);
        formBean.putCellBean(textFormCell2);

        TextFormCellBean textFormCell3 = new TextFormCellBean();
        textFormCell3.setType(TextFormCell.class.getSimpleName());
        textFormCell3.setKey("phoneNo");
        textFormCell3.setDefaultValue("19800004500");
        textFormCell3.setLabel("联系电话");
        textFormCell3.setRequired(true);
        formBean.putCellBean(textFormCell3);

        return formBean;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == formRequestCode && resultCode == FormActivity.RESULT_OK) {
            FormBean formBean = (FormBean) data.getSerializableExtra("form");
            client = new Client();
            client.setName((String) formBean.getCellBeanValue("name"));
            client.setId((String) formBean.getCellBeanValue("contactName"));
            client.setSn((String) formBean.getCellBeanValue("phoneNo"));
            clients.add(client);

            clientListAdapter.notifyDataSetChanged();
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            jupiterSearchInputLayout.getSearchET().setCursorVisible(true);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            jupiterSearchInputLayout.getSearchET().setCursorVisible(true);

        }

        @Override
        public void afterTextChanged(Editable s) {
               showClients.clear();

            if(!AssertValue.isNotNullAndNotEmpty(s.toString())){
                for(Client client : clients){
                    showClients.add(client);
                }
            }else {
                for(Client client : clients){
                    if(StringUtil.contains(client.getName(), s.toString(), true)){
                        showClients.add(client);
                    }
                }
            }
            refresh();
        }
    };

    private View.OnClickListener onTitleRightTvClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FormActivity.start(ClientActivity.this, formRequestCode, fakeData());
        }
    };

    private View.OnClickListener onTitleLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
