package com.yun9.mobile.camera.interfaces.presenter;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;

import com.yun9.mobile.camera.domain.DmNetDocument;
import com.yun9.mobile.framework.interfaces.CallBackYiDianTong;

public interface Pre4uiNetDocumentFragment {

	public void onPullDownToRefresh();


	public void onCreate();


	public void onPullUpToRefresh();


	public void OnItemClickListener(AdapterView<?> parent, View view, int position, long id);


	public void deleteSelectedNetFileSync(CallBackYiDianTong callBack);


}
