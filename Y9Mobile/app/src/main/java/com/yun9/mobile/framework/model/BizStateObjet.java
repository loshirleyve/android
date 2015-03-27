package com.yun9.mobile.framework.model;

public class BizStateObjet implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String state;
	private int numx;
	private String codename;

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getNumx() {
		return numx;
	}

	public void setNumx(int numx) {
		this.numx = numx;
	}

}
