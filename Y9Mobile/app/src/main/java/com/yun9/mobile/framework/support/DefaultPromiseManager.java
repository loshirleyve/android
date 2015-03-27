package com.yun9.mobile.framework.support;

import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.promise.Deferred;
import com.yun9.mobile.framework.promise.Promise;
import com.yun9.mobile.framework.promise.PromiseManager;

public class DefaultPromiseManager implements PromiseManager, Bean, Injection {

	@Override
	public void injection(BeanContext beanContext) {

	}

	@Override
	public Class<?> getType() {
		return PromiseManager.class;
	}
	
	

	public Deferred defer() {

		Deferred deferred = new Deferred();
		Promise promise = new Promise();
		deferred.setPromise(promise);

		return deferred;
	}

}
