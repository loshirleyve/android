package com.yun9.mobile.camera.impl;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yun9.mobile.camera.activity.AlbumActivity;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.interfaces.AlbumEntrance;

public class ImplAlbumEntrance implements AlbumEntrance{

	private Activity activity;
	private Context context;
	
	/**
	 * @param activity
	 */
	public ImplAlbumEntrance(Activity activity) {
		super();
		this.activity = activity;
		this.context = activity;
	}
	
	
	public ImplAlbumEntrance(Context context) {
		super();
		this.context = context;
	}
	
	
	@Override
	public void go2SelectAlbumPhoto(List<DmLocalPhoto> listChosenDmLocalPhoto, List<DmNetPhoto> listChosenDmNetPhoto, AlbumCallBack callBack) {
		Intent intent = new Intent(context, AlbumActivity.class);
		AlbumActivity.staticCallBack = callBack;
		AlbumActivity.staticlistChosenDmLocalPhoto = listChosenDmLocalPhoto;
		AlbumActivity.staticListChosenDmNetPhoto = listChosenDmNetPhoto;
		intent.putExtra(AlbumActivity.FROM_STR, AlbumActivity.FROM_SELECTED);
		intent.putExtra(AlbumActivity.MAXPHOTONUM_STR, AlbumActivity.DEFAULT_MAXPHOTONUM);
		context.startActivity(intent);
	}


	@Override
	public void go2BrowseAlbum() {
		
		
		Intent intent = new Intent(context, AlbumActivity.class);
		intent.putExtra(AlbumActivity.FROM_STR, AlbumActivity.FROM_BROWSE);
//		intent.putExtra(AlbumActivity.FROM_STR, AlbumActivity.FROM_BROWSE);
		context.startActivity(intent);
	}


	@Override
	public void go2SelectNetworkFile(List<DmNetPhoto> listChosenDmNetPhoto, AlbumCallBack callBack) {
		
		Intent intent = new Intent(context, AlbumActivity.class);
		AlbumActivity.staticCallBack = callBack;
		AlbumActivity.staticlistChosenDmLocalPhoto = null;
		AlbumActivity.staticListChosenDmNetPhoto = listChosenDmNetPhoto;
		intent.putExtra(AlbumActivity.FROM_STR, AlbumActivity.FROM_SELECTED);
		intent.putExtra(AlbumActivity.FROM2LEVEL_STR, AlbumActivity.LEVEL_CHOICE_ONLY_NETWORK_PIC);
		intent.putExtra(AlbumActivity.MAXPHOTONUM_STR, AlbumActivity.DEFAULT_MAXPHOTONUM);
		context.startActivity(intent);
	}

}
