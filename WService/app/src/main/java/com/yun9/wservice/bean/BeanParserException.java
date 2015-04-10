package com.yun9.wservice.bean;

import com.yun9.wservice.base.BaseRuntimeException;

public class BeanParserException extends BaseRuntimeException {
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
