package com.yun9.wservice.bizexecutor;

import android.content.Intent;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.FormUtilFactory.BizExecutor;
import com.yun9.jupiter.form.cell.DocFormCell;
import com.yun9.jupiter.form.cell.ImageFormCell;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.wservice.view.doc.DocCompositeActivity;
import com.yun9.wservice.view.doc.DocCompositeCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 7/6/15.
 */
public class SelectImageBizExecutor implements BizExecutor{

    @Override
    public void execute(CustomCallbackActivity activity, FormCell cell) {

        final ImageFormCell formCell = (ImageFormCell) cell;
        DocCompositeCommand compositeCommand = new DocCompositeCommand();
        activity.addActivityCallback(compositeCommand.getRequestCode(), new CustomCallbackActivity.IActivityCallback() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                if (resultCode == JupiterCommand.RESULT_CODE_OK){
                    List<FileBean> docs = (List<FileBean>) data
                            .getSerializableExtra(DocCompositeCommand.PARAM_IMAGE);
                    if (docs != null){
                        List<String> ids =  new ArrayList<String>();
                        for (FileBean fileBean : docs){
                            ids.add(fileBean.getId());
                        }
                        formCell.restore(ids.toArray(new String[0]));
                    }
                }
            }
        });
        String[] docIds = (String[]) formCell.getValue();
        List<FileBean> selectedDoc = new ArrayList<>();
        for (String id : docIds){
            FileBean fileBean = new FileBean();
            fileBean.setId(id);
            selectedDoc.add(fileBean);
        }
        compositeCommand.setEdit(true);
        compositeCommand.setOnSelectImages(selectedDoc);
        compositeCommand.setCompleteType(DocCompositeCommand.COMPLETE_TYPE_CALLBACK);
        DocCompositeActivity.start(activity, compositeCommand);
    }
}
