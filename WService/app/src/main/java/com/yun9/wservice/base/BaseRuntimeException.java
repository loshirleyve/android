package com.yun9.wservice.base;

public class BaseRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code = "500";

	private String reason = "";

	public BaseRuntimeException() {
	}

    public BaseRuntimeException(Throwable cause) {
        super(cause);
        this.reason = cause.getMessage();
    }

	public BaseRuntimeException(String message) {
		super(message);
		this.reason = message;
	}
    public BaseRuntimeException(String message,String code) {
        super(message);
        this.code = code;
        this.reason = message;
    }

	public BaseRuntimeException(String message,String code, Throwable cause) {
		super(message, cause);
		this.reason = message;
        this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

}
