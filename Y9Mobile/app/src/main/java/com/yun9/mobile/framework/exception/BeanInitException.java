package com.yun9.mobile.framework.exception;

public class BeanInitException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BeanInitException(String message) {
		super(message);
	}

	public BeanInitException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanInitException(Throwable cause) {
		super(cause);
	}
}
