package com.yun9.jupiter.conf.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.bean.Injection;
import com.yun9.jupiter.conf.PropertiesManager;
import com.yun9.jupiter.conf.PropertiesParserException;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;

public class DefaultPropertiesManager implements PropertiesManager, Bean,
        Initialization, Injection {
	private static final Logger logger = Logger
			.getLogger(DefaultPropertiesManager.class);

	private Properties properties = null;

	private BeanManager beanManager;

	@Override
	public Class<?> getType() {
		return PropertiesManager.class;
	}

	private Properties load() {
		properties = new Properties();

		InputStream is = null;
		try {
			is = this.beanManager.getApplicationContext().getResources()
					.getAssets().open("conf/app-config.xml");
			this.parse(is, properties);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw new PropertiesParserException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PropertiesParserException(e);
		} finally {
			if (AssertValue.isNotNull(is)) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

	public Object get(String key) {
		return this.get(key, null);
	}

	public Object get(String key, Object defaultValue) {

		if (!AssertValue.isNotNull(this.properties)) {
			this.load();
		}

		Object value = properties.get(key);

		if (!AssertValue.isNotNull(value)) {
			return defaultValue;
		} else {
			return value;
		}
	}

	private void parse(InputStream is, Properties p)
			throws XmlPullParserException, IOException {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "UTF-8");

		int eventType = parser.getEventType();

		String key = null;
		String value = null;

		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:// 判断当前事件是否是文档开始事件
				break;
			case XmlPullParser.START_TAG:// 判断当前事件是否是标签元素开始事件
				if ("config".equals(parser.getName())) {
					String name = parser.getAttributeValue(0);

					if (AssertValue.isNotNullAndNotEmpty(name)) {
						key = name.trim();
					}
					value = parser.nextText();
					if (AssertValue.isNotNullAndNotEmpty(value)) {
						value = value.trim();
					}

					if (AssertValue.isNotNullAndNotEmpty(key)
							&& AssertValue.isNotNullAndNotEmpty(value)) {
						logger.d("put properties name:" + name + ",value:"
								+ value);
						p.put(key, value);
					}
				}
				key = null;
				value = null;
				break;
			case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
				break;
			}
			eventType = parser.next();// 进入下一个元素并触发相应事件
		}

	}

	@Override
	public void init(BeanManager beanManager) {
		logger.d("Properties init.");
		this.load();
	}

	@Override
	public void injection(BeanManager beanManager) {
		this.beanManager = beanManager;
	}

	@Override
	public String getString(String key) {
		return this.getString(key, null);
	}

	@Override
	public String getString(String key, String defaultValue) {
		Object obj = this.get(key, defaultValue);

		if (AssertValue.isNotNull(obj)) {
			return obj.toString();
		} else {
			return null;
		}
	}

	@Override
	public int getInt(String key) {
		return this.getInt(key, -1);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		Object obj = this.get(key, defaultValue);

		if (AssertValue.isNotNull(obj)) {
			return Integer.parseInt(obj.toString());

		} else {
			return -1;
		}
	}
}
