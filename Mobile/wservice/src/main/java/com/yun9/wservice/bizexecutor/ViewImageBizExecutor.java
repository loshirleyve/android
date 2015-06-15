package com.yun9.wservice.bizexecutor;

import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.cell.ImageFormCell;

/**
 * Created by huangbinglong on 15/6/6.
 */
public class ViewImageBizExecutor implements FormUtilFactory.BizExecutor{

    @Override
    public void execute(FormActivity activity, FormCell cell) {
        ImageFormCell formCell = (ImageFormCell) cell;
        int position = formCell.getCurrentIndex();
//        String[] images = formCell.getImages();
//        Intent intent = new Intent(activity, SimpleImageActivity.class);
//        intent.putExtra(Constants.IMAGE.IMAGE_POSITION, position);
//        intent.putExtra(Constants.IMAGE.IMAGE_LIST, images);
//        activity.startActivity(intent);
    }
}
