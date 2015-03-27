package com.yun9.mobile.msg.action.entity;

import java.io.Serializable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yun9.mobile.framework.util.JsonUtil;

public class JsonParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private JsonObject element;
	
	public JsonParam(JsonObject element) {
		this.element = element;
	}
	
	public JsonParam(String json) {
		this.element = JsonUtil.fromString(json);
	}

	public JsonObject getElement() {
		return element;
	}

	public void setElement(JsonObject element) {
		this.element = element;
	}
	
	/*下面就是之类继承它的原因*/
	public String getString() {
		return this.element.toString();
	}
	
	public JsonElement get(String name) {
		return this.element.get(name);
	}
	
	public String getString(String name) {
		JsonElement ele = this.get(name);
		if (ele == null) {
			return null;
		}
		return ele.getAsString();
	}
	
	public long getLong(String name) {
		return this.get(name).getAsLong();
	}
	
	public int getInt(String name) {
		return this.get(name).getAsInt();
	}
	
	public JsonObject getJsonObject(String name) {
		return this.get(name).getAsJsonObject();
	}
	
	public JsonArray getJsonArray(String name) {
		return this.get(name).getAsJsonArray();
	}
	
	public void put(String name,JsonElement value) {
		this.element.add(name, value);
	}
	
	public void putString(String name,String value) {
		this.element.addProperty(name, value);
	}
	
	public boolean isContain(String name) {
		return this.get(name) != null;
	}
}
