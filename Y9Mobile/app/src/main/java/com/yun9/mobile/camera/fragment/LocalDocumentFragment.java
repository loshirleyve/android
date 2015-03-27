package com.yun9.mobile.camera.fragment;

import java.io.File;
import java.nio.channels.AsynchronousCloseException;
import java.util.LinkedList;
import java.util.List;

import com.maoye.form.utils.UtilCommon;
import com.yun9.mobile.R;
import com.yun9.mobile.camera.adapter.AdapterAllDocument;
import com.yun9.mobile.camera.domain.DmDocument;
import com.yun9.mobile.camera.impl.pre4ui.ImplPre4UiLocalDocumentFragment;
import com.yun9.mobile.camera.interfaces.A4FragmentDocument;
import com.yun9.mobile.camera.interfaces.A4FragmentLocalDocument;
import com.yun9.mobile.camera.interfaces.presenter4ui.Pre4uiDocumentFragment;
import com.yun9.mobile.camera.interfaces.ui4Presenter.Ui4PreLocalDocumentFragment;
import com.yun9.mobile.framework.interfaces.CallBackYiDianTong;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.UtilFile;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Files;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LocalDocumentFragment extends Fragment implements Ui4PreLocalDocumentFragment{
	
	private List<DmDocument> listDmDocuments;
	
	private View view;
	private ListView lvAllDocuments;
	private View loading;
	
	
	private AdapterAllDocument adapter;
	
	private Pre4uiDocumentFragment pre;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO 自动生成的方法存根
		super.onAttach(activity);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.fragment_localdocument, null);
		findView();
		init();
		setEvent();
		return this.view;
	}
	
	
	private void setEvent() {
		this.lvAllDocuments.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				pre.onItemClick(parent, view, position, id);
			}
		});		
	}


	private void findView() {
		this.lvAllDocuments = (ListView) this.view.findViewById(R.id.lvAllDocuments);
		this.loading = this.view.findViewById(R.id.loading);
	}


	private void init(){
		pre = new ImplPre4UiLocalDocumentFragment(this,getActivity(),(A4FragmentLocalDocument)getActivity());
		
		listDmDocuments = pre.getDocumentList();
		adapter = new AdapterAllDocument(listDmDocuments, getActivity());
		lvAllDocuments.setAdapter(adapter);
		
		pre.work();
	}


	@Override
	public void setLoadingVisibility(int visibility) {
		loading.setVisibility(visibility);
	}


	@Override
	public void notifyDataSetChanged() {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapter.notifyDataSetChanged();	
			}
		});
	}


	@Override
	public void showToast(String text) {
		UtilCommon.showToast(getActivity(), text);
	}


	@Override
	public void setChecked(int position, boolean isCheck, View view) {
		adapter.setChecked(position, isCheck, view);
	}


	public void uploadDocument(CallBackYiDianTong callBack) {
		pre.uploadLocalDocument(callBack);
	}


	@Override
	public void onDestroy() {
		pre.onDestory();
		super.onDestroy();
	}


	
}
