package com.yun9.jupiter.manager.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.widget.Toast;

import com.yun9.jupiter.R;
import com.yun9.jupiter.afinal.AjaxCallBack;
import com.yun9.jupiter.afinal.FinalHttp;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.cache.UserDataCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.Inst;
import com.yun9.jupiter.model.UpdateProgramBean;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AppUtil;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DefaultSessionManager implements SessionManager, Bean,
        Initialization {

    private static final Logger logger = Logger
            .getLogger(DefaultSessionManager.class);

    private List<OnLoginListener> onLoginListenerList;

    private List<OnLogoutListener> onLogoutListenerList;

    private List<OnChangeInstListener> onChangeInstListenerList;

    private BeanManager beanManager;

    @Override
    public Class<?> getType() {
        return SessionManager.class;
    }

    @Override
    public void clean() {

    }

    @Override
    public void regOnLoginListener(OnLoginListener onLoginListener) {
        if (!AssertValue.isNotNull(onLoginListenerList)) {
            this.onLoginListenerList = new ArrayList<>();
        }
        this.onLoginListenerList.add(onLoginListener);
    }

    @Override
    public void regOnLogoutListener(OnLogoutListener onLogoutListener) {
        if (!AssertValue.isNotNull(onLogoutListenerList)) {
            this.onLogoutListenerList = new ArrayList<>();
        }
        this.onLogoutListenerList.add(onLogoutListener);
    }

    @Override
    public void regOnChangeInstListener(OnChangeInstListener onChangeInstListener) {
        if (!AssertValue.isNotNull(onChangeInstListenerList)) {
            this.onChangeInstListenerList = new ArrayList<>();
        }
        this.onChangeInstListenerList.add(onChangeInstListener);
    }

    @Override
    public void checkUpdate(int versionCode, final OnUpdateProgramCallback onUpdateProgramCallback) {
        ResourceFactory resourceFactory = beanManager.get(ResourceFactory.class);
        Resource resource = resourceFactory.create("QuerySysprogramupdate");
        resource.param("ostype", "android").param("apptype", "app").param("versioncode", versionCode);

        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                UpdateProgramBean updateProgramBean = (UpdateProgramBean) response.getPayload();
                if (AssertValue.isNotNull(onUpdateProgramCallback)) {
                    onUpdateProgramCallback.update(updateProgramBean);
                }
            }

            @Override
            public void onFailure(Response response) {
                onUpdateProgramCallback.update(null);
            }

            @Override
            public void onFinally(Response response) {
            }
        });


    }

    @Override
    public void downloadAndInstallApk(final UpdateProgramBean updateProgramBean, final Activity activity) {
        if (!AssertValue.isNotNull(updateProgramBean))
            return;

        CharSequence msg = activity.getResources().getString(R.string.update_prog_msg, updateProgramBean.getLog());

        //非强制更新提示用户下载更新。
        if (updateProgramBean.getFocus() == 0) {
            new AlertDialog.Builder(activity).setTitle(R.string.update_prog_title).setMessage(msg).setPositiveButton(R.string.jupiter_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    execDownloadAndInstallApk(updateProgramBean, activity);
                }
            }).setNegativeButton(R.string.jupiter_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        } else {
            new AlertDialog.Builder(activity).setTitle(R.string.update_prog_title).setMessage(msg).setPositiveButton(R.string.jupiter_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    execDownloadAndInstallApk(updateProgramBean, activity);
                }
            }).show();
        }
    }

    private void execDownloadAndInstallApk(UpdateProgramBean updateProgramBean, final Activity activity) {
        String apkPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "Download" + File.separatorChar + "Yun9" + File.separatorChar;
            File apkFile = new File(apkPath);
            if (!apkFile.exists()) {
                apkFile.mkdir();
            }
        }

        if (AssertValue.isNotNullAndNotEmpty(apkPath) && AssertValue.isNotNull(updateProgramBean.getUrl())) {
            final ProgressDialog progressDialog = ProgressDialog.show(activity, null, activity.getResources().getString(R.string.update_wating), true);

            FinalHttp finalHttp = new FinalHttp();
            finalHttp.download(updateProgramBean.getUrl(), apkPath + apkName, new AjaxCallBack<File>() {
                @Override
                public void onSuccess(File file) {
                    progressDialog.dismiss();
                    AppUtil.installAPK(file, activity);
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    logger.e(strMsg);
                    Toast.makeText(activity, R.string.update_error, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public boolean isLogin() {
        boolean loginState = AppCache.getInstance().getAsBoolean(SessionManager.LOGIN_STATE);
        return loginState;
    }

    @Override
    public User getUser() {
        User user = AppCache.getInstance().get(SessionManager.USER_INFO, User.class);
        if (!AssertValue.isNotNull(user)) {
            user = new User();
        }
        return user;
    }

    @Override
    public Inst getInst() {
        return this.getInst(null);
    }

    public Inst getInst(String userid) {
        UserDataCache userDataCache = UserDataCache.getInstance(userid);

        if (AssertValue.isNotNull(userDataCache)) {
            Inst inst = userDataCache.get(SessionManager.INST_INFO, Inst.class);
            return inst;
        } else {
            return new Inst();
        }
    }

    private void setLogin(boolean login) {
        AppCache.getInstance().put(SessionManager.LOGIN_STATE, login);
    }

    public void setUser(User user) {
        AppCache.getInstance().put(SessionManager.USER_INFO, user);
    }

    private void setInst(Inst inst) {
        UserDataCache.getInstance().put(SessionManager.INST_INFO, inst);
    }

    @Override
    public void loginIn(User user) {

        if (AssertValue.isNotNull(user) && AssertValue.isNotNullAndNotEmpty(user.getId())) {
            //记录登录状态
            this.setLogin(true);
            //记录用户信息
            this.setUser(user);

            //执行后续动作
            if (AssertValue.isNotNullAndNotEmpty(this.onLoginListenerList)) {
                for (OnLoginListener onLoginListener : this.onLoginListenerList) {
                    onLoginListener.login(user);
                }
            }
        }
    }

    @Override
    public void logout(User user) {
        //解除用户登录状态
        this.setLogin(false);
        //清理本地用户信息
        this.setUser(new User());

        //执行用户注销动作服务等相关后续动作
        if (AssertValue.isNotNullAndNotEmpty(this.onLogoutListenerList)) {
            for (OnLogoutListener onLogoutListener : this.onLogoutListenerList) {
                onLogoutListener.logout(user);
            }
        }
    }


    @Override
    public void init(BeanManager beanManager) {
        this.beanManager = beanManager;
    }


    @Override
    public void changeInst(Inst newInst) {

        if (AssertValue.isNotNull(newInst)) {
            Inst oldInst = this.getInst();

            //新旧机构相同无需切换
            if (AssertValue.isNotNull(oldInst) && oldInst.getId().equals(newInst.getId())) {
                return;
            }

            //执行机构的后续动作
            if (AssertValue.isNotNullAndNotEmpty(this.onChangeInstListenerList)) {
                for (OnChangeInstListener onChangeInstListener : this.onChangeInstListenerList) {
                    onChangeInstListener.changeInst(newInst);
                }
            }
            //记录新机构信息
            this.setInst(newInst);

        }

    }

}
