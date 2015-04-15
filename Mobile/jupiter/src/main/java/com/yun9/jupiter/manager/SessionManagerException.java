package com.yun9.jupiter.manager;

import com.yun9.jupiter.exception.JupiterRuntimeException;

public class SessionManagerException extends JupiterRuntimeException {

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
