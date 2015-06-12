package com.yun9.jupiter.form.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 15/5/28.
 */
public class DocFormCellBean extends FormCellBean{

    @Override
    public void buildValueFromJson(JsonElement element) {
        if (element != null) {
            List<String> ids = new ArrayList<>();
            JsonArray array = element.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                ids.add(array.get(i).getAsString());
            }
            this.setValue(ids.toArray(new String[0]));
        }
    }
}
