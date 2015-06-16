package com.yun9.wservice.view.doc;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.LocalFileBean;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/16.
 */
public class LocalFileCommand extends JupiterCommand {
    public static final String COMPLETE_TYPE_SENDMSGCARD = "sendmsgcard";
    public static final String COMPLETE_TYPE_CALLBACK = "callback";

    public static final String PARAM_FILE = "files";

    private String completeType = COMPLETE_TYPE_SENDMSGCARD;

    private List<LocalFileBean> selectFiles;

    private boolean edit;

    private int maxSelectNum = 0;

    public boolean isEdit() {
        return edit;
    }

    public LocalFileCommand setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }

    public int getMaxSelectNum() {
        return maxSelectNum;
    }

    public LocalFileCommand setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
        return this;
    }

    public String getCompleteType() {
        return completeType;
    }

    public LocalFileCommand setCompleteType(String completeType) {
        this.completeType = completeType;
        return this;
    }

    public List<LocalFileBean> getSelectFiles() {
        return selectFiles;
    }

    public LocalFileCommand setSelectFiles(List<LocalFileBean> selectFiles) {
        this.selectFiles = selectFiles;
        return this;
    }

    public void putSelectFile(LocalFileBean localFileBean){
        if (!AssertValue.isNotNull(selectFiles)){
            this.selectFiles = new ArrayList<>();
        }

        this.selectFiles.add(localFileBean);
    }


}
