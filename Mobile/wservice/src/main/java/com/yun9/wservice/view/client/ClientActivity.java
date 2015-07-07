package com.yun9.wservice.view.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
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
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.util.StringUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterSearchInputLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.CacheClientProxy;
import com.yun9.wservice.cache.ClientProxyCache;
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

    public static final int VIEW_TYPE_NORMAL = 0;

    public static final int VIEW_TYPE_PROXY = 1;

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


    public static void start(Activity activity,ClientCommand command) {
        Intent intent = new Intent(activity, ClientActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command",command);
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

        // 设置左滑菜单
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(233, 75, 53)));
                openItem.setWidth(PublicHelp.dip2px(ClientActivity.this, 90));
                openItem.setTitle("打开");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);

                SwipeMenuItem initalItem = new SwipeMenuItem(
                        getApplicationContext());
                initalItem.setBackground(new ColorDrawable(Color.rgb(6, 119, 183)));
                initalItem.setWidth(PublicHelp.dip2px(ClientActivity.this, 100));
                initalItem.setTitle("初始化机构");
                initalItem.setTitleSize(18);
                initalItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(initalItem);

                if (menu.getViewType() == VIEW_TYPE_NORMAL){
                    SwipeMenuItem proxyItem = new SwipeMenuItem(
                            getApplicationContext());
                    proxyItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                            0xCE)));
                    proxyItem.setWidth(PublicHelp.dip2px(ClientActivity.this, 90));
                    proxyItem.setTitle("代理客户");
                    proxyItem.setTitleSize(18);
                    proxyItem.setTitleColor(Color.WHITE);
                    menu.addMenuItem(proxyItem);
                }

                if (menu.getViewType() == VIEW_TYPE_PROXY){
                    SwipeMenuItem deleteProxy = new SwipeMenuItem(
                            getApplicationContext());
                    deleteProxy.setBackground(new ColorDrawable(Color.rgb(233, 75, 53)));
                    deleteProxy.setWidth(PublicHelp.dip2px(ClientActivity.this, 90));
                    deleteProxy.setTitle("取消代理");
                    deleteProxy.setTitleSize(18);
                    deleteProxy.setTitleColor(Color.WHITE);
                    menu.addMenuItem(deleteProxy);
                }
            }
        };

        clientListView.setMenuCreator(creator);

        clientListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Client client = showClients.get(position);
                switch (index) {
                    case 0:
                        showToast("打开客户");
                        break;
                    case 1:
                        showToast("初始化机构：" + client.getName());
                        break;
                    case 2:
                        if (menu.getViewType() == VIEW_TYPE_NORMAL){
                            CacheClientProxy clientProxy = new CacheClientProxy();
                            clientProxy.setInstId(client.getInstid());
                            clientProxy.setUserId(client.getId());
                            ClientProxyCache.getInstance().putClientProxy(clientProxy);
                            showToast("成功代理客户：" + client.getFullname());
                        } else if (menu.getViewType() == VIEW_TYPE_PROXY) {
                            ClientProxyCache.getInstance().putClientProxy(null);
                            showToast("成功取消代理");
                        }

                        break;
                }
                clientListAdapter.notifyDataSetChanged();
                // false : close the menu; true : not close the menu
                return false;
            }
        });

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
            super.onActivityResult(requestCode, resultCode, data);

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
            // TODO 添加客户
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
            clientListView.smoothOpenMenu(position);
        }
    };

}
