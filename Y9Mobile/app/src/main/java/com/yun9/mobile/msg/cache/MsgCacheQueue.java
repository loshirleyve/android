package com.yun9.mobile.msg.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MsgCacheQueue<T>{

	/**
	 * 缓存文件路径
	 */
	private String cacheFilePath;
	
	
	/**
	 * 缓存消息数量
	 */
	private int caCheMsgNum;
	
	

	public List<T> getMsgCacheData() {
		
		return file2msg(cacheFilePath);
	}

	public Boolean setMsgCache(List<T> msgList) {
		
		return msg2File(cacheFilePath, msgList);
	}
	
	
	/**
	 * @param cacheFilePath
	 * @param caCheMsgNum
	 */
	public MsgCacheQueue(String cacheFilePath, int caCheMsgNum) {
		super();
		this.cacheFilePath = cacheFilePath;
		this.caCheMsgNum = caCheMsgNum;
	}

	private boolean msg2File(String path, List<T> msglist){   
        FileOutputStream fos = null;  
        ObjectOutputStream oos = null;  
        File f = new File(path);  
        if (!f.getParentFile().exists()) {
        	f.getParentFile().mkdirs();
        }
        try {  
            fos = new FileOutputStream(f);  
            oos = new ObjectOutputStream(fos);  
            oos.writeObject(msglist);    //括号内参数为要保存java对象  
            return true;
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
            	if(fos != null)
            	{
            		fos.close();  
            	}
            	if(oos != null)
            	{
            		oos.close(); 
            	}
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		return false;     
    } 
	
    private List<T> file2msg(String path){  
        FileInputStream fis = null;  
        ObjectInputStream ois = null;     
        File f = new File(path);  
        
        if(!f.exists()){
        	return null;
        }
        
        
        try {  
            fis = new FileInputStream(f);  
            ois = new ObjectInputStream(fis);  
            List<T> msglist = (List<T>)ois.readObject();//强制类型转换  
            return msglist;
        }  catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
            	if(ois != null)
            	{
            		ois.close();  
            	}
            	if(fis != null)
            	{
            		fis.close(); 
            	}
                 
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		return null;  
    }

	
    
    public String getCacheFilePath() {
		return cacheFilePath;
	}

    public void setCacheFilePath(String cacheFilePath) {
		this.cacheFilePath = cacheFilePath;
	}

    public int getCaCheMsgNum() {
		return caCheMsgNum;
	}

    public void setCaCheMsgNum(int caCheMsgNum) {
		this.caCheMsgNum = caCheMsgNum;
	}  
}
