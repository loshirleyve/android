package com.yun9.jupiter.http;

import com.yun9.jupiter.exception.JupiterRuntimeException;

public class HttpException extends JupiterRuntimeException {

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
