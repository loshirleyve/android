package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.yun9.wservice.view.camera.CameraActivity;
import com.yun9.wservice.view.camera.CameraCommand;
import com.yun9.wservice.view.doc.LocalImageActivity;
import com.yun9.wservice.view.doc.LocalImageCommand;
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
    private LocalImageCommand localImageCommand;
    private CameraCommand cameraCommand;
    private String userid;
    private String instid;
    private UserInfoCommand command;
    private UserSignatureCommand userSignatureCommand;
    private int maxSelectNum = 1;
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
        bundle.putSerializable(UserInfoCommand.PARAM_USER_INFO_COMMAND, command);
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
        command = (UserInfoCommand)this.getIntent().getSerializableExtra(UserInfoCommand.PARAM_USER_INFO_COMMAND);

        if(!AssertValue.isNotNull(command.getUserid())){
            userid = sessionManager.getUser().getId();
        }
        else {
            userid = command.getUserid();
            sessionManager.getUser().setId(userid);
        }

        if(!AssertValue.isNotNull(command.getInstid())){
            instid = sessionManager.getInst().getId();
        }
        else {
            instid = command.getInstid();
            sessionManager.getInst().setId(instid);
        }
        jupiterTitleBarLayout.getTitleLeftIV().setOnClickListener(onBackClickListener);
        //userInfoWidget.getUserHeadLL().setOnClickListener(onImgClickListener);
        userInfoWidget.getUserHeadLL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptionsMenu();
            }
        });

        userInfoWidget.getSignature().setOnClickListener(onSignatureClickListener);
        userInfoWidget.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 100);
    }

    private void refresh(){
        if(AssertValue.isNotNull(sessionManager.getInst()) && AssertValue.isNotNull(sessionManager.getUser())){
            Resource resource = resourceFactory.create("QueryUserInfoByIdService");
            resource.param("userid", userid);
            resource.param("instid", instid);
            final ProgressDialog refreshDialog = ProgressDialog.show(this, null, getString(R.string.app_wating), true);

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
                    Toast.makeText(mContext, getString(R.string.app_update_success), Toast.LENGTH_SHORT).show();
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

        if(AssertValue.isNotNull(yunImageCommand) && requestCode == yunImageCommand.getRequestCode() && resultCode == YunImageCommand.RESULT_CODE_OK){
           List<FileBean> onSelectYunImages = (List<FileBean>) data.getSerializableExtra(YunImageCommand.PARAM_IMAGE);
            if(AssertValue.isNotNullAndNotEmpty(onSelectYunImages)) {
                ImageLoaderUtil.getInstance(mContext).displayImage(onSelectYunImages.get(0).getId(), userInfoWidget.getUserHeadIV());
                updateUserByHeaderfileid(onSelectYunImages.get(0).getId());

                Intent intent = new Intent();
                intent.putExtra(UserInfoCommand.PARAM_USER_INFO_COMMAND, onSelectYunImages.get(0).getId());
                setResult(UserInfoCommand.RESULT_CODE_OK, intent);}
        }

        if(AssertValue.isNotNull(userSignatureCommand) && requestCode == userSignatureCommand.getRequestCode() && resultCode == UserSignatureCommand.RESULT_CODE_OK){
            String signature = (String) data.getSerializableExtra(UserSignatureCommand.PARAM_SIGNATURE_COMMAND);
            userInfoWidget.getSignature().getSutitleTv().setText(signature);
            upadteSignature(signature);
            User user = sessionManager.getUser();
            user.setSignature(signature);
            sessionManager.setUser(user);

            Intent intent = new Intent();
            intent.putExtra(UserInfoCommand.PARAM_USER_INFO_COMMAND, signature);
            setResult(UserInfoCommand.RESULT_CODE_OK, intent);
         }
    }

    private void updateUserByHeaderfileid(String userHeaderId){
        Resource resource = resourceFactory.create("UpdateUserByHeaderfileid");
        resource.param("userid", userid);
        resource.param("instid", instid);
        resource.param("headerfileid", userHeaderId);
        final ProgressDialog refreshDialog = ProgressDialog.show(this, null, getString(R.string.app_wating), true);

        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, getString(R.string.app_update_success), Toast.LENGTH_SHORT).show();
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
    private void upadteSignature(String signature){
        Resource resource  = resourceFactory.create("UpdateUserBySignature");
        resource.param("userid", userid);
        resource.param("instid", instid);
        resource.param("signature", signature);
        final ProgressDialog refreshDialog = ProgressDialog.show(this, null, getString(R.string.app_wating), true);

        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, getString(R.string.app_update_success), Toast.LENGTH_SHORT).show();
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

    /**
     *
     */
  /*  private View.OnClickListener onImgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            yunImageCommand = new YunImageCommand().setMaxSelectNum(maxSelectNum).setEdit(true)
                    .setCompleteType(YunFileCommand.COMPLETE_TYPE_CALLBACK)
                    .setUserid(userid)
                    .setInstid(instid);
            //yunImageCommand.setSelectImages(onSelectYunImages);
            YunImageActivity.start(UserInfoActivity.this, yunImageCommand);
        }
    };*/


    private View.OnClickListener onSignatureClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            userSignatureCommand = new UserSignatureCommand().setUserid(userid).setInstid(instid);
            UserSignatureActivity.start(UserInfoActivity.this, userSignatureCommand);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menIteCloudPic = menu.add(1001, 100, 1, "云图片");
        menIteCloudPic.setTitle("云图片");
        MenuItem menuItemLocPic = menu.add(1001, 101, 2, "本地图片");
        menuItemLocPic.setTitle("本地图片");
        MenuItem menuItemPhoto = menu.add(1001, 102, 3, "拍照");
        menuItemPhoto.setTitle("拍照");
        MenuItem menuItemCancel = menu.add(1001, 103, 4, "取消");
        menuItemCancel.setTitle("取消");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 100:
                if(!AssertValue.isNotNull(yunImageCommand)) {
                    yunImageCommand = new YunImageCommand().setMaxSelectNum(maxSelectNum).setEdit(true)
                            .setCompleteType(YunFileCommand.COMPLETE_TYPE_CALLBACK)
                            .setUserid(userid)
                            .setInstid(instid);
                    //yunImageCommand.setSelectImages(onSelectYunImages);
                }
                YunImageActivity.start(UserInfoActivity.this, yunImageCommand);
                break;
            case 101:
                if (!AssertValue.isNotNull(localImageCommand)) {
                    localImageCommand = new LocalImageCommand().setEdit(true).setCompleteType(LocalImageCommand.COMPLETE_TYPE_CALLBACK).setMaxSelectNum(maxSelectNum).setUserid(userid).setInstid(instid);
                }
                LocalImageActivity.start(UserInfoActivity.this, localImageCommand);
                break;
            case 102:
                if (!AssertValue.isNotNull(cameraCommand)) {
                    cameraCommand = new CameraCommand();
                }
                CameraActivity.start(UserInfoActivity.this, cameraCommand);
                break;
            case 103:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
