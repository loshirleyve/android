package com.yun9.wservice.view.org;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.model.UserContact;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.SelectableRoundedImageView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.ContactKey;
import com.yun9.wservice.model.OrgUser;
import com.yun9.wservice.view.dynamic.NewDynamicActivity;
import com.yun9.wservice.view.dynamic.NewDynamicCommand;
import com.yun9.wservice.view.msgcard.MsgCardListActivity;
import com.yun9.wservice.view.msgcard.MsgCardListCommand;

import java.util.Collections;

/**
 * Created by huangbinglong on 7/9/15.
 */
public class OrgUserDetailActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.user_head)
    private ImageView userHead;

    @ViewInject(id=R.id.user_name_tv)
    private TextView userNameTv;

    @ViewInject(id=R.id.user_org_name_tv)
    private TextView userOrgNameTv;

    @ViewInject(id=R.id.signature_tv)
    private TextView signature;

    @ViewInject(id=R.id.checkout_his_msg_ll)
    private LinearLayout checkoutHisMsg;

    @ViewInject(id=R.id.user_contact_ll)
    private LinearLayout userContactLl;

    @ViewInject(id=R.id.confirm_ll)
    private LinearLayout confirmLl;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private OrgUserDetailCommand command;

    private OrgUser user;

    public static void start(Activity activity,OrgUserDetailCommand command) {
        Intent intent = new Intent(activity,OrgUserDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND,command);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrgUserDetailCommand) getIntent()
                                            .getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        buildView();
        loadData();
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null,
                                                getResources()
                                                        .getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryUserInfoByIdService");
        resource.param("userid", command.getUserId());
        resource.param("instid", sessionManager.getInst().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                OrgUser user = (OrgUser) response.getPayload();
                buildWithData(user);
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
                buildWithData(null);
            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
            }
        });
    }

    private void buildWithData(OrgUser user) {
        if (user == null){
            return;
        }
        this.user = user;
        ImageLoaderUtil.getInstance(this).displayImage(user.getHeaderfileid(),userHead);
        userNameTv.setText(user.getName());
        userOrgNameTv.setText(user.getOrgNames());
        signature.setText(user.getSignature());
        if (user.getContacts() != null){
            UserContact contact;
            OrgUserContactWidget contactWidget;
            for (int i = 0; i < user.getContacts().size(); i++) {
                contact = user.getContacts().get(i);
                contactWidget = new OrgUserContactWidget(this);
                contactWidget.getTitleTv().setText(contact.getContentlabel());
                contactWidget.getContentTv().setText(contact.getContactvalue());
                appendOperator(contact,contactWidget);
                userContactLl.addView(contactWidget);
            }
        }
    }

    private void appendOperator(final UserContact contact, OrgUserContactWidget contactWidget) {
        ImageView operate1 = new ImageView(this);
        operate1.setLayoutParams(new ViewGroup.LayoutParams(
                PublicHelp.dip2px(this,30),PublicHelp.dip2px(this,30)
        ));
        if (ContactKey.WEIXIN.equals(contact.getContactkey())) {
            operate1.setBackgroundDrawable(getResources().getDrawable(R.drawable.fw1));
            operate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, contact.getContactvalue()));
                    showToast("已复制微信号到粘贴板");
                    Intent intent = new Intent();
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivityForResult(intent,0);
                }
            });
        } else if (ContactKey.EMAIL.equals(contact.getContactkey())) {
            operate1.setBackgroundDrawable(getResources().getDrawable(R.drawable.email));
            operate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] reciver = new String[]{contact.getContactvalue()};
                    String[] mySbuject = new String[]{getResources().getString(R.string.app_name)};
                    Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
                    myIntent.setType("plain/text");
                    myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
                    myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mySbuject);
                    startActivity(Intent.createChooser(myIntent, mySbuject[0]));
                }
            });
        } else if (ContactKey.PHONE.equals(contact.getContactkey())) {
            operate1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phone2));
            operate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //用intent启动拨打电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getContactvalue()));
                    OrgUserDetailActivity.this.startActivity(intent);
                }
            });
        }
        contactWidget.getOperationLl().addView(operate1);
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgUserDetailActivity.this.finish();
            }
        });
        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewDynamicActivity.start(OrgUserDetailActivity.this,
                        new NewDynamicCommand()
                            .setUserid(sessionManager.getUser().getId())
                            .setInstid(sessionManager.getInst().getId())
                            .setSelectUsers(Collections.singletonList((User)user)));
            }
        });
        checkoutHisMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgCardListActivity.start(OrgUserDetailActivity.this,
                        new MsgCardListCommand()
                                .setFromuserid(user.getId())
                                .setTitle(user.getName())
                                .setUserid(sessionManager.getUser().getId())
                                .setType(MsgCardListCommand.TYPE_USER_GIVEME));
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_org_user_detail;
    }
}
