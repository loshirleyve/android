package com.yun9.wservice.bizexecutor;

import android.content.Context;
import android.content.Intent;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.wservice.view.common.Constants;
import com.yun9.wservice.view.common.SimpleImageActivity;

import java.util.Map;

/**
 * Created by huangbinglong on 15/6/6.
 */
public class ViewImageBizExecutor implements FormUtilFactory.BizExecutor{

    @Override
    public void execute(Context context, Map<String, Object> config, FormUtilFactory.BizExecuteCompleted callback) {
        Object tmp = config.get("position");
        int position = 0;
        if (tmp != null) {
            position = (int) tmp;
        }
        tmp = config.get("images");
        String[] images = null;
        if (tmp != null) {
            images = (String[]) tmp;
        } else {
            throw new RuntimeException("缺少必要参数：images");
        }
        Intent intent = new Intent(context, SimpleImageActivity.class);
        intent.putExtra(Constants.IMAGE.IMAGE_POSITION, position);
        intent.putExtra(Constants.IMAGE.IMAGE_LIST, images);
        context.startActivity(intent);
    }
}
