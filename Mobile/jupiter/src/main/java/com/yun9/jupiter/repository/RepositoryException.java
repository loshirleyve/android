package com.yun9.jupiter.repository;

import com.yun9.jupiter.exception.JupiterRuntimeException;

public class RepositoryException extends JupiterRuntimeException {

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
