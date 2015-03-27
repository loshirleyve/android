package com.yun9.mobile.framework.interfaces.ui4presenter;

import java.io.File;

public interface UI4preMineFragment {

	public void showToast(String string);

	public void showFocusUpdateDlg(String message);

	public void showChoseUpdateDlg(String message);

	public void closeUpdateDlg();

	public void showIntallApkDlg();

	public void closeIntallApkDlg();

	public void setIntallApkDlgProgress(String msg);
	
	
	public void isShowNewVersionTip(boolean isShow);

}
