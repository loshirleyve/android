package com.yun9.wservice.conf;

import com.yun9.wservice.base.BaseRuntimeException;

public class PropertiesParserException extends BaseRuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public PropertiesParserException(String message) {
        super(message);
    }

    public PropertiesParserException(String message, String code, Throwable cause) {
        super(message, code,cause);
    }

    public PropertiesParserException(Throwable cause) {
        super(cause);
    }
}
