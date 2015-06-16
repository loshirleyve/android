package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.yun9.jupiter.model.LocalFileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.FileUtil;
import com.yun9.wservice.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/16.
 */
public class LoadFileAsyncTask extends AsyncTask<Void, Void, List<LocalFileBean>> {

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
    protected List<LocalFileBean> doInBackground(Void... params) {

        File extFile = Environment.getExternalStorageDirectory();

        List<File> files = new ArrayList<>();
        List<LocalFileBean> localFileBeans = new ArrayList<>();
        FileUtil.searchFile(extFile, files);

        if (AssertValue.isNotNullAndNotEmpty(files)) {
            for (File file : files) {
                localFileBeans.add(new LocalFileBean(file));
            }
        }
        return localFileBeans;
    }

    @Override
    protected void onPostExecute(List<LocalFileBean> localFileBeans) {
        super.onPostExecute(localFileBeans);

        if (AssertValue.isNotNull(onFileLoadCallback)) {
            onFileLoadCallback.onPostExecute(localFileBeans);
        }

        if (AssertValue.isNotNull(progressDialog)) {
            progressDialog.dismiss();
        }
    }

    public interface OnFileLoadCallback {
        public void onPostExecute(List<LocalFileBean> localFileBeans);
    }
}
