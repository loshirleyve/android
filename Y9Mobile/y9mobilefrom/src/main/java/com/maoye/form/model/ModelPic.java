package com.maoye.form.model;

import java.io.File;

import com.maoye.form.R;
import com.maoye.form.constact.ConstactForm;
import com.maoye.form.utils.UtilFile;
/**
 * @author lhk
 *
 */
public class ModelPic extends ModelFile{
	
	private File file;
	private String path;
	private String value;
	
    private Long createdate;
  
	
	/**
	 * @param path
	 */
	public ModelPic() {
		super();
	}
	
	
	private void init(){
		setName(UtilFile.getFileNameNoEx(this.file));
		
		path = file.getAbsolutePath();
	}
	




	public String getPath() {
		return path;
	}

	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
	
	
	public String getShowPath(){
		if(emumType == null){
			return null;
		}
		
		if(emumType == FileType.PicNetWork){
			return value;
		}else if(emumType == FileType.PicLocal){
			return path;
		}
		
		return null;
	}


	public Long getCreatedate() {
		return createdate;
	}


	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}


	public void setPath(String path) {
		this.path = path;
	}


}
