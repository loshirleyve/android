package com.yun9.mobile.framework.util;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;

public class ResourceUtil {
	
	/**
	 * 根据指定资源名称，获取资源
	 * @param resName 资源名称
	 * @return 资源Resource
	 */
	public static Resource get(String resName) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		return resourceFactory.create(resName);
	}

}
