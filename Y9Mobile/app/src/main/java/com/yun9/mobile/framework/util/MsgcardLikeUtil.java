package com.yun9.mobile.framework.util;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.resources.Resource;

public class MsgcardLikeUtil {
	
	public static void like(String msgcardid,AsyncHttpResponseCallback callback) {
		Resource resource = ResourceUtil.get("SysMsgCardPraiseLikeService");
		resource.param("msgcardid", msgcardid);
		resource.invok(callback);
	}
	
	public static void unLike(String msgcardid,AsyncHttpResponseCallback callback) {
		Resource resource = ResourceUtil.get("SysMsgCardPraiseUnLikeService");
		resource.param("msgcardid", msgcardid);
		resource.invok(callback);
	}

}
