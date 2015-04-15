package com.yun9.jupiter.conf;

import com.yun9.jupiter.exception.JupiterRuntimeException;

public class PropertiesParserException extends JupiterRuntimeException {
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
