package com.yun9.jupiter.http;

import com.yun9.jupiter.actvity.BaseRuntimeException;

public class HttpException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HttpException(String message) {
		super(message);
	}

	public HttpException(String message,String code, Throwable cause) {
		super(message,code, cause);
	}

	public HttpException(Throwable cause) {
		super(cause);
	}
}
