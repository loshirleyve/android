package com.yun9.mobile.msg.cache;

import java.io.File;
import java.util.List;

import com.yun9.mobile.MainApplication;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.conf.PropertiesFactory;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.msg.model.MyMsgCard;

import android.content.Context;

/**
 * 
 * 目前只能简单的将数据缓存和获取缓存数据
 * 
 * 
 * @author lhk
 *
 * @param <T>
 */
public final class MsgCacheManager<T> {
	
	/**
	 * 缓存的消息数量
	 */
	public static int CACHENUM_DEFAULT = 20;
	private int caCheNum = CACHENUM_DEFAULT;
	private int maxCacheNum = CACHENUM_DEFAULT;
	
	/**
	 * 缓存文件路径
	 */
	private final String  FILENAME_KEY = "app.config.msg.cache.filename";
	private String FILENAME_DEFAULT = "msgCache";
	private String cacheFilePath;
	private Context context;
	private String userId;
	private MsgLevel2Cache<MyMsgCard> msgCache;

	
	private static MsgCacheManager<?> instance = null;
	
	private MsgCacheManager(Context context){
		this.context = context;
		init();
	}
	
	private void init() {
		SessionManager sessionManager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
        userId = sessionManager.getAuthInfo().getUserinfo().getId();
		PropertiesFactory propertiesFactory = BeanConfig.getInstance().getBeanContext().get(PropertiesFactory.class);
		String cacheFileName = propertiesFactory.getString(FILENAME_KEY, FILENAME_DEFAULT);
		cacheFilePath = context.getCacheDir().getPath() + File.separatorChar + userId + File.separatorChar + cacheFileName;
		
		msgCache = new MsgLevel2Cache<MyMsgCard>(cacheFilePath, caCheNum);
	}

	public static MsgCacheManager<?> getInstance(Context context){
		 if(instance == null)
	        {
	            synchronized(MsgCacheManager.class)
	            {
	                if(instance == null)
	                	instance = new MsgCacheManager(context);
	            }
	        }
	        return instance;
	}
	
	
	
	/**
	 * 销毁
	 */
	public static void destoryMsgCacheManager(){
		
		// TODU 多线程的并发安全性没做处理
		
		instance = null;
	}

	public Boolean setMsgCache(List<T> data){
		return set2LevelCacheData(data);
		
	}
	public List<T> getMsgCache(){
		return get2LevelCacheData();
	}
	
	public Boolean clearMsgCache(){
		return clear2LevelCacheData();
	}
	
	/**
	 * 设置2级消息缓存
	 * @param data
	 * @return
	 */
	private Boolean set2LevelCacheData(List<T> data) {
		return msgCache.setCacheData(data);
	}

	/**
	 * 获取2级消息缓存
	 * @return
	 */
	private List<T> get2LevelCacheData() {
		Object data = msgCache.getCacheData();
		if(data instanceof List){
			return (List<T>)data;
		}
		return null;
	}

	/**
	 * 清除2级缓存
	 * @return
	 */
	private Boolean clear2LevelCacheData() {
		return msgCache.clearCacheData();
	}

	/**
	 * 最大缓存数量
	 * @return
	 */
	public int getMaxCacheNum() {
		return maxCacheNum;
	}

}
