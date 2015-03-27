package com.yun9.mobile.camera.interfaces;

import java.util.List;

import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;

public interface AlbumEntrance {
	
	/**
	 * 去相册选择图片
	 * @param listChosenDmLocalPhoto
	 * @param listChosenDmNetPhoto
	 * @param callBack
	 */
	public void go2SelectAlbumPhoto(List<DmLocalPhoto> listChosenDmLocalPhoto, List<DmNetPhoto> listChosenDmNetPhoto, AlbumCallBack callBack);

	
	/**
	 * 去相册看图片
	 */
	public void go2BrowseAlbum();
	
	interface AlbumCallBack{
		
		public void photoCallBack(List<DmLocalPhoto> listChosenDmLocalPhoto, List<DmNetPhoto> listChosenDmNetPhoto, boolean isOrigin);
	}
	
	
	public void go2SelectNetworkFile(List<DmNetPhoto> listChosenDmNetPhoto, AlbumCallBack callBack);
	
}


 
