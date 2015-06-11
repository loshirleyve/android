package com.yun9.jupiter.form.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/1.
 * 可供多选的cell，选择数目范围通过mixNum跟maxNum配置；
 * 选择项优先实用optionMap配置(如果存在的话)，否则通过ctrlCode动态查询
 * optionMap使用key:label的形式存取值
 */
public class MultiSelectFormCellBean extends FormCellBean{

    private List<SerialableEntry<String,String>> optionMap;

    private String ctrlCode;

    public List<SerialableEntry<String,String>> getOptionMap() {
        return optionMap;
    }

    public void setOptionMap(List<SerialableEntry<String,String>> optionMap) {
        this.optionMap = optionMap;
    }

    public String getCtrlCode() {
        return ctrlCode;
    }

    public void setCtrlCode(String ctrlCode) {
        this.ctrlCode = ctrlCode;
    }

    @Override
    public void buildValueFromJson(JsonElement element) {
        if (element != null) {
            List<SerialableEntry<String, String>> list = new ArrayList<>();
            JsonArray array = element.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                list.add(JsonUtil.jsonElementToBean(array.get(i),SerialableEntry.class));
            }
            this.setValue(list);
        }
    }
}
