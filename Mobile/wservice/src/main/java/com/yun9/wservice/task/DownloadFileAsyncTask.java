package com.yun9.wservice.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;

import com.yun9.jupiter.afinal.AjaxCallBack;
import com.yun9.jupiter.afinal.FinalHttp;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/7/8.
 */
public class DownloadFileAsyncTask extends AsyncTask<FileBean, FileBean, List<FileBean>> {

    private String downLoadDir;

    private boolean downloading = false;

    private OnFileDownloadCallback onFileDownloadCallback;

    private Activity mActivity;

    private List<FileBean> fileBeanList = new ArrayList<>();

    private ProgressDialog progressDialog = null;

    public DownloadFileAsyncTask(Activity activity) {

        this.mActivity = activity;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            downLoadDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "Download" + File.separatorChar + "Yun9" + File.separatorChar;
            File downfile = new File(downLoadDir);
            if (!downfile.exists()) {
                downfile.mkdir();
            }
        }
    }

    public void setOnFileDownloadCallback(OnFileDownloadCallback onFileDownloadCallback) {
        this.onFileDownloadCallback = onFileDownloadCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(mActivity, null, mActivity.getResources().getString(R.string.app_wating), true);

    }

    @Override
    protected List<FileBean> doInBackground(FileBean... params) {

        if (AssertValue.isNotNullAndNotEmpty(params)) {
            for (FileBean fileBean : params) {
                this.download(fileBean);
            }
        }

        return fileBeanList;
    }

    private void download(final FileBean fileBean) {
        if (!AssertValue.isNotNull(fileBean))
            return;
        if (!AssertValue.isNotNullAndNotEmpty(downLoadDir))
            return;

        FinalHttp finalHttp = new FinalHttp();
        String filePath = downLoadDir + fileBean.getName();
        downloading = true;
        finalHttp.download(fileBean.getUrl(), filePath, new AjaxCallBack<File>() {
            @Override
            public void onSuccess(File file) {
                FileBean localFileBean = new FileBean(file);
                fileBeanList.add(localFileBean);
                publishProgress(localFileBean);
                downloading = false;
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                fileBeanList.add(fileBean);
                downloading = false;
            }
        });

        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (downloading);
    }

    @Override
    protected void onProgressUpdate(FileBean... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<FileBean> fileBeans) {
        if (AssertValue.isNotNull(onFileDownloadCallback)) {
            onFileDownloadCallback.onPostExecute(fileBeans);
        }

        if (AssertValue.isNotNull(progressDialog)) {
            progressDialog.dismiss();
        }
    }

    public interface OnFileDownloadCallback {
        public void onPostExecute(List<FileBean> fileBeans);
    }
}
