package com.yun9.wservice.view.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.util.StringUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterSearchInputLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
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

    public static final int VIEW_TYPE_NORMAL = 0;

    public static final int VIEW_TYPE_PROXY = 1;

    public static final int VIEW_TYPE_INITED_NORMAL = 2;

    public static final int VIEW_TYPE_INITED_PROXY = 3;

    private static final Logger logger = Logger.getLogger(ClientActivity.class);

    @ViewInject(id=R.id.client_title)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.searchRL)
    private JupiterSearchInputLayout jupiterSearchInputLayout;

    private ClientListAdapter clientListAdapter;

    @ViewInject(id=R.id.client_list_ptr)
    private SwipeMenuListView clientListView;

    @ViewInject(id=R.id.rotate_header_list_view_frame_client)
    private PtrClassicFrameLayout mPtrFrame;

    @BeanInject
    private ResourceFactory resourceFactory;
    @BeanInject
    private SessionManager sessionManager;

    private List<Client> clients;
    private List<Client> showClients;

    private EditClientCommand command;
    private ClientItemLayout clientItemLayout;


    public static void start(Activity activity,ClientCommand command) {
        Intent intent = new Intent(activity, ClientActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clients = new ArrayList<>();
        showClients = new ArrayList<>();
        buildView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        jupiterSearchInputLayout.getEditLL().setVisibility(View.GONE);
        jupiterSearchInputLayout.getShowLL().setVisibility(View.VISIBLE);
        inputMethodManager.hideSoftInputFromWindow(jupiterSearchInputLayout.getSearchET().getWindowToken(), 0);
        return super.dispatchTouchEvent(ev);
    }

    private void buildView() {
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
        Resource resource = resourceFactory.create("QueryClientsByAdviser");
        resource.param("instid", sessionManager.getInst().getId()).param("userid", sessionManager.getUser().getId());
        showClients.clear();
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                clients = (List<Client>) response.getPayload();
                if (AssertValue.isNotNullAndNotEmpty(clients)) {
                    showClients.addAll(clients);
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
            if (requestCode == command.getRequestCode()
                    && resultCode == JupiterCommand.RESULT_CODE_OK){
                refresh();
            }
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
            editClient(null);
        }
    };

    private void editClient(String clientId) {
        command = new EditClientCommand();
        command.setClientId(clientId);
        ClientDetaiActivity.start(this, command);
    }

    private View.OnClickListener onTitleLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //clientListView.smoothOpenMenu(position);
            Client client = showClients.get(position);
            editClient(client.getId());
        }
    };
}
