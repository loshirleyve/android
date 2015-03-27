package com.yun9.mobile.position.iface;

import android.content.Intent;

public interface IAcquirePosition {
	/**
	 * 获取位置
	 * @param mode 获取位置的模式（打开、不限制位置）
	 * @param radius (搜索位置的半径 ，单位：米)
	 * @param reqCode （请求码）
	 */
	public void go2GetPosition(int mode, int radius, int reqCode);

    public void go2GetPosition(int mode, int radius, IAcquirePositionCallBack callBack);

	public IAcquirePositionCallBack.OutParam getResult(int resultCode, Intent data);

}
