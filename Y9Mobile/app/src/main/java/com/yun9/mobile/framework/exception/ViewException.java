package com.yun9.mobile.framework.exception;

public class ViewException extends BaseRuntimeException {
	private static final long serialVersionUID = 1L;
	private String strMsg = null;

	public ViewException(String strExce) {
		strMsg = strExce;
	}

	public void printStackTrace() {
		if (strMsg != null)
			System.err.println(strMsg);

		super.printStackTrace();
	}
}
