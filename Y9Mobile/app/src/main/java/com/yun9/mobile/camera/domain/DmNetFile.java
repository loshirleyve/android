package com.yun9.mobile.camera.domain;

import com.yun9.mobile.framework.model.FileByUserId;
import com.yun9.mobile.framework.util.DateUtil;

public class DmNetFile extends DmFile implements Comparable<DmNetFile>{

	private FileByUserId fileInfo;

	protected String type;
	private String name;
	private String time;
	
	
	/**
	 * @param fileInfo
	 */
	public DmNetFile(FileByUserId fileInfo) {
		super();
		this.fileInfo = fileInfo;
		
		init();
	}
	
	private void init(){
		System.out.println("DmNetFile");
		setType(fileInfo.getType());
		setName(fileInfo.getName());
		setTime(DateUtil.getDateStr(fileInfo.getUpdatedate()));
	}
	

	public FileByUserId getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileByUserId fileInfo) {
		this.fileInfo = fileInfo;
	}





	public String getType() {
		return type;
	}





	public void setType(String type) {
		this.type = type;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	@Override
	public int compareTo(DmNetFile another) {
		Long time = another.fileInfo.getUpdatedate();
		return time.compareTo(fileInfo.getUpdatedate());
		
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		
		if(!(o instanceof DmNetFile)){
			throw new RuntimeException("非法参数");
		}
		
		DmNetFile file = (DmNetFile)o;
		if(this.fileInfo.getId().equals(file.getFileInfo().getId())){
			return true;
		}else{
			return false;
		}
	}
	
}
