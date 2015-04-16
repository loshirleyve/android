package com.yun9.jupiter.bean.support;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.BeanWrapper;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.bean.BeanParserException;
import com.yun9.jupiter.bean.BeanInitException;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.mobile.annotation.BeanInject;

public class DefaultBeanManager implements BeanManager,Bean {

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
                objects.put(bean.getType(), new BeanWrapper(bean));
            }else{
                logger.d("对象："+object.getClass().getName()+"，没有实现Bean接口，已经做忽略处理！");
            }
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
        BeanWrapper beanWrapper = (BeanWrapper)this.objects.get(clazz);

        if (beanWrapper == null){
            return null;
        }else{
            return (T) beanWrapper.getBean();
        }
	}

    @Override
    public BeanWrapper getBeanWrapper(Class<?> clazz) {
        BeanWrapper beanWrapper = (BeanWrapper)this.objects.get(clazz);
        return beanWrapper;
    }

    public Context getApplicationContext() {
		return context;
	}

    @Override
    public void initInjected(Object bean) throws IllegalAccessException {

        if (AssertValue.isNotNull(bean)) {
            Field[] fields = bean.getClass().getDeclaredFields();

            if (fields != null && fields.length > 0) {
                for (Field field : fields) {

                    field.setAccessible(true);

                    BeanInject beanInject = field.getAnnotation(BeanInject.class);

                    if (beanInject !=null){
                        Object beanObj = this.get(field.getType());
                            field.set(bean,beanObj);
                    }
                }
            }
        }

    }

    private void loadByConfig() throws IOException, XmlPullParserException {
		InputStream is = this.context.getAssets().open("conf/beans.xml");

		try {
			List<Map<String, String>> beans = this.parser(is);
			// 载入class
			this.builderBeans(beans);
			// 执行注入
			this.injectionBeans();

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
				Object bean =  Class.forName(className).newInstance();
				this.put(bean);
			}
            //加入应用程序
            JupiterApplication appContext = (JupiterApplication) this.context.getApplicationContext();
            this.put(appContext);

            //加入BeanManager
            this.put(this);
		}
	}

	private void injectionBeans() throws IllegalAccessException {
		if (AssertValue.isNotNullAndNotEmpty(this.objects)) {
			for (Map.Entry<Class<?>, Object> entity : this.objects.entrySet()) {
				BeanWrapper beanWrapper = (BeanWrapper) entity.getValue();
                this.injectBean(beanWrapper);
			}
		}
	}

    private void injectBean(BeanWrapper beanWrapper) throws IllegalAccessException {

        Field[] fields = beanWrapper.getBean().getClass().getDeclaredFields();

        if(fields!=null && fields.length>0) {

            for (Field field : fields) {
                field.setAccessible(true);
                BeanInject beanInject = field.getAnnotation(BeanInject.class);
                if (beanInject !=null){
                    BeanWrapper tempBeanWrapper = this.getBeanWrapper(field.getType());

                    if (!tempBeanWrapper.getInjected()){
                        this.injectBean(tempBeanWrapper);
                    }

                    field.set(beanWrapper.getBean(),tempBeanWrapper.getBean());
                }
            }

            if (!beanWrapper.getInjected()){
                //注入完成后执行初始化
                this.initBean(beanWrapper.getBean());
                //设置为已经注入
                beanWrapper.setInjected(true);
            }

        }
    }

    private void initBean(Object bean){
        if (bean instanceof Initialization) {
            Initialization initialization = (Initialization) bean;
            initialization.init(this);
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

    @Override
    public Class<?> getType() {
        return BeanManager.class;
    }
}
