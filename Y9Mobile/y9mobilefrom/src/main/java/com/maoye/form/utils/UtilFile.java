package com.maoye.form.utils;

import java.io.File;

public class UtilFile {
	 /**
     * 获取文件扩展名
     * @param file
     * @return
     */
    public static String getExtensionName(File file) {   
    	try {
			String fileName = file.getName();
			if ((fileName != null) && (fileName.length() > 0)) {   
			    int dot = fileName.lastIndexOf('.');   
			    if ((dot >-1) && (dot < (fileName.length() - 1))) {   
			        return fileName.substring(dot + 1);   
			    }   
			}   
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}   
    	return null;
    }  
    
    
    /**
     * 获取不带扩展名的文件名 
     * @param file
     * @return
     */
    public static String getFileNameNoEx(File file) {   
    	try {
			String fileName = file.getName();
			if ((fileName != null) && (fileName.length() > 0)) {   
			    int dot = fileName.lastIndexOf('.');   
			    if ((dot >-1) && (dot < (fileName.length()))) {   
			        return fileName.substring(0, dot);   
			    }   
			}   
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}  
    	return null;
    } 
}
