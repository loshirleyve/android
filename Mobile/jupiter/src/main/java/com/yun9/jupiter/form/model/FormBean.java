package com.yun9.jupiter.form.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/5/28.
 */
public class FormBean implements java.io.Serializable{
    private String title;

    private String key;

    private boolean editableWhenLoaded;

    private boolean saveFormWhenGoBack;

    private Map<String,Object> value;

    private List<FormCellBean> cellBeanList;

    public static FormBean getInstance() {
        FormBean form = new FormBean();
        return form;
    }

    public void putCellBean(FormCellBean cellBean) {
        if (cellBeanList == null){
            cellBeanList = new ArrayList<>();
        }
        cellBeanList.add(cellBean);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<FormCellBean> getCellBeanList() {
        return cellBeanList;
    }

    public void setCellBeanList(List<FormCellBean> cellBeanList) {
        this.cellBeanList = cellBeanList;
    }

    public Map<String, Object> getValue() {
        return value;
    }

    public void setValue(Map<String, Object> value) {
        this.value = value;
    }

    public Object getCellBeanValue(String key) {
        return this.value.get(key);
    }

    public boolean isEditableWhenLoaded() {
        return editableWhenLoaded;
    }

    public void setEditableWhenLoaded(boolean editableWhenLoaded) {
        this.editableWhenLoaded = editableWhenLoaded;
    }

    public boolean isSaveFormWhenGoBack() {
        return saveFormWhenGoBack;
    }

    public void setSaveFormWhenGoBack(boolean saveFormWhenGoBack) {
        this.saveFormWhenGoBack = saveFormWhenGoBack;
    }

    public static FormBean fromJson(String json) {
        FormBean formBean = JsonUtil.jsonToBean(json, FormBean.class);
        if (formBean.getCellBeanList() != null) {
            formBean.getCellBeanList().clear(); // 直接上面这样转，类型是错误的
        } else {
            return formBean;
        }
        JsonObject jsonObject = JsonUtil.fromString(json);
        JsonArray cellBeanArray = jsonObject.getAsJsonArray("cellBeanList");
        if (cellBeanArray != null && cellBeanArray.size() > 0) {
            for (int i = 0; i < cellBeanArray.size(); i++) {
                JsonElement element = cellBeanArray.get(i);
                JsonObject object = element.getAsJsonObject();
                Class<? extends FormCellBean> cls = FormUtilFactory.getInstance()
                        .getCellBeanTypeClassByType(object.get("type").getAsString());
                FormCellBean bean = JsonUtil.jsonElementToBean(element, cls);
                bean.buildConfigFromJson(element);
                bean.buildValueFromJson(object.get("value"));
                formBean.getCellBeanList().add(bean);
            }
        }
        return formBean;
    }
}
