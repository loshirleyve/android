package com.maoye.form.model;

public class ModelFile {
	protected FileType emumType;

	
	private String name;
	
	public enum FileType {
		PicNetWork, PicLocal, DocumentNetWork, DocumentLocal
	}
	
	public FileType getEmumType() {
		return emumType;
	}

	public void setEmumType(FileType emumType) {
		this.emumType = emumType;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
