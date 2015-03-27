package com.yun9.mobile.position.fragment;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.core.PoiInfo;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.yun9.mobile.R;
import com.yun9.mobile.position.adapter.PositionListAdapter;
import com.yun9.mobile.position.iview.PositionFragmentIView;
import com.yun9.mobile.position.iview.PositionIView;
import com.yun9.mobile.position.presenter.PositionFragmentPresenter;
import com.yun9.mobile.position.iface.IPoiSearch;
import com.yun9.mobile.position.view.pullrefresh.PullToRefreshBase;
import com.yun9.mobile.position.view.pullrefresh.PullToRefreshListView;
import com.yun9.mobile.position.utils.TimeUtil;

public abstract class PositionFragment extends Fragment implements PositionFragmentIView {
	protected static final String TAG = PositionFragment.class.getSimpleName();
	private PositionFragmentPresenter presenter;
	private PullToRefreshListView lvPosition;
	private ListView listView;
	
	private PositionIView positionIView;
	private IPoiSearch IPoiSearch;
	private List<PoiInfo> poiLoadList = new ArrayList<PoiInfo>();
	private PositionListAdapter adapter;
	
	private View vError;
	private View vload;
	private View vContent;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(IPoiSearch==null){
			IPoiSearch = (IPoiSearch)activity;
		}
		if(positionIView == null){
			positionIView = (PositionIView) activity;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_position, container, false);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		presenter = new PositionFragmentPresenter(this,poiLoadList,IPoiSearch,positionIView);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		findView();
		init();
	}
	
	private void findView() {
		vload = getView().findViewById(R.id.vload);
		vError = getView().findViewById(R.id.vError);	
		vContent = getView().findViewById(R.id.vContent);	
		lvPosition = (PullToRefreshListView) getView().findViewById(R.id.lvPosition);
		
	}

	private void init(){
		listInit();
	}
	
	private void listInit(){
		
		
		lvPosition.setPullLoadEnabled(true);
		listView = lvPosition.getRefreshableView();
		
		adapter = new PositionListAdapter(poiLoadList, getActivity());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				presenter.onItemClick(parent, view, position, id);
			}
		});
		
		lvPosition.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				presenter.onPullDownToRefresh(getSearKey());
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				presenter.onPullUpToRefresh(getSearKey());
			}
		});
		
		
	}
	@Override
	public void onStart() {
		super.onStart();
		presenter.getInitData(getSearKey());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		presenter.onDestroy();
		super.onDestroy();
	}
	
	@Override
    public void setLastUpdateTime()
    {
        String text = TimeUtil.getDayTime();
        lvPosition.setLastUpdatedLabel(text);
    }

	@Override
	public void PoiResultNull(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
		lvPosition.onPullDownRefreshComplete();
		lvPosition.onPullUpRefreshComplete();		
	}

	@Override
	public void onPullDownRefreshComplete() {
		lvPosition.onPullDownRefreshComplete();		
	}

	@Override
	public void onPullUpRefreshComplete() {
		lvPosition.onPullUpRefreshComplete();			
	}

	@Override
	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void notifyDataSetChanged() {
		adapter.notifyDataSetChanged();		
	}
	
	@Override
	public void showLoad() {
		vContent.setVisibility(View.GONE);
		vError.setVisibility(View.GONE);
		vload.setVisibility(View.VISIBLE);
	}


	@Override
	public void showError() {
		vContent.setVisibility(View.VISIBLE);
		
		vload.setVisibility(View.GONE);	
		vError.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void showContent(){
		vload.setVisibility(View.GONE);	
		vError.setVisibility(View.GONE);
		vContent.setVisibility(View.VISIBLE);
	}

    public void setHasMoreData(Boolean hasMoreData){
        lvPosition.setHasMoreData(hasMoreData);
    }

	public abstract String getSearKey();
}
