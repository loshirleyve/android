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

    private int maxSelectNum = 1;
    private UserInfoCommand command;
    private List<FileBean> onSelectYunImages = new ArrayList<>();
    private boolean mEdit;

    private YunImageCommand yunImageCommand;
    private UserSignatureCommand userSignatureCommand;

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
        bundle.putSerializable(UserInfoCommand.PARAM_COMMAND, command);
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
        command = (UserInfoCommand)this.getIntent().getSerializableExtra("command");
        userInfoWidget.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 100);
        jupiterTitleBarLayout.getTitleLeftIV().setOnClickListener(BackClickListener);
        
    }

    private void refresh(){
        if(AssertValue.isNotNull(sessionManager.getInst()) && AssertValue.isNotNull(sessionManager.getUser())){
            Resource resource = resourceFactory.create("QueryUserInfoByIdService");
            resource.param("userid", sessionManager.getUser().getId());
            resource.param("instid", sessionManager.getInst().getId());

            final ProgressDialog refreshDialog = ProgressDialog.show(this, null, "正在处理，请稍后...", true);

            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    final User user = (User)response.getPayload();

                    if(AssertValue.isNotNull(user)){
                        userInfoWidget.getUserHeadTV().setText(user.getName());
                        ImageLoaderUtil.getInstance(mContext).displayImage(user.getHeaderURL(), userInfoWidget.getUserHeadIV());
                        userInfoWidget.getUserName().getHotNitoceTV().setText(user.getName());
                        userInfoWidget.getUserName().getHotNitoceTV().setVisibility(View.VISIBLE);
                        /**
                         * 下面两栏的数据是暂时演示用的
                         */
                        userInfoWidget.getAgency().getHotNitoceTV().setText(user.getNo());
                        userInfoWidget.getAgency().getHotNitoceTV().setVisibility(View.VISIBLE);
                        userInfoWidget.getDepartment().getHotNitoceTV().setText(user.getId());
                        userInfoWidget.getDepartment().getHotNitoceTV().setVisibility(View.VISIBLE);

                        userInfoWidget.getSignature().getSutitleTv().setText(user.getSignature());
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

/*                        userInfoWidget.getAgency().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SelectInstActivity.start(UserInfoActivity.this, new SelectInstCommand().setUser(sessionManager.getUser()));
                            }
                        });*/

                        userInfoWidget.getSignature().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!AssertValue.isNotNull(userSignatureCommand)){
                                    userSignatureCommand = new UserSignatureCommand().setUserid(sessionManager.getUser().getId()).setInstid(sessionManager.getInst().getId()).setSignature(user.getSignature());
                                }
                                UserSignatureActivity.start(UserInfoActivity.this, userSignatureCommand);
                            }
                        });
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
        }

        if(AssertValue.isNotNull(userSignatureCommand) && requestCode == userSignatureCommand.getRequestCode() && resultCode == UserSignatureCommand.RESULT_CODE_OK){
            userInfoWidget.getSignature().getSutitleTv().setText(((UserSignatureCommand)data.getSerializableExtra(UserSignatureCommand.PARAM_COMMAND)).getSignature());
        }
    }
}
