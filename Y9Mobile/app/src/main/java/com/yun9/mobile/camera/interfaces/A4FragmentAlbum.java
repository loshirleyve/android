package com.yun9.mobile.camera.interfaces;

import java.util.List;

import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.camera.enums.ModeAlbum.ModelAlbum2Level;

public interface A4FragmentAlbum {
	public ModeAlbum getAlbumMode();
	
	
	public int getMaxChoosePicNum();
	
	/**
	 * 
	 * @return 还可以选择的图片数量
	 */
	public int getSelectablePicNum();
	
	/**
	 * 添加选中的网络图片
	 * @param photo
	 */
	public void addChosenNetPhoto(DmNetPhoto photo);
	
	
	/**
	 * 从列表中移除网络图片
	 * @param photo
	 */
	public void removeChosenNetPhoto(DmNetPhoto photo);
	
	/**
	 * 添加选中的本地图片
	 * @param photo
	 */
	public void addChosenLocalPhoto(DmLocalPhoto photo);
	
	/**
	 *  从列表中移除本地图片
	 * @param photo
	 */
	public void removeChosenLocalPhoto(DmLocalPhoto photo);
	
	/**
	 * 获取被选择的本地图片集合
	 * @return
	 */
	public List<DmLocalPhoto> getChosenLocalPhotos();
	
	/**
	 * 获取被选择的网络图片集合
	 * @return
	 */
	public List<DmNetPhoto> getChosenNetPhotos();
	
	
	/**
	 * 通知，更新当前相册
	 */
	public void updateCurrentAlbum();
	
	
	/**
	 * 是否原图
	 * @return true 表示原图
	 */
	public boolean isOrigin();


	public ModelAlbum2Level getAlbumModeLevel();
}
