package com.yun9.wservice.view.client;

import android.content.Context;
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
import com.yun9.jupiter.form.cell.MultiSelectFormCell;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
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

    private int formRequestCode = 1000;

    private PtrClassicFrameLayout mPtrFrame;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarLayout = (JupiterTitleBarLayout) findViewById(R.id.client_title);
        clientListView = (ListView) findViewById(R.id.client_list_ptr);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame_client);

        jupiterSearchInputLayout = (JupiterSearchInputLayout) this.findViewById(R.id.searchRL);

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
        }, 100);

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
                clientListAdapter.notifyDataSetChanged();
                mPtrFrame.refreshComplete();
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

        TextFormCellBean instidTFC = new TextFormCellBean();
        instidTFC.setType(TextFormCell.class.getSimpleName());
        instidTFC.setKey("instid");
        instidTFC.setDefaultValue("2");
        instidTFC.setLabel("ID");
        instidTFC.setRequired(true);
        formBean.putCellBean(instidTFC);

        TextFormCellBean nameTFC = new TextFormCellBean();
        nameTFC.setType(TextFormCell.class.getSimpleName());
        nameTFC.setKey("name");
        nameTFC.setDefaultValue("客户名XXX有限公司");
        nameTFC.setLabel("公司简称");
        nameTFC.setRequired(true);
        formBean.putCellBean(nameTFC);

        TextFormCellBean fullnameTFC = new TextFormCellBean();
        fullnameTFC.setType(TextFormCell.class.getSimpleName());
        fullnameTFC.setKey("fullname");
        fullnameTFC.setDefaultValue("客户名XXX有限公司全称");
        fullnameTFC.setLabel("公司全称");
        fullnameTFC.setRequired(true);
        formBean.putCellBean(fullnameTFC);

        MultiSelectFormCellBean typeMSFC = new MultiSelectFormCellBean();
        typeMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        typeMSFC.setCtrlCode("clientlevel");
        typeMSFC.setKey("type");
        typeMSFC.setLabel("类型");
        //typeMSFC.setRequired(true);
        formBean.putCellBean(typeMSFC);

        MultiSelectFormCellBean levelMSFC = new MultiSelectFormCellBean();
        levelMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        levelMSFC.setCtrlCode("clientlevel");
        levelMSFC.setKey("level");
        levelMSFC.setLabel("客户等级");
        //levelMSFC.setRequired(true);
        formBean.putCellBean(levelMSFC);

        TextFormCellBean contactmanTFC = new TextFormCellBean();
        contactmanTFC.setType(TextFormCell.class.getSimpleName());
        contactmanTFC.setKey("contactman");
        contactmanTFC.setDefaultValue("联系人名");
        contactmanTFC.setLabel("联系人名");
        contactmanTFC.setRequired(true);
        formBean.putCellBean(contactmanTFC);

        TextFormCellBean contactphoneTFC = new TextFormCellBean();
        contactphoneTFC.setType(TextFormCell.class.getSimpleName());
        contactphoneTFC.setKey("contactphone");
        contactphoneTFC.setDefaultValue("19800004500");
        contactphoneTFC.setLabel("联系电话");
        contactphoneTFC.setRequired(true);
        formBean.putCellBean(contactphoneTFC);

        TextFormCellBean regionTFC = new TextFormCellBean();
        regionTFC.setType(TextFormCell.class.getSimpleName());
        regionTFC.setKey("region");
        regionTFC.setDefaultValue("1");
        regionTFC.setLabel("区域");
        regionTFC.setRequired(true);
        formBean.putCellBean(regionTFC);

        MultiSelectFormCellBean sourceMSFC = new MultiSelectFormCellBean();
        sourceMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        sourceMSFC.setCtrlCode("clientsource");
        sourceMSFC.setKey("source");
        sourceMSFC.setLabel("来源");
       // sourceMSFC.setRequired(true);
        formBean.putCellBean(sourceMSFC);

        MultiSelectFormCellBean industryMSFC = new MultiSelectFormCellBean();
        industryMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        industryMSFC.setCtrlCode("clientindustry");
        industryMSFC.setKey("industry");
        industryMSFC.setLabel("行业");
        //industryMSFC.setRequired(true);
        formBean.putCellBean(industryMSFC);

        MultiSelectFormCellBean contactpositionMSFC = new MultiSelectFormCellBean();
        contactpositionMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        contactpositionMSFC.setCtrlCode("contactposition");
        contactpositionMSFC.setKey("contactposition");
        contactpositionMSFC.setLabel("职位");
        //contactpositionMSFC.setRequired(true);
        formBean.putCellBean(contactpositionMSFC);

        return formBean;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == formRequestCode && resultCode == FormActivity.RESPONSE_CODE.COMPLETE) {
        FormBean formBean = (FormBean) data.getSerializableExtra("form");
        Client client = new Client();
        client.setInstid((String) formBean.getCellBeanValue("instid"));
        client.setName((String) formBean.getCellBeanValue("name"));
        client.setFullname((String) formBean.getCellBeanValue("fullname"));

        //client.setType((String) formBean.getCellBeanValue("type"));
        client.setType("dnterprise");
        //client.setLevel((String) formBean.getCellBeanValue("level"));
        client.setLevel("A");
        client.setContactman((String) formBean.getCellBeanValue("contactman"));
        client.setContactphone((String) formBean.getCellBeanValue("contactphone"));
        client.setRegion((String) formBean.getCellBeanValue("region"));

        //client.setSource((String) formBean.getCellBeanValue("source"));
        client.setSource("network");
        //client.setIndustry((String) formBean.getCellBeanValue("industry"));
        client.setIndustry("retail");
        //client.setContactposition((String) formBean.getCellBeanValue("contactposition"));
        client.setContactposition("legalperson");

        addToDB(client);
        }
    }

    private void addToDB(Client client) {
        Resource resource = resourceFactory.create("AddInstClients");

        resource.param("instid", client.getInstid());
        resource.param("name", client.getName());
        resource.param("fullname", client.getFullname());
        resource.param("type", client.getType());
        resource.param("level", client.getLevel());
        resource.param("contactman", client.getContactman());
        resource.param("contactphone", client.getContactphone());
        resource.param("region", client.getRegion());
        resource.param("source", client.getSource());
        resource.param("industry", client.getIndustry());
        resource.param("contactposition", client.getContactposition());
        resource.param("createby", sessionManager.getUser().getId());

        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {

            @Override
            public void onSuccess(Response response) {
                refresh();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(ClientActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                clientListAdapter.notifyDataSetChanged();
                mPtrFrame.refreshComplete();
            }
        });
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

            if (!AssertValue.isNotNullAndNotEmpty(s.toString())) {
                for (Client client : clients) {
                    showClients.add(client);
                }
            } else {
                for (Client client : clients) {
                    if (StringUtil.contains(client.getName(), s.toString(), true)) {
                        showClients.add(client);
                    }
                }
            }
            clientListAdapter.notifyDataSetChanged();
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
