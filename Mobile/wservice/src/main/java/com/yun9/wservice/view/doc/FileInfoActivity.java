package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.task.UploadFileAsyncTask;
import com.yun9.wservice.view.org.OrgCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeCommand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/17.
 */
public class FileInfoActivity extends JupiterFragmentActivity {

    private FileInfoCommand command;
    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.file_name)
    private TextView fileName;

    @ViewInject(id = R.id.file_from)
    private TextView fileFrom;

    @ViewInject(id = R.id.file_uploaddate)
    private TextView fileUploadDate;

    @ViewInject(id = R.id.file_size)
    private TextView fileSize;

    @ViewInject(id = R.id.fileopenway)
    private Button fileopenway;


    @ViewInject(id = R.id.filedownload)
    private Button filedownload;

    @ViewInject(id = R.id.fileshare)
    private Button fileshare;

    @ViewInject(id = R.id.fileupload)
    private Button fileupload;

    private FileBean fileBean;

    private List<User> users = new ArrayList<User>();

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;


    public static void start(Activity activity, FileInfoCommand command) {
        Intent intent = new Intent(activity, FileInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FileInfoCommand.PARAM_COMMAND, command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_doc_yun_fileinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (FileInfoCommand) getIntent().getSerializableExtra(FileInfoCommand.PARAM_COMMAND);
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        initView();

    }

    public void initView() {
        users = new ArrayList<>();
        if (AssertValue.isNotNull(command) && AssertValue.isNotNull(command.getFileBean())) {
            fileBean = command.getFileBean();
            fileName.setText(fileBean.getName());
            fileUploadDate.setText(fileBean.getDateAdded());
            fileSize.setText(fileBean.getSize());
            CacheUser cacheUser = UserCache.getInstance().getUser(fileBean.getUserid());
            if (AssertValue.isNotNull(cacheUser))
                fileFrom.setText(cacheUser.getName());
            else
                fileFrom.setText("未知");

            if (fileBean.getStorageType().equals(FileBean.FILE_STORAGE_TYPE_LOCAL)) {
                fileopenway.setVisibility(View.VISIBLE);
                fileupload.setVisibility(View.VISIBLE);
                fileopenway.setTag(fileBean);
                fileopenway.setOnClickListener(onOpenlClickListener);
                fileupload.setTag(fileBean);
                fileupload.setOnClickListener(onUpLoadlClickListener);
            } else if (fileBean.getStorageType().equals(FileBean.FILE_STORAGE_TYPE_YUN)) {
                filedownload.setVisibility(View.VISIBLE);
                fileshare.setVisibility(View.VISIBLE);
                fileshare.setOnClickListener(onSharelClickListener);
            }

        }
    }

    private View.OnClickListener onOpenlClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FileBean fileBean=(FileBean)v.getTag();
            openFile(fileBean);

        }
    };

    private View.OnClickListener onDownLoadlClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    private View.OnClickListener onUpLoadlClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FileBean fileBean=(FileBean)v.getTag();
            uploadFiles(fileBean);
        }
    };

    private View.OnClickListener onSharelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrgCompositeCommand orgCompositeCommand = new OrgCompositeCommand().setEdit(true).setOnlyUsers(true).setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK);
            OrgCompositeActivity.start(FileInfoActivity.this, orgCompositeCommand);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        users.clear();
        if (requestCode == OrgCompositeCommand.REQUEST_CODE && resultCode == OrgCompositeCommand.RESULT_CODE_OK) {
            List<User> users = (List<User>) data.getSerializableExtra(OrgCompositeCommand.PARAM_USER);
            this.users = users;
        }
        if (AssertValue.isNotNullAndNotEmpty(users)) {
            Resource resource = resourceFactory.create("ShareFile");
            resource.param("instid", sessionManager.getInst().getId());
            resource.param("userid", sessionManager.getUser().getId());
            resource.param("id", fileBean.getId());
            resource.param("shareUsers", getSelectUserIds(users));
            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    Toast.makeText(FileInfoActivity.this, R.string.doc_file_share_success_tip, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(FileInfoActivity.this, R.string.doc_file_share_failure_tip, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {

                }
            });
        } else
            Toast.makeText(FileInfoActivity.this, R.string.doc_file_share_tip, Toast.LENGTH_SHORT).show();
    }


    private void uploadFiles(FileBean filebean) {

        //合并发送的图片和文件，并执行上传任务（任务中只处理存储类型为本地的文件）
        List<FileBean> fileBeans = new ArrayList<>();
        filebean.setInstid(sessionManager.getInst().getId());
        filebean.setUserid(sessionManager.getUser().getId());
        fileBeans.add(filebean);
        UploadFileAsyncTask uploadFileAsyncTask = new UploadFileAsyncTask(FileInfoActivity.this, fileBeans);
        uploadFileAsyncTask.setCompImage(true);
        uploadFileAsyncTask.setOnFileUploadCallback(new UploadFileAsyncTask.OnFileUploadCallback() {
            @Override
            public void onPostExecute(List<FileBean> fileBeans) {
                //上传文件完成后,检查是否所有文件都已经成功上传，启动发送动态
                boolean upload = true;
                if (AssertValue.isNotNull(fileBeans)) {
                    for (FileBean fileBean : fileBeans) {
                        if (FileBean.FILE_STORAGE_TYPE_LOCAL.equals(fileBean.getStorageType())) {
                            upload = false;
                        }
                    }
                }

                if (!upload) {
                    Toast.makeText(mContext, R.string.new_dynamic_upload_error, Toast.LENGTH_SHORT);
                }
            }
        });
        uploadFileAsyncTask.execute();
    }


    /**
     * 打开文件
     *
     * @param fileBean
     */
    private void openFile(FileBean fileBean) {
        File file=new File(fileBean.getFilePath());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转
        startActivity(intent);

    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }


    public List<String> getSelectUserIds(List<User> users) {
        List<String> userids = new ArrayList<String>();
        if (AssertValue.isNotNullAndNotEmpty(users)) {
            for (User user : users) {
                userids.add(user.getId());
            }
        }
        return userids;
    }

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private final String[][] MIME_MapTable = {
            //{后缀名，	MIME类型}
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".pdf", "application/pdf"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".txt", "text/plain"},
            {".wps", "application/vnd.ms-works"},

    };


}

