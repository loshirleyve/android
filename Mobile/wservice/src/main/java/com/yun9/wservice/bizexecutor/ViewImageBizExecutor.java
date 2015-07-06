package com.yun9.wservice.bizexecutor;

import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.cell.ImageFormCell;
import com.yun9.jupiter.image.ImageBrowerActivity;
import com.yun9.jupiter.image.ImageBrowerCommand;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.view.CustomCallbackActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/6.
 */
public class ViewImageBizExecutor implements FormUtilFactory.BizExecutor{

    @Override
    public void execute(CustomCallbackActivity activity, FormCell cell) {
        ImageFormCell formCell = (ImageFormCell) cell;
        int position = formCell.getCurrentIndex();
        String[] images = formCell.getImages();
        if (images.length == 0){
            return;
        }
        List<FileBean> fileBeans = new ArrayList<>();
        FileBean fileBean;
        for (String id : images){
            fileBean = new FileBean();
            fileBean.setId(id);
            fileBeans.add(fileBean);
        }
        ImageBrowerCommand command = new ImageBrowerCommand();
        command.setFileBeans(fileBeans);
        command.setPosition(position);
        ImageBrowerActivity.start(activity,command);
    }
}
