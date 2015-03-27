package com.yun9.mobile.push;


public interface PushDeviceRegisterFactory {
	
	void bind(String appid, String userId, String channelId,
			String requestId,String pushType);
	
	void unBind(String binduserid);

}
