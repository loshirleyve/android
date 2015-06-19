package com.yun9.wservice.task;

import com.yun9.jupiter.model.FileBean;

/**
 * Created by Leon on 15/6/19.
 */
public class UploadFileBeanWrapper {
    private FileBean fileBean;

    private boolean uploaded;

    private boolean successed;

    public UploadFileBeanWrapper(FileBean fileBean) {
        this.fileBean = fileBean;
    }

    public FileBean getFileBean() {
        return fileBean;
    }

    public void setFileBean(FileBean fileBean) {
        this.fileBean = fileBean;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public boolean isSuccessed() {
        return successed;
    }

    public void setSuccessed(boolean successed) {
        this.successed = successed;
    }
}
