package com.yun9.jupiter.actvity.exception;

import com.yun9.jupiter.exception.JupiterRuntimeException;

public class JupiterActivityException extends JupiterRuntimeException {
	private static final long serialVersionUID = 1L;
	
	public JupiterActivityException() {
		super();
	}
	
	public JupiterActivityException(String msg) {
		super(msg);
	}
	
	public JupiterActivityException(Throwable ex) {
		super(ex);
	}
	
	public JupiterActivityException(String msg, String code, Throwable ex) {
		super(msg,code,ex);
	}

}
