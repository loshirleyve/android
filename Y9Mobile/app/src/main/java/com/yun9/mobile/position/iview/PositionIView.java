package com.yun9.mobile.position.iview;


public interface PositionIView {
	
	public void showChoosePoiName(String name);
	
	public void showChoosePoiAddr(String addr);

	public void showBottomView();

	public void closeBottomLoad();

	public void ShowToast(String msg);

	public void initBottomView();


}
