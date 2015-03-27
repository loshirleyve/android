package com.yun9.mobile.camera.interfaces.presenter;

import java.util.List;
import java.util.TreeSet;

import android.view.View;

import com.yun9.mobile.camera.domain.DmNetDocument;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.framework.model.FileByUserId;



public interface Ui4PreNetDocumentFragment {

	public void showToast(String string);

	public void onPullDownRefreshComplete();

	public List<DmNetDocument> getNetDocuments();

	public void notifyDataSetChanged();

	public int addNetDocument(List<DmNetDocument> netDocuments, List<FileByUserId> fileInfos);

	public void onPullUpRefreshComplete();

	public void setChecked(int position, boolean isCheck, View view);



}
