package com.yun9.jupiter.form;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yun9.jupiter.exception.JupiterRuntimeException;
import com.yun9.jupiter.form.cell.DetailFormCell;
import com.yun9.jupiter.form.cell.DocFormCell;
import com.yun9.jupiter.form.cell.ImageFormCell;
import com.yun9.jupiter.form.cell.MultiSelectFormCell;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.form.cell.UserFormCell;
import com.yun9.jupiter.form.model.DetailFormCellBean;
import com.yun9.jupiter.form.model.DocFormCellBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.ImageFormCellBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.form.model.TextFormCellBean;
import com.yun9.jupiter.form.model.UserFormCellBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.CustomCallbackActivity;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/3.
 */
public class FormUtilFactory {

    private static FormUtilFactory singleton;

    private Map<String,Class<? extends FormCell>> cellClassMap;

    private Map<String,Class<? extends FormCellBean>> cellBeanClassMap;

    private Map<String,LoadValueHandler> loadValueHandlerMap;

    private Map<String,BizExecutor> bizExecutorMap;

    public static FormUtilFactory getInstance(){
        synchronized (FormUtilFactory.class){
            if (singleton == null){
                singleton = new FormUtilFactory();
            }
        }
        return singleton;
    }


    public static <T extends FormCell> T createCell(Class<T> type,FormCellBean bean) {
        try {
            FormCell formCell = type.getConstructor(FormCellBean.class).newInstance(bean);
            formCell.init();
            return (T) formCell;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new JupiterRuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new JupiterRuntimeException(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new JupiterRuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new JupiterRuntimeException(e);
        }
    }

    private FormUtilFactory() {
        cellClassMap = new HashMap<>();
        cellBeanClassMap = new HashMap<>();
        loadValueHandlerMap = new HashMap<>();
        bizExecutorMap = new HashMap<>();
        initCellType();
        initCellBeanType();
    }

    private void initCellType() {
        registerCellType("TextFormCell", TextFormCell.class);
        registerCellType("DocFormCell", DocFormCell.class);
        registerCellType("ImageFormCell", ImageFormCell.class);
        registerCellType("MultiSelectFormCell", MultiSelectFormCell.class);
        registerCellType("UserFormCell", UserFormCell.class);
        registerCellType("DetailFormCell", DetailFormCell.class);
    }

    private void initCellBeanType() {
        registerCellBeanType("TextFormCell", TextFormCellBean.class);
        registerCellBeanType("DocFormCell", DocFormCellBean.class);
        registerCellBeanType("ImageFormCell", ImageFormCellBean.class);
        registerCellBeanType("MultiSelectFormCell", MultiSelectFormCellBean.class);
        registerCellBeanType("UserFormCell", UserFormCellBean.class);
        registerCellBeanType("DetailFormCell", DetailFormCellBean.class);
    }

    public void registerCellType(String name,Class<? extends FormCell> cellClass) {
        if (AssertValue.isNotNullAndNotEmpty(name)
                && cellClass != null) {
            cellClassMap.put(name,cellClass);
        }
    }

    public void registerCellBeanType(String name,Class<? extends FormCellBean> cellClass) {
        if (AssertValue.isNotNullAndNotEmpty(name)
                && cellClass != null) {
            cellBeanClassMap.put(name,cellClass);
        }
    }

    /**
     * 根据类型标示获取cell的Class对象
     * @param type 类型标识，字符串
     * @return
     */
    public Class<? extends FormCell> getCellTypeClassByType(String type) {
        return cellClassMap.get(type);
    }

    public Class<? extends FormCellBean> getCellBeanTypeClassByType(String type) {
        return cellBeanClassMap.get(type);
    }

    /**
     * 注册取值器
     * @param type 取值器类型，请使用LoadValueHandler.Type_*
     * @param handler 自定义的取值处理器
     */
    public void registerLoadValueHandler(String type,LoadValueHandler handler) {
        if (AssertValue.isNotNullAndNotEmpty(type)
                && handler != null) {
            loadValueHandlerMap.put(type,handler);
        }
    }

    public LoadValueHandler getLoadValueHandler(String type) {
        return loadValueHandlerMap.get(type);
    }

    public interface LoadValueHandler{
        public static final String TYPE_USER = "user";
        public static final String TYPE_ORG = "org";
        public static final String TYPE_FILE = "file";
        public void load(String id,LoadValueCompleted callback);
    }

    public interface LoadValueCompleted{
        public void callback(Object data);
    }

    public void registerBizExecutor(String type,BizExecutor executor) {
        if (AssertValue.isNotNullAndNotEmpty(type)
                && executor != null) {
            bizExecutorMap.put(type,executor);
        }
    }

    public BizExecutor getBizExcutor(String type) {
        return bizExecutorMap.get(type);
    }

    public interface BizExecutor{
        public static final String TYPE_SELECT_USER_OR_DEPT = "selectUserOrDept";
        public static final String TYPE_SELECT_DOC = "selectDoc";
        public static final String TYPE_SELECT_IMAGE = "selectImage";
        public static final String TYPE_VIEW_IMAGE = "viewImage";
        public static final String TYPE_MULTI_SELECT = "multiSelect";
        public void execute(CustomCallbackActivity activity,FormCell cell);
    }

}
