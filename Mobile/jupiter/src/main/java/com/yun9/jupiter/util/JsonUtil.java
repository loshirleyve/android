package com.yun9.jupiter.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {

	public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {

		Gson gson = new Gson();

		T bean = (T) gson.fromJson(jsonString, beanCalss);

		return bean;

	}

	public static  <T> T jsonElementToBean(JsonElement element,Class<T> beanCalss) {
		Gson gson = new Gson();

		T bean = (T) gson.fromJson(element, beanCalss);

		return bean;
	}
	
	/**
	 * 将String 转为JsonObject
	 * @param json
	 * @return
	 */
	public static JsonObject fromString(String json) {
		JsonParser jp = new JsonParser();
		return (JsonObject) jp.parse(json);
	}

	public static String beanToJson(Object bean) {
		if (AssertValue.isNotNull(bean)) {
			Gson gson = new Gson();

			return gson.toJson(bean);
		} else {
			return null;
		}
	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param beanClass
	 * @return
	 * @throws org.json.JSONException
	 */
	public static <T> List<T> jsonToBeanList(String jsonString,
			Class<T> beanClass) throws JSONException {

		JSONArray jsonArray = new JSONArray(jsonString);

		return jsonToBeanList(jsonArray, beanClass);

	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonArray
	 * @param beanClass
	 * @return
	 * @throws org.json.JSONException
	 */
	public static <T> List<T> jsonToBeanList(JSONArray jsonArray,
			Class<T> beanClass) throws JSONException {

		JSONObject jsonObject;
		T bean;
		int size = jsonArray.length();

		List<T> list = new ArrayList<T>(size);

		Gson gson = new Gson();

		for (int i = 0; i < size; i++) {
			jsonObject = jsonArray.getJSONObject(i);

			bean = gson.fromJson(jsonObject.toString(), beanClass);
			list.add(bean);
		}

		return list;

	}
}
