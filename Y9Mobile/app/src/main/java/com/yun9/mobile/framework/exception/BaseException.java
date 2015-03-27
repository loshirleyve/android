package com.yun9.mobile.framework.exception;

public class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code = "100";

	private String reason = "";

	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
		this.reason = message;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
		this.reason = message;
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

	public BaseException code(String code) {
		this.code = code;
		return this;
	}

	public BaseException reason(String reason) {
		this.reason = reason;
		return this;
	}

}
