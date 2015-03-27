package com.yun9.mobile.framework.promise;

import com.yun9.mobile.framework.util.AssertValue;

public class Deferred {
	private Promise promise;

	private ExecuterWrapper currExecuterWrapper;

	public Promise getPromise() {
		return promise;
	}

	public void setPromise(Promise promise) {
		this.promise = promise;
	}

	public void resolve(Object param) {
		this.next();
		if (AssertValue.isNotNull(this.currExecuterWrapper)) {
			this.currExecuterWrapper.getExecuter().onSuccess(param, this);
		}
	}

	public void reject(Object param) {
		this.next();
		if (AssertValue.isNotNull(this.currExecuterWrapper)) {
			this.currExecuterWrapper.getExecuter().onFailed(param, this);
		}
	}

	private void next() {
		if (!AssertValue.isNotNull(currExecuterWrapper)) {
			this.currExecuterWrapper = this.getPromise().getExecuterWrapper();
		} else {
			this.currExecuterWrapper = this.currExecuterWrapper.getNext();
		}
	}

}
