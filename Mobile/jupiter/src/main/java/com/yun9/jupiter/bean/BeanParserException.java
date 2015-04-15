package com.yun9.jupiter.bean;

import com.yun9.jupiter.exception.JupiterRuntimeException;

public class BeanParserException extends JupiterRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public BeanParserException(String message) {
        super(message);
    }

    public BeanParserException(String message,String code, Throwable cause) {
        super(message, code,cause);
    }

    public BeanParserException(Throwable cause) {
        super(cause);
    }
}
