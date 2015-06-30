package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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


    private UserInfoCommand userInfoCommand;
    private UserSignatureCommand userSignatureCommand;
    private int maxSelectNum = 1;
    private List<FileBean> onSelectYunImages = new ArrayList<>();
    private boolean mEdit;

    private YunImageCommand yunImageCommand;

    @ViewInject(id = R.id.user_info_title)
    private JupiterTitleBarLayout jupiterTitleBarLayout;

    @ViewInject(id = R.id.userInfo)
    private UserInfoWidget userInfoWidget;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    public static void start(Activity activity, UserInfoCommand command){
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
        userInfoCommand = (UserInfoCommand)this.getIntent().getSerializableExtra("command");
        userInfoWidget.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 100);
        jupiterTitleBarLayout.getTitleLeftIV().setOnClickListener(BackClickListener);

        userInfoWidget.getUserHeadLL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AssertValue.isNotNull(yunImageCommand)) {
                    yunImageCommand = new YunImageCommand().setMaxSelectNum(maxSelectNum).setEdit(mEdit)
                            .setCompleteType(YunFileCommand.COMPLETE_TYPE_CALLBACK)
                            .setUserid(sessionManager.getUser().getId())
                            .setInstid(sessionManager.getInst().getId());
                }
                yunImageCommand.setSelectImages(onSelectYunImages);
                YunImageActivity.start(UserInfoActivity.this, yunImageCommand);
            }
        });

        userInfoWidget.getSignature().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 userSignatureCommand = new UserSignatureCommand().setUserid(sessionManager.getUser().getId())
                        .setSignature(sessionManager.getUser().getSignature());
                UserSignatureActivity.start(UserInfoActivity.this, userSignatureCommand);
            }
        });

    }

    private void refresh(){
        if(AssertValue.isNotNull(sessionManager.getInst()) && AssertValue.isNotNull(sessionManager.getUser())){
            Resource resource = resourceFactory.create("QueryUserInfoByIdService");
            resource.param("userid", sessionManager.getUser().getId());
            resource.param("instid", sessionManager.getInst().getId());
            resource.param("signature", sessionManager.getUser().getSignature());
            final ProgressDialog refreshDialog = ProgressDialog.show(this, null, "正在处理，请稍后...", true);

            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    final User user = (User)response.getPayload();

                    if(AssertValue.isNotNull(user)){
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

                }

                @Override
                public void onFinally(Response response) {
                    refreshDialog.dismiss();
                }
            });
        }

    }

    private View.OnClickListener BackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(AssertValue.isNotNull(yunImageCommand) && requestCode == yunImageCommand.getRequestCode() && resultCode == YunImageCommand.RESULT_CODE_OK){
            onSelectYunImages = (List<FileBean>) data.getSerializableExtra(YunImageCommand.PARAM_IMAGE);
            ImageLoaderUtil.getInstance(mContext).displayImage(onSelectYunImages.get(0).getId(), userInfoWidget.getUserHeadIV());
            updateUserByHeaderfileid(onSelectYunImages.get(0).getId());


            Intent intent = new Intent();
            intent.putExtra("command", onSelectYunImages.get(0).getId());
            setResult(UserInfoCommand.RESULT_CODE_OK, intent);
        }

        else if(AssertValue.isNotNull(userSignatureCommand) && requestCode == userSignatureCommand.getRequestCode() && resultCode == UserSignatureCommand.RESULT_CODE_OK){
            String signature = (String) data.getSerializableExtra(UserSignatureCommand.PARAM_COMMAND);
            userInfoWidget.getSignature().getSutitleTv().setText(signature);
            upadteSignature(signature);
            User user = sessionManager.getUser();
            user.setSignature(signature);
            sessionManager.setUser(user);

            Intent intent = new Intent();
            intent.putExtra("command", signature);
            setResult(UserInfoCommand.RESULT_CODE_OK, intent);
         }
    }

    private void updateUserByHeaderfileid(String userHeaderId){
        Resource resource = resourceFactory.create("UpdateUserByHeaderfileid");
        resource.param("userid", sessionManager.getUser().getId());
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("headerfileid", userHeaderId);
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
    private void upadteSignature(String signature){
        Resource resource  = resourceFactory.create("UpdateUserBySignature");
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
