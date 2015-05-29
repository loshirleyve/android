package com.yun9.jupiter.form.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/5/28.
 */
public class FormBean implements java.io.Serializable{
    private String title;

    private String key;

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
}
