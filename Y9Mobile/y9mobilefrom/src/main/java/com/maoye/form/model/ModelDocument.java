package com.maoye.form.model;

import java.io.File;

import com.maoye.form.R;
import com.maoye.form.constact.ConstactForm;
import com.maoye.form.utils.UtilFile;

public class ModelDocument extends ModelFile{
	private int photo;
	private String suffix;
	private String path;
	private String value;
	
	public ModelDocument() {
		super();
	}
	
	
	public ModelDocument(String path) {
		super();
		this.path = path;
		init();
	}
	
	private void init(){
		suffix = UtilFile.getExtensionName(new File(path));
		initPhoto();
	}


	private void initPhoto(){
		if(ConstactForm.SUFFIX_PDF.equals(this.suffix)){
			photo = R.drawable.form_small_ico_pdf;
		}
		else if(ConstactForm.SUFFIX_TXT.equals(this.suffix)){
			photo = R.drawable.form_small_ico_txt;
		}
		else if(ConstactForm.SUFFIX_DOC.equals(this.suffix) || ConstactForm.SUFFIX_DOCX.equals(this.suffix)){
			photo = R.drawable.form_small_ico_doc;
		}else if(ConstactForm.SUFFIX_PPT.equals(this.suffix)|| ConstactForm.SUFFIX_PPTX.equals(this.suffix)){
			photo = R.drawable.form_small_ico_ppt;
		}else if(ConstactForm.SUFFIX_WPS.equals(this.suffix)){
			photo = R.drawable.form_small_ico_wps;
		}else if(ConstactForm.SUFFIX_XLS.equals(this.suffix) || ConstactForm.SUFFIX_XLSX.equals(this.suffix)){
			photo = R.drawable.form_small_ico_xls;
		}
		else{
			photo = R.drawable.form_ic_file_any;
		}
	}
	public int getPhoto() {
		return photo;
	}

	public void setPhoto(int photo) {
		this.photo = photo;
	}
}
