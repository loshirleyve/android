package com.yun9.wservice.bizexecutor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.cell.MultiSelectFormCell;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.wservice.view.common.MultiSelectActivity;
import com.yun9.wservice.view.common.MultiSelectCommand;
import com.yun9.wservice.view.org.OrgCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeCommand;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/8.
 */
public class MultiSelectBizExcutor implements FormUtilFactory.BizExecutor {

    @Override
    public void execute(final CustomCallbackActivity activity, final FormCell cell) {
        Intent intent = new Intent(activity,MultiSelectActivity.class);
        final MultiSelectFormCell formCell = (MultiSelectFormCell) cell;
        MultiSelectFormCellBean cellBean = (MultiSelectFormCellBean) cell.getFormCellBean();
        MultiSelectCommand command = new MultiSelectCommand();
        command.setIsCancelable(true);
        command.setCtrlCode(cellBean.getCtrlCode());
        command.setOptions(cellBean.getOptionMap());
        command.setSelectedList((List<SerialableEntry<String, String>>) cell.getValue());
        command.setMinNum(cellBean.getMinNum());
        command.setMaxNum(cellBean.getMaxNum());
        intent.putExtra(MultiSelectCommand.PARAM_COMMAND,command);
        int requestCode = activity.addActivityCallback(new FormActivity.IActivityCallback() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                List<SerialableEntry<String, String>> selectedList = (List<SerialableEntry<String, String>>) cell.getValue();
                Serializable obj = data.getSerializableExtra("selectedList");
                if (obj != null) {
                    selectedList = (List<SerialableEntry<String, String>>) obj;
                }
                formCell.reload(selectedList);
            }
        });
        activity.startActivityForResult(intent,requestCode);
    }

}
