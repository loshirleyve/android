package com.yun9.jupiter.form.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.yun9.jupiter.form.Form;
import com.yun9.jupiter.util.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/5/28.
 */
public class DetailFormCellBean extends FormCellBean{

    private FormBean formBean;

    private String titlekey;

    private String subtitlekey;

    public FormBean getFormBean() {
        return formBean;
    }

    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }

    public String getTitlekey() {
        return titlekey;
    }

    public void setTitlekey(String titlekey) {
        this.titlekey = titlekey;
    }

    public String getSubtitlekey() {
        return subtitlekey;
    }

    public void setSubtitlekey(String subtitlekey) {
        this.subtitlekey = subtitlekey;
    }


    @Override
    public void buildConfigFromJson(JsonElement element) {
        if (element != null) {
            this.formBean = FormBean.fromJson(element.getAsJsonObject().get("formBean").toString());
        }
    }

    @Override
    public void buildValueFromJson(JsonElement element) {
        if (element != null){
            List<Map<String,String>> mapList = new ArrayList<>();
            JsonArray array = element.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                mapList.add(JsonUtil.jsonElementToBean(array.get(i), HashMap.class));
            }
            this.setValue(mapList);
        }
    }
}
