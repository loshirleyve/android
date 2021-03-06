package com.yun9.wservice.view.myself;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.FileBean;

import java.util.List;

/**
 * Created by li on 2015/6/26.
 */
public class UserInfoCommand extends JupiterCommand {
    public static final String PARAM_USER_INFO_COMMAND = "userinfocommand";
    private String instid;
    private String userid;
    private List<FileBean> onSelectImages;
    public String getInstid() {
        return instid;
    }

    public UserInfoCommand setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public UserInfoCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public List<FileBean> getOnSelectImages() {
        return onSelectImages;
    }

    public UserInfoCommand setOnSelectImages(List<FileBean> onSelectImages) {
        this.onSelectImages = onSelectImages;
        return this;
    }
}
