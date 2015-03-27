package com.yun9.mobile.msg.interfaces;

public interface MsgCardListFragmentIView {

	public void showToast(String msg);
	
	public void notifyDataSetChanged();
	
	public void onRefreshComplete();
	
	public void onLoadMoreComplete();

	public void setCanLoadMore(boolean isCanLoadMore) ;
	
	public void isShowLoading(boolean isShow);
	
	public void isShowMsgUI(boolean isShow);
}
