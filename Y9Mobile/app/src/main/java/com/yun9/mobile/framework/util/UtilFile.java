package com.yun9.mobile.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UtilFile {

	public static void list(File path, String fileType) {

		if (!path.exists()) {
			System.out.println("文件名称不存在!");
		} else {
			if (path.isFile()) {
				if (AssertValue.isNotNullAndNotEmpty(fileType)) {
					// 过滤文件名设置
					if (path.getName().toLowerCase().endsWith(fileType)) {// 文件格式
						System.out.println(path);
						System.out.println(path.getName());
					}
				} else {
					System.out.println(path);
					System.out.println(path.getName());
				}

			} else {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					list(files[i], fileType);
				}
			}
		}
	}

	public static void copy(File source, File target) {
		File tarpath = new File(target, source.getName());
		if (source.isDirectory()) {
			tarpath.mkdir();
			File[] dir = source.listFiles();
			for (int i = 0; i < dir.length; i++) {
				copy(dir[i], tarpath);
			}
		} else {
			try {
				InputStream is = new FileInputStream(source); // 用于读取文件的原始字节流
				OutputStream os = new FileOutputStream(tarpath); // 用于写入文件的原始字节的流
				byte[] buf = new byte[1024];// 存储读取数据的缓冲区大小
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("test");
	}

	
    
    /**
     * 获取文件扩展名
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length() - 1))) {   
                return filename.substring(dot + 1);   
            }   
        }   
        return filename;   
    }  
    
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
    
    
    /**
     * 获取不带扩展名的文件名 
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length()))) {   
                return filename.substring(0, dot);   
            }   
        }   
        return filename;   
    }  
    
    
    public static void searchFile(File file, String regularExpression, List<File> lists){
    	try {
			if(file.isDirectory()){
				File[] resultfiles = searchFile4Type(file, regularExpression);
				if(resultfiles != null){
					for(int i = 0; i < resultfiles.length; i++){
						lists.add(resultfiles[i]);
					}
				}
				
				File[] childFiles = file.listFiles();
				for(int i = 0; i < childFiles.length; i++){
					searchFile(childFiles[i], "", lists);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    public static File[] searchFile4Type(File fileDir, String regularExpression){
    	File[] files;
		try {
				files = fileDir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
//					String regularExpression = "*.txt";
//					if(name.matches(regularExpression)){
//						return true;
//					}else{
//						return false;
//					}
					
					String doc = ".doc";
					String docx = ".docx";
					String pdf = ".pdf";
					String txt = ".txt";
					String xls = ".xls";
					String xlsx = ".xlsx";
					String ppt = ".ppt";
					String pptx = ".pptx";
					String wps = ".wps";
					if(name.endsWith(doc) || 
							name.endsWith(docx) || 
							name.endsWith(pdf)  ||  
							name.endsWith(xls)  || 
							name.endsWith(xlsx) || 
							name.endsWith(ppt)  || 
							name.endsWith(pptx) || 
							name.endsWith(wps) 	|| 
							name.endsWith(txt))
					{
						return true;
					}else
					{
						return false;
					}
				}
			});
			return files;
		} catch (Exception e) {
			
		}
		return null;
    }
}
