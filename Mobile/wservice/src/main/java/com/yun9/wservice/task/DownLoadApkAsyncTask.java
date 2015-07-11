package com.yun9.wservice.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;

import com.yun9.jupiter.afinal.AjaxCallBack;
import com.yun9.jupiter.afinal.FinalHttp;
import com.yun9.jupiter.model.UpdateProgramBean;
import com.yun9.jupiter.util.AppUtil;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.R;

import java.io.File;

/**
 * Created by Leon on 15/7/11.
 */
public class DownLoadApkAsyncTask extends AsyncTask<Void, Void, Void> {

    private String apkName = "Y9WService.apk";
    private String apkPath;
    private Activity mActivity;
    private UpdateProgramBean mUpdateProgramBean;
    private ProgressDialog progressDialog = null;
    private boolean download = false;
    private boolean downloading = false;
    private boolean downloadSuccess = false;


    public DownLoadApkAsyncTask(Activity activity, UpdateProgramBean updateProgramBean) {
        this.mActivity = activity;
        this.mUpdateProgramBean = updateProgramBean;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "Download" + File.separatorChar + "Yun9" + File.separatorChar + apkName;
            File apkFile = new File(apkPath);
            if (!apkFile.exists()) {
                apkFile.mkdir();
            }
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        if (download && AssertValue.isNotNullAndNotEmpty(mUpdateProgramBean.getUrl()) && AssertValue.isNotNullAndNotEmpty(apkPath)) {
            //执行下载Apk
            downloading = true;

        }

        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (downloading);

        return null;
    }

    @Override
    protected void onPreExecute() {
        //非强制更新提示用户下载更新。
        if (AssertValue.isNotNull(mUpdateProgramBean) && mUpdateProgramBean.getFocus() == 0) {
            CharSequence msg = mActivity.getResources().getString(R.string.update_prog_title, mUpdateProgramBean.getLog());

            new AlertDialog.Builder(mActivity).setTitle(R.string.update_prog_title).setMessage(msg).setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    download = true;
                }
            }).setNegativeButton(R.string.app_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    download = false;
                }
            }).show();
        }

        //强制更新，提示用户
        if (AssertValue.isNotNull(mUpdateProgramBean) && mUpdateProgramBean.getFocus() == 1) {
            CharSequence msg = mActivity.getResources().getString(R.string.update_prog_title, mUpdateProgramBean.getLog());
            new AlertDialog.Builder(mActivity).setTitle(R.string.update_prog_title).setMessage(msg).setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    download = true;
                }
            }).show();
        }

        if (download) {
            progressDialog = ProgressDialog.show(mActivity, null, mActivity.getResources().getString(R.string.app_wating), true);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (AssertValue.isNotNull(progressDialog)) {
            progressDialog.dismiss();
        }
        //如果成功下载了apk,执行安装apk
        if (downloadSuccess) {
            AppUtil.installAPK(new File(apkPath), mActivity);
        }
    }
}
