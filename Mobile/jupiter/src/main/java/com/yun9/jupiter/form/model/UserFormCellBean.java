package com.yun9.jupiter.form.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/5/30.
 */
public class UserFormCellBean extends FormCellBean{

    private int mode;

    public class MODE {
       public static final int USER = 1; // 选择用户
       public static final int DEPT = 2; // 选择部门
       public static final int MIX = 3; // 混合
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void add(String userid,String name,String headurl){

    }

    @Override
    public void buildValueFromJson(JsonElement element) {
        if (element != null){
            List<Map<String,String>> mapList = new ArrayList<>();
            JsonArray array = element.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                mapList.add(JsonUtil.jsonElementToBean(array.get(i),HashMap.class));
            }
            this.setValue(mapList);
        }
    }
}
