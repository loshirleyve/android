package com.yun9.mobile.camera.interfaces.presenter4ui;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;

import com.yun9.mobile.camera.domain.DmDocument;
import com.yun9.mobile.framework.interfaces.CallBackYiDianTong;

public interface Pre4uiDocumentFragment {

	public List<DmDocument> getDocumentList();

	public void work();

	public void onItemClick(AdapterView<?> parent, View view, int position, long id);

	public void uploadLocalDocument(CallBackYiDianTong callBack);

	public void onDestory();

}
