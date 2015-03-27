package com.yun9.mobile.camera.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.baidu.platform.comapi.map.A;
import com.maoye.form.utils.UtilCommon;
import com.yun9.mobile.R;
import com.yun9.mobile.camera.adapter.AdapterNetDocument;
import com.yun9.mobile.camera.domain.DmNetDocument;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.camera.factory.FactoryPresenter;
import com.yun9.mobile.camera.factory.FactoryPresenterDefault;
import com.yun9.mobile.camera.interfaces.A4FragmentNetDocument;
import com.yun9.mobile.camera.interfaces.presenter.Pre4uiNetDocumentFragment;
import com.yun9.mobile.camera.interfaces.presenter.Ui4PreNetDocumentFragment;
import com.yun9.mobile.framework.interfaces.CallBackYiDianTong;
import com.yun9.mobile.framework.model.FileByUserId;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.position.view.pullrefresh.PullToRefreshBase;
import com.yun9.mobile.position.view.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.yun9.mobile.position.view.pullrefresh.PullToRefreshListView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NetDocumentFragment extends Fragment implements Ui4PreNetDocumentFragment{
	
	private View mainView;
	private PullToRefreshListView pullRefreshListView;
	private ListView listView;
	
	private Pre4uiNetDocumentFragment presenter;
	private AdapterNetDocument adapter;
	private List<DmNetDocument> netDucoments;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.fragment_netdocument, null);
		findView();
		init();
		setEvent();
		return mainView;
	}

	private void findView() {
		pullRefreshListView = (PullToRefreshListView) mainView.findViewById(R.id.lvDocuments);
		listView = pullRefreshListView.getRefreshableView();
		pullRefreshListView.setPullLoadEnabled(true);
	}
	
	private void init() {
		FactoryPresenter factoryPresenter = new FactoryPresenterDefault();
		netDucoments = new ArrayList<DmNetDocument>();
		presenter = factoryPresenter.creatPre4uiNetDocumentFragment(this, getActivity(), (A4FragmentNetDocument)getActivity());
		adapter = new AdapterNetDocument(netDucoments, getActivity());
		listView.setAdapter(adapter);
		presenter.onCreate();
		
		
		presenter.onPullDownToRefresh();
	}


	private void setEvent() {
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				presenter.OnItemClickListener(parent, view, position, id);
			}
		});
		
		pullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				presenter.onPullDownToRefresh();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				presenter.onPullUpToRefresh();
			}
		});
		
	}


	@Override
	public void showToast(String string) {
		UtilCommon.showToast(getActivity(), string);
	}


	@Override
	public void onPullDownRefreshComplete() {
		pullRefreshListView.onPullDownRefreshComplete();
	}
	@Override
	public void onPullUpRefreshComplete() {
		pullRefreshListView.onPullUpRefreshComplete();
	}

	@Override
	public List<DmNetDocument> getNetDocuments() {
		return netDucoments;
	}


	@Override
	public void notifyDataSetChanged() {
		adapter.notifyDataSetChanged();
	}


	@Override
	public int addNetDocument(List<DmNetDocument> netDocuments, List<FileByUserId> fileInfos) {
		
		int num = 0;
		if(!AssertValue.isNotNullAndNotEmpty(fileInfos)){
			return num;
		}
		boolean result = false;
		for(FileByUserId fileInfo : fileInfos){
			DmNetDocument document = new DmNetDocument(fileInfo);
			result = addNetDocument(netDocuments, document);
			if(result){
				num++;
			}
		}
		return num;
	}
	
	public boolean addNetDocument(List<DmNetDocument> netDocuments, DmNetDocument document){
		
		if(!AssertValue.isNotNull(netDocuments) || document == null){
			return false;
		}
		
		if(netDocuments.contains(document)){
			return false;
		}
		
		return netDocuments.add(document);
	}


	@Override
	public void setChecked(int position, boolean isCheck, View view) {
		adapter.setChecked(position, isCheck, view);
	}


	public void deleteSelectedNetFileSync(CallBackYiDianTong callBack) {
		presenter.deleteSelectedNetFileSync(callBack);
	}

	
}
