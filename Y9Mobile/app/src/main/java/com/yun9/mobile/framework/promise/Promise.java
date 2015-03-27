package com.yun9.mobile.framework.promise;

import com.yun9.mobile.framework.util.AssertArgument;
import com.yun9.mobile.framework.util.AssertValue;

public class Promise {

	private ExecuterWrapper executerWrapper;

	public Promise then(Executer executer) {
		AssertArgument.isNotNull(executer, "executer");

		ExecuterWrapper currExecuterWrapper = new ExecuterWrapper();
		currExecuterWrapper.setExecuter(executer);

		if (AssertValue.isNotNull(executerWrapper)) {
			executerWrapper.last().setNext(currExecuterWrapper);
		} else {
			executerWrapper = currExecuterWrapper;
		}

		return this;
	}

	public ExecuterWrapper getExecuterWrapper() {
		return executerWrapper;
	}

	public void setExecuterWrapper(ExecuterWrapper executerWrapper) {
		this.executerWrapper = executerWrapper;
	}

}
