package com.yun9.mobile.framework.promise;

public interface Executer {
	public void onSuccess(Object param, final Deferred d);

	public void onFailed(Object param, final Deferred d);
}
