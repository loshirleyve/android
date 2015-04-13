package com.yun9.jupiter.manager;

import com.yun9.jupiter.actvity.BaseRuntimeException;

public class SessionManagerException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SessionManagerException(String message) {
		super(message);
	}

	public SessionManagerException(String message,String code, Throwable cause) {
		super(message,code, cause);
	}

	public SessionManagerException(Throwable cause) {
		super(cause);
	}
}
