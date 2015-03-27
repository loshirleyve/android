package com.yun9.mobile.camera.interfaces.ui4Presenter;

import android.view.View;

public interface Ui4PreLocalDocumentFragment {

	void setLoadingVisibility(int visibility);

	void notifyDataSetChanged();

	void showToast(String text);

	void setChecked(int position, boolean isCheck, View view);


}
