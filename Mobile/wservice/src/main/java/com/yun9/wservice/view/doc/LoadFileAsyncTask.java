package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;

import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.FileUtil;
import com.yun9.wservice.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/16.
 */
public class LoadFileAsyncTask extends AsyncTask<Void, Void, List<FileBean>> {

    private OnFileLoadCallback onFileLoadCallback;

    private Activity mActivity;

    private ProgressDialog progressDialog = null;


    public LoadFileAsyncTask(OnFileLoadCallback onFileLoadCallback, Activity activity) {
        this.onFileLoadCallback = onFileLoadCallback;
        this.mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(mActivity, null, mActivity.getResources().getString(R.string.app_wating), true);
    }

    @Override
    protected List<FileBean> doInBackground(Void... params) {

        File extFile = Environment.getExternalStorageDirectory();

        List<File> files = new ArrayList<>();
        List<FileBean> fileBeans = new ArrayList<>();
        FileUtil.searchFile(extFile, files);

        if (AssertValue.isNotNullAndNotEmpty(files)) {
            for (File file : files) {
                fileBeans.add(new FileBean(file));
            }
        }
        return fileBeans;
    }

    @Override
    protected void onPostExecute(List<FileBean> fileBeans) {
        super.onPostExecute(fileBeans);

        if (AssertValue.isNotNull(onFileLoadCallback)) {
            onFileLoadCallback.onPostExecute(fileBeans);
        }

        if (AssertValue.isNotNull(progressDialog)) {
            progressDialog.dismiss();
        }
    }

    public interface OnFileLoadCallback {
        public void onPostExecute(List<FileBean> fileBeans);
    }
}
