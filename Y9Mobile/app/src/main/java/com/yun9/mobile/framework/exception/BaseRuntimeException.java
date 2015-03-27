package com.yun9.mobile.framework.exception;

public class BaseRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code = "100";

	private String reason = "";

	public BaseRuntimeException() {
	}

	public BaseRuntimeException(String message) {
		super(message);
		this.reason = message;
	}

	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.reason = message;
	}

	public BaseRuntimeException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

	public BaseRuntimeException code(String code) {
		this.code = code;
		return this;
	}

	public BaseRuntimeException reason(String reason) {
		this.reason = reason;
		return this;
	}

}
