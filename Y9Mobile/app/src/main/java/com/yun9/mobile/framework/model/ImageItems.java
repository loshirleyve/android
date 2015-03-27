package com.yun9.mobile.framework.model;

import java.util.List;

import com.yun9.mobile.camera.domain.DmImageItem;

public class ImageItems implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DmImageItem> imageItems;
	
	public ImageItems(List<DmImageItem> imageItems){
		this.imageItems = imageItems;
	}

	public List<DmImageItem> getImageItems() {
		return imageItems;
	}

	public void setImageItems(List<DmImageItem> imageItems) {
		this.imageItems = imageItems;
	}

}
