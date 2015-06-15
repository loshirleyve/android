package com.yun9.wservice.view.client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.cell.DetailFormCell;
import com.yun9.jupiter.form.cell.DocFormCell;
import com.yun9.jupiter.form.cell.ImageFormCell;
import com.yun9.jupiter.form.cell.MultiSelectFormCell;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.form.cell.UserFormCell;
import com.yun9.jupiter.form.model.DetailFormCellBean;
import com.yun9.jupiter.form.model.DocFormCellBean;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.ImageFormCellBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.form.model.TextFormCellBean;
import com.yun9.jupiter.form.model.UserFormCellBean;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Client;
import com.yun9.wservice.view.dynamic.NewDynamicActivity;
import com.yun9.wservice.view.main.MainActivity;
import com.yun9.wservice.view.myself.UserFragment;
import com.yun9.wservice.view.register.UserRegisterActivity;

import java.util.ArrayList;
import java.util.LinkedList;
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

    private LinearLayout searchLL;
    private ImageView searchFIV;

    private LinkedList<Client> clients = new LinkedList<Client>();
    private ClientListAdapter clientListAdapter;
    private ListView clientListView;
    private Client client;

    //private PtrClassicFrameLayout mPtrFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //clients = new LinkedList<>();
        clientListView = (ListView) findViewById(R.id.client_list_ptr);
/*
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame_client);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshClient();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
*/


        this.setClientSearchTextChanged();
        titleBarLayout = (JupiterTitleBarLayout) findViewById(R.id.client_title);

        titleBarLayout.getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormActivity.start(ClientActivity.this, 1, fakeData());
            }
        });

        titleBarLayout.getTitleLeftIV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //completeAddClient();
        if(!AssertValue.isNotNull(clientListAdapter)) {
            clientListAdapter = new ClientListAdapter(this, clients);
            clientListView.setAdapter(clientListAdapter);
        }else {
            clientListAdapter.notifyDataSetChanged();
        }

    }

/*    private void completeAddClient(){
        if(AssertValue.isNotNull(clients)){
            clients = new LinkedList<>();
        }

        for(int i = 0; i < clients.size(); i++){
            Client client = new Client();
            client.setId("Id" + i);
            client.setNo("No" + i);
            client.setName("Name" + i);
            clients.addFirst(client);
        }
    }*/


    @Override
    protected int getContentView() {
        return R.layout.activity_client;
    }


    private void setClientSearchTextChanged() {
        clientSearchEdt = (EditText) findViewById(R.id.searchEdt);
        searchLL = (LinearLayout) findViewById(R.id.searchLL);
        searchFIV = (ImageView) findViewById(R.id.searchFIV);
        searchEdt = (EditText) findViewById(R.id.searchEdt);

        clientSearchEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdt.setCursorVisible(true);
                searchLL.setVisibility(View.GONE);
                searchFIV.setVisibility(View.VISIBLE);

            }
        });
        clientSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchFIV.setVisibility(View.VISIBLE);
                if (s.length() == 0) {
                    searchLL.setVisibility(View.VISIBLE);

                } else {
                    searchLL.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchFIV.setVisibility(View.VISIBLE);
                if (s.length() == 0) {
                    searchLL.setVisibility(View.VISIBLE);

                } else {
                    searchLL.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                searchFIV.setVisibility(View.VISIBLE);
                searchLL.setVisibility(View.GONE);
            }
        });
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
        FormBean formBean = (FormBean) data.getSerializableExtra("form");
        //formBean.getCellBeanValue("testText");
        //client.setName((String)formBean.getCellBeanValue("testText"));
        client = new Client();
        client.setName((String) formBean.getCellBeanValue("name"));
        client.setId((String) formBean.getCellBeanValue("contactName"));
        client.setNo((String) formBean.getCellBeanValue("phoneNo"));
        clients.addFirst(client);

        clientListAdapter.notifyDataSetChanged();
    }


/*    private void refreshClient() {
        //TODO 执行服务器刷新
        if (AssertValue.isNotNull(clients)) {
            new GetDataTask().execute();
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return new String[1];
        }
        @Override
        protected void onPostExecute(String[] result) {
            //completeAddClient();
            super.onPostExecute(result);
        }
    }*/
}