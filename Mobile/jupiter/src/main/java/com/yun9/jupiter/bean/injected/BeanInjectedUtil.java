package com.yun9.jupiter.bean.injected;


import android.content.Context;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.mobile.annotation.BeanInject;

import java.lang.reflect.Field;

/**
 * Created by Leon on 15/4/16.
 */
public class BeanInjectedUtil {
    public static void initInjected(Context context ,Object bean) throws IllegalAccessException {
        JupiterApplication jupiterApplication = (JupiterApplication) context.getApplicationContext();
        BeanManager beanManager =jupiterApplication.getBeanManager();

        if (AssertValue.isNotNull(bean)) {
            Field[] fields = bean.getClass().getDeclaredFields();

            if (fields != null && fields.length > 0) {
                for (Field field : fields) {

                    field.setAccessible(true);

                    BeanInject beanInject = field.getAnnotation(BeanInject.class);

                    if (beanInject !=null){
                        Object beanObj = beanManager.get(field.getType());
                        field.set(bean,beanObj);
                    }
                }
            }
        }

    }
}
