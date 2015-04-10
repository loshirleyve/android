package com.yun9.wservice.repository;

import com.yun9.wservice.base.BaseRuntimeException;

public class RepositoryException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RepositoryException(String message) {
		super(message);
	}

	public RepositoryException(String message,String code, Throwable cause) {
		super(message,code, cause);
	}

	public RepositoryException(Throwable cause) {
		super(cause);
	}
}
