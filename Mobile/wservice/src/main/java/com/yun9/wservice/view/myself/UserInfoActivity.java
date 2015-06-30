package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.doc.YunFileCommand;
import com.yun9.wservice.view.doc.YunImageActivity;
import com.yun9.wservice.view.doc.YunImageCommand;
import com.yun9.wservice.view.inst.SelectInstActivity;
import com.yun9.wservice.view.inst.SelectInstCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2015/6/25.
 */
public class UserInfoActivity extends JupiterFragmentActivity {

    private UserInfoCommand command;
    private UserSignatureCommand userSignatureCommand;
    private int maxSelectNum = 1;

    private String userid;

    private String instid;

    private YunImageCommand yunImageCommand;

    @ViewInject(id = R.id.user_info_title)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.userInfo)
    private UserInfoWidget userInfoWidget;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    public static void start(Activity activity, UserInfoCommand command) {
        Intent intent = new Intent(activity, UserInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取参数
        command = (UserInfoCommand) this.getIntent().getSerializableExtra("command");

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getInstid())) {
            instid = command.getInstid();
        } else {
            instid = sessionManager.getInst().getId();
        }
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getUserid())) {
            userid = command.getUserid();
        } else {
            userid = sessionManager.getUser().getId();
        }

        titleBarLayout.getTitleLeftIV().setOnClickListener(onBackClickListener);

        userInfoWidget.getUserHeadLL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yunImageCommand = new YunImageCommand().setMaxSelectNum(maxSelectNum).setEdit(true)
                        .setCompleteType(YunFileCommand.COMPLETE_TYPE_CALLBACK)
                        .setUserid(userid)
                        .setInstid(instid);
                YunImageActivity.start(UserInfoActivity.this, yunImageCommand);
            }
        });

        userInfoWidget.getSignature().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 此处不带入用户签名，通过用户id检索
                userSignatureCommand = new UserSignatureCommand().setUserid(userid)
                        .setSignature(sessionManager.getUser().getSignature());
                UserSignatureActivity.start(UserInfoActivity.this, userSignatureCommand);
            }
        });

        userInfoWidget.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 100);

    }

    private void refresh() {
        if (AssertValue.isNotNull(sessionManager.getInst()) && AssertValue.isNotNull(sessionManager.getUser())) {
            Resource resource = resourceFactory.create("QueryUserInfoByIdService");
            resource.param("userid", userid);
            resource.param("instid", instid);

            final ProgressDialog refreshDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);

            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    User user = (User) response.getPayload();

                    if (AssertValue.isNotNull(user)) {
                        ImageLoaderUtil.getInstance(mContext).displayImage(user.getHeaderfileid(), userInfoWidget.getUserHeadIV());
                        userInfoWidget.getUserName().getHotNitoceTV().setText(user.getName());
                        userInfoWidget.getUserName().getHotNitoceTV().setVisibility(View.VISIBLE);
                        userInfoWidget.getUserName().getHotNitoceTV().setTextColor(getResources().getColor(R.color.black));
                        userInfoWidget.getAgency().getHotNitoceTV().setText(sessionManager.getInst().getName());
                        userInfoWidget.getAgency().getHotNitoceTV().setVisibility(View.VISIBLE);
                        userInfoWidget.getAgency().getHotNitoceTV().setTextColor(getResources().getColor(R.color.black));

                        userInfoWidget.getDepartment().getHotNitoceTV().setText(user.getOrgNames());
                        userInfoWidget.getDepartment().getHotNitoceTV().setVisibility(View.VISIBLE);
                        userInfoWidget.getDepartment().getHotNitoceTV().setTextColor(getResources().getColor(R.color.black));

                        userInfoWidget.getSignature().getSutitleTv().setText(user.getSignature());

                    }
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    refreshDialog.dismiss();
                }
            });
        }

    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (AssertValue.isNotNull(yunImageCommand) && requestCode == yunImageCommand.getRequestCode() && resultCode == YunImageCommand.RESULT_CODE_OK) {
            List<FileBean> onSelectYunImages = (List<FileBean>) data.getSerializableExtra(YunImageCommand.PARAM_IMAGE);

            if (AssertValue.isNotNullAndNotEmpty(onSelectYunImages)) {
                ImageLoaderUtil.getInstance(mContext).displayImage(onSelectYunImages.get(0).getId(), userInfoWidget.getUserHeadIV());
                updateUserByHeaderfileid(onSelectYunImages.get(0).getId());
            }

        }

        if (AssertValue.isNotNull(userSignatureCommand) && requestCode == userSignatureCommand.getRequestCode() && resultCode == UserSignatureCommand.RESULT_CODE_OK) {
            String signature = (String) data.getSerializableExtra(UserSignatureCommand.PARAM_SIGNATURE);
            userInfoWidget.getSignature().getSutitleTv().setText(signature);
            upadteSignature(signature);

            //更新本地缓存用户信息
            User user = sessionManager.getUser();
            user.setSignature(signature);
            sessionManager.setUser(user);
        }
    }

    private void updateUserByHeaderfileid(String userHeaderId) {
        Resource resource = resourceFactory.create("UpdateUserByHeaderfileid");
        resource.param("userid", sessionManager.getUser().getId());
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("headerfileid", userHeaderId);

        final ProgressDialog registerDialog = ProgressDialog.show(UserInfoActivity.this, null, getResources().getString(R.string.app_wating), true);

        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, R.string.app_update_success, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
            }
        });
    }

    private void upadteSignature(String signature) {
        Resource resource = resourceFactory.create("UpdateUserBySignature");
        resource.param("userid", sessionManager.getUser().getId());
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("signature", signature);

        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {

            }

            @Override
            public void onFailure(Response response) {

            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }
}
