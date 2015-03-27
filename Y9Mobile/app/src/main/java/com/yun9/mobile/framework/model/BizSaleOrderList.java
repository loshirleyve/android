package com.yun9.mobile.framework.model;

import java.util.ArrayList;
import java.util.List;

public class BizSaleOrderList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int countnum;
	private List<BizStateObjet> bizlist = new ArrayList<BizStateObjet>();

	public int getCountnum() {
		return countnum;
	}

	public void setCountnum(int countnum) {
		this.countnum = countnum;
	}

	public List<BizStateObjet> getBizlist() {
		return bizlist;
	}

	public void setBizlist(List<BizStateObjet> bizlist) {
		this.bizlist = bizlist;
	}
}
