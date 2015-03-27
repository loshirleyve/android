package com.yun9.mobile.camera.presenter;

import android.app.Activity;

import com.yun9.mobile.camera.uiface.UiFaceNetWorkAlbumFragment;

public class PresenterNetWorkAlbumFragment {

	private UiFaceNetWorkAlbumFragment UiFace;
	private Activity activity;
	/**
	 * @param uiFace
	 * @param activity
	 */
	public PresenterNetWorkAlbumFragment(UiFaceNetWorkAlbumFragment uiFace,
			Activity activity) {
		super();
		UiFace = uiFace;
		this.activity = activity;
	}
	public void onPullUpToRefresh() {
		// TODO 自动生成的方法存根
		
	}
}
