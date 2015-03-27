package com.yun9.mobile.camera.domain;

import java.io.Serializable;

import com.yun9.mobile.framework.model.FileByUserId;

/**
 * 
 * 本地相册图片bean<br>
 *  {@link #image_id}图片id<br>
 *  {@link #path_absolute} 绝对路径<br>
 *  {@link #file_path} 用于显示的路径<br>
 *  {@link #choose} 是否被选择<br>
 */
public class DmLocalPhoto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int image_id;
	private String path_file;
	private String path_absolute;
	private String thumbnailPath;
	
	private int ablumId;
	
	/**
	 * 加入媒体库时间
	 */
	private String dateAdded;
	/**
	 * 记录该图片是否被选中
	 */
	private boolean isChecked = false;
	
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	public String getPath_file() {
		return path_file;
	}
	public void setPath_file(String path_file) {
		this.path_file = path_file;
	}
	public String getPath_absolute() {
		return path_absolute;
	}
	public void setPath_absolute(String path_absolute) {
		this.path_absolute = path_absolute;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	
	/**
	 * 如果该对象是相机位置的话，则设置为true，并将其要比较的数设置为最大，让相机在第0位置或者最后一位
	 * @param isCamera
	 */
	public void isCamera(boolean isCamera){
		if(isCamera == true){
			dateAdded = String.valueOf(Long.MAX_VALUE);
		}
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public void reverseChecked() {
		isChecked = !isChecked;
	}
	public int getAblumId() {
		return ablumId;
	}
	public void setAblumId(int ablumId) {
		this.ablumId = ablumId;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		
		if(!(o instanceof DmLocalPhoto)){
			throw new RuntimeException("非法参数");
		}
		
		DmLocalPhoto photo = (DmLocalPhoto)o;
//		if(image_id == photo.image_id){
//			return true;
//		}else{
//			return false;
//		}
		
		
		if(path_absolute != null){
			if(path_absolute.equals(photo.getPath_absolute())){
				return true;
			}else{
				return false;
			}
		}else{
			if(image_id == photo.image_id){
				return true;
			}else{
				return false;
			}
		}
	
	}
	
	
	@Override
	public String toString() {
		return "image_id = " + String.valueOf(image_id) + "   path_absolute = " + path_absolute;
	}
}
