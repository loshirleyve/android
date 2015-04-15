package com.yun9.jupiter.bean;

/**
 * Created by Leon on 15/4/15.
 */
public class BeanWrapper {

    private Object bean;

    private String name;

    public Boolean getInjected() {
        return injected;
    }

    public void setInjected(Boolean injected) {
        this.injected = injected;
    }

    private Boolean injected = false;

    public BeanWrapper(Object bean){
        this.bean = bean;
        this.name = bean.getClass().getName();
    }

    public Object getBean(){
        return bean;
    }
}
