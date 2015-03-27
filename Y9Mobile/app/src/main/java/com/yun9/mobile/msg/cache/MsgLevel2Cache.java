package com.yun9.mobile.msg.cache;

import java.util.List;

public class MsgLevel2Cache<T> implements Level2Cache{


	
	/**
	 * 缓存的消息数量
	 */
	private int caCheNum;
	
	
	/**
	 * 缓存文件路径
	 */
	private String cacheFilePath;
	
	
	private MsgCacheQueue<T> msgCacheQueue;
	
	
	/**
	 * 
	 */
	public MsgLevel2Cache(String cacheFilePath, int caCheNum) {
		super();
		
		this.cacheFilePath = cacheFilePath;
		this.caCheNum = caCheNum;
		
		init();
	}

	private void init() {
		msgCacheQueue = new MsgCacheQueue<T>(cacheFilePath, caCheNum);	
	}

	@Override
	public Boolean setCacheData(Object data) {
		
		// TODU 类型检查 
		if(!(data instanceof List))
		{
			return null;
		}
		
		return doSetCacheData((List<T>)data);
	}
	
	private Boolean doSetCacheData(List<T> msgList){
		
		return msgCacheQueue.setMsgCache(msgList);
	}
	

	@Override
	public Object getCacheData() {
		return msgCacheQueue.getMsgCacheData();
	}

	
	@Override
	public Boolean clearCacheData() {
		return doClearCacheData();
	}

	private Boolean doClearCacheData(){
		
		return null;
	}
}
