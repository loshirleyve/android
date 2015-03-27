package com.yun9.mobile.position.iview;

public interface PositionFragmentIView {

	public void PoiResultNull(String msg);
	
	public void onPullDownRefreshComplete();
	
	public void onPullUpRefreshComplete();
	
	public void showToast(String msg);
	
	public void setLastUpdateTime();
	
	public void notifyDataSetChanged();
	
	public void showLoad();
	
	public void showError();
	
	public void showContent();

    public void setHasMoreData(Boolean hasMoreData);


}
