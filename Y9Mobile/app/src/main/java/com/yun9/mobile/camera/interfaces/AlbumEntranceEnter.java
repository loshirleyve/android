package com.yun9.mobile.camera.interfaces;

import java.util.Map;

import android.app.Activity;

import com.yun9.mobile.camera.impl.ImplAlbumEntrance;
import com.yun9.mobile.framework.base.activity.EnterActivity;

public class AlbumEntranceEnter implements EnterActivity {

	public Activity context ;
	private Map<String, Object> params;
	
	@Override
	public void enter(Activity context, Map<String, Object> params) {
		this.context= context;
		this.params = params;
		AlbumEntrance album = new ImplAlbumEntrance(context);
		album.go2BrowseAlbum();
	}

}
