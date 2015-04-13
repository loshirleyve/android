package com.yun9.jupiter.repository;

import com.yun9.jupiter.actvity.BaseRuntimeException;

public class ResourceNotFoundException extends BaseRuntimeException {

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
