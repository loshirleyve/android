package com.yun9.wservice.bizexecutor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.wservice.view.common.MultiSelectActivity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/8.
 */
public class MultiSelectBizExcutor implements FormUtilFactory.BizExecutor {

    @Override
    public void execute(Activity activity, int requestCode, Map<String, Object> config) {
        Intent intent = new Intent(activity,MultiSelectActivity.class);
        if (config != null) {
            Iterator<String> iterator = config.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                intent.putExtra(key, (Serializable) config.get(key));
            }
        }
        activity.startActivityForResult(intent,requestCode);
    }
}
