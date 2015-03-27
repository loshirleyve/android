package com.yun9.mobile.framework.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.msg.impl.ImplComparatorMsg;
import com.yun9.mobile.msg.model.MyMsgCard;

/**
 * 在UserInfoCache上面代理一層 本来可以直接使用UserInfoCache的， 但是消息卡片有个特殊的要求：限制缓存列表个数，所以独立出来
 * 
 * @author yun9
 * 
 */
public class MsgCardCache {

	private static int DEFAULT_CACHENUM = 20;

	private static final String PREFIX_CACHE_KEY = "msgcards_";

	private int cacheNum;

	private static MsgCardCache instance;

	private Map<String, List<MyMsgCard>> memoryCache;
	
	private Set<String> queryGroupSet;

	public static MsgCardCache getInstance() {
		synchronized (MsgCardCache.class) {
			if (instance == null) {
				instance = new MsgCardCache();
			}
		}
		return instance;
	}

	/**
	 * 新增缓存 新的将追加到列表前头
	 * 
	 * @param msgCards
	 */
	public List<MyMsgCard> cacheNewMsgCards(List<MyMsgCard> msgCards,
			String queryGroup) {
		String cacheKey = genCacheKey(queryGroup);
		boolean isExist = containsCache(cacheKey);
		List<MyMsgCard> exists = new ArrayList<MyMsgCard>();
		if (isExist) {
			exists = getCaches(cacheKey);
		}
		if (!AssertValue.isNotNull(msgCards)) {
			return exists;
		}
		if (AssertValue.isNotNull(exists)) {
			msgCards = appendNewMsgCards(msgCards, exists);
		}
		cacheInMemory(cacheKey, msgCards);
		cacheInDisk(cacheKey, msgCards);
		return msgCards;
	}
	
	/**
	 * 缓存在内存中
	 * @param cacheKey
	 * @param msgCards
	 */
	@SuppressWarnings("unchecked")
	public void cacheInMemory(String cacheKey,List<MyMsgCard> msgCards) {
		List<MyMsgCard> exists = this.memoryCache.get(cacheKey);
		if (!AssertValue.isNotNull(exists)) {
			exists = new ArrayList<MyMsgCard>();
		}
		exists.clear();
		exists.addAll(msgCards);
		Collections.sort(exists, new ImplComparatorMsg());
		this.memoryCache.put(cacheKey, exists);
	}
	
	/**
	 * 缓存到磁盘
	 * @param cacheKey
	 * @param msgCards
	 */
	@SuppressWarnings("unchecked")
	public void cacheInDisk(String cacheKey,List<MyMsgCard> msgCards) {
		List<MyMsgCard> temp = new ArrayList<MyMsgCard>();
		if (msgCards.size() > getCacheNum()) {
			temp = msgCards.subList(0, getCacheNum());
		} else {
			temp = msgCards;
		}
		Collections.sort(temp, new ImplComparatorMsg());
		UserInfoCache.getInstance().put(cacheKey, temp);
	}

	/**
	 * 追加更多的消息卡片
	 * 
	 * @param msgCards
	 * @return
	 */
	public List<MyMsgCard> cacheMoreMsgCards(List<MyMsgCard> msgCards,
			String queryGroup) {
		String cacheKey = genCacheKey(queryGroup);
		boolean isExist = containsCache(cacheKey);
		List<MyMsgCard> exists = new ArrayList<MyMsgCard>();
		if (isExist) {
			exists = getCaches(cacheKey);
		}
		if (!AssertValue.isNotNull(msgCards)) {
			return exists;
		}
		if (AssertValue.isNotNull(exists)) {
			msgCards = appendMoreMsgCards(msgCards, exists);
		}
		cacheInMemory(cacheKey, msgCards);
		cacheInDisk(cacheKey, msgCards);
		return msgCards;
	}

	/**
	 * 根据分组标示生成缓存标示
	 * @param queryGroup 分组标示，可以为空
	 * @return 缓存标示
	 */
	private String genCacheKey(String queryGroup) {
		if (AssertValue.isNotNullAndNotEmpty(queryGroup)) {
			this.queryGroupSet.add(PREFIX_CACHE_KEY + queryGroup);
			return PREFIX_CACHE_KEY + queryGroup;
		}
		return PREFIX_CACHE_KEY;
	}

	private List<MyMsgCard> appendMoreMsgCards(List<MyMsgCard> msgCards,
			List<MyMsgCard> exists) {
		List<MyMsgCard> extraList = new ArrayList<MyMsgCard>();
		int i = 0;
		for (MyMsgCard e : exists) {
			MyMsgCard n = getFromNewList(e, msgCards);
			if ( n != null) {
				extraList.add(n);
				i ++;
			} else  {
				extraList.add(e);
			}
		}
		if (msgCards.size() > i) {
			extraList.addAll(msgCards.subList(i, msgCards.size()));
		}
		return extraList;
	}

	private MyMsgCard getFromNewList(MyMsgCard e, List<MyMsgCard> msgCards) {
		for (MyMsgCard msg : msgCards) {
			if (msg.getMain().getId().equals(e.getMain().getId())) {
				return msg;
			}
		}
		return null;
	}

	private List<MyMsgCard> appendNewMsgCards(List<MyMsgCard> msgCards,
			List<MyMsgCard> exists) {
		List<MyMsgCard> extraList = new ArrayList<MyMsgCard>();
		for (MyMsgCard e : exists) {
			if (!isContainInNewList(e, msgCards)) {
				extraList.add(e);
			}
		}
		msgCards.addAll(extraList);
		return msgCards;
	}

	private boolean isContainInNewList(MyMsgCard e, List<MyMsgCard> msgCards) {
		for (MyMsgCard msg : msgCards) {
			if (msg.getMain().getId().equals(e.getMain().getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取指定分组的缓存
	 * @param queryGroup 分组标示，可以为空
	 * @return
	 */
	public List<MyMsgCard> getQueryGroupCaches(String queryGroup) {
		String cacheKey  = genCacheKey(queryGroup);
		return getCaches(cacheKey);
	}
	
	/**
	 * 从内存获取指定cacheKey缓存，如果没有则从磁盘中获取
	 * @param cacheKey
	 * @return
	 */
	public List<MyMsgCard> getCaches(String cacheKey) {
		 List<MyMsgCard> temp = this.memoryCache.get(cacheKey);
		if (AssertValue.isNotNull(temp)
				&& temp.size() > 0) {
			return temp;
		}
		return UserInfoCache.getInstance().getList(cacheKey,MyMsgCard.class);
	}
	
	/**
	 * 检查内存跟磁盘中是否包含指定cacheKey的缓存；
	 * 存在于一方即返回true
	 * @param cacheKey
	 * @return
	 */
	private boolean containsCache(String cacheKey) {
		return this.memoryCache.containsKey(cacheKey) || UserInfoCache.getInstance().contains(cacheKey);
	}
	

	private MsgCardCache() {
		cacheNum = DEFAULT_CACHENUM;
		memoryCache = new HashMap<String, List<MyMsgCard>>();
		queryGroupSet = new HashSet<String>();
	}

	public int getCacheNum() {
		return cacheNum;
	}

	public void setCacheNum(int cacheNum) {
		this.cacheNum = cacheNum;
	}

	public void clean() {
		UserInfoCache.getInstance().remove(PREFIX_CACHE_KEY);
		if (!queryGroupSet.isEmpty()) {
			for (String cacheKey : queryGroupSet) {
				UserInfoCache.getInstance().remove(cacheKey);
			}
		}
		memoryCache.clear();
		queryGroupSet.clear();
	}

}
