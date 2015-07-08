package com.yun9.wservice.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;

import com.yun9.jupiter.util.AppUtil;
import com.yun9.wservice.model.UpdateProgramBean;

import java.io.File;

/**
 * Created by Leon on 15/7/8.
 */
public class UpdateProgramAsyncTask extends AsyncTask<Void, Void, Void> {

    private Activity mActivity;
    private String mVersion;
    private int mVersionCode;
    private UpdateProgramBean mUpdateProgramBean;
    private String apkName = "Y9WService.apk";
    private String apkPath;

    public UpdateProgramAsyncTask(Activity activity) {
        this.mActivity = activity;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "Download" + File.separatorChar + "Yun9" + File.separatorChar + apkName;
            File apkFile = new File(apkPath);
            if (!apkFile.exists()) {
                apkFile.mkdir();
            }
        }
    }

    public void checkUpdate(OnUpdateProgramCallback onUpdateProgramCallback) {

        mVersion = AppUtil.getVersion(mActivity);
        mVersionCode = AppUtil.getVersionCode(mActivity);

        //TODO 如果存在更新则根据更新是否强制，弹出提示窗口，强制时只有确定按钮，非强制时有确定、取消按钮。用户点击后回调

        mUpdateProgramBean = new UpdateProgramBean();
        mUpdateProgramBean.setUrl("");
    }

    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
    @Override
    protected void onPreExecute() {
    }

    /**
     * 参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
     */
    @Override
    protected void onPostExecute(Void aVoid) {

    }

    /**
     * 参数对应AsyncTask中的第二个参数
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
     */
    @Override
    protected void onProgressUpdate(Void... values) {
    }

    /**
     * 参数对应AsyncTask中的第一个参数
     * 返回值对应AsyncTask的第三个参数
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
     */
    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    public interface OnUpdateProgramCallback {
        public void update(boolean update);
    }
}
