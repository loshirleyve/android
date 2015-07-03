package com.yun9.jupiter.model;

import com.yun9.jupiter.util.AssertValue;

import java.util.List;

/**
 * Created by huangbinglong on 7/3/15.
 */
public class CacheCtrlcode implements java.io.Serializable{
    private String defno;

    private List<CacheCtrlcodeItem> items;

    public CacheCtrlcode(String defno, List<CacheCtrlcodeItem> items) {
        this.defno = defno;
        this.items = items;
    }

    public CacheCtrlcodeItem getItem(String no){
        if (AssertValue.isNotNullAndNotEmpty(items)
                && AssertValue.isNotNullAndNotEmpty(no)){
            for (CacheCtrlcodeItem item : items){
                if (item.getNo().equals(no)){
                    return  item;
                }
            }
        }
        return null;

    }

    public String getDefno() {
        return defno;
    }

    public void setDefno(String defno) {
        this.defno = defno;
    }

    public List<CacheCtrlcodeItem> getItems() {
        return items;
    }

    public void setItems(List<CacheCtrlcodeItem> items) {
        this.items = items;
    }
}
