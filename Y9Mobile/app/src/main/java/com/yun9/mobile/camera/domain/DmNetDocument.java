package com.yun9.mobile.camera.domain;

import java.io.File;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.constant.ConstantAlbum;
import com.yun9.mobile.framework.model.FileByUserId;
import com.yun9.mobile.framework.util.DateUtil;

public class DmNetDocument extends DmNetFile{
	
	private int photo;

	
	
	/**
	 * @param fileInfo
	 */
	public DmNetDocument(FileByUserId fileInfo) {
		super(fileInfo);
		
		init();
	}
	
	private void init(){
		System.out.println("DmNetDocument");
		initPhoto();
	}

	private void initPhoto() {
		if(ConstantAlbum.TYPE_PDF.equals(type)){
			photo = R.drawable.small_ico_pdf;
		}
		else if(ConstantAlbum.TYPE_TXT.equals(type)){
			photo = R.drawable.small_ico_txt;
		}
		else if(ConstantAlbum.TYPE_DOC.equals(type) || ConstantAlbum.TYPE_DOCX.equals(type)){
			photo = R.drawable.small_ico_doc;
		}else if(ConstantAlbum.TYPE_PPT.equals(type)|| ConstantAlbum.TYPE_PPTX.equals(type)){
			photo = R.drawable.small_ico_ppt;
		}else if(ConstantAlbum.TYPE_WPS.equals(type)){
			photo = R.drawable.small_ico_wps;
		}else if(ConstantAlbum.TYPE_XLS.equals(type) || ConstantAlbum.TYPE_XLSX.equals(type)){
			photo = R.drawable.small_ico_xls;
		}
		else{
			photo = R.drawable.small_ico_unkwon;
		}
	}
	public int getPhoto() {
		return photo;
	}
}

