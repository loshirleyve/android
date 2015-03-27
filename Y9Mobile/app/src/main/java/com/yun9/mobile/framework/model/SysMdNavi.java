package com.yun9.mobile.framework.model;


/**
 * 对象功能:cmd_navigation Model对象 创建时间:2013-09-16 17:33:34
 */
public class SysMdNavi implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String type;
	private String parentid;
	private String device;
	private String actiontype;
	private String url;
	private String icopath;
	private long sort;
	private String mainurl;
	private String no;
	private String name;
	private String actionparams;
	public SysMdNavi(String id,String name,String icopath,String actionparams)
	{
		this.id=id;
		this.name=name;
		this.icopath=icopath;
		this.actionparams=actionparams;
	}
	

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcopath() {
		return icopath;
	}

	public void setIcopath(String icopath) {
		this.icopath = icopath;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

	public String getMainurl() {
		return mainurl;
	}

	public void setMainurl(String mainurl) {
		this.mainurl = mainurl;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getActionparams() {
		return actionparams;
	}

	public void setActionparams(String actionparams) {
		this.actionparams = actionparams;
	}
	
	

}