package com.yun9.jupiter.bean;

import com.yun9.jupiter.actvity.BaseRuntimeException;

public class BeanInitException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BeanInitException(String message) {
		super(message);
	}

	public BeanInitException(String message,String code, Throwable cause) {
		super(message, code,cause);
	}

	public BeanInitException(Throwable cause) {
		super(cause);
	}
}
