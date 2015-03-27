package com.yun9.mobile.camera.domain;

import java.io.Serializable;
import java.util.List;

/**    
 *  相册bean<br>
 *  {@link #image_id}图片id<br>
 *  {@link #path_absolute} 绝对路径<br>
 *  {@link #path_file} 用于显示的路径<br>
 *  {@link #name_album} 相册名称<br>
 */
public class DmLocalAlbum implements Serializable{

	private static final long serialVersionUID = 1L;
	private int image_id;
	private String path_absolute;
	
	
	/**
	 * 可以直接输出在imageloader，已经加入"file://"格式
	 */
	private String path_file;
	private String name_album;
	private List<DmLocalPhoto> list;
	
	
	/**
	 * 主要用于排序，将Camera相册放在第一位
	 */
	private String dateAdded;
	
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	public String getPath_absolute() {
		return path_absolute;
	}
	public void setPath_absolute(String path_absolute) {
		this.path_absolute = path_absolute;
	}
	public String getPath_file() {
		return path_file;
	}
	public void setPath_file(String path_file) {
		this.path_file = path_file;
	}
	public String getName_album() {
		return name_album;
	}
	public void setName_album(String name_album) {
		this.name_album = name_album;
	}
	public List<DmLocalPhoto> getList() {
		return list;
	}
	public void setList(List<DmLocalPhoto> list) {
		this.list = list;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
}
