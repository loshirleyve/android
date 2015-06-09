package com.yun9.jupiter.navigation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.thoughtworks.xstream.XStream;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.util.AssertValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/6/6.
 */
public class DefaultNavigationManager implements NavigationManager, Bean, Initialization {

    private List<NavigationHandler> navigationHandlers;

    private Map<String, FuncEnterHandler> funcEnterHandlers;

    private BeanManager beanManager;

    @Override
    public void navigation(Activity activity,Bundle bundle, NavigationBean navigationBean) {

        //首先查找功能入口，如果功能入口不存在则查找公共处理器
        FuncEnterHandler funcEnterHandler = this.findFuncEnterHandler(navigationBean);

        if (AssertValue.isNotNull(funcEnterHandler)){
            funcEnterHandler.enter(activity,bundle,navigationBean);
        }else{
            NavigationHandler handler = this.findHandler(navigationBean);

            if (AssertValue.isNotNull(handler)) {
                handler.navigation(activity, navigationBean);
            } else {
                Toast.makeText(activity, "没有找到导航功能！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void regHandler(NavigationHandler navigationHandler) {
        if (!AssertValue.isNotNull(navigationHandlers)) {
            navigationHandlers = new ArrayList<>();
        }

        if (AssertValue.isNotNull(navigationHandler)) {
            navigationHandlers.add(navigationHandler);
        }
    }

    public void regEnter(String no,FuncEnterHandler funcEnterHandler){
        if (!AssertValue.isNotNull(funcEnterHandlers)){
            this.funcEnterHandlers = new HashMap<>();
        }

        this.funcEnterHandlers.put(no,funcEnterHandler);
    }

    @Override
    public Class<?> getType() {
        return NavigationManager.class;
    }

    @Override
    public void init(BeanManager beanManager) {
        this.beanManager = beanManager;
        try {
            this.loadConfig(beanManager.getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private NavigationHandler findHandler(NavigationBean navigationBean) {

        NavigationHandler navigationHandler = null;

        if(AssertValue.isNotNullAndNotEmpty(this.navigationHandlers)) {
            for (NavigationHandler handler : this.navigationHandlers) {
                if (handler.support(navigationBean)) {
                    navigationHandler = handler;
                    break;
                }
            }
        }
        return navigationHandler;
    }

    private FuncEnterHandler findFuncEnterHandler(NavigationBean navigationBean){
        if (AssertValue.isNotNullAndNotEmpty(this.funcEnterHandlers) && AssertValue.isNotNull(navigationBean)){
            return this.funcEnterHandlers.get(navigationBean.getNo());
        }else{
            return null;
        }
    }

    private void loadConfig(Context context) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        InputStream is = context.getAssets().open("conf/navigation.xml");

        XStream xstream1 = new XStream();
        xstream1.alias("navigation", NavigationConfig.class);
        xstream1.alias("enters", List.class);
        xstream1.alias("enter", NavigationEnterConfig.class);
        xstream1.alias("handlers", List.class);
        xstream1.alias("handler", NavigationHandlerConfig.class);
        NavigationConfig config = (NavigationConfig) xstream1
                .fromXML(is);


        if (AssertValue.isNotNull(config)) {
            this.builderEnter(config);
            this.builderHandler(config);
        }

    }

    private void builderEnter(NavigationConfig navigationConfig) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (AssertValue.isNotNullAndNotEmpty(navigationConfig.getEnters())){
            for(NavigationEnterConfig config:navigationConfig.getEnters()){
                if (AssertValue.isNotNull(config) && AssertValue.isNotNullAndNotEmpty(config.getClazz()) && AssertValue.isNotNullAndNotEmpty(config.getName())){
                    FuncEnterHandler entityHandler = (FuncEnterHandler) Class.forName(config.getClazz()).newInstance();

                    if (entityHandler instanceof Initialization){
                        ((Initialization) entityHandler).init(beanManager);
                    }
                    this.regEnter(config.getName(),entityHandler);
                }
            }

        }
    }

    private void builderHandler(NavigationConfig navigationConfig) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (AssertValue.isNotNullAndNotEmpty(navigationConfig.getHandlers())){
            for(NavigationHandlerConfig config:navigationConfig.getHandlers()){

                if (AssertValue.isNotNull(config) && AssertValue.isNotNullAndNotEmpty(config.getClazz())){
                    NavigationHandler handler = (NavigationHandler) Class.forName(config.getClazz()).newInstance();

                    if (handler instanceof Initialization){
                        ((Initialization)handler).init(beanManager);
                    }
                    this.regHandler(handler);
                }
            }
        }
    }
}
