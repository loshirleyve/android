package com.yun9.mobile.camera.domain;

import com.yun9.mobile.framework.model.FileByUserId;

public class DmNetPhoto{
	private FileByUserId fileInfo;
	
	/**
	 * 记录该图片是否被选中
	 */
	private boolean isChecked = false;
	
	public FileByUserId getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileByUserId fileInfo) {
		this.fileInfo = fileInfo;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public void reverseChecked(){
		boolean stat = isChecked;
		isChecked = !stat;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		
		if(!(o instanceof DmNetPhoto)){
			throw new RuntimeException("非法参数");
		}
		
		DmNetPhoto photo = (DmNetPhoto)o;
		if(this.fileInfo.getId().equals(photo.getFileInfo().getId())){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return fileInfo.getId() + "  " + fileInfo.getName();
	}
	
	
	/**
	 * 如果该对象是相机位置的话，则设置为true，并将其要比较的数设置为最大，让相机在第0位置或者最后一位
	 * @param isCamera
	 */
	public void isCamera(boolean isCamera){
		if(isCamera == true){
			FileByUserId fileInfo = new FileByUserId();
			fileInfo.setId(String.valueOf(Long.MAX_VALUE));
			fileInfo.setCreatedate(Long.MAX_VALUE);
			this.fileInfo = fileInfo;
		}
	}

}
