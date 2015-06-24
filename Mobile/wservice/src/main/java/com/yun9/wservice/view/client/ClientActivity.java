package com.yun9.wservice.view.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.FormCommand;
import com.yun9.jupiter.form.cell.MultiSelectFormCell;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.form.model.TextFormCellBean;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.util.StringUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterSearchInputLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by li on 2015/6/11.
 */
public class ClientActivity extends JupiterFragmentActivity {
    private static final Logger logger = Logger.getLogger(ClientActivity.class);

    private EditText clientSearchEdt;
    private JupiterTitleBarLayout titleBarLayout;
    private EditText searchEdt;

    private JupiterSearchInputLayout jupiterSearchInputLayout;

    private List<Client> clients = new ArrayList<>();
    private List<Client> showClients = new ArrayList<>();

    private ClientListAdapter clientListAdapter;
    private ListView clientListView;
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
        clientListView.setOnItemClickListener(onItemClickListener);

        this.autoRefresh();
    }

    private void refresh() {
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                completeRefresh();
            }
        }, 100);
    }

    private void autoRefresh(){
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    private void completeRefresh() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == FormActivity.RESPONSE_CODE.COMPLETE) {
        FormBean formBean = (FormBean) data.getSerializableExtra("form");
        Client client = new Client();
        client.setName((String) formBean.getCellBeanValue("name"));
        client.setFullname((String) formBean.getCellBeanValue("fullname"));
        List<SerialableEntry<String,String>> typLs = (List<SerialableEntry<String, String>>) formBean.getCellBeanValue("type");
        client.setType(typLs.get(0).getValue());

        /*List<SerialableEntry<String,String>> levLs = (List<SerialableEntry<String, String>>)formBean.getCellBeanValue("level");
        client.setLevel(levLs.get(0).getValue());*/
            client.setLevel("A");

        client.setContactman((String) formBean.getCellBeanValue("contactman"));
        client.setContactphone((String) formBean.getCellBeanValue("contactphone"));
        client.setRegion((String) formBean.getCellBeanValue("region"));

           /* List<SerialableEntry<String, String>> souLs = (List<SerialableEntry<String, String>>)formBean.getCellBeanValue("source");
            client.setSource(souLs.get(0).getValue());*/
            client.setSource("network");

            /*List<SerialableEntry<String, String>> indLs = (List<SerialableEntry<String, String>>)formBean.getCellBeanValue("industry");
            client.setIndustry(indLs.get(0).getValue());*/
            client.setIndustry("retail");

            List<SerialableEntry<String, String>> conLs = (List<SerialableEntry<String, String>>)formBean.getCellBeanValue("contacterposition");
            client.setContactposition(conLs.get(0).getValue());

        addToDB(client);
        }
    }

    private void addToDB(Client client) {
        Resource resource = resourceFactory.create("AddInstClients");

        resource.param("instid", sessionManager.getInst().getId());
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
                autoRefresh();
            }

            @Override
            public void onFailure(Response response) {
               // Toast.makeText(ClientActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                autoRefresh();
                clientListAdapter.notifyDataSetChanged();
                mPtrFrame.refreshComplete();
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
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
            FormCommand formCommand = new FormCommand();
            formCommand.setFormBean(fakeData());
            FormActivity.start(ClientActivity.this, formCommand);
        }
    };

    private View.OnClickListener onTitleLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Client client = (Client)view.getTag();
            logger.d("客户列表项被点击！" + client.getName());

            FormCommand formCommand = new FormCommand();
            formCommand.setFormBean(fakeData());

            Map<String,Object> value = new HashMap<String,Object>();
            value.put("name",client.getName());
            value.put("fullname", client.getFullname());
            value.put("type", Collections.singletonList(
                    new SerialableEntry<String,String>("type",client.getType())));
            value.put("level", Collections.singletonList(
                    new SerialableEntry<String,String>("level",client.getLevel())));
            value.put("contactman", client.getContactman());
            value.put("contactphone", client.getContactphone());
            value.put("region", client.getRegion());
            value.put("source", Collections.singletonList(
                    new SerialableEntry<String,String>("source", client.getSource())));
            value.put("industry", Collections.singletonList(
                    new SerialableEntry<String,String>("industry", client.getIndustry())));
            value.put("contacterposition", Collections.singletonList(
                    new SerialableEntry<String,String>("contacterposition", client.getContactposition())));

            formCommand.setValueJson(JsonUtil.beanToJson(value));

            FormActivity.start(ClientActivity.this, formCommand);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        jupiterSearchInputLayout.getEditLL().setVisibility(View.GONE);
        jupiterSearchInputLayout.getShowLL().setVisibility(View.VISIBLE);
        inputMethodManager.hideSoftInputFromWindow(jupiterSearchInputLayout.getSearchET().getWindowToken(), 0);
        return super.dispatchTouchEvent(ev);
    }

    public FormBean fakeData() {
        FormBean formBean = FormBean.getInstance();
        formBean.setTitle("测试表单");
        formBean.setKey("demoform");

        TextFormCellBean nameTFC = new TextFormCellBean();
        nameTFC.setType(TextFormCell.class.getSimpleName());
        nameTFC.setKey("name");
        nameTFC.setLabel("公司简称");
        nameTFC.setRequired(true);
        formBean.putCellBean(nameTFC);

        TextFormCellBean fullnameTFC = new TextFormCellBean();
        fullnameTFC.setType(TextFormCell.class.getSimpleName());
        fullnameTFC.setKey("fullname");
        fullnameTFC.setLabel("公司全称");
        fullnameTFC.setRequired(true);
        formBean.putCellBean(fullnameTFC);

        MultiSelectFormCellBean typeMSFC = new MultiSelectFormCellBean();
        typeMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        typeMSFC.setCtrlCode("clienttype");
        typeMSFC.setKey("type");
        typeMSFC.setLabel("类型");
        typeMSFC.setRequired(true);
        formBean.putCellBean(typeMSFC);

        MultiSelectFormCellBean levelMSFC = new MultiSelectFormCellBean();
        levelMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        levelMSFC.setCtrlCode("clientlevel");
        levelMSFC.setKey("level");
        levelMSFC.setLabel("客户等级");
        levelMSFC.setRequired(true);
        formBean.putCellBean(levelMSFC);

        TextFormCellBean contactmanTFC = new TextFormCellBean();
        contactmanTFC.setType(TextFormCell.class.getSimpleName());
        contactmanTFC.setKey("contactman");
        contactmanTFC.setLabel("联系人名");
        contactmanTFC.setRequired(true);
        formBean.putCellBean(contactmanTFC);

        TextFormCellBean contactphoneTFC = new TextFormCellBean();
        contactphoneTFC.setType(TextFormCell.class.getSimpleName());
        contactphoneTFC.setKey("contactphone");
        contactphoneTFC.setLabel("联系电话");
        contactphoneTFC.setRequired(true);
        formBean.putCellBean(contactphoneTFC);

        TextFormCellBean regionTFC = new TextFormCellBean();
        regionTFC.setType(TextFormCell.class.getSimpleName());
        regionTFC.setKey("region");
        regionTFC.setLabel("区域");
        regionTFC.setRequired(true);
        formBean.putCellBean(regionTFC);

        MultiSelectFormCellBean sourceMSFC = new MultiSelectFormCellBean();
        sourceMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        sourceMSFC.setCtrlCode("clientsource");
        sourceMSFC.setKey("source");
        sourceMSFC.setLabel("来源");
        //sourceMSFC.setRequired(true);
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
        contactpositionMSFC.setKey("contacterposition");
        contactpositionMSFC.setLabel("职位");
        contactpositionMSFC.setRequired(true);
        formBean.putCellBean(contactpositionMSFC);

        return formBean;
    }
}
