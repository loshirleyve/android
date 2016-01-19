package com.yun9.wservice.view.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.ClientAndUsers;
import com.yun9.wservice.model.ClientUser;
import com.yun9.wservice.model.CtrlCode;
import com.yun9.wservice.view.org.OrgCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeCommand;
import com.yun9.wservice.view.org.OrgUserOperateLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by li on 2015/8/25.
 */
public class ClientUserroleActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.client_consultant_salesman_title)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.client_consultant_salesman_list)
    private SwipeMenuListView listView;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    @BeanInject
    private SessionManager sessionManager;

    private PopupWindow popupWindow;

    private ClientAddNewSaleManPopLayout addNewSaleManPopLayout;

    private ProgressDialog registerDialog;

    private ClientConsultantSalesmanAdapter listViewAdapter;

    private AdviserOperateLayout adviserOperateLayout;

    private PopupWindow adviserOperatePopupW;

    private List<ClientUser> clientUsers;
    private String clientid;
    private EditClientCommand command;

    private String userrole;// 将要新增的角色
    @BeanInject
    private ResourceFactory resourceFactory;

    public static void start(Activity activity, EditClientCommand command) {
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
        titleBarLayout.getTitleRightTv().setOnClickListener(onAddNewSaleMan);
        command = (EditClientCommand) getIntent().getSerializableExtra("command");
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getClientId())) {
            clientid = command.getClientId();
        }
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        autoRefresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OrgCompositeCommand.REQUEST_CODE && resultCode == OrgCompositeCommand.RESULT_CODE_OK) {
            List<User> users = (List<User>) data.getSerializableExtra(OrgCompositeCommand.PARAM_USER);
            if (AssertValue.isNotNullAndNotEmpty(users) && AssertValue.isNotNullAndNotEmpty(userrole)) {
                addNewOne(users.get(0));
            }
        }
    }

    private void addNewOne(User user) {
        final Resource resource = resourceFactory.create("AddOrUpdateClientUser");
        resource.param("clientid", clientid);
        resource.param("userid", user.getId());
        resource.param("userrole", userrole);
        resource.invok(new AsyncHttpResponseCallback() {

            @Override
            public void onSuccess(Response response) {

            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                autoRefresh();
            }
        });
    }

    private void refresh() {
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getConsultantSalesman();
            }
        }, 100);
    }

    private void autoRefresh() {
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        }, 100);
    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener onAddNewSaleMan = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupWindow();
        }
    };

    private void getConsultantSalesman() {
        final Resource resource = resourceFactory.create("QueryInstClientInfoById");
        resource.param("instClient", clientid);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                ClientAndUsers clientAndUsers = (ClientAndUsers) response.getPayload();
                clientUsers = new ArrayList<ClientUser>();
                if (AssertValue.isNotNull(clientAndUsers.getClientUsers())) {
                    for (int i = 0; i < clientAndUsers.getClientUsers().size(); i++) {
                        ClientUser clientUser = clientAndUsers.getClientUsers().get(i);
                        clientUsers.add(clientUser);
                    }
                    showUp();
                }
                initPopWindow();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    private void showUp() {
        listViewAdapter=new ClientConsultantSalesmanAdapter(mContext);
        listView.setAdapter(listViewAdapter);
    }

    private class ClientConsultantSalesmanAdapter extends JupiterAdapter {
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

            if (AssertValue.isNotNull(convertView)) {
                clientUserroleItemWidget = (ClientUserroleItemWidget) convertView;
            } else {
                clientUserroleItemWidget = new ClientUserroleItemWidget(mContext);
            }
            clientUserroleItemWidget.setTag(clientUser);
            clientUserroleItemWidget.setOnLongClickListener(onLongAdviserClick);
            if (AssertValue.isNotNull(clientUser)) {
                switch (clientUser.getUserrole()) {
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

    /**
     * 数据完成时才调用
     */
    private void initPopWindow() {
        addNewSaleManPopLayout = new ClientAddNewSaleManPopLayout(mContext);
        popupWindow = new PopupWindow(addNewSaleManPopLayout, 300,300);
        popupWindow.setOnDismissListener(onDismissListener);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        loadUserRoleList();

    }

    private void loadUserRoleList() {
        Resource resource1 = resourceFactory.create("QueryCtrlCode");
        resource1.param("defno", "userrole");
        resource1.invok(new AsyncHttpResponseCallback() {

            @Override
            public void onSuccess(Response response) {
                final List<CtrlCode> ctrlCodes = (List<CtrlCode>) response.getPayload();
                if (AssertValue.isNotNullAndNotEmpty(ctrlCodes)) {
                    JupiterAdapter adapter = new JupiterAdapter() {
                        @Override
                        public int getCount() {
                            return ctrlCodes.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return ctrlCodes.get(position);
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            TextView textView = null;
                            if (convertView == null) {
                                textView = new TextView(ClientUserroleActivity.this);
                                textView.setPadding(10, 10, 10, 10);
                                textView.setGravity(Gravity.CENTER);
                                convertView = textView;
                            } else {
                                textView = (TextView) convertView;
                            }
                            textView.setTag(ctrlCodes.get(position));
                            textView.setText(ctrlCodes.get(position).getName());
                            return convertView;
                        }
                    };
                    addNewSaleManPopLayout.getListView().setAdapter(adapter);
                    addNewSaleManPopLayout.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            userrole = ((CtrlCode)view.getTag()).getNo();
                            popupWindow.dismiss();
                            OrgCompositeCommand orgCompositeCommand = new OrgCompositeCommand().setEdit(true)
                                    .setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK)
                                    .setUserid(sessionManager.getUser().getId())
                                    .setInstid(sessionManager.getInst().getId())
                                    .setSingleSelect(true);
                            OrgCompositeActivity.start(ClientUserroleActivity.this, orgCompositeCommand);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Response response) {

            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    private void showPopupWindow() {
        if (popupWindow != null) {
            WindowManager.LayoutParams lp = this.getWindow().getAttributes();
            lp.alpha = 1f;
            this.getWindow().setAttributes(lp);
            popupWindow.showAtLocation(titleBarLayout, Gravity.NO_GRAVITY,titleBarLayout.getMeasuredWidth()-200,titleBarLayout.getMeasuredHeight() + 50);

        }
    }

    private void queryAndDisplayUserName(final ClientUser clientUser, final ClientUserroleItemWidget clientUserroleItemWidget) {
        Resource resource1 = resourceFactory.create("QueryUserInfoByIdService");
        resource1.param("userid", clientUser.getUserid());
        resource1.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                User user = (User) response.getPayload();
                if (AssertValue.isNotNull(user)) {
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

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = ClientUserroleActivity.this.getWindow().getAttributes();
            lp.alpha = 1f;
            ClientUserroleActivity.this.getWindow().setAttributes(lp);
        }
    };


    View.OnLongClickListener onLongAdviserClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            final ClientUserroleItemWidget item=(ClientUserroleItemWidget)v;
            ClientUser clientUser=(ClientUser)v.getTag();
            if(clientUser!=null)
            {
                ininPupup(item,clientUser);
            }
            return false;
        }
    };


    public void ininPupup(final ClientUserroleItemWidget item,final ClientUser clientUser) {
        adviserOperateLayout = new AdviserOperateLayout(mContext);
        adviserOperateLayout.getCancle().setOnClickListener(onAdviserCancelClickListener);
        adviserOperatePopupW = new PopupWindow(adviserOperateLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        adviserOperatePopupW.setOnDismissListener(onDismissListener);
        adviserOperatePopupW.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adviserOperatePopupW.setOutsideTouchable(true);
        adviserOperatePopupW.setFocusable(true);
        adviserOperateLayout.getDelete_orguser().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClientUserItem(item,clientUser);
            }
        });
        adviserOperatePopupW.setAnimationStyle(R.style.bottom2top_top2bottom);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.4f;
        this.getWindow().setAttributes(lp);
        adviserOperatePopupW.showAtLocation(titleBarLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        adviserOperatePopupW.update();
    }

    private View.OnClickListener onAdviserCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adviserOperatePopupW.dismiss();
        }
    };


    //删除用户的一项的方法
    private void deleteClientUserItem(final ClientUserroleItemWidget item,ClientUser clientUser) {
        registerDialog = ProgressDialog.show(ClientUserroleActivity.this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("RemoveClientUser");
        resource.param("id", clientUser.getId());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                refresh();
                Toast.makeText(ClientUserroleActivity.this, R.string.delete_tip, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(ClientUserroleActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
                adviserOperatePopupW.dismiss();
            }
        });
    }
}
