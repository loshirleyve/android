package com.yun9.wservice.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.HttpFactory;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/19.
 */
public class UploadFileAsyncTask extends AsyncTask<Void, FileBean, List<FileBean>> {

    private OnFileUploadCallback onFileUploadCallback;

    private Activity mActivity;

    private HttpFactory httpFactory;

    private SessionManager sessionManager;

    private String userid;

    private String instid;

    private String level;

    private List<UploadFileBeanWrapper> uploadFileBeanWrappers;

    private List<FileBean> fileBeans;

    private ProgressDialog progressDialog = null;

    public UploadFileAsyncTask(Activity activity, String level, List<FileBean> fileBeans, OnFileUploadCallback onFileUploadCallback) {
        JupiterApplication jupiterApplication = (JupiterApplication) activity.getApplicationContext();
        this.init(activity, null, null, level, fileBeans, onFileUploadCallback);
    }

    public UploadFileAsyncTask(Activity activity, String userid, String instid, String level, OnFileUploadCallback onFileUploadCallback) {
        this.init(activity, userid, instid, level, fileBeans, onFileUploadCallback);
    }

    private void init(Activity activity, String userid, String instid, String level, List<FileBean> fileBeans, OnFileUploadCallback onFileUploadCallback) {

        this.onFileUploadCallback = onFileUploadCallback;
        this.mActivity = activity;
        this.level = level;
        this.fileBeans = fileBeans;
        uploadFileBeanWrappers = this.builderWrapper(fileBeans);

        JupiterApplication jupiterApplication = (JupiterApplication) activity.getApplicationContext();
        httpFactory = jupiterApplication.getBeanManager().get(HttpFactory.class);
        sessionManager = jupiterApplication.getBeanManager().get(SessionManager.class);

        if (!AssertValue.isNotNullAndNotEmpty(userid)) {
            this.userid = sessionManager.getUser().getId();
        }

        if (!AssertValue.isNotNullAndNotEmpty(instid)) {
            this.instid = sessionManager.getInst().getId();
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(mActivity, null, mActivity.getResources().getString(R.string.app_wating), true);
    }

    @Override
    protected List<FileBean> doInBackground(Void... params) {
        this.noticeUpload();
        //一直检查等待所有文件完成操作
        boolean complete = false;
        do {
            if (findFileBean(uploadFileBeanWrappers) == null) {
                complete = true;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!complete);

        return fileBeans;
    }

    private List<UploadFileBeanWrapper> builderWrapper(List<FileBean> fileBeans) {
        List<UploadFileBeanWrapper> uploadFileBeanWrappers = new ArrayList<>();

        if (AssertValue.isNotNullAndNotEmpty(fileBeans)) {
            for (FileBean fileBean : fileBeans) {
                if (AssertValue.isNotNull(fileBean) && FileBean.FILE_STORAGE_TYPE_LOCAL.equals(fileBean.getStorageType())) {
                    uploadFileBeanWrappers.add(new UploadFileBeanWrapper(fileBean));
                }
            }
        }
        return uploadFileBeanWrappers;
    }

    private UploadFileBeanWrapper findFileBean(List<UploadFileBeanWrapper> uploadFileBeanWrappers) {
        for (UploadFileBeanWrapper uploadFileBeanWrapper : uploadFileBeanWrappers) {
            if (!uploadFileBeanWrapper.isUploaded()) {
                return uploadFileBeanWrapper;
            }
        }
        return null;
    }

    private void noticeUpload() {
        //查找一个待上传的对象
        UploadFileBeanWrapper uploadFileBeanWrapper = this.findFileBean(uploadFileBeanWrappers);

        if (AssertValue.isNotNull(uploadFileBeanWrapper)) {
            upload(uploadFileBeanWrapper);
        }
    }

    private void upload(final UploadFileBeanWrapper uploadFileBeanWrapper) {
        if (AssertValue.isNotNull(uploadFileBeanWrapper) && !uploadFileBeanWrapper.isUploaded()) {

            httpFactory.uploadFile(userid, instid, "", level, uploadFileBeanWrapper.getFileBean().getType(), "", new File(uploadFileBeanWrapper.getFileBean().getAbsolutePath()), new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    uploadFileBeanWrapper.setSuccessed(true);
                    uploadFileBeanWrapper.getFileBean().setStorageType(FileBean.FILE_STORAGE_TYPE_YUN);
                }

                @Override
                public void onFailure(Response response) {
                    uploadFileBeanWrapper.setSuccessed(false);
                }

                @Override
                public void onFinally(Response response) {
                    uploadFileBeanWrapper.setUploaded(true);
                    noticeUpload();
                }
            });
        }
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    protected void onPostExecute(List<FileBean> fileBeans) {
        super.onPostExecute(fileBeans);

        if (AssertValue.isNotNull(onFileUploadCallback)) {
            onFileUploadCallback.onPostExecute(fileBeans);
        }

        if (AssertValue.isNotNull(progressDialog)) {
            progressDialog.dismiss();
        }
    }

    public interface OnFileUploadCallback {
        public void onPostExecute(List<FileBean> fileBeans);
    }
}
