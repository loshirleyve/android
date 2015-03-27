package com.yun9.mobile.camera.domain;

import java.io.File;
import java.math.BigDecimal;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.constant.ConstantAlbum;
import com.yun9.mobile.framework.util.DateUtil;
import com.yun9.mobile.framework.util.UtilFile;

public class DmDocument {

	private int photo;
	private String type;
	private String time;
	private String size;
	private String name;
	private File file;
	private boolean isChecked;
	/**
	 * @param file
	 */
	public DmDocument(File file) {
		super();
		this.file = file;
		
		init();
	}
	
	private void init(){
		this.type = UtilFile.getExtensionName(this.file);
		this.name = UtilFile.getFileNameNoEx(this.file);
		this.time = DateUtil.getDateStr(this.file.lastModified());
		this.size = DmDocument.getFileSize(file);
		
		initPhoto();
	}
	
	
	private void initPhoto(){
		if(ConstantAlbum.TYPE_PDF.equals(this.type)){
			photo = R.drawable.small_ico_pdf;
		}
		else if(ConstantAlbum.TYPE_TXT.equals(this.type)){
			photo = R.drawable.small_ico_txt;
		}
		else if(ConstantAlbum.TYPE_DOC.equals(this.type) || ConstantAlbum.TYPE_DOCX.equals(this.type)){
			photo = R.drawable.small_ico_doc;
		}else if(ConstantAlbum.TYPE_PPT.equals(this.type)|| ConstantAlbum.TYPE_PPTX.equals(this.type)){
			photo = R.drawable.small_ico_ppt;
		}else if(ConstantAlbum.TYPE_WPS.equals(this.type)){
			photo = R.drawable.small_ico_wps;
		}else if(ConstantAlbum.TYPE_XLS.equals(this.type) || ConstantAlbum.TYPE_XLSX.equals(this.type)){
			photo = R.drawable.small_ico_xls;
		}
		else{
			photo = R.drawable.small_ico_unkwon;
		}
	}

	public int getPhoto() {
		return photo;
	}

	public String getType() {
		return type;
	}

	public String getTime() {
		return time;
	}

	public String getSize() {
		return size;
	}

	public String getName() {
		return name;
	}
	
	
	private static String getFileSize(File file){
		String unit = null;
		long size = file.length();
		long MB = 1024 * 1024;
		long KB = 1024;
		float fileSize = 0.0f;
		if(size >= MB){
			fileSize = (float) size / MB;
			unit = "MB";
		}else if(size >= KB){
			fileSize = (float) size / KB;
			unit = "KB";
		}else{
			fileSize = (float) size;
			unit = "B";
		}
		
		BigDecimal b = new BigDecimal(fileSize);
		fileSize = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		String sizeStr =  fileSize + unit;
		return sizeStr;
	}

	public File getFile() {
		return file;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	
}
