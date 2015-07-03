package com.yun9.wservice.view.doc;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/16.
 */
public class YunFileInfoCommand extends JupiterCommand {

    private FileBean fileBean;

    private String userid;

    private String instid;


    public FileBean getFileBean() {
        return fileBean;
    }

    public YunFileInfoCommand setFileBean(FileBean fileBean) {
        this.fileBean = fileBean;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public YunFileInfoCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public YunFileInfoCommand setInstid(String instid) {
        this.instid = instid;
        return this;
    }




}
