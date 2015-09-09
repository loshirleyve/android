package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheUser;
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
import com.yun9.wservice.task.UploadFileAsyncTask;
import com.yun9.wservice.view.camera.CameraActivity;
import com.yun9.wservice.view.camera.CameraCommand;
import com.yun9.wservice.view.doc.LocalImageActivity;
import com.yun9.wservice.view.doc.LocalImageCommand;
import com.yun9.wservice.view.doc.YunFileCommand;
import com.yun9.wservice.view.doc.YunImageActivity;
import com.yun9.wservice.view.doc.YunImageCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2015/6/25.
 */
public class UserInfoActivity extends JupiterFragmentActivity {
    private UserPwdCommand userPwdCommand;
    private LocalImageCommand localImageCommand;
    private CameraCommand cameraCommand;
    private String userid;
    private String instid;
    private UserInfoCommand command;
    private UserSignatureCommand userSignatureCommand;
    private int maxSelectNum = 1;
    private YunImageCommand yunImageCommand;
    private PopupWindow pop;
    private View menuLayout;

    @ViewInject(id = R.id.activity_user_info)
    private RelativeLayout userInfoRL;

    @ViewInject(id = R.id.user_info_title)
    private JupiterTitleBarLayout TitleBarLayout;

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

        if(!AssertValue.isNotNull(command) || !AssertValue.isNotNullAndNotEmpty(command.getUserid())){
            userid = sessionManager.getUser().getId();
        }
        else {
            userid = command.getUserid();
        }

        if(!AssertValue.isNotNull(command) || !AssertValue.isNotNullAndNotEmpty(command.getInstid())){
            instid = sessionManager.getInst().getId();
        }
        else {
            instid = command.getInstid();
        }
        TitleBarLayout.getTitleLeftIV().setOnClickListener(onBackClickListener);
        userInfoWidget.getUserHeadLL().setOnClickListener(onMenuClickListener);
        userInfoWidget.getSignature().setOnClickListener(onSignatureClickListener);
        userInfoWidget.getPassword().setOnClickListener(onPwdClickListener);

        //初始化菜单弹出窗口
        initImgMenu();

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
                        CacheUser cacheUser = UserCache.getInstance().getUser(user.getId());
                        if (AssertValue.isNotNull(cacheUser)) {
                            ImageLoaderUtil.getInstance(mContext).displayImage(cacheUser.getUrl(), userInfoWidget.getUserHeadIV());
                        }
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

    private View.OnClickListener onPwdClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!AssertValue.isNotNull(userPwdCommand)){
                userPwdCommand = new UserPwdCommand().setUserId(userid);
            }
            UserChangePwdActivity.start(UserInfoActivity.this, userPwdCommand);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(AssertValue.isNotNull(yunImageCommand) && requestCode == yunImageCommand.getRequestCode() && resultCode == YunImageCommand.RESULT_CODE_OK){
           List<FileBean> onSelectYunImages = (List<FileBean>) data.getSerializableExtra(YunImageCommand.PARAM_IMAGE);
            if(AssertValue.isNotNullAndNotEmpty(onSelectYunImages)) {
                ImageLoaderUtil.getInstance(mContext).displayImage(onSelectYunImages.get(0).getId(), userInfoWidget.getUserHeadIV(), R.drawable.user_head);
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
            setResult(UserInfoCommand.RESULT_CODE_OK);
         }

        if(AssertValue.isNotNull(localImageCommand) && requestCode == localImageCommand.getRequestCode() && resultCode == LocalImageCommand.RESULT_CODE_OK){
            List<FileBean> images = new ArrayList<>();
            //images.get(0).setLevel(FileBean.FILE_LEVEL_SYSTEM);
            images = (List<FileBean>) data.getSerializableExtra(LocalImageCommand.PARAM_IMAGE);
            if(AssertValue.isNotNull(images)){
                for(FileBean currentImg : images){
                    currentImg.setUserid(userid);
                    currentImg.setInstid(instid);
                }
            }
            upLoadHeadImg(images);
        }

        if(AssertValue.isNotNull(cameraCommand) && requestCode == cameraCommand.getRequestCode() && resultCode == CameraCommand.RESULT_CODE_OK){
            List<FileBean> cameraImages = new ArrayList<>();
            FileBean cameraImage = (FileBean) data.getSerializableExtra(CameraCommand.PARAM_IMAGE);
            //cameraImage.setLevel(FileBean.FILE_LEVEL_SYSTEM);
            if(AssertValue.isNotNull(cameraImage)){
                cameraImage.setUserid(userid);
                cameraImage.setInstid(instid);
                cameraImages.add(cameraImage);
            }
            upLoadHeadImg(cameraImages);
        }
    }

    private void upLoadHeadImg(List<FileBean> imgs){
        UploadFileAsyncTask uploadFileAsyncTask = new UploadFileAsyncTask(UserInfoActivity.this, imgs);
        uploadFileAsyncTask.setCompImage(true);
        uploadFileAsyncTask.setOnFileUploadCallback(new UploadFileAsyncTask.OnFileUploadCallback() {
            @Override
            public void onPostExecute(List<FileBean> imgs) {
                boolean upload = true;
                if (AssertValue.isNotNull(imgs)) {
                    for (FileBean image : imgs) {
                        if (image.FILE_STORAGE_TYPE_LOCAL.equals(image.getStorageType())) {
                            upload = false;
                        }
                    }
                }

                if (!upload) {
                    Toast.makeText(mContext, getString(R.string.new_image_upload_error), Toast.LENGTH_SHORT).show();
                } else {
                    if (AssertValue.isNotNull(imgs.get(0))) {
                        ImageLoaderUtil.getInstance(mContext).displayImage(imgs.get(0).getId(), userInfoWidget.getUserHeadIV());
                        updateUserByHeaderfileid(imgs.get(0).getId());
                        setResult(UserInfoCommand.RESULT_CODE_OK);
                    }
                }
            }
        });
        uploadFileAsyncTask.execute();
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

    private View.OnClickListener onSignatureClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            userSignatureCommand = new UserSignatureCommand().setUserid(userid).setInstid(instid);
            UserSignatureActivity.start(UserInfoActivity.this, userSignatureCommand);
        }
    };

    private View.OnClickListener onMenuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.4f;
            getWindow().setAttributes(layoutParams);
            pop.showAtLocation(userInfoRL, Gravity.BOTTOM, 0, 0);
        }
    };

    private void initImgMenu(){
        menuLayout = LayoutInflater.from(UserInfoActivity.this).inflate(R.layout.widget_user_menu, null);
        pop = new PopupWindow(menuLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOnDismissListener(onDismissListener);
        pop.setBackgroundDrawable(getResources().getDrawable(R.color.drak));
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.bottom2top_top2bottom);

        View yunImg = menuLayout.findViewById(R.id.yun_image);
        View localImg = menuLayout.findViewById(R.id.local_image);
        View photo = menuLayout.findViewById(R.id.photo);
        View cancel = menuLayout.findViewById(R.id.cancel);

        yunImg.setOnClickListener(new onMenuItemClickListener());
        localImg.setOnClickListener(new onMenuItemClickListener());
        photo.setOnClickListener(new onMenuItemClickListener());
        cancel.setOnClickListener(new onMenuItemClickListener());
    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().setAttributes(layoutParams);
        }
    };
    private class onMenuItemClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.yun_image:
                    if(!AssertValue.isNotNull(yunImageCommand)) {
                        yunImageCommand = new YunImageCommand().setMaxSelectNum(maxSelectNum).setEdit(true)
                                .setCompleteType(YunFileCommand.COMPLETE_TYPE_CALLBACK)
                                .setUserid(userid)
                                .setInstid(instid);
                    }
                    YunImageActivity.start(UserInfoActivity.this, yunImageCommand);
                    pop.dismiss();
                    break;
                case R.id.local_image:
                    if (!AssertValue.isNotNull(localImageCommand)) {
                        localImageCommand = new LocalImageCommand().setEdit(true).setCompleteType(LocalImageCommand.COMPLETE_TYPE_CALLBACK).setMaxSelectNum(maxSelectNum).setUserid(userid).setInstid(instid);
                    }
                    LocalImageActivity.start(UserInfoActivity.this, localImageCommand);
                    pop.dismiss();
                    break;
                case R.id.photo:
                    if (!AssertValue.isNotNull(cameraCommand)) {
                        cameraCommand = new CameraCommand();
                    }
                    CameraActivity.start(UserInfoActivity.this, cameraCommand);
                    pop.dismiss();
                    break;
                case R.id.cancel:
                    pop.dismiss();
                    break;
            }
        }
    }
}
