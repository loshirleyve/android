package com.yun9.jupiter.bean.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.bean.Injection;
import com.yun9.jupiter.bean.BeanParserException;
import com.yun9.jupiter.bean.BeanInitException;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;

public class DefaultBeanManager implements BeanManager {

	private Map<Class<?>, Object> objects;

	private Context context;

	private static final Logger logger = Logger
			.getLogger(DefaultBeanManager.class);

	public DefaultBeanManager(Context context) {
		this.context = context;
		this.init();
	}

	// 解析配置文件载入bean
	private void init() {
		try {
			loadByConfig();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BeanParserException(e);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw new BeanParserException(e);
		}
	}

	public void put(Object object) {
		if (AssertValue.isNotNull(object)) {
			if (!AssertValue.isNotNull(objects)) {
				objects = new HashMap<Class<?>, Object>();
			}
			// 检查对象是否实现了Bean
			if (object instanceof Bean) {
				Bean bean = (Bean) object;
				objects.put(bean.getType(), object);
			} else {
				objects.put(object.getClass(), object);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
		return (T) this.objects.get(clazz);
	}

	public Context getApplicationContext() {
		return context;
	}

	private void loadByConfig() throws IOException, XmlPullParserException {
		InputStream is = this.context.getAssets().open("conf/beans.xml");

		try {
			List<Map<String, String>> beans = this.parser(is);
			// 载入class
			this.builderBeans(beans);
			// 执行注入
			this.injectionBeans();
			// 执行初始化
			this.initBean();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new BeanInitException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new BeanInitException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new BeanInitException(
                    e);
		} finally {
			if (AssertValue.isNotNull(is)) {
				is.close();
			}
		}
	}

	private void builderBeans(List<Map<String, String>> beans)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		if (AssertValue.isNotNullAndNotEmpty(beans)) {
			for (Map<String, String> beanInfo : beans) {
				String className = beanInfo.get("class");
				Object bean = Class.forName(className).newInstance();
				this.put(bean);
			}
		}
	}

	private void injectionBeans() {
		if (AssertValue.isNotNullAndNotEmpty(this.objects)) {
			for (Map.Entry<Class<?>, Object> entity : this.objects.entrySet()) {
				Object tempBean = entity.getValue();

				if (tempBean instanceof Injection) {
					Injection injection = (Injection) tempBean;
					injection.injection(this);
				}
			}
		}
	}

	private void initBean() {
		if (AssertValue.isNotNullAndNotEmpty(this.objects)) {
			for (Map.Entry<Class<?>, Object> entity : this.objects.entrySet()) {
				Object tempBean = entity.getValue();

				if (tempBean instanceof Initialization) {
					Initialization initialization = (Initialization) tempBean;
					initialization.init(this);
				}
			}
		}
	}

	private List<Map<String, String>> parser(InputStream is)
			throws IOException, XmlPullParserException {

		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "UTF-8");

		int eventType = parser.getEventType();
		List<Map<String, String>> beans = new ArrayList<Map<String, String>>();

		Map<String, String> beanInfo = null;

		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:// 判断当前事件是否是文档开始事件
				break;
			case XmlPullParser.START_TAG:// 判断当前事件是否是标签元素开始事件
				if ("bean".equals(parser.getName())) {// 判断开始标签元素是否是book
					String className = parser.getAttributeValue(0);
					logger.d("config bean :" + className);

					if (AssertValue.isNotNullAndNotEmpty(className)) {
						className = className.trim();

						beanInfo = new HashMap<String, String>();
						beanInfo.put("class", className);
						beans.add(beanInfo);
					}
				}
				break;
			case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
				if ("bean".equals(parser.getName())) {
					beanInfo = null;
				}
				break;
			}
			eventType = parser.next();// 进入下一个元素并触发相应事件
		}

		return beans;
	}
}
