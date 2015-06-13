package com.yun9.wservice.view.client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    private Client client;
    private LinkedList<Client> clients;
    private ClientListAdapter clientListAdapter;
    private ListView clientListView;

    private PtrClassicFrameLayout mPtrFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clients = new LinkedList<>();
        clientListView = (ListView) findViewById(R.id.client_list_ptr);
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


        this.setClientSearchTextChanged();
        titleBarLayout = (JupiterTitleBarLayout) findViewById(R.id.client_title);
        titleBarLayout.getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent();
                //这里跳转的对象到时候要换成表单
                intent.setClass(ClientActivity.this, NewDynamicActivity.class);
                startActivity(intent);*/
                FormActivity.start(ClientActivity.this, 1, fakeData());
            }
        });
        titleBarLayout.getTitleLeftIV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /*
        *下面是示例代码
         */

        client = new Client();
        client.setId("clientID:id1");
        client.setName("clientName:name1");
        client.setNo("clientNo:no1");
        clients.addFirst(client);
        /*
        *上面是示例代码
         */
        clientListAdapter = new ClientListAdapter(this, clients);
        clientListView.setAdapter(clientListAdapter);

    }

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

        TextFormCellBean textFormCell = new TextFormCellBean();
        textFormCell.setType(TextFormCell.class.getSimpleName());
        textFormCell.setKey("name");
        textFormCell.setDefaultValue("hello");
        textFormCell.setLabel("客户名称");
        textFormCell.setRequired(true);
        formBean.putCellBean(textFormCell);

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
        client.setNo((String) formBean.getCellBeanValue("name"));
        client.setId((String) formBean.getCellBeanValue("name"));
        clients.addFirst(client);

        clientListAdapter.notifyDataSetChanged();
    }


    private void refreshClient() {
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
            //completeRefreshProduct();
            super.onPostExecute(result);
        }

    }
}
