package com.yun9.wservice.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.HttpFactory;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.model.SysFileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageUtil;
import com.yun9.wservice.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/19.
 */
public class UploadFileAsyncTask extends AsyncTask<Void, FileBean, List<FileBean>> {

    private OnFileUploadCallback onFileUploadCallback;

    private OnProgressUpdateCallback onProgressUpdateCallback;

    private Activity mActivity;

    private HttpFactory httpFactory;

    private List<UploadFileBeanWrapper> uploadFileBeanWrappers;

    private List<FileBean> fileBeans;

    private ProgressDialog progressDialog = null;

    private boolean compImage = false;


    public UploadFileAsyncTask(Activity activity, List<FileBean> fileBeans) {
        this.init(activity, fileBeans);
    }

    private void init(Activity activity, List<FileBean> fileBeans) {
        this.mActivity = activity;
        this.fileBeans = fileBeans;
        uploadFileBeanWrappers = this.builderWrapper(fileBeans);

        JupiterApplication jupiterApplication = (JupiterApplication) activity.getApplicationContext();
        httpFactory = jupiterApplication.getBeanManager().get(HttpFactory.class);
    }

    public void setOnFileUploadCallback(OnFileUploadCallback onFileUploadCallback) {
        this.onFileUploadCallback = onFileUploadCallback;
    }

    public void setOnProgressUpdateCallback(OnProgressUpdateCallback onProgressUpdateCallback) {
        this.onProgressUpdateCallback = onProgressUpdateCallback;
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
                uploadFileBeanWrappers.add(new UploadFileBeanWrapper(fileBean));
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

        //如果已经是云文件则直接上传成功。
        if (AssertValue.isNotNull(uploadFileBeanWrapper) && FileBean.FILE_STORAGE_TYPE_YUN.equals(uploadFileBeanWrapper.getFileBean().getStorageType())) {
            uploadFileBeanWrapper.setUploaded(true);
            uploadFileBeanWrapper.setSuccessed(true);
            uploadSuccess(uploadFileBeanWrapper);
            noticeUpload();
            return;
        }


        //如果是本地文件并且还为上传过，执行上传动作
        if (AssertValue.isNotNull(uploadFileBeanWrapper) && !uploadFileBeanWrapper.isUploaded() && FileBean.FILE_STORAGE_TYPE_LOCAL.equals(uploadFileBeanWrapper.getFileBean().getStorageType())) {

            InputStream is = null;
            if (compImage && FileBean.FILE_TYPE_IMAGE.equals(uploadFileBeanWrapper.getFileBean().getType())) {
                Bitmap bitmap = ImageUtil.getimage(uploadFileBeanWrapper.getFileBean().getAbsolutePath());
                is = ImageUtil.bitmap2InputStream(bitmap);
            } else {
                File tempFile = new File(uploadFileBeanWrapper.getFileBean().getAbsolutePath());
                try {
                    is = new FileInputStream(tempFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    uploadFileBeanWrapper.setSuccessed(false);
                    uploadFailure(uploadFileBeanWrapper);
                    return;
                }
            }


            httpFactory.uploadFile(uploadFileBeanWrapper.getFileBean(), is, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    uploadFileBeanWrapper.setSuccessed(true);

                    if (AssertValue.isNotNull(response.getPayload()) && AssertValue.isNotNull(response.getPayload())) {
                        List<SysFileBean> sysFileBeans = (List<SysFileBean>) response.getPayload();

                        if (AssertValue.isNotNullAndNotEmpty(sysFileBeans)) {
                            uploadFileBeanWrapper.getFileBean().setSysFileBean(sysFileBeans.get(0));
                        }
                    }
                    uploadSuccess(uploadFileBeanWrapper);
                }

                @Override
                public void onFailure(Response response) {
                    uploadFileBeanWrapper.setSuccessed(false);
                    uploadFailure(uploadFileBeanWrapper);
                }

                @Override
                public void onFinally(Response response) {
                    uploadFileBeanWrapper.setUploaded(true);
                    noticeUpload();
                }
            });
        }
    }

    private void uploadSuccess(UploadFileBeanWrapper uploadFileBeanWrapper) {
        if (AssertValue.isNotNull(onProgressUpdateCallback)) {
            uploadFileBeanWrapper.getFileBean().setState(FileBean.FILE_STATE_SUCCESS);
            onProgressUpdateCallback.onProgressUpdate(uploadFileBeanWrapper.getFileBean());
        }
    }

    private void uploadFailure(UploadFileBeanWrapper uploadFileBeanWrapper) {
        if (AssertValue.isNotNull(onProgressUpdateCallback)) {
            uploadFileBeanWrapper.getFileBean().setState(FileBean.FILE_STATE_FAILURE);
            onProgressUpdateCallback.onProgressUpdate(uploadFileBeanWrapper.getFileBean());
        }
    }


    public boolean isCompImage() {
        return compImage;
    }

    public void setCompImage(boolean compImage) {
        this.compImage = compImage;
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

    @Override
    protected void onProgressUpdate(FileBean... values) {
        super.onProgressUpdate(values);

    }

    public interface OnFileUploadCallback {
        public void onPostExecute(List<FileBean> fileBeans);
    }

    public interface OnProgressUpdateCallback {
        public void onProgressUpdate(FileBean values);
    }
}
