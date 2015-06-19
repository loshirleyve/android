package com.yun9.wservice.view.doc;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/16.
 */
public class YunFileCommand extends JupiterCommand {
    public static final String COMPLETE_TYPE_SENDMSGCARD = "sendmsgcard";
    public static final String COMPLETE_TYPE_CALLBACK = "callback";

    public static final String PARAM_FILE = "files";

    private String completeType = COMPLETE_TYPE_SENDMSGCARD;

    private List<FileBean> selectFiles;

    private boolean edit;

    private int maxSelectNum = 0;

    private String userid;

    private String instid;

    public boolean isEdit() {
        return edit;
    }

    public YunFileCommand setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }

    public int getMaxSelectNum() {
        return maxSelectNum;
    }

    public YunFileCommand setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
        return this;
    }

    public String getCompleteType() {
        return completeType;
    }

    public YunFileCommand setCompleteType(String completeType) {
        this.completeType = completeType;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public YunFileCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public YunFileCommand setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public List<FileBean> getSelectFiles() {
        return selectFiles;
    }

    public YunFileCommand setSelectFiles(List<FileBean> selectFiles) {
        this.selectFiles = selectFiles;
        return this;
    }

    public void putSelectFile(FileBean fileBean){
        if (!AssertValue.isNotNull(selectFiles)){
            this.selectFiles = new ArrayList<>();
        }

        this.selectFiles.add(fileBean);
    }


}
