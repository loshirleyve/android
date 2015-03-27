package com.yun9.mobile.msg.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.base.activity.BaseFragment;
import com.yun9.mobile.framework.cache.MsgCardCache;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.view.CustomListView;
import com.yun9.mobile.framework.view.CustomListView.OnLoadMoreListener;
import com.yun9.mobile.framework.view.CustomListView.OnRefreshListener;
import com.yun9.mobile.framework.view.LoadingView;
import com.yun9.mobile.msg.adapter.MsgCardAdapter;
import com.yun9.mobile.msg.interfaces.MsgCardListFragmentIView;
import com.yun9.mobile.msg.model.MyMsgCard;
import com.yun9.mobile.msg.presenter.MsgCardListFragmentPresenter;

public class MsgCardListFragment extends BaseFragment implements MsgCardListFragmentIView{
	private View mBaseView;
	private CustomListView mCustomListView;
	private MsgCardAdapter msgCardAdapter;
	public static MsgCardListFragmentPresenter presenter;

	private List<MyMsgCard> msgCards;
	
	private LoadingView loading;
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mBaseView = inflater.inflate(R.layout.fragment_msgcard_list, null);
		presenter = new MsgCardListFragmentPresenter(getActivity(), MsgCardListFragment.this);
		return mBaseView;
	}

	@Override
	protected void initWidget() {
		loading = (LoadingView) mBaseView.findViewById(R.id.loading);
		
		mCustomListView = (CustomListView) mBaseView.findViewById(R.id.lv_msgs);
		mCustomListView.setCanLoadMore(true);

	}


	@Override
	protected void bindEvent() {

		mCustomListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (AssertValue.isNotNull(presenter.getMsgCards())) {
					presenter.getMsgCards().clear();
					MsgCardCache.getInstance().clean();
				}
				presenter.onRefresh();
			}
		});
		
		mCustomListView.setOnLoadListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				presenter.onLoadMore();
			}
		});
		
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}
	

	private void init(){
		msgCards = presenter.getMsgCards();
		msgCardAdapter = new MsgCardAdapter(mContext, msgCards);
		mCustomListView.setAdapter(msgCardAdapter);
		
		presenter.initUI();
	}
	
	
	@Override
	public void showToast(String msg) {
		Toast.makeText(mContext, msg,Toast.LENGTH_SHORT).show();		
	}

	@Override
	public void notifyDataSetChanged() {
		msgCardAdapter.notifyDataSetChanged();		
	}

	@Override
	public void onRefreshComplete() {
		mCustomListView.onRefreshComplete();		
	}

	@Override
	public void onLoadMoreComplete() {
		mCustomListView.onLoadMoreComplete();			
	}

	@Override
	public void setCanLoadMore(boolean isCanLoadMore) {
		mCustomListView.setCanLoadMore(isCanLoadMore);
	}

	@Override
	public void isShowLoading(boolean isShow) {
		if(isShow){
			loading.setVisibility(View.VISIBLE);
		}else{
			loading.setVisibility(View.GONE);
		}
	}

	@Override
	public void isShowMsgUI(boolean isShow) {
				
		if(isShow){
			mCustomListView.setVisibility(View.VISIBLE);
		}else{
			mCustomListView.setVisibility(View.GONE);
		}
	}
}
