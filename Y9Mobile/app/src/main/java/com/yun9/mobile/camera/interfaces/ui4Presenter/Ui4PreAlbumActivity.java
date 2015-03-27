package com.yun9.mobile.camera.interfaces.ui4Presenter;

import android.widget.PopupWindow;

public interface Ui4PreAlbumActivity {
	public void showToast(String msg);

	public void updateBtnCompleteContent(int maxChoosePicNum, int selectablPicNum);

	public void showBottom(int value);

	public void showTitle4LocalAlbum(int value);

	public void showTitl4NetAlbum(int visible);
}
