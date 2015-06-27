package com.yun9.wservice.view.myself;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.FileBean;

import java.util.List;

/**
 * Created by li on 2015/6/26.
 */
public class UserInfoCommand extends JupiterCommand {
    private boolean edit;

    private String instid;

    private String userid;

    private List<FileBean> onSelectImages;

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<FileBean> getOnSelectImages() {
        return onSelectImages;
    }

    public void setOnSelectImages(List<FileBean> onSelectImages) {
        this.onSelectImages = onSelectImages;
    }
}
