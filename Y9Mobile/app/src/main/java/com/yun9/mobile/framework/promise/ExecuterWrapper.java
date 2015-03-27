package com.yun9.mobile.framework.promise;

import com.yun9.mobile.framework.util.AssertValue;

public class ExecuterWrapper {
	private Executer executer;

	private String state;

	private ExecuterWrapper next;

	public Executer getExecuter() {
		return executer;
	}

	public void setExecuter(Executer executer) {
		this.executer = executer;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ExecuterWrapper getNext() {
		return next;
	}

	public void setNext(ExecuterWrapper next) {
		this.next = next;
	}

	public ExecuterWrapper last() {

		ExecuterWrapper last = this;
		ExecuterWrapper next = last.next;

		while (AssertValue.isNotNull(next)) {

			if (!AssertValue.isNotNull(next.getNext())) {
				last = next;
				break;
			} else {
				next = next.getNext();
			}
		}

		return last;
	}

}
