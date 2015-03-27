package com.yun9.mobile.framework.exception;

public class SessionManagerException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SessionManagerException(String message) {
		super(message);
	}

	public SessionManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionManagerException(Throwable cause) {
		super(cause);
	}
}
