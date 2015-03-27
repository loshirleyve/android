package com.yun9.mobile.framework.model;

import java.util.HashMap;
import java.util.Map;

import com.yun9.mobile.framework.util.AssertValue;

public class NavItem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String label;

	private String type;

	private String logoUrl;

	private int drawable;
	
	private String fileid;
	
	private Map<String,Object> params;

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public NavItem(String id, String name, String label, String type,
			String logoUrl, int drawable) {
		this.id = id;
		this.name = name;
		this.label = label;
		this.type = type;
		this.logoUrl = logoUrl;
		this.drawable = drawable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public int getDrawable() {
		return drawable;
	}

	public void setDrawable(int drawable) {
		this.drawable = drawable;
	}
	
	public NavItem param(String key,Object value){
		if (!AssertValue.isNotNull(params)){
			this.params = new HashMap<String, Object>();
		}
		
		this.params.put(key, value);
		return this;
	}

}
