package com.yun9.jupiter.form.model;

import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/1.
 * 可供多选的cell，选择数目范围通过mixNum跟maxNum配置；
 * 选择项优先实用optionMap配置(如果存在的话)，否则通过ctrlCode动态查询
 * optionMap使用key:label的形式存取值
 */
public class MultiSelectFormCellBean extends FormCellBean{

    private int minNum;

    private int maxNum;

    private Map<String,String> optionMap;

    private String ctrlCode;

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public Map<String, String> getOptionMap() {
        return optionMap;
    }

    public void setOptionMap(Map<String, String> optionMap) {
        this.optionMap = optionMap;
    }

    public String getCtrlCode() {
        return ctrlCode;
    }

    public void setCtrlCode(String ctrlCode) {
        this.ctrlCode = ctrlCode;
    }
}
