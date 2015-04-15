package com.yun9.jupiter.repository;

import com.yun9.jupiter.exception.JupiterRuntimeException;

public class ResourceNotFoundException extends JupiterRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message,String code, Throwable cause) {
		super(message,code, cause);
	}

	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}
}
